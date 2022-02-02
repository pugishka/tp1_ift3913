package tp1_ift3913;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateCVS {

	public static void classes() {
		
		try {
			File csvFile = new File("classes.csv");
			FileWriter writer = new FileWriter(csvFile);
			writer.append("chemin");
			writer.append(",");
			writer.append("class");
			writer.append(",");
			writer.append("classe_LOC");
			writer.append(",");
			writer.append("classe_CLOC");
			writer.append(",");
			writer.append("classe_DC");
			writer.append("\n");
			

			CalculMetriques c = new CalculMetriques(true);
			for (Classe cl : c.allClasse) {
				//TODO : ajouter les attributs de chaque classe cl dans le csv csvFile
				
				//writer.append(String.join(",", listStringData));
				//writer.append("\n");
			}

			writer.flush();
			writer.close();
		      
		      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
	}
	
	
	public static void main(String[] args) {
		
		
	}

}
