package tp1_ift3913;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CalculMetriques {
	
	public static int classe_LOC(String dir) {
		int lignes = 0;
		try {
			File file = new File(dir);
			Scanner myReader = new Scanner(file);
			Boolean ignore = false;
			String data = "";
			
			while (myReader.hasNextLine() || ignore) {
				if (!ignore) {
					data = myReader.nextLine();
					data = data.replaceAll("\s", "");
					data = data.replaceAll("\n", "");
				}
				if (data != "") {
					
					if(data.length() < 2) {
						if (!ignore) {
							lignes++; 
							ignore = false;
						}
						
					// 1 ligne de commentaire, commence plus tard sur la ligne
					} else if (data.contains("//")) {
						if (!ignore && !data.substring(0, 2).matches("//")) {
							lignes++; 
						}
						
					// commentaire sur plusieurs lignes
					} else if (data.contains("/*") || data.contains("/**")) {
						
						// commence plus tard sur la ligne
						if (!data.substring(0, 2).matches("/\\*") && !data.substring(0, 3).matches("/\\*\\*")) {
							if (!ignore) {
								lignes++;
							}
						}
						// tant qu'on a pas la fin du commentaire
						while(!data.contains("*/") && myReader.hasNextLine()) {
							data = myReader.nextLine();
							data = data.replaceAll("\s", "");
						}
						String parts[] = data.split("\\*/", 2);
						
						// si jamais il y a un 2eme commentaire sur la meme ligne
						if (parts[1].length() != 0) {
							if (!parts[0].contains("/*") && !parts[0].contains("/*s*")) {
								lignes++;
							}
							data = parts[1];
							ignore = true;
						}
					} else {
						if (!ignore) {
							lignes++; 
						}
					}
				}
				ignore = false;
			}
			
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		}
		return lignes;
	}
	
	public static int classe_CLOC(String dir) {
		int lignes = 0;
		try {
			File file = new File(dir);
			Scanner myReader = new Scanner(file);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data != "") {
					lignes++;
				}
			}
			
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		}
		return lignes;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String dir = "../jfreechart-master/src/main/java/org/jfree/chart/ChartColor.java";
		//String dir = "test.txt";
		System.out.println(classe_LOC(dir));
		
	}
}