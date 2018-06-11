package com.example.berny.motoruta;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GuardarRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    //Conexion con la BBDD
    public ConexionSQLiteHelper con;

    //Mapa
    private GoogleMap mMap;
    //Variables elementos
    private RatingBar ratingBar;
    private Button guardar;
    private TextView tfTiempo;
    private EditText nombre_ruta, comentario_ruta;
    private CheckBox sol, nuves, llubia;

    //Variable control meteorologia por defecto es 1 =Sol
    private int meteo=1;
    //Variable para recojer la id de la ruta
    private String id_ruta;
    //Array de ruta
    private ArrayList<String> latlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_ruta);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mRuta);
        mapFragment.getMapAsync(this);

        //INICIAMOS LA CONEXION LA BBDD Creara las tablas si es la 1 vez que se ejecuta
        con = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null, Utilidades.DATABASE_VERSION);

        //Asignamos variables
        nombre_ruta = (EditText) findViewById(R.id.etNombreRuta);
        comentario_ruta = (EditText) findViewById(R.id.etComentario);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        guardar = (Button) findViewById(R.id.bGuardar);
        tfTiempo = (TextView) findViewById(R.id.tvTiempo);
        sol = (CheckBox) findViewById(R.id.cbSol);
        nuves = (CheckBox) findViewById(R.id.cbNuves);
        llubia = (CheckBox) findViewById(R.id.cbLlubia);

        //Array para ruta
        latlng = new ArrayList<String>();

        //Recojemos parametros de la activity anterior (CrearRutaActivity)
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String tiempo = parametros.getString("tiempo");
            //tfTiempo.setText(tiempo);
            latlng = parametros.getStringArrayList("LatLng");
        }

        //Obtenemos la fecha del sistema
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        final String fecha = dateFormat.format(date);

        //Ponemos fecha en su campo
        tfTiempo.setText(fecha);

        //-----------------     CHECKBOXs      -----------------------------------------------------

        //Sol controla que solo se marque 1 checkbox
        sol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(nuves.isChecked()) {
                    nuves.setChecked(false);
                }
                if(llubia.isChecked()) {
                    llubia.setChecked(false);
                }
            }
        });

        //Nuves controla que solo se marque 1 checkbox
        nuves.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(sol.isChecked()) {
                    sol.setChecked(false);
                }
                if(llubia.isChecked()) {
                    llubia.setChecked(false);
                }
            }
        });

        //LLubia controla que solo se marque 1 checkbox
        llubia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(sol.isChecked()) {
                    sol.setChecked(false);
                }
                if(nuves.isChecked()) {
                    nuves.setChecked(false);
                }
            }
        });

        //------------------------------------------------------------------------------------------

        //-----------------     BOTONES            -------------------------------------------------


        //Escuchador de clicks en botone guarda la ruta
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Commprovamos si el campo nombre esta vacio si lo esta lanza alerta
                if (nombre_ruta.getText().toString().length() > 0 ) {

                    //Recuperamos el texto del Edit Text Nombre
                    String nombre = nombre_ruta.getText().toString();

                    //Recuperamos texto en comentario
                    String comentario = comentario_ruta.getText().toString();

                    //Encapsulamos en un Float el valor de las estrellas
                    Float estrellas = ratingBar.getRating();

                    //Comprovamos CheckBox meteorologia
                    if (sol.isChecked()) meteo = 1;
                    if (nuves.isChecked()) meteo = 2;
                    if (llubia.isChecked()) meteo = 3;

                    //Path, que sera el nombre de la ruta mas fecha y hora
                    String hora = Calendar.getInstance().getTime().toString();
                    String path_to_ruta = nombre + hora;
                    System.out.println(path_to_ruta);

                    //----------    INSERT TABLA RUTA       ----------------------------------------

                    //Escribe en la BBDD
                    SQLiteDatabase db = con.getWritableDatabase();

                    //Si la conexion para grabar esta preparada
                    //Ejecuta comando para activar las ForeignKeys
                    if (!db.isReadOnly()) {
                        db.execSQL("PRAGMA foreign_keys = ON;");
                    }

                    //Insert into tbl_ruta
                    String insert = "INSERT INTO tbl_ruta (nombre, fecha, tiempo, meteorologia, valoracion, path)" +
                            " VALUES ('" + nombre + "','" + fecha + "','" + 1 + "','" + meteo + "','" + estrellas + "','" + path_to_ruta + "');";

                    db.execSQL(insert);

                    //Cerramos conexion
                    db.close();

                    //-----------    INSERT TABLA COMENTARIO     -----------------------------------

                    SQLiteDatabase db1 = con.getReadableDatabase();
                    //------  SELECT TBL_RUTA  -----------------
                    //Para conseguir el ID de la ruta para el comentario
                    //Pasamos argumento path_to_ruta ya que sera unico para esta ruta
                    String[] args = new String[] {path_to_ruta};
                    //Creamos consulta BBDD
                    Cursor cur = db1.rawQuery(" SELECT id FROM tbl_ruta WHERE path=? ", args);

                    //Nos aseguramos de que existe al menos un registro
                    if (cur.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros

                        do {
                            id_ruta = cur.getString(0);
                        } while (cur.moveToNext());
                    }
                    //Cerramos conexion
                    db1.close();

                    //guarda en la BBDD
                    SQLiteDatabase db2 = con.getWritableDatabase();

                    //Una vez obtenido el Id del registro podemos hacer el Insert into tbl_comentario
                    String insert_comentario = "INSERT INTO tbl_comentario (ruta_id, comentario)" +
                            " VALUES ('" + id_ruta + "','" + comentario + "');";
                    //Ejecutamos SQL
                    db2.execSQL(insert_comentario);

                    //Cerramos conexion
                    db2.close();

                    //----------------------- GUARDAR RUTA EN CARPETA ------------------------------------------

                    //Fila origen
                    File ruta_tmp = new File(getFilesDir(), "ruta_tmp.txt");
                    //Fila destino
                    File nuevaCarpeta = new File(getFilesDir(), path_to_ruta + ".txt");

                    boolean mover = ruta_tmp.renameTo(nuevaCarpeta);

                    if (mover) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(GuardarRutaActivity.this);

                        builder.setMessage(R.string.alerta_guardado).setTitle(R.string.titulo_alerta_guardado)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // Abre las opciones de la localizazcion
                                        Intent myIntent = new Intent(GuardarRutaActivity.this, SplashScreenActivity.class);
                                        startActivity(myIntent);
                                    }

                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else {

                        //Avisamos ususrio que no se ha podido guardar la ruta
                        AlertDialog.Builder builder = new AlertDialog.Builder(GuardarRutaActivity.this);

                        builder.setMessage(R.string.alerta_NO_guardado).setTitle(R.string.titulo_alerta_NO_guardado)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    //Lanza alerta campo nombre esta vacio
                }else{

                    //Avisamos ususrio que no se ha podido guardar la ruta
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuardarRutaActivity.this);

                    builder.setMessage(R.string.alerta_nombre_obligatorio).setTitle(R.string.titulo_alerta_nombre_obligatorio)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }

        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Creamoss un nuevo hilo encargado de pintar la ruta en el mapa
        //Utiliza el Array recibido con las Lat y Lng
        Pintarruta1 pintarruta1 = new Pintarruta1();
        pintarruta1.execute(latlng);

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
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 12);
                mMap.animateCamera(cameraUpdate);

                //Añadimos el nuevo punto
                points.add(position);

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
}
