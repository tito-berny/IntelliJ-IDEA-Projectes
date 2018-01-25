package com.company;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //-------------------Variables-------------------------

    static Scanner scn = new Scanner(System.in);

    static File file = new File("./prueba.txt");

    public static void main(String[] args) throws Exception {
	// write your code here

        menu();


    }

    private static void menu() throws Exception {

        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        //----------------MENU---------------

        while(!salir){

            System.out.println("1. Insereix nous parametres");
            System.out.println("2. Generar XML");
            System.out.println("3. Opcion 3");
            System.out.println("4. Salir");

            try {

                System.out.println("Escribe una de las opciones");
                opcion = scn.nextInt();

                switch (opcion) {

                    case 1:
                        System.out.println("Has seleccionat Insereix nous parametres");

                        String[] lista = pedirParametros();

                        FileWriter fichero = null;
                        PrintWriter pw;
                        try
                        {
                            fichero = new FileWriter("./prueba.txt",true);
                            pw = new PrintWriter(fichero);

                            for (String aLista : lista) pw.print(aLista);

                                //Agregamos el salto de linea
                                pw.println("");

                                //Informamos al usuario que el archivo se a guardado
                                System.out.println("Arxiu dreçat ;");
                                System.out.println("");

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Problema al emmagatzenar l'arxui");
                        } finally {
                            try {
                                // Nuevamente aprovechamos el finally para
                                // asegurarnos que se cierra el fichero.
                                if (null != fichero)
                                    fichero.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }

                        break;

                    case 2:

                        System.out.println("Has seleccionat Generar XML");

                        ArrayList<String> arrayList = new ArrayList<String>();

                        arrayList = leerFichero();

                        generarXml(arrayList);

                        break;
                    case 3:
                        System.out.println("Has seleccionado la opcion 3");
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println();
                        System.out.println("Solo números entre 1 y 4");
                        System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Debes insertar un número");
                scn.next();
                System.out.println();
            }
        }

    }

    /**
     * Leemos el fichero linea a linea
     * @return Un ArrayList con una linea del fichero en cada posicion.
     */
    private static ArrayList leerFichero() {

        ArrayList<String> lineas = new ArrayList<>();

        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null)
                lineas.add(linea);

        }
        catch(Exception e){
            System.out.println("");
            System.out.println("L'arxiu no s'ha trobat. " +
                     " Introdueix nous parametres.");
            System.out.println("");
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

        return lineas;
    }

    private static void generarXml(ArrayList<String> key) throws Exception {

        if(key.isEmpty()){
            System.out.println("ERROR la ArrayList es buida.");
            return;
        }else {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   // pag 77
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "DGT", null);
            document.setXmlVersion("1.0");

            Element raiz = document.getDocumentElement();

            for (String aLista : key) {

                String[] string = aLista.split("|");

                Element ABM = document.createElement("ABM");
                Text nodeABMValue = document.createTextNode((String) string[0]);
                ABM.appendChild(nodeABMValue);

                ABM.setAttribute("tipus",string[1] );

                Element DATA = document.createElement("DATA");
                Text nodeDATAValue = document.createTextNode((String) string[2]);
                DATA.appendChild(nodeDATAValue);

                Element MATRICULA = document.createElement("MATRICULA");
                Text nodeMATRICULAValue = document.createTextNode((String) string[3]);
                MATRICULA.appendChild(nodeMATRICULAValue);

                Element N_BASTIDOR = document.createElement("N_BASTIDOR");
                Text nodeN_BASTIDORValue = document.createTextNode((String) string[4]);
                N_BASTIDOR.appendChild(nodeN_BASTIDORValue);

                Element N_MOTOR = document.createElement("N_MOTOR");
                Text nodeN_MOTORValue = document.createTextNode((String) string[5]);
                N_MOTOR.appendChild(nodeN_MOTORValue);

                raiz.appendChild(ABM);
                raiz.appendChild(DATA);
                raiz.appendChild(MATRICULA);
                raiz.appendChild(N_BASTIDOR);
            }

            //Generate XML
            Source source = new DOMSource(document);     // pag 77
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File("Arxui.xml"));  // pag 77  //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);   // pag 77
/*
            //Main Node
            Element raiz = document.getDocumentElement();             // pag 81, 83
            //Por cada key creamos un item que contendr� la key y el value
            for(int i=0; i<key.size();i++){

                //Item Node
                Element itemNode = document.createElement("DNI");   // pag 81, 83

                //Key Node
                Element keyNode = document.createElement("GOGNOMS_NOM");

                // keyNode.setAttribute("id",String.valueOf(i) );    // pag 81

                Text nodeKeyValue = document.createTextNode((String) key.get(i));

                keyNode.appendChild(nodeKeyValue);                        //pag 81, 83

                //Value Node
                Element valueNode = document.createElement("VALUE");

                Text nodeValueValue = document.createTextNode(value.get(i));
                valueNode.appendChild(nodeValueValue);

                //append keyNode and valueNode to itemNode
                itemNode.appendChild(keyNode);
                itemNode.appendChild(valueNode);
                //append itemNode to raiz
                raiz.appendChild(itemNode); //pegamos el elemento a la raiz "Documento"
            }
            //Generate XML
            Source source = new DOMSource(document);     // pag 77
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File("Arxui.xml"));  // pag 77  //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);   // pag 77
        }
*/
        }

    }

    private static String[] pedirParametros() {

        //Variables

        String ABM, TIPUS,DATA, MATRICULA, N_BASTIDO ,N_MOTOR ,DNI, COGNOM_NOM,ADRECA;

        String[] lista = new String[9];

        //----------------Solicitar---------------


        System.out.println(" Dona'm A(alta), B(baixa) o M(Modificacio)");
        ABM = scn.next();
        lista[0] = ABM+"|";

        System.out.println(" Dona 'm el Tipus\n" +

                            "AL Alta nou vehicle\n" +
                "\n" +
                "            CD canvi domicili\n" +
                "\n" +
                "            CP canvi propietari\n" +
                "\n" +
                "            BD Baixa definitiva - desvallastament\n" +
                "\n" +
                "            BT  Baixa temporal");
        TIPUS = scn.next();
        lista[1] = TIPUS+"|";

        System.out.println(" Dona'm la Data (DD/MM/AAAA)");
        DATA = scn.next();
        lista[2] = DATA+"|";

        System.out.println(" Dona'm la Matricula");
        MATRICULA = scn.next();
        lista[3] = MATRICULA+"|";

        System.out.println(" Dona'm el Numero de Bastidor");
        N_BASTIDO = scn.next();
        lista[4] = N_BASTIDO+"|";

        System.out.println(" Dona'm el Numero de Motor");
        N_MOTOR = scn.next();
        lista[5] = N_MOTOR+"|";

        System.out.println(" Dona'm el DNI");
        DNI = scn.next();
        lista[6] = DNI+"|";

        System.out.println(" Dona'm el Cognom");
        COGNOM_NOM = scn.next();
        lista[7] = COGNOM_NOM+"|";

        System.out.println(" Dona'm l'Adreça");
        ADRECA = scn.next();
        lista[8] = ADRECA;

        System.out.println("Contigut final : ");
        System.out.println("");

        Arrays.stream(lista, 0, lista.length).forEach(System.out::print);
        System.out.println("");

        return lista;

    }


}
