package rewards;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * La clase Eliminar proporciona métodos estáticos para obtener datos, disminuir
 * recompensas y borrar archivos XML.
 */
public class Eliminar {

    /**
     * Obtiene el valor numérico de la etiqueta "quantity" de un archivo XML.
     *
     * @param ruta Ruta del archivo XML.
     * @return Valor numérico de la etiqueta "quantity" o -1 si hay un error.
     */
    public static int obtenerDatos(String ruta) {
        try {
            File fichero = new File(ruta);
            if (fichero.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fichero);

                doc.getDocumentElement().normalize();

                // Obtener la lista de nodos de "quantity"
                NodeList nodeList = doc.getElementsByTagName("quantity");

                // Asegurarse de que haya al menos un nodo de "quantity"
                if (nodeList.getLength() > 0) {
                    Element quantityElement = (Element) nodeList.item(0);

                    // Obtener el contenido numérico de la etiqueta "quantity"
                    int quantityValue = Integer.parseInt(quantityElement.getTextContent());

                    return quantityValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Disminuye el valor de la etiqueta "quantity" en 1 en un archivo XML y guarda
     * los cambios.
     *
     * @param filePath Ruta del archivo XML.
     */
    public static void dismnuirRecompensas(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // Obtener la lista de nodos de "quantity"
            NodeList nodeList = doc.getElementsByTagName("quantity");

            // Asegurarse de que haya al menos un nodo de "quantity"
            if (nodeList.getLength() > 0) {
                Element quantityElement = (Element) nodeList.item(0);

                // Obtener el valor actual de quantity
                int currentQuantity = Integer.parseInt(quantityElement.getTextContent());

                // Restar 1 al valor de quantity
                int newQuantity = currentQuantity - 1;

                // Actualizar el contenido de quantity en el documento
                quantityElement.setTextContent(String.valueOf(newQuantity));

                // Guardar los cambios en el archivo XML
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Mensaje error con helper");

        }
    }

    /**
     * Borra un archivo XML en la ruta especificada.
     *
     * @param filePath Ruta del archivo XML a borrar.
     * @return `true` si se eliminó correctamente, `false` si no se pudo eliminar o
     *         el archivo no existe.
     */
    public static boolean borrarXML(String filePath) {
        File xmlFile = new File(filePath);

        // Verificar si el archivo existe antes de intentar eliminarlo
        if (xmlFile.exists()) {
            // Intentar eliminar el archivo
            if (xmlFile.delete()) {
                System.out.println("Archivo XML eliminado.");
                return true;
            } else {
                System.out.println("Fallo al eliminar el  XML.");
                return false;
            }
        } else {
            System.out.println("El archivo XML no ha sido encontrado.");
            return false;
        }
    }
}