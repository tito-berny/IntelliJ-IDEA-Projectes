package main;

import Tablas.TblContribuyentesEntity;
import Tablas.TblHistorialEntity;
import Tablas.TblMovimentdgtEntity;
import Tablas.TblVehiclesEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;

public class Main {

    // private static SessionFactory sessionFactory;

    private static File DGT = new File("./DGTcsv.txt");

    private static EntityManagerFactory emf;

    private static DAOHelper<TblMovimentdgtEntity> helperMoviment;
    private static DAOHelper<TblContribuyentesEntity> helperContribuyent;
    private static DAOHelper<TblHistorialEntity> helperHistorial;
    private static DAOHelper<TblVehiclesEntity> helperVehicles;


    public static void main(final String[] args) throws Exception {

        DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();


        helperMoviment = new DAOHelper<>(emf, TblMovimentdgtEntity.class);
        helperContribuyent = new DAOHelper<>(emf, TblContribuyentesEntity.class);
        helperHistorial = new DAOHelper<>(emf, TblHistorialEntity.class);
        helperVehicles = new DAOHelper<>(emf, TblVehiclesEntity.class);

        TblMovimentdgtEntity a = new TblMovimentdgtEntity();

        a.setAbm("A");
        a.setAdreca("aAd");
        a.setBastidor("AAA");
        a.setDni("12524");

        helperMoviment.insert(a);


        //--------------------------------LEEMOS FICHERO ------------------------------------------


        //Fecha del sistema
        Date lastCrawlDate = new Date();
        // Convertimos al formato de MYSQL
        java.sql.Date sqlDate = new java.sql.Date(lastCrawlDate.getTime());

        //Para leer fichero y cargar buffer
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

            //POR CADA LINEA DEL FICHERO REPITE EL PROGRAMA ASTA QUE NO QUEDEN LINEAS

            while ((linea = br.readLine()) != null) {

                //Separamos los datos por "|" necesitamos // delante por ser un caracter especial
                string = linea.split("\\|");


                //_--------------------------INSERT----------------------------------------

                TblMovimentdgtEntity moviment = new TblMovimentdgtEntity();

                moviment.setDni(string[0]);
                moviment.setBastidor(string[1]);
                moviment.setAdreca(string[2]);
                moviment.setAbm(string[3]);
                moviment.setBastidor(string[4]);
                moviment.setGognomsNom(string[5]);
                moviment.setMatricula(string[6]);
                moviment.setTipus(string[7]);
                moviment.setData(string[8]);

                helperMoviment.insert(moviment);

                // ------------------------------ALTA "A"--------------------------------------------

                if (string[0].equals("A")) {

                    TblContribuyentesEntity contribuyente = new TblContribuyentesEntity();

                    contribuyente.setDni(string[6]);
                    contribuyente.setCognomNom(string[7]);
                    contribuyente.setAdreca(string[8]);

                    helperContribuyent.insert(contribuyente);
                }


                //-----------------------------BAIXA----------------------------------


                if (string[0].equals("B")) {


                    //-----------------DEFINITIVA--------------

                    //-----------------UPDATE-------------------------------------

                    if (string[1].equals("BD")) {

                        TblVehiclesEntity vehicle = new TblVehiclesEntity();

                        vehicle.setDataBaixa(string[3]);
                        vehicle.setMatricula(string[3]);

                        helperVehicles.update(vehicle);

                    }


                    //Consulta para consegir la primary key del registro histrial con el cont_Id y vehi_Id

                    ResultSet rs = (ResultSet) helperContribuyent.getFilteredList("Matricula", string[3]);

                    //-----------------------DELETE--------------------------------------

                    if (rs.next()) {

                        helperContribuyent.delete(Integer.parseInt(string[3]));

                    }

                }

                }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Fallo al insertar valores. " +
                    " Comprueva el fichero de origen.");
            System.out.println("");

        } finally {

            emf.close();
        }

    }
}
