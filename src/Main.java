import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String filename = "src/ninja_events.xml";
        List<Ninja> ninjas = readXMLFile(filename);

        ninjas.forEach(System.out::println);

        filterNinjasByPoints(ninjas);

    }

    public static List<Ninja> readXMLFile(String filename) {
        List<Ninja> ninjas = new ArrayList<>();
        try {
            // Initialize the XML parser
            File xmlFile = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Normalize the XML structure
            document.getDocumentElement().normalize();

            // Get all "log" elements
            NodeList nodeList = document.getElementsByTagName("log");

            // Iterate through each "log"
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Extract values from the XML
                    int id = Integer.parseInt(element.getElementsByTagName("Id").item(0).getTextContent().trim());
                    String charaktername = element.getElementsByTagName("Charaktername").item(0).getTextContent().trim();
                    String stufe = element.getElementsByTagName("Stufe").item(0).getTextContent().trim();
                    String beschreibung = element.getElementsByTagName("Beschreibung").item(0).getTextContent().trim();
                    String datum = element.getElementsByTagName("Datum").item(0).getTextContent().trim();
                    double kraftpunkte = Double.parseDouble(element.getElementsByTagName("Kraftpunkte").item(0).getTextContent().trim());

                    ninjas.add(new Ninja(id, charaktername, stufe, beschreibung, datum, kraftpunkte));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ninjas;
    }

    public static void filterNinjasByPoints(List<Ninja> ninjas) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a value: ");
        double points = Double.parseDouble(scanner.nextLine());
        ninjas.stream().filter(n -> n.getKraftpunkte() > points).map(Ninja::getCharacterName).distinct().forEach(System.out::println);
    }

}