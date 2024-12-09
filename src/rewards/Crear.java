package rewards;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * La clase Crear proporciona métodos estáticos para la creación de diferentes
 * tipos de recompensas.
 * Se encarga de generar archivos XML para representar recompensas como
 * almacenes, comida, monedas, piscifactorías y tanques.
 */
public class Crear {
    /**
     * Añade un almacén central como recompensa.
     *
     * @param botin Tipo de botín para el almacén.
     */
    public static void addAlmacen(String botin) {
        try {
            int almacenados = 1;

            String rutaArchivo = "recompensas/almacen_" + botin.toLowerCase() + ".xml";
            if (existeArchivo(rutaArchivo)) {
                almacenados = obtenerValor(rutaArchivo);
                borrarArchivo(rutaArchivo);
                almacenados = almacenados + 1;
            }


            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();


            Element rootElement = document.createElement("reward");
            document.appendChild(rootElement);


            rootElement.appendChild(createElement(document, "name", "Almacén central [" + botin + "]"));
            rootElement.appendChild(createElement(document, "origin", "Merluza Experience"));
            rootElement.appendChild(createElement(document, "desc",
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita."));
            rootElement.appendChild(createElement(document, "rarity", "3"));

            Element giveElement = document.createElement("give");
            giveElement.appendChild(createElement(document, "building", "Almacén central", "code", "4"));
            giveElement.appendChild(createElement(document, "part", botin));
            giveElement.appendChild(createElement(document, "total", "ABCD"));
            rootElement.appendChild(giveElement);

            rootElement.appendChild(createElement(document, "quantity", String.valueOf(almacenados)));

            File folder = new File("recompensas");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, "almacen_" + botin.toLowerCase() + ".xml");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Mensaje error con helper");
        }
    }

    /**
     * Da comida como recompensa.
     *
     * @param botin Tipo de botín para la comida.
     */
    public static void darComida(int botin) {
        try {
            int almacenados = 1;
            int cantidad = 0;

            cantidad = (botin == 1) ? 50 : cantidad;
            cantidad = (botin == 2) ? 100 : cantidad;
            cantidad = (botin == 3) ? 250 : cantidad;
            cantidad = (botin == 4) ? 500 : cantidad;
            cantidad = (botin == 5) ? 1000 : cantidad;

            String mostrar = String.valueOf(cantidad);

            String rutaArchivo = "recompensas/comida_" + botin + ".xml";
            if (existeArchivo(rutaArchivo)) {
                almacenados = obtenerValor(rutaArchivo);
                borrarArchivo(rutaArchivo);
                almacenados = almacenados + 1;
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("reward");
            document.appendChild(rootElement);

            rootElement.appendChild(createElement(document, "name", "Comida general I"));
            rootElement.appendChild(createElement(document, "origin", "Merluza Experience"));
            rootElement.appendChild(createElement(document, "desc",
                    cantidad + " unidades de pienso multipropósito para todo tipo de peces."));
            rootElement.appendChild(createElement(document, "rarity", String.valueOf(botin - 1)));

            Element giveElement = document.createElement("give");
            giveElement.appendChild(createElement(document, "food", mostrar, "type", "general"));
            rootElement.appendChild(giveElement);

            rootElement.appendChild(createElement(document, "quantity", String.valueOf(almacenados)));

            File folder = new File("recompensas");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, "comida_" + botin + ".xml");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Mensaje error con helper");

        }
    }

    /**
     * Da monedas como recompensa.
     *
     * @param botin Tipo de botín para las monedas.
     */
    public static void darMonedas(int botin) {
        try {
            int almacenados = 1;
            int cantidad = 0;
            String romano = "";

            cantidad = (botin == 1) ? 100 : cantidad;
            cantidad = (botin == 2) ? 300 : cantidad;
            cantidad = (botin == 3) ? 500 : cantidad;
            cantidad = (botin == 4) ? 750 : cantidad;
            cantidad = (botin == 5) ? 1000 : cantidad;

            romano = (botin == 1) ? "I" : romano;
            romano = (botin == 2) ? "II" : romano;
            romano = (botin == 3) ? "III" : romano;
            romano = (botin == 4) ? "IV" : romano;
            romano = (botin == 5) ? "V" : romano;

            String rutaArchivo = "recompensas/monedas_" + botin + ".xml";
            if (existeArchivo(rutaArchivo)) {
                almacenados = obtenerValor(rutaArchivo);
                borrarArchivo(rutaArchivo);
                almacenados = almacenados + 1;
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("reward");
            document.appendChild(rootElement);

            rootElement.appendChild(createElement(document, "name", "Monedas " + romano));
            rootElement.appendChild(createElement(document, "origin", "Merluza Experience"));
            rootElement.appendChild(createElement(document, "desc", cantidad + " monedas"));
            rootElement.appendChild(createElement(document, "rarity", String.valueOf(botin - 1)));

            Element giveElement = document.createElement("give");
            giveElement.appendChild(createElement(document, "coins", String.valueOf(cantidad)));
            rootElement.appendChild(giveElement);

            rootElement.appendChild(createElement(document, "quantity", String.valueOf(almacenados)));

            File folder = new File("recompensas");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, "monedas_" + botin + ".xml");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); 

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Mensaje error con helper");

        }
    }

