package it.uniroma1.nlp.verbatlas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.uniroma1.nlp.verbatlas.VerbAtlasFrame.Role.Type;
import it.uniroma1.nlp.verbatlas.kb.BabelSynsetId;
import it.uniroma1.nlp.verbatlas.kb.ResourceId;
import it.uniroma1.nlp.verbatlas.kb.SelectionalPreference;
import it.uniroma1.nlp.verbatlas.kb.VerbAtlasFrameId;

/**
 * VerbAtlasFrame è una classe che costruisce i Frame di tipo VerbAtlasFrame 
 * 
 * @author anton
 *
 */
public class VerbAtlasFrame implements Frame, Iterable<BabelSynsetId>
{
	/**
	 * Role è una classe che determina un ruolo e le relative preferenze di selezione per un determinato Frame
	 * 
	 * @author anton
	 *
	 */
	public static class Role
	{
		/**
		 * Type contiene al proprio interno tutti i possibili ruoli che possono essere associati ad un Frame
		 * 
		 * @author anton
		 *
		 */
		public enum Type
		{
			AGENT,ASSET,ATTRIBUTE,BENEFICIARY,CAUSE,COAGENT,COPATIENT,COTHEME,DESTINATION,EXPERIENCER,EXTENT,GOAL,IDIOM,INSTRUMENT,LOCATION,MATERIAL,PATIENT,PRODUCT,PURPOSE,RECIPIENT,RESULT,SOURCE,STIMULUS,THEME,TIME,TOPIC,VALUE;
		}
		
		/**
		 * Variabile in cui viene inserito il tipo di ruolo dell'oggetto Role
		 */
		private Type type;
		
		/**
		 * Variabile in cui vengono inserite le preferenze di selezione per il ruolo 
		 */
		private List<SelectionalPreference> selectionalPreferences;
		
		/**
		 * Costruttore dell'oggetto 'Role'
		 * 
		 * @param type: tipo di ruolo
		 * @param selectionalPreference: singola preferenza di selezione per quel ruolo
		 */
		public Role(Type type, SelectionalPreference selectionalPreference)
		{
			this.type = type;
			this.selectionalPreferences = new ArrayList<SelectionalPreference>();
			this.selectionalPreferences.add(selectionalPreference);
		}
		
		/**
		 * Costruttore dell'oggetto 'Role'
		 * 
		 * @param type: tipo di ruolo
		 * @param selectionalPreferences: le varie preferenze di selezione per quel ruolo
		 */
		public Role(Type type, List<SelectionalPreference> selectionalPreferences)
		{
			this.type = type;
			this.selectionalPreferences = selectionalPreferences;
		}
		
		/**
		 * Getter per il tipo di ruolo
		 * 
		 * @return tipo di ruolo
		 */
		public Type getType() { return this.type; }
		
		/**
		 * Getter per le preferenze di selezione
		 * 
		 * @return preferenze di selezione
		 */
		public List<SelectionalPreference> getSelectionalPreference() { return this.selectionalPreferences; }
	}
	
	/**
	 * Variabile con tutte le strutture dati contenenti le informazioni per creare i singoli Frame
	 */
	private Utility utility;
	
	/**
	 * Variabile che contiene il nome del VerbAtlasFrame
	 */
	private String name;
	
	/**
	 * Variabile che contiene l'id del VerbAtlasFrame
	 */
	private VerbAtlasFrameId id;
	
	/**
	 * Variabile che contiene tutti i ruoli del VerbAtlasFrame
	 */
	private List<Role> roles = new ArrayList<VerbAtlasFrame.Role>();
	
	/**
	 * Variabile che contiene tutti i BabelSynsetID inerenti al VerbAtlasFrame
	 */
	private List<BabelSynsetId> idsB = new ArrayList<BabelSynsetId>();	
	
	/**
	 * Costruttore dell'oggetto 'VerbAtlasFrame'
	 * 
	 * @param name: nome del Frame
	 * @param id: id del Frame 
	 */
	public VerbAtlasFrame(String name, VerbAtlasFrameId id)
	{
		this.utility = Utility.getInstance();
		this.name = name;
		this.id = id;
		
		if (id.getID() != 632)
		{
			List<String> roles = utility.getVaRoles().get(id);
			for (String role : roles)
			{
				Map<String, String> spRole = utility.getVaSelectionalPreference().get(id);
				if (!spRole.keySet().contains(role))
					this.roles.add(new Role(Type.valueOf(role.replace("-", "").toUpperCase()), new SelectionalPreference("va:0037p", new BabelSynsetId("bn:00031027n"), "entity")));
				else
				{
					String selectionalPreference = spRole.get(role);
					if (selectionalPreference.contains("|"))
					{
						List<String> selectionalPreferences = Arrays.asList(selectionalPreference.split("\\|"));
						List<SelectionalPreference> finale = new ArrayList<SelectionalPreference>();
						for (String string: selectionalPreferences)
						{
							List<String> sp = utility.getSelectionalPreference().get(string);
							finale.add(new SelectionalPreference(string, new BabelSynsetId(sp.get(0)), sp.get(1)));
						}
						this.roles.add(new Role(Type.valueOf(role.replace("-", "").toUpperCase()), finale));
					}
					else
					{
						List<String> sp = utility.getSelectionalPreference().get(selectionalPreference);
						this.roles.add(new Role(Type.valueOf(role.replace("-", "").toUpperCase()), new SelectionalPreference(selectionalPreference, new BabelSynsetId(sp.get(0)), sp.get(1))));
					}
				}
			}
			for (Entry<BabelSynsetId, VerbAtlasFrameId> entry : utility.getBabelToVerb().entrySet())
				if (entry.getValue().equals(this.id))
					this.idsB.add(entry.getKey());
				
		}
	}
	
	
	/**
	 * Getter per i BabelSynsetID
	 * 
	 * @return lista dei BabelSynsetID
	 */
	public List<BabelSynsetId> getBabelIds()
	{
		return this.idsB;
	}	
	
	
	
	
	@Override
	public String getName() { return this.name; }

	@Override
	public ResourceId getId() { return this.id; }

	@Override
	public List<Role> getRoles() { return this.roles; }


	@Override
	public Iterator<BabelSynsetId> iterator()
	{
		return new Iterator<BabelSynsetId>()
		{
			BabelSynsetId babelSynsetID;
			
			@Override
			public boolean hasNext()
			{
				return getBabelIds().indexOf(babelSynsetID) < (getBabelIds().size() - 1);
			}

			@Override
			public BabelSynsetId next()
			{
				return getBabelIds().get(getBabelIds().indexOf(babelSynsetID) + 1);
			}
		};
	}
}
