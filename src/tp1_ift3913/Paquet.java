package tp1_ift3913;

import java.util.ArrayList;

/**
 * Classe pour creer un objet qui contiendra les informations du packet analyse.
 *
 */

public class Paquet {
	public String dir;
	public String name;
	public String loc;
	public String cloc;
	public String dc;
	public String WCP;
	public String paquet_BC;
	
	public Paquet(String dir, String name) {
		this.dir = dir;
		this.name = name;
	}

	public String getDir() {
		return dir;
	}

	public String getName() {
		return name;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getCloc() {
		return cloc;
	}

	public void setCloc(String cloc) {
		this.cloc = cloc;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public void setWCP(String WCP) { this.WCP = WCP; }

	public String getWCP() { return this.WCP; }

	public void setPaquet_BC(){

		if(this.WCP == null || this.dc == null){
			System.out.println("An error has occured when calculating BC of paquet:");
			System.out.println(this.dir);
			return;
		}

		int paquetBC = Integer.valueOf(this.dc)/Integer.valueOf(this.WCP);
		this.paquet_BC = String.valueOf(paquetBC);
	}

	public String getPaquet_BC() {return this.paquet_BC;}

	/**
	 * Covertit les paramètres d'un object Paquet en un tableau de Strings
	 * @return un tableau de Strings correspondant aux paramètres du paquet
	 */
	public ArrayList<String> toList(){
		ArrayList<String> liste = new ArrayList<String>();
		liste.add(dir);
		liste.add(name);
		liste.add(loc);
		liste.add(cloc);
		liste.add(dc);
		liste.add(this.WCP);
		liste.add(this.paquet_BC);
		return liste;
	}
	
}
