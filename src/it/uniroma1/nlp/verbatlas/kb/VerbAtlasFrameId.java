package it.uniroma1.nlp.verbatlas.kb;

/**
 * Classe che rappresenta un VerbAtlasFrameId
 * 
 * @author anton
 *
 */
public class VerbAtlasFrameId extends ResourceId
{
	/**
	 * Costruttore dell'oggetto VerbAtlasFrameId (tramite il super-costruttore)
	 * 
	 * @param id: stringa contenente il valore dell'id
	 */
	public VerbAtlasFrameId(String id)
	{
		super(id);
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
