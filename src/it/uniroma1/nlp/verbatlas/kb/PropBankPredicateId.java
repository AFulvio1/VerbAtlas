package it.uniroma1.nlp.verbatlas.kb;

/**
 * Classe che rappresenta un PropBankPredicateId
 * 
 * @author anton
 *
 */
public class PropBankPredicateId extends ResourceId
{	
	/**
	 * Costruttore dell'oggetto PropBankPredicateId
	 * 
	 * @param idP: stringa contenente il valore dell'id
	 */
	public PropBankPredicateId(String idP) 
	{ 
		super(idP); 
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
