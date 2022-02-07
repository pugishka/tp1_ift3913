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

	public static void classes() {
		
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
			writer.append("\n");
			

			CalculMetriques c = new CalculMetriques(true);
			for (Classe cl : c.allClasse) {
				writer.append(String.join(",", cl.toList()));
				writer.append("\n");
			}

			writer.flush();
			writer.close();
			
			System.out.println("Fichier créé.");
		      
		      
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}
	
	
	public static void main(String[] args) {
		
		//GenerateCVS.classes();
		
	}

}
