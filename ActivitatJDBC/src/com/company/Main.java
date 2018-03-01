package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class Main {

    private static File DGT = new File("./DGTcsv.txt");


    public static void main(String[] args) throws SQLException, ParseException {
	// write your code here

        // Se registra el Driver de MySQL
        DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());

        Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost/DGT","local", "local");


        // Preparamos la consulta
        Statement s = conexion.createStatement();
       // ResultSet rs = s.executeQuery ("select * from tbl_vehicles");


        //s.executeUpdate ("INSERT INTO tbl_contribuyentes " + "VALUES (1002, 'McBeal', 'Ms.', 'Boston;')");


        // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla.
/*        while (rs.next())
        {
            System.out.println (rs.getInt (1) + " " + rs.getString (2)  + rs.getString (3) + rs.getString (4) + rs.getString (5)+ rs.getString (6)+ rs.getString (7));
        }
*/
        // Cerramos la conexion a la base de datos.
        conexion.close();

        leerFichero();



    }

    private static void leerFichero() throws SQLException, ParseException {

        Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost/DGT","local", "local");

        //Fecha del sistema
        Date lastCrawlDate = new Date();
        // Convertimos al formato de MYSQL
        java.sql.Date sqlDate = new java.sql.Date(lastCrawlDate.getTime());

        //MovimentDGT mvm = new MovimentDGT();
        String[] cof = new String[0];

        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            fr = new FileReader(DGT);
            br = new BufferedReader(fr);

            // Lectura del fichero
            // Cada linea leida en el fichero se guarda en una posicion del array
            String linea;
            String[] string;
            while ((linea = br.readLine()) != null){
                //Separamos los datos por "|" necesitamos // delante por ser un caracter especial
                string = linea.split("\\|");



                // crear un extracto de la conexión
                Statement s = conexion.createStatement();
                // inserte los datos
                String sql = "INSERT INTO tbl_movimentdgt " +
                        "(ABM,TIPUS,Data,Matricula,BASTIDOR," +
                        "N_MOTOR,DNI,GOGNOMS_NOM,ADRECA,NOM_FITXER,DATA_INTRODUCCIO) " +
                        "VALUES ('"+string[0]+"','"+string[1]+"','"+string[2]+"','"+string[3]+"','"+string[4]+
                        "','"+string[5]+"','"+string[6]+"','"+string[7]+"','"+string[8]+"','"+DGT+"','"+sqlDate+"')";
                                 //('"+pid+"','"+tid+"','"+rid+"',"+tspent+",'"+des+"')")

                s.execute(sql);

                //ALTA "A"
                if (string[0].equals("A")){

                    // inserte los datos tabla contribuyentes,  Si exite el mismo DNI salta excepcion
                    String sqlA = "INSERT INTO tbl_contribuyentes " +
                            "(DNI,COGNOM_NOM,ADRECA ) " +
                            "VALUES ('"+string[6]+"','"+string[7]+"','"+string[8]+"')";

                    //Ejecuta la sentencia
                    s.execute(sqlA);


                    // inserte los datos tabla Vehicle, Si exite la misma matricula salta excepcion
                    String sqlAV = "INSERT INTO tbl_vehicles " +
                            "(Matricula,BASTIDOR,N_MOTOR,Data_Alta ) " +
                            "VALUES ('"+string[3]+"','"+string[4]+"','"+string[5]+"','"+sqlDate+"')";

                    //Ejecuta la sentencia
                    s.execute(sqlAV);

                    //TODO Falta poner el id de la tabla contribuyente y el ID de la tabla Vehiculo
                    // inserte los datos tabla Historial, Si exite Cont_ID y Vehi_ID conjunta como clave plimaria salta excepcion
                    String sqlAH = "INSERT INTO tbl_historial " +
                            "(Cont_ID,Vehi_Id,Data_Alta ) " +
                            "VALUES ('"++"','"+string[4]+"','"+sqlDate+"')";

                    //Ejecuta la sentencia
                    s.execute(sqlAH);

                }

                if (string[0].equals("B")){

                }

                if (string[0].equals("M")){
                }

                }


        }
        //Capturamos la exepcion e informamos al usuario
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Fallo al insertar valores. " +
                    " Comprueva el fichero de origen.");
            System.out.println("");
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }
}
