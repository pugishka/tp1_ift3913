package tp1_ift3913;

import java.io.File;
import java.io.FileWriter;

/**
 * Partie 2
 *
 */

public class GenerateCVS {

	/**
	 * Genere le fichier .csv contennant les informations des classes.
	 *
	 */

	public static void classes(CalculMetriques c) {
		
		try {
			File csvFile = new File("classes.csv");
			FileWriter writer = new FileWriter(csvFile);
			writer.append("chemin");
			writer.append(",");
			writer.append("classe");
			writer.append(",");
			writer.append("classe_LOC");
			writer.append(",");
			writer.append("classe_CLOC");
			writer.append(",");
			writer.append("classe_DC");
			writer.append(",");
			writer.append("classe_WMC");
			writer.append(",");
			writer.append("classe_BC");
			writer.append("\n");
			

			//CalculMetriques c = new CalculMetriques(true);
			for (Classe cl : c.allClasse) {
				writer.append(String.join(",", cl.toList()));
				writer.append("\n");
			}

			writer.flush();
			writer.close();
			
			System.out.println("Fichier .cvs de classes créé.");
		      
		      
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}

	public static void paquetCSV(CalculMetriques c){

		try {
			File csvFile = new File("paquets.csv");
			FileWriter writer = new FileWriter(csvFile);
			writer.append("chemin");
			writer.append(",");
			writer.append("paquet");
			writer.append(",");
			writer.append("paquet_LOC");
			writer.append(",");
			writer.append("paquet_CLOC");
			writer.append(",");
			writer.append("paquet_DC");
			writer.append(",");
			writer.append("paquet_WCP");
			writer.append(",");
			writer.append("paquet_BC");
			writer.append("\n");


			//CalculMetriques c = new CalculMetriques(true);
			for (Paquet pq : c.allPaquet) {
				writer.append(String.join(",", pq.toList()));
				writer.append("\n");
			}

			writer.flush();
			writer.close();

			System.out.println("Fichier .cvs de paquets créé.");


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) {

		//CalculMetriques cm = new CalculMetriques(true);
		//GenerateCVS.classes(cm);
		//GenerateCVS.paquetCSV(cm);
		
	}

}
