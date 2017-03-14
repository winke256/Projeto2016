package Enum;

public class Database {

    public enum Databases{
        ProjetoIntegrado,
        Outros
    }

    public static String getDatabaseName(Databases databases){
        switch(databases){
            case ProjetoIntegrado:
                return "ProjetoIntegrado";
            default:
                return "";
        }
    }

}