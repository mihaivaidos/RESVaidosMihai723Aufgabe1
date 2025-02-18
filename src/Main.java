import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String filename = "src/ninja_events.xml";
        List<Ninja> ninjas = readXMLFile(filename);

        //ninjas.forEach(System.out::println);

        //filterNinjasByPoints(ninjas);

        //filterStufeJonin(ninjas);

        writeNumberNinjaProStufe(ninjas);

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

    public static void filterStufeJonin(List<Ninja> ninjas) {
        System.out.println("All ninjas with Stufe Jonin: ");
        ninjas.stream().filter(n -> n.getStufe().equals("Jonin")).sorted(Comparator.comparing(Ninja::getDatum).reversed())
                .map(n -> n.getDatum() + ": " + n.getCharacterName() + " - " + n.getBeschreibung()).forEach(System.out::println);
    }

    public static void writeNumberNinjaProStufe(List<Ninja> ninjas) {

        Map<String, Integer> numberNinjas = new HashMap<>();
        Map<String, Double> totalPointsStufe = new HashMap<>();
        for (Ninja n : ninjas) {
            String stufe = n.getStufe();
            numberNinjas.put(stufe, numberNinjas.getOrDefault(stufe, 0) + 1);
            totalPointsStufe.put(stufe, totalPointsStufe.getOrDefault(stufe, 0.0) + n.getKraftpunkte());
        }

        List<Map.Entry<String, Integer>> sortedNinjas = numberNinjas.entrySet().stream().sorted((c1, c2) -> {
            int countCompare = Integer.compare(c2.getValue(), c1.getValue());
            if (countCompare == 0) {
                return c1.getKey().compareTo(c2.getKey());
            }
            return countCompare;
        }).toList();

        List<Map.Entry<String, Double>> sortedPoints = totalPointsStufe.entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).toList();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/gesammtzahl.txt"))) {
            for (Map.Entry<String, Integer> entry : sortedNinjas) {
                if(entry.getKey().equals("Genin")) {
                    bw.write(entry.getKey() + "%" + entry.getValue() + "#" + sortedPoints.stream().filter(c -> c.getKey().equals("Genin")).findFirst().get().getValue());
                    bw.newLine();
                }
                if(entry.getKey().equals("Kage")) {
                    bw.write(entry.getKey() + "%" + entry.getValue() + "#" + sortedPoints.stream().filter(c -> c.getKey().equals("Genin")).findFirst().get().getValue());
                    bw.newLine();
                }
                if(entry.getKey().equals("Chunin")) {
                    bw.write(entry.getKey() + "%" + entry.getValue() + "#" + sortedPoints.stream().filter(c -> c.getKey().equals("Chunin")).findFirst().get().getValue());
                    bw.newLine();
                }
                if(entry.getKey().equals("Jonin")) {
                    bw.write(entry.getKey() + "%" + entry.getValue() + "#" + sortedPoints.stream().filter(c -> c.getKey().equals("Jonin")).findFirst().get().getValue());
                    bw.newLine();
                }
            }
            System.out.println("\nNumber ninjas saved to 'gesammtzahl.txt'.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }

}