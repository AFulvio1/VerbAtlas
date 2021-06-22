package it.uniroma1.nlp.verbatlas.kb;

/**
 * Classe che rappresenta la generalizzazione dei vari id possibili
 * 
 * @author anton
 *
 */
public abstract class ResourceId implements Comparable<ResourceId>
{
	/**
	 * Variabile dove viene inserito l'id
	 */
	private int id;
	
	/**
	 * Costruttore dell'oggetto ResourceId 
	 * 
	 * @param id: stringa contenente l'id
	 */
	public ResourceId(String id)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<id.length(); i++)
			if (Character.isDigit(id.charAt(i)))
				sb.append(id.charAt(i));
		this.id = Integer.parseInt(sb.toString());
	}
	
	/**
	 * Getter per l'id
	 * 
	 * @return l'intero che rappresenta l'id
	 */
	public int getID() { return this.id; }

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceId other = (ResourceId) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
