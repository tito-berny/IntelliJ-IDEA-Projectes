package com.example.berny.motoruta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.berny.motoruta.DataBase.ConexionSQLiteHelper;
import com.example.berny.motoruta.Utilidades.Utilidades;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BuscarRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    //Conexion con la BBDD
    public ConexionSQLiteHelper con;

    //Variable mapa
    private GoogleMap mMap;

    //Array de ruta
    private ArrayList<String> latlng;

    //Variable id ruta, la primera ruta en cargar sera la numero 1
    private int id_ruta= 1;

    //Variable botones
    private Button anterior, siguiente;

    //VAriable RatingBar
    private RatingBar ratingBar;

    //Varable TextField
    private TextView nombre, fecha, comentario;

    //Variable ImageView
    private ImageView meteo;

    //Variables BBDD
    private String nombre_bd;
    private String fecha_bd;
    private String tiempo_bd;
    private String meteo_bd;
    private String valoracion_bd;
    private String path_bd;
    private String comentario_bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ruta);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mRuta);
        mapFragment.getMapAsync(this);

        //INICIAMOS LA CONEXION LA BBDD Creara las tablas si es la 1 vez que se ejecuta
        con = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null, Utilidades.DATABASE_VERSION);

        //Asignamos variables
        anterior = (Button) findViewById(R.id.bAnterior);
        siguiente = (Button) findViewById(R.id.bSiguiente);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        fecha = (TextView) findViewById(R.id.tvFecha);
        nombre = (TextView) findViewById(R.id.tvNombre);
        comentario = (TextView) findViewById(R.id.tfTextoComentario);
        meteo = (ImageView) findViewById(R.id.ivMeteo);


        //-----------------     BOTONES            -------------------------------------------------


        //Escuchador de clicks en boton anterior
        //Carga la ruta anterior si es posible
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Desactivamos el boton si llega al primer registro
                if (id_ruta==1){
                    anterior.setEnabled(false);
                }
                //Comprovamos que no sea el primer valor
                if(id_ruta>1) {
                    //Retrocedemos en id tabla BBDD
                    id_ruta--;
                    //Cargamos la nueva ruta
                    CargarRuta(id_ruta);
                }

            }
        });

        //Escuchador de clicks en boton siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Activamos boton anterior
                anterior.setEnabled(true);
                //Avanzamos en id tabla BBDD
                id_ruta++;
                //Cargamos la nueva ruta
                CargarRuta(id_ruta);

            }
        });

        //..........................................................................................

    }

    /**
     * Clase que se encarga de cargar la ruta de la BBDD.
     * Recoge los valores de la ruta, los inserta los campos correspondientes de la vista.
     * Carga la ruta obtenida de paht y la pinta en el mapa.
     * @param id_ruta Recibe el id de la ruta a cargar en la BBDD.
     */
    private void CargarRuta(int id_ruta) {

        //-----------------     CONSULTA TABLA BBDD ------------------------------------------------

        SQLiteDatabase db = con.getReadableDatabase();
        //Cambiamos int a String
        String id = Integer.toString(id_ruta);
        //Creamos argumento para consulta
        String[] args = new String[] {id};
        //Creamos consulta BBDD
        Cursor c = db.rawQuery(" SELECT nombre, fecha, tiempo, meteorologia, valoracion, path" +
                " FROM tbl_ruta WHERE id=? ", args);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                nombre_bd = c.getString(0);
                fecha_bd = c.getString(1);
                tiempo_bd = c.getString(2);
                meteo_bd = c.getString(3);
                valoracion_bd = c.getString(4);
                path_bd = c.getString(5);

            } while (c.moveToNext());

        }
        //Creamos cursor para consulta tabla tbl_comntario
        Cursor cur = db.rawQuery(" SELECT comentario FROM tbl_comentario WHERE ruta_id=? ", args);

        //Nos aseguramos de que existe al menos un registro
        if (cur.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //Asignamos a variable el valor obtenido
                comentario_bd = cur.getString(0);
            } while (c.moveToNext());

        }

        //--------------    CARGA CAMPOS ---------------------------------------------------

        //Rellenamos los elemento con los datos recojidos de la BBDD
        nombre.setText(nombre_bd);
        ratingBar.setRating(Float.parseFloat(valoracion_bd));
        fecha.setText(fecha_bd);
        comentario.setText(comentario_bd);

        //Pasamos de String a integer el valor obtenido
        int numMeteo = Integer.parseInt(meteo_bd);
         //Selecciona imagen en Meteorologia segun valor BBDD
        if (numMeteo == 1){
            meteo.setImageResource(R.drawable.sol);
        }
        if (numMeteo == 2){
            meteo.setImageResource(R.drawable.solnubes);
        }
        if (numMeteo == 3){
            meteo.setImageResource(R.drawable.llubia);
        }

        //File Path
        File ruta_path = new File(getFilesDir(), path_bd + ".txt");

        //Encapsula en el Array la ruta del fichero con el nombre path de la BBDD
        latlng = leerFichero(ruta_path);

        //Creamoss un nuevo hilo encargado de pintar la ruta en el mapa
        //Utiliza el Array recibido con las Lat y Lng
        Pintarruta1 pintarruta1 = new Pintarruta1 ();
        pintarruta1.execute(latlng);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //Carga la primera ruta el id sera el primerode la BBDD
        CargarRuta(id_ruta);
    }


    /**
     * Clase para dibujar la ruta en el mapa con un hilo
     * Recibe un Array de String con Lng y Lng
     */
    class Pintarruta1 extends AsyncTask< ArrayList<String> , Void ,  ArrayList<String> > {


        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... arrayLists) {
            //Retorna al metodo onPostExecute
            //Solo le pasamos el array recibido
            //El lo almacena en otro Array asi que solo le pasamos el primero (0).
            return arrayLists[0];
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            //Limpiamos la ruta anterior de mapa
            mMap.clear();

            //Controlamos el inicio de la ruta para colocar el marcador
            boolean inicio_ruta = true;

            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            // Por cada String del Array recoje Lat y Lng
            for (String aLista : result){

                //Separamos
                //Esta almacenado separado por | en el string
                //Separamos Lat | Log
                String[] string;
                string = aLista.split("\\|");
                //Almacenamos variables Lat Lng casteando a double
                double lat = Double.parseDouble(string[0]);
                double lng = Double.parseDouble(string[1]);

                //Creamos nueva LatLng con los valores
                LatLng position = new LatLng(lat, lng);

                //Enfocamos la camara a la ruta y le damos un zoom
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 10);
                mMap.animateCamera(cameraUpdate);

                //Añadimos el nuevo punto
                points.add(position);

                //Ponemos marcador en el mapa al inicio de la ruta con el nombre de esta
                if(inicio_ruta){
                    mMap.addMarker(new MarkerOptions().position(position).title(nombre_bd));
                    //Controlamos que solo haya un inicio
                    inicio_ruta= false;
                }

            }

            // Añade todos los puntos a la ruta con LineOptions
            lineOptions.addAll(points);
            //Define tamaño de la linea
            lineOptions.width(10);
            //Define el color de la linea
            lineOptions.color(Color.BLUE);

            // Añade la linea en el mapa si no es nula
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {

            }

        }

    }

    /**
     * Leemos el fichero linea a linea
     * @return Un ArrayList con una linea del fichero en cada posicion.
     * @param ruta_path Recibe la ruta del archivo a cargar.
     */
    private static ArrayList<String> leerFichero(File ruta_path) {

        //Creamos ArrayList de String, en cada posicion se situa un array de String
        //con la lectura de cada linea
        ArrayList<String> lineas = new ArrayList<>();

        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            fr = new FileReader(ruta_path);
            br = new BufferedReader(fr);

            // Lectura del fichero
            // Cada linea leida en el fichero se guarda en una posicion del array
            String linea;
            while((linea=br.readLine())!=null)
                lineas.add(linea);

        }
        //Capturamos la exepcion e informamos al usuario
        catch(Exception e){

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


}
