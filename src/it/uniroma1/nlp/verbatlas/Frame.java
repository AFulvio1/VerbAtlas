package it.uniroma1.nlp.verbatlas;

import java.util.List;

import it.uniroma1.nlp.verbatlas.VerbAtlasFrame.Role;
import it.uniroma1.nlp.verbatlas.kb.ResourceId;

/**
 * Classe che rappresenta la generalizzazione dei vari tipi di Frame possibili (VerbAtlasFrame e VerbAtlasSynsetFrame)
 * 
 * @author anton
 *
 */
public interface Frame
{
	/**
	 * Metodo per fornire il nome del Frame
	 * 
	 * @return nome del Frame
	 */
	String getName();
	
	/**
	 * Metodo per fornire l'input del Frame
	 * 
	 * @return id del Frame
	 */
	ResourceId getId();
	
	/**
	 * Metodo per fornire i ruoli del Frame
	 * 
	 * @return id del Frame
	 */
	List<Role> getRoles();
}
