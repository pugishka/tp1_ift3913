package tp1_ift3913;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Partie 1
 */


public class CalculMetriques {
	
	/**
	 * true pour prendre en compte les paquets à l'interieur d'une direction donnee
	 * false pour seulement prendre en compte les classes
	 */
	
	public boolean recursive;
	public ArrayList<Classe> allClasse = new ArrayList<Classe>();
	public ArrayList<Paquet> allPaquet = new ArrayList<Paquet>();
	
	public CalculMetriques(boolean recursive) {
		this.recursive = recursive;
		generateClassPaquet();
	}
	
	/**
	 * Compte le nombre de lignes de code dans un fichier .java en incluant les commentaires.
	 * 
	 * @param dir direction du fichier
	 * @return nombre de lignes de code de la classe
	 */
	
	public int classe_LOC (String dir) {
		int lignes = 0;
		try {
			File file = new File(dir);
			Scanner myReader = new Scanner(file);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				data = data.replaceAll("\s", "");
				data = data.replaceAll("\n", "");
				if (!data.equals("")) {
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
	 * Compte le nombre de lignes de code des fichiers .java dans un paquet en incluant les commentaires.
	 * 
	 * @param dir direction du paquet
	 * @return nombre de lignes de code de ses classes
	 */
	
	public int paquet_LOC (String dir) {
		return paquet(dir, "LOC");
	}
	
	/**
	 * Compte le nombre de lignes de code dans un fichier .java en excluant les commentaires.
	 * 
	 * @param dir direction du fichier
	 * @return nombre de lignes de code de la classe
	 */

	public int classe_CLOC (String dir) {
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
				if (!data.equals("")) {
					
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
	 * Compte le nombre de lignes de code des fichiers .java dans un paquet en excluant les commentaires.
	 * 
	 * @param dir direction du paquet
	 * @return nombre de lignes de code de ses classes
	 */
	
	public int paquet_CLOC (String dir) {
		return paquet(dir, "CLOC");
	}
	
	/**
	 * Methode commune pour compter les lignes de code des classes d'un paquet.
	 * 
	 * @param dir direction du paquet
	 * @param option "LOC" pour inclure les commentaires, "CLOC" pour les exclure
	 * @return nombre de lignes de code de ses classes
	 */
	
	public int paquet (String dir, String option) {
		int lignes = 0;
		boolean classe = false;
		String name = "";
		try {
			File directory = new File(dir);
			directory.createNewFile();
			name = directory.getName();
			
			if(recursive) {
				// package
				if (directory.isDirectory()) {
					classe = false;
					File filesList[] = directory.listFiles();
					for(File directory2 : filesList) {
						lignes += paquet(directory2.getAbsolutePath(), option);
					}
					//System.out.println("----- " + name + " : " + lignes + "\n");
					
				// file
				} else {
					if(name.substring(name.length()-5, name.length()).equals(".java")) {
						classe = true;
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
					String name2 = directory2.getName();
					if(name2.length() > 5 && name2.substring(name2.length()-5, name2.length()).equals(".java")) {
						int l = 0;
						if(option.equals("LOC")) {
							l = classe_LOC(directory2.getAbsolutePath());
						} else {
							l = classe_CLOC(directory2.getAbsolutePath());
						}
						addClassePaquet(true, directory2.getAbsolutePath(), name2, String.valueOf(l), option);
						lignes += l;
						//System.out.println(name + " : " + lignes);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		addClassePaquet(classe, dir, name, String.valueOf(lignes), option);
		
		return lignes;
	}
	
	/**
	 * Cree les objets Classe et Paquet et les ajoute aux listes.
	 * Trouve l'instance si deja existant et la modifie.
	 * 
	 * @param classe si c'est une classe ou un paquet
	 * @param dir direction
	 * @param name nom
	 * @param valeur le nombre de lignes ou la densite
	 * @param option "LOC" si commentaires inclus dans les lignes comptees, "CLOC" si exclus, "DC" pour la densite
	 */
	
	public void addClassePaquet(boolean classe, String dir, String name, String valeur, String option) {
		if(classe) {
			Classe c = null;
			for(Classe i : allClasse){
		        if(i.getDir() != null && i.getDir().equals(dir)){
		        	c = i;
		        	break;
		        }
		    }
			if (c == null) {
				c = new Classe(dir, name);
				if(option.equals("LOC")) {
					c.setLoc(valeur);
				} else if(option.equals("CLOC")) {
					c.setCloc(valeur);
				} else {
					c.setDc(valeur);
				}
				allClasse.add(c);
			} else {
				if(option.equals("LOC")) {
					c.setLoc(valeur);
				} else if(option.equals("CLOC")){
					c.setCloc(valeur);
				} else {
					c.setDc(valeur);
				}
			}
		} else {
			Paquet p = null;
			for(Paquet i : allPaquet){
		        if(i.getDir() != null && i.getDir().equals(dir)){
		        	p = i;
		        	break;
		        }
		    }
			if (p == null) {
				p = new Paquet(dir, name);
				if(option.equals("LOC")) {
					p.setLoc(valeur);
				} else if(option.equals("CLOC")){
					p.setCloc(valeur);
				} else {
					p.setDc(valeur);
				}
				allPaquet.add(p);
			} else {
				if(option.equals("LOC")) {
					p.setLoc(valeur);
				} else if(option.equals("CLOC")){
					p.setCloc(valeur);
				} else {
					p.setDc(valeur);
				}
			}
		}
	}

	/**
	 * Densite de commentaires d'une classe.
	 * 
	 * @param dir direction du fichier
	 * @return densité de commentaires de la classe
	 */
	
	public double classe_DC (String dir) {
		return (double)classe_CLOC (dir) / (double)classe_LOC (dir);
	}

	/**
	 * Densite de commentaires d'un paquet.
	 * 
	 * @param dir direction du paquet
	 * @return densité de commentaires du paquet
	 */
	
	public double paquet_DC (String dir) {
		return (double)paquet_CLOC (dir) / (double)paquet_LOC (dir);
	}

	/**
	 * Methode commune pour calculer les densites des paquets et classes.
	 * 
	 * @param dir direction du dossier
	 */
	
	public void densite (String dir) {
		double densite = 0.0;
		boolean classe = false;
		String name = "";
		try {
			File directory = new File(dir);
			directory.createNewFile();
			name = directory.getName();
			
			if(recursive) {
				// package
				if (directory.isDirectory()) {
					classe = false;
					densite = paquet_DC(dir);
					File filesList[] = directory.listFiles();
					for(File directory2 : filesList) {
						densite(directory2.getAbsolutePath());
					}
					
				// file
				} else if(name.substring(name.length()-5, name.length()).equals(".java")) {
					classe = true;
					densite = classe_DC(dir);
				}
			} else {
				File filesList[] = directory.listFiles();
				densite = paquet_DC(dir);
				for(File directory2 : filesList) {
					String name2 = directory2.getName();
					if(name2.length() > 5 && name2.substring(name2.length()-5, name2.length()).equals(".java")) {
						double d = classe_DC(directory2.getAbsolutePath());
						addClassePaquet(true, directory2.getAbsolutePath(), name2, String.valueOf(d), "DC");
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		addClassePaquet(classe, dir, name, String.valueOf(densite), "DC");
	}
	
	/**
	 * Genere les instances de Classe et Paquet, avec LOC, CLOC et DC calcules.
	 */
	
	public void generateClassPaquet() {
		System.out.println("Direction du dossier à analyser :");
		Scanner in = new Scanner(System.in);
		String dir = in.nextLine();
		densite(dir);
		in.close();
	}
}