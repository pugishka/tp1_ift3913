package tp1_ift3913;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Partie 1
 * 
 * @author Louis Bertrand ()
 * @author Nadia Charonov (20121063)
 */


public class CalculMetriques {
	
	/**
	 * true pour prendre en compte les paquets à l'interieur d'une direction donnee
	 * false pour seulement prendre en compte les classes
	 */
	
	public static boolean recursive = false;
	
	/**
	 * Compte le nombre de lignes de code dans un fichier .java en incluant les commentaires.
	 * 
	 * @param dir direction du fichier
	 * @return nombre de lignes de code de la classe
	 */
	
	public static int classe_LOC (String dir) {
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
						
						boolean sameLine = true;
						
						// commence plus tard sur la ligne
						if (!data.substring(0, 2).matches("/\\*") && !data.substring(0, 3).matches("/\\*\\*")) {
							if (!ignore) {
								lignes++;
							}
						}
						// tant qu'on a pas la fin du commentaire

						// commentaire imbriqué
						int commentEnd = 1;
						if (data.contains("/*")) {
							data = data.split("/\\*", 2)[1];
						} else {
							data = data.split("/\\*\\*", 2)[1];
						}
						
						while(commentEnd > 0) {
							if(data.contains("*/")) {
								commentEnd--;
								if(commentEnd == 0) {
									break;
								}
							}
							if(data.contains("/*") || data.contains("/**")) {
								commentEnd++;
							}
							data = myReader.nextLine();
							data = data.replaceAll("\s", "");
							sameLine = false;
						}
						
						String parts[] = data.split("\\*/", 2);
						
						// si jamais il y a un 2eme commentaire sur la meme ligne
						if (parts[1].length() != 0) {
							if (!sameLine) {
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
			e.printStackTrace();
		}
		return lignes;
	}

	/**
	 * Compte le nombre de lignes de code des fichiers .java dans un paquet en incluant les commentaires.
	 * 
	 * @param dir direction du paquet
	 * @return nombre de lignes de code de ses classes
	 */
	
	public static int paquet_LOC (String dir) {
		return paquet(dir, "LOC");
	}
	
	/**
	 * Compte le nombre de lignes de code dans un fichier .java en excluant les commentaires.
	 * 
	 * @param dir direction du fichier
	 * @return nombre de lignes de code de la classe
	 */
	
	public static int classe_CLOC (String dir) {
		int lignes = 0;
		try {
			File file = new File(dir);
			Scanner myReader = new Scanner(file);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				data = data.replaceAll("\s", "");
				data = data.replaceAll("\n", "");
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
	
	/**
	 * Compte le nombre de lignes de code des fichiers .java dans un paquet en excluant les commentaires.
	 * 
	 * @param dir direction du paquet
	 * @return nombre de lignes de code de ses classes
	 */
	
	public static int paquet_CLOC (String dir) {
		return paquet(dir, "CLOC");
	}
	
	/**
	 * Methode commune pour compter les lignes de code des classes d'un paquet.
	 * 
	 * @param dir direction du paquet
	 * @param option "LOC" pour inclure les commentaires, "CLOC" pour les exclure
	 * @return nombre de lignes de code de ses classes
	 */
	
	public static int paquet (String dir, String option) {
		int lignes = 0;
		try {
			File directory = new File(dir);
			directory.createNewFile();
			
			if(recursive) {
				// package
				if (directory.isDirectory()) {
					File filesList[] = directory.listFiles();
					for(File directory2 : filesList) {
						if(option.equals("LOC")) {
							lignes += paquet(directory2.getAbsolutePath(), option);
						} else {
							lignes += paquet(directory2.getAbsolutePath(), option);
						} 
					}
					//System.out.println("----- " + directory.getName() + " : " + lignes + "\n");
					
				// file
				} else {
					String name = directory.getName();
					if(name.substring(name.length()-5, name.length()).equals(".java")) {
						if(option.equals("LOC")) {
							lignes = classe_LOC(dir);
						} else {
							lignes = classe_CLOC(dir);
						}
						//System.out.println(name + " : " + lignes);
					}
				}
			} else {
				File filesList[] = directory.listFiles();
				for(File directory2 : filesList) {
					String name = directory2.getName();
					if(name.length() > 5 && name.substring(name.length()-5, name.length()).equals(".java")) {
						if(option.equals("LOC")) {
							lignes += classe_LOC(directory2.getAbsolutePath());
						} else {
							lignes += classe_CLOC(directory2.getAbsolutePath());
						}
						//System.out.println(name + " : " + lignes);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return lignes;
	}

	/**
	 * Densité de commentaires d'une classe.
	 * 
	 * @param dir direction du fichier
	 * @return densité de commentaires de la classe
	 */
	
	public static double classe_DC (String dir) {
		return (double)classe_CLOC (dir) / (double)classe_LOC (dir);
	}

	/**
	 * Densité de commentaires d'un paquet.
	 * 
	 * @param dir direction du paquet
	 * @return densité de commentaires du paquet
	 */
	
	public static double paquet_DC (String dir) {
		return (double)paquet_CLOC (dir) / (double)paquet_LOC (dir);
	}
	
	public static void main(String[] args) throws IOException {
		//String dir = "../jfreechart-master/src/main/java/org/jfree/chart";
		//System.out.println(paquet_DC(dir));
	}
}