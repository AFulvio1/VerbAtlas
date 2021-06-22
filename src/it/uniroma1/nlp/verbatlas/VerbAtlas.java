package it.uniroma1.nlp.verbatlas;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


import it.uniroma1.nlp.verbatlas.kb.BabelSynsetId;
import it.uniroma1.nlp.verbatlas.kb.PropBankPredicateId;
import it.uniroma1.nlp.verbatlas.kb.ResourceId;
import it.uniroma1.nlp.verbatlas.kb.VerbAtlasFrameId;
import it.uniroma1.nlp.verbatlas.kb.WordNetSynsetId;

/**
 * VerbAtlas è una classe che costruisce tutti i Frame verbali indicati dal documento e li salva al proprio interno,
 * per poi poter cercare uno o più Frame desiderati
 * 
 * @author anton
 *
 */
public class VerbAtlas implements Iterable<VerbAtlasFrame>
{
	/**
	 * Variabile in cui viene inserita l'unica istanza di VerbAtlas (Singleton Pattern)
	 */
	private static VerbAtlas instance = null;
	
	/**
	 * Variabile che contiene tutti i frame verbali
	 */
	private List<VerbAtlasFrame> frames = new ArrayList<VerbAtlasFrame>();	
	
	/**
	 * Variabile con tutte le strutture dati contenenti le informazioni per creare i singoli frame
	 */
	private Utility utility;
	
	
	/**
	 * Costruttore dell'oggetto 'VerbAtlas'
	 */
	private VerbAtlas()
	{
		this.utility = Utility.getInstance();
		
		// CREAZIONE DEI SINGOLI FRAME INSERENDO NOME E ID
		List<List<String>> framesIds = utility.getFrameIds();
		for (List<String> riga : framesIds)
			this.frames.add(new VerbAtlasFrame(riga.get(1), new VerbAtlasFrameId(riga.get(0))));
	}
	
	
	/**
	 * Metodo che permette di creare al massimo un istanza di questa classe (Singleton Pattern)
	 * 
	 * @return istanza
	 */
	public static VerbAtlas getInstance()
	{
		if (instance == null)
			instance = new VerbAtlas();
		return instance;
	}
	
	/**
	 * Getter per i frame
	 * 
	 * @return lista dei frame
	 */
	public List<VerbAtlasFrame> getFrames() { return this.frames; }
	
	/**
	 * Metodo per ottenere un determinato frame
	 * 
	 * @param nome: nome del frame desiderato
	 * @return frame desiderato
	 */
	public VerbAtlasFrame getFrame(String nome)
	{
		for (VerbAtlasFrame frame : frames)
			if (frame.getName() == nome)
				return frame;
		return null;
	}
	
	/**
	 * Metodo per ottenere un determinato frame
	 * 
	 * @param id: id del frame desiderato
	 * @return frame desiderato
	 */
	public VerbAtlasFrame getFrame(ResourceId id)
	{
		if (id instanceof VerbAtlasFrameId) 
		{
			for (VerbAtlasFrame frame : frames)
				if (frame.getId().getID() == id.getID())
					return frame;
			return null;
		}
		else 
			if (id instanceof PropBankPredicateId)
				return getFrame(utility.getBabelToVerb().get(id));
		return null;
	}
	
	/**
	 * Metodo che permette di cercare tutti i VerbAtlasFrame che contengono il verbo fornito in input
	 * 
	 * @param verb: verbo da cercare
	 * @return insieme di VerbAtlasFrame che contengono il verbo fornito in input
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public Set<VerbAtlasFrame> getFramesByVerb(String verb) throws IOException, URISyntaxException
	{
		Set<VerbAtlasFrame> insiemeFrames = new HashSet<VerbAtlasFrame>();
		for (Entry<WordNetSynsetId, String> entry : utility.getWordToLemma().entrySet())
			if (entry.getValue().contains(verb) && (entry.getValue().startsWith(verb) || entry.getValue().endsWith(verb)))
				for (BabelSynsetId idB : utility.getBabelToWord().keySet())
					if (entry.getKey().equals(utility.getBabelToWord().get(idB)))
						insiemeFrames.add((VerbAtlasFrame) getFrame(utility.getBabelToVerb().get(idB)));
		return insiemeFrames;
	}
	
	/**
	 * Metodo che restituisce un istanza di VerbAtlasVersion
	 * 
	 * @return istanza di VerbAtlasVersion
	 */
	public VerbAtlasVersion getVersion()
	{
		return new VerbAtlasVersion();
	}

	@Override
	public Iterator<VerbAtlasFrame> iterator()
	{
		return new Iterator<VerbAtlasFrame>()
		{
			VerbAtlasFrame verbAtlasFrame;
			
			@Override
			public boolean hasNext()
			{
				return getFrames().indexOf(verbAtlasFrame) < (getFrames().size() - 1);
			}

			@Override
			public VerbAtlasFrame next()
			{
				return getFrames().get(getFrames().indexOf(verbAtlasFrame) + 1);
			}
		};
	}
}
