package it.uniroma1.nlp.verbatlas.kb;

/**
 * Classe che rappresenta un BabelSynsetId
 * 
 * @author anton
 *
 */
public class BabelSynsetId extends ResourceId
{	
	/**
	 * Costruttore dell'oggetto BabelSynsetId
	 * 
	 * @param idB: stringa contenente il valore dell'id
	 */
	public BabelSynsetId(String idB)
	{
		super(idB);
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
