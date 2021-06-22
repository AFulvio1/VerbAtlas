package it.uniroma1.nlp.verbatlas;

import java.time.LocalDate;

/**
 * Classe che rappresenta la versione del progetto e la data di rilascio
 * 
 * @author anton
 *
 */
public class VerbAtlasVersion
{
	/**
	 * Costante che contiene la versione del progetto
	 */
	public final static String VERSIONE = "V1_0_3";
	
	/**
	 * Metodo che ritorna la data di rilascio del progetto
	 * 
	 * @return oggetto LocalDate con la data di rilascio
	 */
	public LocalDate getReleaseDate() { return LocalDate.of(2020, 7, 8); }
}