    /**
     * Añade una piscifactoría como recompensa.
     *
     * @param botin    Tipo de botín para la piscifactoría.
     * @param tipoPisc Tipo de piscifactoría (río o mar).
     */
    public static void addPisci(String botin, String tipoPisc) {
        try {
            int almacenados = 1;

            String tipo = "";
            int rareza = 0;
            int codigo = 0;

            tipo = (tipoPisc.equals("r")) ? "río" : tipo;
            tipo = (tipoPisc.equals("m")) ? "mar" : tipo;

            rareza = (tipoPisc.equals("r")) ? 3 : rareza;
            rareza = (tipoPisc.equals("m")) ? 4 : rareza;

            codigo = (tipoPisc.equals("r")) ? 1 : codigo;

            String rutaArchivo = "recompensas/pisci_" + tipoPisc + "_" + botin.toLowerCase() + ".xml";
            if (existeArchivo(rutaArchivo)) {
                almacenados = obtenerValor(rutaArchivo);
                borrarArchivo(rutaArchivo);
                almacenados = almacenados + 1;
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("reward");
            document.appendChild(rootElement);

            rootElement.appendChild(createElement(document, "name", "Piscifactoría de mar [" + botin + "]"));
            rootElement.appendChild(createElement(document, "origin", "Merluza Experience"));
            rootElement.appendChild(
                    createElement(document, "desc", "Materiales para la construcción de una piscifactoría de " + tipo
                            + ". Con la parte " + botin + " y B, puedes obtenerla de forma gratuita."));
            rootElement.appendChild(createElement(document, "rarity", String.valueOf(rareza)));

            Element giveElement = document.createElement("give");
            giveElement.appendChild(
                    createElement(document, "building", "Piscifactoría de " + tipo, "code", String.valueOf(codigo)));
            giveElement.appendChild(createElement(document, "part", botin));
            giveElement.appendChild(createElement(document, "total", botin + "B"));
            rootElement.appendChild(giveElement);

            rootElement.appendChild(createElement(document, "quantity", String.valueOf(almacenados)));

            File folder = new File("recompensas");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, "pisci_" + tipoPisc + "_" + botin.toLowerCase() + ".xml");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Mensaje error con helper");
        }
    }

    /**
     * Añade un tanque como recompensa.
     *
     * @param tipo_pisc Tipo de piscifactoría para el tanque (río o mar).
     */
    public static void addTanque(String tipo_pisc) {
        try {
            int almacenados = 1;
            String tipo = "";
            int rareza = 0;
            int codigo = 0;

            tipo = (tipo_pisc.equals("r")) ? "río" : tipo;
            tipo = (tipo_pisc.equals("m")) ? "mar" : tipo;

            rareza = (tipo_pisc.equals("r")) ? 2 : rareza;
            rareza = (tipo_pisc.equals("m")) ? 3 : rareza;

            codigo = (tipo_pisc.equals("r")) ? 2 : codigo;
            codigo = (tipo_pisc.equals("m")) ? 3 : codigo;

            String rutaArchivo = "recompensas/tanque_" + tipo_pisc + ".xml";
            if (existeArchivo(rutaArchivo)) {
                almacenados = obtenerValor(rutaArchivo);
                borrarArchivo(rutaArchivo);
                almacenados = almacenados + 1;
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("reward");
            document.appendChild(rootElement);

            rootElement.appendChild(createElement(document, "name", "Tanque de " + tipo));
            rootElement.appendChild(createElement(document, "origin", "Merluza Experience"));
            rootElement.appendChild(createElement(document, "desc",
                    "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de " + tipo
                            + "."));
            rootElement.appendChild(createElement(document, "rarity", String.valueOf(rareza)));

            Element giveElement = document.createElement("give");
            giveElement.appendChild(
                    createElement(document, "building", "Tanque de " + tipo, "code", String.valueOf(codigo)));
            giveElement.appendChild(createElement(document, "part", "A"));
            giveElement.appendChild(createElement(document, "total", "A"));
            rootElement.appendChild(giveElement);

            rootElement.appendChild(createElement(document, "quantity", String.valueOf(almacenados)));

            File folder = new File("recompensas");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, "tanque_" + tipo_pisc + ".xml");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); 

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Mensaje error con helper");
        }
    }

    /**
     * Crea un elemento XML con el nombre del tag, contenido de texto y atributos
     * dados.
     *
     * @param document    Documento XML.
     * @param tagName     Nombre del tag.
     * @param textContent Contenido de texto del tag.
     * @param attributes  Atributos del tag en pares nombre-valor.
     * @return Elemento XML creado.
     */
    private static Element createElement(Document document, String tagName, String textContent, String... attributes) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);

        for (int i = 0; i < attributes.length; i += 2) {
            element.setAttribute(attributes[i], attributes[i + 1]);
        }

        return element;
    }

    /**
     * Verifica si un archivo existe en la ruta especificada.
     *
     * @param rutaArchivo Ruta del archivo.
     * @return `true` si el archivo existe, `false` en caso contrario.
     */
    private static boolean existeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        return archivo.exists();
    }

    /**
     * Borra un archivo en la ruta especificada.
     *
     * @param rutaArchivo Ruta del archivo a borrar.
     */
    private static void borrarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        archivo.delete();
    }

    /**
     * Obtiene el valor de la etiqueta "quantity" de un archivo XML.
     *
     * @param rutaArchivo Ruta del archivo XML.
     * @return Valor de "quantity" o 1 si hay un error.
     */
    public static int obtenerValor(String rutaArchivo) {
        try {
            File archivoXML = new File(rutaArchivo);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);

            // Obtener el valor de quantity
            NodeList nodeList = doc.getElementsByTagName("quantity");
            if (nodeList.getLength() > 0) {
                Element quantityElement = (Element) nodeList.item(0);
                String quantityValue = quantityElement.getTextContent();
                return Integer.parseInt(quantityValue);
            }
        } catch (Exception e) {
            System.out.println("Mensaje error con helper");
        }
        return 1;
    }
}