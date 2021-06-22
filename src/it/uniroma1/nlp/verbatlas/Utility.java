package it.uniroma1.nlp.verbatlas;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.uniroma1.nlp.verbatlas.kb.BabelSynsetId;
import it.uniroma1.nlp.verbatlas.kb.PropBankPredicateId;
import it.uniroma1.nlp.verbatlas.kb.VerbAtlasFrameId;
import it.uniroma1.nlp.verbatlas.kb.WordNetSynsetId;

/**
 * Classe che legge le informazioni dei file nella cartella 'resources' e li inserisce in apposite strutture dati
 * 
 * @author anton
 *
 */
public class Utility
{
	/**
	 * Variabile in cui viene inserita l'unica istanza di Utility (Singleton Pattern)
	 */
	private static Utility instance = null;
	
	/**
	 * Costruttore dell'oggetto 'Utility' che va a leggere i file
	 */
	private Utility()
	{
		setFrameIds();
		setVaRoles();
		setVaSelectionalPreference();
		setSelectionalPreference();
		setBabelToVerb();
		setPropToVerb();
	}
	
	/**
	 * Metodo che permette di creare al massimo un istanza di questa classe (Singleton Pattern)
	 * 
	 * @return istanza
	 */
	public static Utility getInstance()
	{
		if (instance == null)
			instance = new Utility();
		return instance;
	}
	
	
	
	/**
	 * Metodo per creare il Path per ogni file che si vuole leggere
	 * 
	 * @param s: nome del file
	 * @return: path del file desiderato
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static Path getPath(String s) throws URISyntaxException, IOException
	{
		return Paths.get(ClassLoader.getSystemResource("VerbAtlas-1.0.3/"+s).toURI());
	}
	
	
	
	// -----------------------------------------------------------------------------------------------------
	
	
	/**
	 * Variabile in cui viene inserita la lista di liste contenente le informazioni del file 'VA_frame_ids.tsv'
	 */
	private List<List<String>> frameIds;
	
