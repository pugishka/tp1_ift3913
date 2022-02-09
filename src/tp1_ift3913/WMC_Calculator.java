package tp1_ift3913;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WMC_Calculator {

    /**
     *
     * @param dir le nom du fichier à analyser
     * @return WMC la somme pondérée des complexités cyclomatiques de McCabe des méthodes de la classe
     */
    public int class_WMC (String dir) {
        int WMC  = 0;
        int methodCount = -1;
        int nodes = 2;
        int edges = 0;


        try {
            File file = new File(dir);
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                //on verifie si la ligne correspond au début d'unce classe
                if (data.contains("public") || data.contains("private") || data.contains("restricted")) {
                    methodCount++;
                    WMC += edges - nodes + 2;
                    nodes = 2;
                    edges = 1;
                    continue;
                }

                //TODO revenir optimiser cette partie de calcul des edges et nodes
                if(data.contains("else if")){nodes += 1; edges +=1; continue;}
                else if(data.contains("if")){nodes +=2; edges +=3; continue;}
                else if(data.contains("else")){nodes += 1; edges +=1; continue;}
                if(data.contains("while")){nodes += 2; edges += 3; continue;}
                if(data.contains("for")){nodes += 2; edges += 3; continue;}
                if(data.contains("switch")){nodes +=1; edges += 1; continue;}
                if(data.contains("case")){nodes += 1; nodes +=2; continue;}

            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        if(methodCount == -1){
            return -1;
        }
        return WMC/methodCount;
    }

    /**
     * Calcule le WCP d'un paquet
     * @param paquet un paquet pour lequel il faut calculer le WCP
     * @param classes un tableau de classe se trouvant dans le paquet
     * @return le WCP pondere des classes se trouvant dans la paquet fourni
     */
    public int paquet_WCP(Paquet paquet, ArrayList<Classe> classes){
        int sumWMC = 0;
        int classCount = 0;
        String paquetDir = paquet.getDir();

        for (int i = 0; i < classes.size(); i++) {

            Classe classe = classes.get(i);

            if (classe.getDir().contains(paquetDir)) {

                sumWMC += Integer.valueOf(classe.getWMC());
                classCount++;

            }
        }

        if(classCount == 0){
            return -1;
        }
        return sumWMC/classCount;
    }

    /**
     * calcule le WMC d'une classe
     * @param classe la classe dont le WMC doit etre calcule
     */
    public void calculateWMC(Classe classe){

        classe.setWMC(Integer.toString(class_WMC(classe.getDir())));

    }


}
