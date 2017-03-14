package Util;

import Model.Usuario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;

public class TextFile {

    private String fullPath;
    private List<String> list;

    public TextFile(String fullPath){
        this.fullPath = fullPath;
    }

    public TextFile(String fullPath, boolean deletePreviousFile){
        this.fullPath = fullPath;
        if(deletePreviousFile){
            try{
                new File(fullPath).delete();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public List<String> getFileLines(){
        try {
            return list = Files.readAllLines(Paths.get(this.fullPath), Charset.defaultCharset());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void write(String text, boolean overridePreviousContent){
        if(overridePreviousContent){
            try(Formatter formatter = new Formatter(fullPath)){
                formatter.format("%s", text);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else{
            try(Formatter formatter = new Formatter(new BufferedWriter(new FileWriter(this.fullPath, true)))){
                formatter.format(this.isEmpty() ? String.format("%s", text) : String.format("\n%s", text));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }

    public boolean isEmpty(){
        return getFileLines().isEmpty();
    }

    public List<String> getList(){
        return this.list;
    }

    public String fullPath(){
        return this.fullPath;
    }

    public boolean isFileExists(){
        return new File(fullPath).exists();
    }


}
