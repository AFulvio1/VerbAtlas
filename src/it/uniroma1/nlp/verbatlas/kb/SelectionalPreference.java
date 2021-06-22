package it.uniroma1.nlp.verbatlas.kb;

/**
 * Classe che rappresenta una preferenza di selezione per un determinato ruolo
 * 
 * @author anton
 *
 */
public class SelectionalPreference
{	
	/**
	 * Variabile contenente il nome della preferenza di selezione
	 */
	private String name;
	
	/**
	 * Variabile contenente l'id della preferenza di selezione
	 */
	private String vaSp;
	
	/**
	 * Variabile contenente il BabelSynsetId della preferenza di selezione
	 */
	private BabelSynsetId idB;
	
	/**
	 * Costruttore dell'oggetto 'SelectionalPreference'
	 * 
	 * @param vaSp: id della preferenza di selezione
	 * @param idB: BabelSynsetId della preferenza di selezione
	 * @param name: nome della preferenza di selezione
	 */
	public SelectionalPreference(String vaSp, BabelSynsetId idB, String name)
	{
		this.vaSp = vaSp;
		this.idB = idB;
		this.name = name;
	}	
	
	/**
	 * Getter per il nome 
	 * 
	 * @return: stringa contenente il nome
	 */
	public String getName()	{ return name; }
	
	/**
	 * Getter per l'id della preferenza di selezione
	 * 
	 * @return: stringa contenente l'id
	 */
	public String getId() {	return vaSp; }
	
	/**
	 * Getter per il BabelSynsetId della preferenza di selezione
	 * 
	 * @return: BabelSynsetId
	 */
	public BabelSynsetId getBabelSynsetID()	{ return idB; }
}