	/**
	 * Setter per la lista di nomi e id dei VerbAtlasFrame
	 */
	public void setFrameIds()
	{
		List<List<String>> lista = new ArrayList<List<String>>();
		try (BufferedReader br = Files.newBufferedReader(getPath("VA_frame_ids.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.contains("va:"))
					continue;
				if (line.startsWith("va:1"))
					break;
				lista.add(Arrays.asList(line.split("\\s+")));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.frameIds = lista;
	}
	
	/**
	 * Getter per la lista di nomi e id dei VerbAtlasFrame
	 * 
	 * @return list
	 */
	public List<List<String>> getFrameIds() { return this.frameIds; }
	
	
	/**
	 * Variabile in cui viene inserita la mappa che assegna ad ogni VerbAtlasFrameId la lista dei ruoli per quel Frame, dal file 'VA_va2pas.tsv'
	 */
	private Map<VerbAtlasFrameId, List<String>> vaRoles;
	
	/**
	 * Setter per la la mappa che assegna ad ogni VerbAtlasFrameId la lista dei ruoli per quel Frame
	 */
	public void setVaRoles()
	{
		Map<VerbAtlasFrameId, List<String>> map = new TreeMap<VerbAtlasFrameId, List<String>>();
		try (BufferedReader br = Files.newBufferedReader(getPath("VA_va2pas.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.contains("va:"))
					continue;
				if (line.startsWith("va:1"))
					continue;
				List<String> split = Arrays.asList(line.split("\\s+"));
				List<String> roles = new ArrayList<String>();
				int lenght = split.size();
				for (int i=1; i<lenght; i++)
					roles.add(split.get(i));
				map.put(new VerbAtlasFrameId(split.get(0)), roles);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.vaRoles = map;
	}
	
	/**
	 * Getter per la la mappa che assegna ad ogni VerbAtlasFrameId la lista dei ruoli per quel Frame
	 * 
	 * @return map
	 */
	public Map<VerbAtlasFrameId, List<String>> getVaRoles() { return this.vaRoles; }

	
	
	/**
	 * Variabile in cui viene inserita la mappa che associa un VerbAtlasFrameId con una mappa che a sua volta associa il ruoli di quel Frame con le relative preferenze di selezione, dal file 'VA_va2sp.tsv'
	 */
	private Map<VerbAtlasFrameId, Map<String, String>> vaSelectionalPreference;
	
	/**
	 * Setter per la mappa che associa un VerbAtlasFrameId con una mappa che a sua volta associa il ruoli di quel Frame con le relative preferenze di selezione
	 */
	public void setVaSelectionalPreference()
	{
		Map<VerbAtlasFrameId, Map<String, String>> map = new TreeMap<VerbAtlasFrameId, Map<String, String>>();
		try (BufferedReader br = Files.newBufferedReader(getPath("VA_va2sp.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.contains("va:"))
					continue;
				List<String> split = Arrays.asList(line.split("\\s+"));
				Map<String, String> preference = new TreeMap<String, String>();
				int lenght = split.size();
				for (int i=1; i<lenght; i+=2)
					preference.put(split.get(i), split.get(i+1));
				map.put(new VerbAtlasFrameId(split.get(0)), preference);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.vaSelectionalPreference = map;
	}
	
	/**
	 * Getter per la mappa che associa un VerbAtlasFrameId con una mappa che a sua volta associa il ruoli di quel Frame con le relative preferenze di selezione
	 * 
	 * @return map
	 */
	public Map<VerbAtlasFrameId, Map<String, String>> getVaSelectionalPreference() { return this.vaSelectionalPreference; }
	
	
	
	/**
	 * Variabile in cui viene inserita la mappa che associa l'id di una preferenza di selezione con il suo BabelSynsetId e il suo nome, dal file 'VA_preference_ids.tsv'
	 */
	private Map<String, List<String>> selectionalPreference;
	
	/**
	 * Setter per la mappa che associa l'id di una preferenza di selezione con il suo BabelSynsetId e il suo nome
	 */
	public void setSelectionalPreference()
	{
		Map<String, List<String>> map = new TreeMap<String, List<String>>();
		try (BufferedReader br = Files.newBufferedReader(Utility.getPath("VA_preference_ids.tsv")))
		{			
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.contains("va:"))
					continue;
				List<String> split = Arrays.asList(line.split("\\s+"));
				map.put(split.get(0), List.of(split.get(1),split.get(2)));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		this.selectionalPreference = map;
	}
	
	/**
	 * Getter per la mappa che associa l'id di una preferenza di selezione con il suo BabelSynsetId e il suo nome
	 * 
	 * @return map
	 */
	public Map<String, List<String>> getSelectionalPreference() { return this.selectionalPreference; }
	
	
	
	// ---------------------------------------------------------------------------------------------------------
	

	/**
	 * Variabile in cui viene inserita la mappa che associa un BabelSynsetId con il corrispondente VerbAtlasFrameId, dal file 'VA_bn2va.tsv'
	 */
	private Map<BabelSynsetId, VerbAtlasFrameId> babelToVerb;
	
	/**
	 * Setter per la mappa che associa un BabelSynsetId con il corrispondente VerbAtlasFrameId
	 */
	public void setBabelToVerb()
	{
		Map<BabelSynsetId, VerbAtlasFrameId> map = new LinkedHashMap<BabelSynsetId, VerbAtlasFrameId>();
		try (BufferedReader br = Files.newBufferedReader(getPath("VA_bn2va.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.startsWith("bn:"))
					continue;
				map.put(new BabelSynsetId(line.split("\\s+")[0]), new VerbAtlasFrameId(line.split("\\s+")[1]));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.babelToVerb = map;
	}
	
	/**
	 * Getter per la mappa che associa un BabelSynsetId con il corrispondente VerbAtlasFrameId
	 * 
	 * @return map
	 */
	public  Map<BabelSynsetId, VerbAtlasFrameId> getBabelToVerb() { return this.babelToVerb; }
	
	
	
	/**
	 * Variabile in cui viene inserita la mappa che associa un PropBankPredicateId con il corrispondente VerbAtlasFrameId, dal file 'pb2va.tsv'
	 */
	private Map<PropBankPredicateId, VerbAtlasFrameId> propToVerb;
	
	/**
	 * Setter per la mappa che associa un PropBankPredicateId con il corrispondente VerbAtlasFrameId
	 */
	public void setPropToVerb()
	{
		Map<PropBankPredicateId, VerbAtlasFrameId> map = new TreeMap<PropBankPredicateId, VerbAtlasFrameId>();
		try (BufferedReader br = Files.newBufferedReader(getPath("pb2va.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.startsWith("ab"))
					continue;
				String temp = line.split("\\s+")[0];
				map.put(new PropBankPredicateId(temp.split(">")[0]), new VerbAtlasFrameId(temp.split(">")[1]));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.propToVerb = map;
	}
	
	/**
	 * Getter la mappa che associa un PropBankPredicateId con il corrispondente VerbAtlasFrameId
	 * 
	 * @return map
	 */
	public Map<PropBankPredicateId, VerbAtlasFrameId> getPropToVerb() { return this.propToVerb; }
	
	
	
	/**
	 * Variabile in cui viene inserita la mappa che associa un BabelSynsetId con il corrispondente WordNetSynsetId, dal file 'bn2wn.tsv'
	 */
	private Map<BabelSynsetId, WordNetSynsetId> babelToWord;
	
	/**
	 * Setter per la mappa che associa un BabelSynsetId con il corrispondente WordNetSynsetId
	 */
	public void setBabelToWord()
	{
		Map<BabelSynsetId, WordNetSynsetId> map = new TreeMap<BabelSynsetId, WordNetSynsetId>();
		try (BufferedReader br = Files.newBufferedReader(getPath("bn2wn.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.contains("va:"))
					continue;
				map.put(new BabelSynsetId(line.split("\\s+")[0]), new WordNetSynsetId(line.split("\\s+")[1]));
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.babelToWord = map;
	}
	
	/**
	 * Getter la mappa che associa un BabelSynsetId con il corrispondente WordNetSynsetId
	 * 
	 * @return map
	 */
	public Map<BabelSynsetId, WordNetSynsetId> getBabelToWord() { return this.babelToWord; }
	
	
	
	/**
	 * Variabile in cui viene inserita la mappa che associa un WordNetSynsetId con il corrispondente lemma, dal file 'wn2lemma.tsv'
	 */
	private Map<WordNetSynsetId, String> wordToLemma;
	
	/**
	 * Setter per la mappa che associa un WordNetSynsetId con il corrispondente lemma
	 */
	public void setWordToLemma()
	{
		Map<WordNetSynsetId, String> map = new TreeMap<WordNetSynsetId, String>();
		try (BufferedReader br = Files.newBufferedReader(getPath("wn2lemma.tsv")))
		{
			while (br.ready())
			{
				String line = br.readLine();
				if (!line.contains("va:"))
					continue;
				map.put(new WordNetSynsetId(line.split("\\s+")[0]), line.split("\\s+")[1]);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.wordToLemma = map;;
	}
	
	/**
	 * Getter per la mappa che associa un WordNetSynsetId con il corrispondente lemma
	 * 
	 * @return map
	 */
	public Map<WordNetSynsetId, String> getWordToLemma() { return this.wordToLemma; }
	
}
