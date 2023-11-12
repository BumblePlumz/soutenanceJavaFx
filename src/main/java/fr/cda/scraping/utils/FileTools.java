package fr.cda.scraping.utils;

import java.io.*;
import java.util.Collection;

public class FileTools {
    // TODO : gestion des fichiers textes
    private final String DELIMITER = ";";
    private final String SEPARATOR = "\n";
    private String basePath = "fr/cda/scraping/files/";
    private String filePath; // monFichier.extension
    private FILEHEADER header; // "Nom,Prenom,Email"

    public FileTools(String filePath, FILEHEADER header) {
        this.filePath = filePath;
        this.header = header;
    }

    public void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(basePath+filePath));
        String ligne = null;
        String[] data = new String[3];
        while ((ligne = br.readLine()) != null) {
            //retourne loa ligne du fichier
            // et stocke chaque occurence délimité par un ,
            // dans une case du tableau
            data = ligne.split(",");

            for (String val : data) {
                System.out.print(val +" ");
            }
            System.out.println();
        }
    }

    public <E> void writeFile(Collection<E> data) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(basePath+filePath));
            for (E s : data) {
                bufferedWriter.write(s+DELIMITER);
            }
            bufferedWriter.close();
            System.out.println("Données écrites avec succès dans le fichier.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
