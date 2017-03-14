package Util;

import Util.Parser;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class Internacionalizacao {

    private final int DEFAULT_VALUE = 1;
    private final String DEFAULT_DESCRICAO = "PT-BR";

    public Internacionalizacao(){
        checkDefaultXMLExists();
    }

    public ResourceBundle getResourceBundle(){
        int id = Parser.parse(readFromXMLFile("idioma", "id"), 0);
        switch(id){
            case 1:
                return ResourceBundle.getBundle("ProjetoIntegrado", new Locale("pt", "BR"));
            case 2:
                return ResourceBundle.getBundle("ProjetoIntegrado", new Locale("en", "US"));
            case 3:
                return ResourceBundle.getBundle("ProjetoIntegrado", new Locale("es", "ES"));
            default:
                return null;
        }
    }

    private void checkDefaultXMLExists() {
        if (!new File("idioma.xml").exists()) {
            createDefaultXMLFile();
        }
    }

    private void createDefaultXMLFile(){
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //ROOT element
            Element rootElement = doc.createElement("idiomas");
            doc.appendChild(rootElement);

            //IDIOMA
            Element idioma = doc.createElement("idioma");
            rootElement.appendChild(idioma);

            //ID
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode("1"));
            idioma.appendChild(id);

            //DESCRICAO
            Element descricao = doc.createElement("descricao");
            descricao.appendChild(doc.createTextNode("PT-BR"));
            idioma.appendChild(descricao);

            //CRIAR ARQUIVO COM OS VALORES DEFAULT (CASO NAO EXISTA)
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("idioma.xml"));
            transformer.transform(source, result);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void createXMLFile(String id, String descricao){
        try {
            File file = new File("idioma.xml");
            if(file.exists()){
                file.delete();
            }

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //ROOT element
            Element rootElement = doc.createElement("idiomas");
            doc.appendChild(rootElement);

            //IDIOMA
            Element eIdioma = doc.createElement("idioma");
            rootElement.appendChild(eIdioma);

            //ID
            Element eID = doc.createElement("id");
            eID.appendChild(doc.createTextNode(id));
            eIdioma.appendChild(eID);

            //DESCRICAO
            Element eDescricao = doc.createElement("descricao");
            eDescricao.appendChild(doc.createTextNode(descricao));
            eIdioma.appendChild(eDescricao);

            //CRIAR ARQUIVO COM OS VALORES DEFAULT (CASO NAO EXISTA)
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("idioma.xml"));
            transformer.transform(source, result);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private String readFromXMLFile(String tagName, String childTagName){
        try {
            File file = new File("idioma.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nodes = doc.getElementsByTagName(tagName);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);
                return e.getElementsByTagName(childTagName).item(0).getTextContent();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

}
