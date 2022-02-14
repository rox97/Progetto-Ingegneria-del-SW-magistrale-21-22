package it.univr.elearning.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileListing {
    //Viene specificata la path della directory in cui viene fatto l'upload
    private final String UPLOAD_DIR = "./testUPLOADFILES/";

    public String getUploadDir(String userNameFolder){
        return this.UPLOAD_DIR+userNameFolder+"/";
    }

    public void setUploadDir(String userNameFolder){
        try {
            Path path = Paths.get(UPLOAD_DIR+userNameFolder+"/");
            //java.nio.file.Files;
            if(!Files.exists(path)){
                Files.createDirectories(path);
                System.out.println("Directory is created!");
            }else{
                System.out.println("Directory already exist,not created!");
            }
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }


    public File[] getFilePathListing(String userNameFolder) { //Ritorna il percorso del file
        File dir = new File(UPLOAD_DIR+userNameFolder+"/");
        return dir.listFiles();
    }

    public List<String> getFileStringListing(String userNameFolder) { //Ritorna il nome del file
        File dir = new File(UPLOAD_DIR+userNameFolder+"/"); //
        File[] files = dir.listFiles();//Crea un'array con tutti i file in quel percorso

        List<String> filPaths = new ArrayList<>();

        assert files != null;
        for (File file : files) {
            filPaths.add(file.getName());
        }
        return filPaths;
    }

}
