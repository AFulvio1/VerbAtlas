package it.uniroma1.nlp.verbatlas.kb;

/**
 * Classe che rappresenta un WordNetSynsetId
 * 
 * @author anton
 *
 */
public class WordNetSynsetId extends ResourceId
{	
	/**
	 * Costruttore dell'oggetto WordNetSynsetId
	 * 
	 * @param idW: stringa contenente il valore dell'id
	 */
	public WordNetSynsetId(String idW) 
	{ 
		super(idW); 
	}

	@Override
	public int compareTo(ResourceId o)
	{
		if (this.getID() > o.getID())
			return 1;
		else if (this.getID() == o.getID())
			return 0;
		else
			return -1;
	}
}
