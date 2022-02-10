package it.univr.elearning;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListing {
    //Viene specificata la path della directory in cui viene fatto l'upload
    private final String UPLOAD_DIR = "./testUPLOADFILES/";

    public String getUploadDir(){
        return this.UPLOAD_DIR;
    }

    public List<String> getFileStringListing() { //Ritorna il nome del file
        File dir = new File(UPLOAD_DIR); //
        File[] files = dir.listFiles();//Crea un'array con tutti i file in quel percorso

        List<String> filPaths = new ArrayList<>();

        assert files != null;
        for (File file : files) {
            filPaths.add(file.getName());
        }
        return filPaths;
    }

    public File[] getFilePathListing() { //Ritorna il percorso del file
        File dir = new File(UPLOAD_DIR);
        return dir.listFiles();
    }
}
