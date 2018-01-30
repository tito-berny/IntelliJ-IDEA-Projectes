package com.company;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //-------------------Variables Glovales-------------------------

    private static Scanner scn = new Scanner(System.in);

    private static File file = new File("./prueba.txt");

    public static void main(String[] args) throws Exception {
	// write your code here

        menu();

    }

    /**
     * Crea el menu de interaccion con el usuario (Insertar parametros, generar XML o salir)
     * @throws Exception Se controla que el usuario solo puedo introducir numeros 1-3
     */
    private static void menu() throws Exception {

        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        //----------------MENU---------------


        while(!salir){

            System.out.println("1. Insereix nous parametres");
            System.out.println("2. Generar XML");
            System.out.println("3. Salir ");


            try {

                System.out.println("Escribe una de las opciones");
                opcion = scn.nextInt();

                switch (opcion) {

                    case 1:
                        System.out.println("Has seleccionat Insereix nous parametres");

                        //Guardamos en un array de Strings los parametros introducidos por el usuario
                        //ya estaran separados por "|"
                        String[] lista = pedirParametros();

                        FileWriter fichero = null;
                        PrintWriter pw;
                        try
                        {
                            //Indicamos el fichero donde se escribiran los datos
                            //Si indicamos true como segundo parametro y el fichero ya existe
                            //copiara la linea seguidamente de los data ya existentes
                            //si no SOBREESCRIBE el archivo con los datos nuevos
                            fichero = new FileWriter("./prueba.txt",true);
                            pw = new PrintWriter(fichero);

                            //Por cada string de la lista lo guarda en una linea en el fichero
                            for (String aLista : lista) pw.print(aLista);

                                //Agregamos el salto de linea al final de la linea almacenada
                                pw.println("");

                                //Informamos al usuario que el archivo se a guardado
                                System.out.println("Arxiu dreçat ;");
                                System.out.println("");

                        } catch (Exception e) {
                            e.printStackTrace();
                            //En caso de error informamos al usuario
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

                        ArrayList<String> arrayList;

                        //Guardamos en el arrayList los datos del fichero leido
                        arrayList = leerFichero();

                        //Lamamos a la funcion que genera el XML y le pasamos arraylist2
                        //con los datos del fichero
                        generarXml(arrayList);

                        //Informamos al usuario que se a generado correctamente el fichero XML
                        System.out.println("Arxui generat correctament.");
                        System.out.println("");

                        break;

                    case 3:
                        salir = true;
                        break;

                        //En el caso de no introducir un numero del 1 al 3
                    //informamos al usuario
                    default:
                        System.out.println();
                        System.out.println("Solo números entre 1 y 3");
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
    private static ArrayList<String> leerFichero() {

        ArrayList<String> lineas = new ArrayList<>();

        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // Lectura del fichero
            // Cada linea leida en el fichero se guarda en una posicion del array
            String linea;
            while((linea=br.readLine())!=null)
                lineas.add(linea);

        }
        //Capturamos la exepcion e informamos al usuario
        catch(Exception e){
            System.out.println("");
            System.out.println("L'arxiu no s'ha trobat. " +
                     " Introdueix nous parametres.");
            System.out.println("");
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si va bien como si salta
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

    /**
     * Genera el XML con todos sus nodos a partir de los datos del fichero
     * @param key ArraList de String obtenida del fichero leido
     * @throws Exception Se controla excepcion si el Array esta vacio
     */
    private static void generarXml(ArrayList<String> key) throws Exception {

        if(key.isEmpty()){
            System.out.println("ERROR la ArrayList es buida.");
        }else {

            //Inicializamos el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   // pag 77
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();

            //Indicamos el nombre de la Raiz
            Document document = implementation.createDocument(null, "DGT", null);
            document.setXmlVersion("1.0");

            Element raiz = document.getDocumentElement();

            //For each para cojer cada String[] del Array
            for (String aLista : key) {

                //Separamos los campos con split (necesitamos poner \\ antes de |
                //por que es un caracter especial
                String[] string;
                string = aLista.split("\\|");

                //Creamos el nodo ABM y le añadimos el texto
                //de el array correspondiente (0)
                Element ABM = document.createElement("ABM");
                Text nodeABMValue = document.createTextNode(string[0]);
                ABM.appendChild(nodeABMValue);

                //Creamos un atributo para ABM llamado TIPUS
                //le insertamos el valor correspondiente de la Array (1)
                ABM.setAttribute("tipus",string[1] );

                //Creamos el nodo DATA y le añadimos el texto
                //de el array correspondiente (2)
                Element DATA = document.createElement("DATA");
                Text nodeDATAValue = document.createTextNode(string[2]);
                DATA.appendChild(nodeDATAValue);

                //Creamos el nodo MATRICULA y le añadimos el texto
                //de el array correspondiente (3)
                Element MATRICULA = document.createElement("MATRICULA");
                Text nodeMATRICULAValue = document.createTextNode(string[3]);
                MATRICULA.appendChild(nodeMATRICULAValue);

                //Creamos el nodo N_BASTIDOR y le añadimos el texto
                //de el array correspondiente (4)
                Element N_BASTIDOR = document.createElement("N_BASTIDOR");
                Text nodeN_BASTIDORValue = document.createTextNode(string[4]);
                N_BASTIDOR.appendChild(nodeN_BASTIDORValue);

                //Creamos el nodo N_MOTOR y le añadimos el texto
                //de el array correspondiente (5)
                Element N_MOTOR = document.createElement("N_MOTOR");
                Text nodeN_MOTORValue = document.createTextNode(string[5]);
                N_MOTOR.appendChild(nodeN_MOTORValue);

                //Creamos el nodo DNI y le añadimos el texto
                //de el array correspondiente (6)
                Element DNI = document.createElement("DNI");
                Text nodeN_DNIValue = document.createTextNode(string[6]);
                DNI.appendChild(nodeN_DNIValue);

                //Creamos el nodo COGNOM_NOM y le añadimos el texto
                //de el array correspondiente (7)
                Element COGNOM_NOM = document.createElement("COGNOM_NOM");
                Text nodeN_COGNOM_NOMValue = document.createTextNode(string[7]);
                COGNOM_NOM.appendChild(nodeN_COGNOM_NOMValue);

                //Creamos el nodo DOMICILI y le añadimos el texto
                //de el array correspondiente (8)
                Element ADRECA = document.createElement("DOMICILI");
                Text nodeN_ADRECAValue = document.createTextNode(string[8]);
                ADRECA.appendChild(nodeN_ADRECAValue);

                //Insertamos en VEHICLE los nodos MATRICULA,N_MOTOR.
                Element VEHICLE = document.createElement("VEHICLE");
                VEHICLE.appendChild(MATRICULA);
                VEHICLE.appendChild(N_BASTIDOR);
                VEHICLE.appendChild(N_MOTOR);

                //Insertamos en TITULAR los nodos DNI,COGNOM_NOM.
                Element TITULAR = document.createElement("TITULAR");
                TITULAR.appendChild(DNI);
                TITULAR.appendChild(COGNOM_NOM);
                TITULAR.appendChild(ADRECA);

                //Insertamos en MOV los nodos ABM, DATA, TITULAR y VEHICLE
                //Apareceran en el documento por este orden
                Element MOV = document.createElement("MOV");
                MOV.appendChild(ABM);
                MOV.appendChild(DATA);
                MOV.appendChild(TITULAR);
                MOV.appendChild(VEHICLE);

                //Insertamos en la raiz el nodo MOV
                raiz.appendChild(MOV);
            }

            //Generate XML
            Source source = new DOMSource(document);

            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File("Arxui.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

        }

    }

    /**
     * Solicitamos al usuario los campos a guardar
     * @return Una lista String[] con los campos separados por |
     */
    private static String[] pedirParametros() {

        //Variables

        String ABM, TIPUS,DATA, MATRICULA, N_BASTIDO ,N_MOTOR ,DNI, COGNOM_NOM,ADRECA;

        String[] lista = new String[9];

        //----------------Solicitar---------------
        //Creamos las preguntas para rellenar los campos y las guadamos
        //en en la lista seguido del separador "|" para poder idenrtificarlas posteriormente

        // ABM
        System.out.println(" Dona'm A(alta), B(baixa) o M(Modificacio)");
        ABM = scn.next();
        lista[0] = ABM+"|";

        // TIPUS
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

        // DATA
        System.out.println(" Dona'm la Data (DD/MM/AAAA)");
        DATA = scn.next();
        lista[2] = DATA+"|";

        // MATRICULA
        System.out.println(" Dona'm la Matricula");
        MATRICULA = scn.next();
        lista[3] = MATRICULA+"|";

        // N_BASTIDOR
        System.out.println(" Dona'm el Numero de Bastidor");
        N_BASTIDO = scn.next();
        lista[4] = N_BASTIDO+"|";

        // N_MOTOR
        System.out.println(" Dona'm el Numero de Motor");
        N_MOTOR = scn.next();
        lista[5] = N_MOTOR+"|";

        // DNI
        System.out.println(" Dona'm el DNI");
        DNI = scn.next();
        lista[6] = DNI+"|";

        // COGNOM_NOM
        System.out.println(" Dona'm els Cognoms i el nom (1Cognom_2Cognom_Nom");
        COGNOM_NOM = scn.next();
        lista[7] = COGNOM_NOM+"|";

        // ADREÇA
        System.out.println(" Dona'm l'Adreça (Carrer_Numero");
        ADRECA = scn.next();
        lista[8] = ADRECA;

        // Imprimimos por consola el resultado guardado
        System.out.println("");
        System.out.println("Contigut final : ");
        System.out.println("");

        //Imprime el array mediante un for each
        Arrays.stream(lista, 0, lista.length).forEach(System.out::print);
        System.out.println("");

        // Retorna el array
        return lista;

    }


}
