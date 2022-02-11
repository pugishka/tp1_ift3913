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
    public double class_WMC (String dir) {
        double WMC  = 0;
        double methodCount = -1;
        int nodes = 2;
        int edges = 0;
        int commentIndex;
        boolean longComment = false;


        try {
            File file = new File(dir);
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                //on verifie si la ligne correspond au début d'unce classe
                if ((data.contains("public") || data.contains("private") || data.contains("restricted")) && data.contains("{")) {
                    methodCount++;
                    WMC += edges - nodes + 2;
                    nodes = 2;
                    edges = 1;
                    continue;
                }

                //vérifie si on se trouve dans une section de commentaire sur plusieurs lignes
                if(data.contains("/*")){
                    longComment = true;
                }
                if(data.contains("*/")){
                    longComment = false;
                }
                if(longComment == true){
                    continue;
                }
                //ignore les commentaires sur une seule ligne en enlevant le texte après le début du commentaire
                if(data.contains("//")){
                    commentIndex = data.indexOf("//");
                    data = data.substring(0,commentIndex);
                }


                if(data.contains("else if")){nodes += 1; edges +=1; continue;}
                else if(data.contains(" if")){nodes +=2; edges +=3; continue;}
                else if(data.contains(" else")){nodes += 1; edges +=1; continue;}
                if(data.contains(" while")){nodes += 2; edges += 3; continue;}
                if(data.contains(" for")){nodes += 2; edges += 3; continue;}
                if(data.contains(" switch")){nodes +=1; edges += 1; continue;}
                if(data.contains(" case")){nodes += 1; nodes +=2; continue;}

            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        WMC += edges - nodes + 2;

        if(methodCount == -1){
            return 1;
        }
        //cas si une classe n'a pas de methode for some reason
        if(methodCount == 0){
            methodCount = 1;
        }

        if(WMC == 0){
            WMC = 1;
        }
        return (double)Math.max(WMC/methodCount,1);
    }

    /**
     * Calcule le WCP d'un paquet
     * @param paquet un paquet pour lequel il faut calculer le WCP
     * @param classes un tableau de classe se trouvant dans le paquet
     * @return le WCP pondere des classes se trouvant dans la paquet fourni
     */
    public double paquet_WCP(Paquet paquet, ArrayList<Classe> classes){
        double sumWMC = 0;
        double classCount = 0;
        String paquetDir = paquet.getDir();

        for (int i = 0; i < classes.size(); i++) {

            Classe classe = classes.get(i);

            if (classe.getDir().contains(paquetDir)) {

                sumWMC += Double.valueOf(classe.getWMC());
                classCount++;

            }
        }

        if(classCount == 0){
            return 0;
        }
        return sumWMC;
    }

    /**
     * calcule le WMC d'une classe
     * @param classe la classe dont le WMC doit etre calcule
     */
    public void calculateWMC(Classe classe){

        classe.setWMC(Double.toString(class_WMC(classe.getDir())));

    }


}
