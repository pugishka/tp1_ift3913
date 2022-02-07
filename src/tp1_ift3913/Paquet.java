package tp1_ift3913;

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
	
	
	
	
}
