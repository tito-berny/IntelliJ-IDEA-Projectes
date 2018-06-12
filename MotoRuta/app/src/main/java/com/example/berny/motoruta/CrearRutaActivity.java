package com.example.berny.motoruta;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CrearRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    //Variable mapa
    private GoogleMap mMap;

    //VAriable Array ruta completa
    private ArrayList<String> latlog_general;

    //Tamaño de lineas Lat|Lng/n a cada vez que e guarda en el fichero tmp
    private final int tamaño_lineas_a_guardar = 1;

    //Botones
    private Button inicia, finaliza;

    //Sonido Botones
    private MediaPlayer mpInicia, mpApaga;

    //TextView
    private TextView tiempo;

    //Estado del boton iniciar ruta
    private boolean guardar;

    //Control primer inicio de ruta
    private boolean empezado = false;

    // Declaro Location Manager
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ruta);
        // Obtenga el SupportMapFragment y reciba notificaciones cuando el mapa esté listo para ser utilizado.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mRuta);
        mapFragment.getMapAsync(this);

        //Inicializamos Array Ruta
        latlog_general = new ArrayList<String>();

        //Boton guardar sin pulsar
        guardar = false;

        //Asignamos sonido
        mpInicia = MediaPlayer.create(this, R.raw.cbrarranca);
        mpApaga = MediaPlayer.create(this, R.raw.cbrapaga);

        //Inicia TextView Tiempo
        tiempo = (TextView) findViewById(R.id.tvTiempo);

        //Obtenemos la fecha del sistema con patron
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        //Asignamos la fecha a la Variable
        final String fecha = dateFormat.format(date);

        //Ponemos fecha en su campo
        tiempo.setText(fecha);

        //Clases del GPS
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();

        //Permisos del GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //Llama al escuchador del gps, podemos indicar minimo de tiempo y distancia para cada escucha
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);


        //-----------------     BOTONES            -------------------------------------------------

        inicia = (Button) findViewById(R.id.bIniciarRuta);
        finaliza = (Button) findViewById(R.id.bFinalizarRuta);


        //Escuchador de clicks en boton, inicia la ruta
        inicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Primera activacion comienza ruta, mientras e boton finaliza ruta muestra
                //Toast pidiendo al usuario que pulse inicia ruta
                empezado = true;

                //Evitar que la pantalla se apague
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                //Controlamos si el boton ya se a pulsado o no
                //Empieza ruta
                if (!guardar) {

                    //Intenta lanzar el hilo del cronometro si no esta lanzado ya
                    try {
                        // new Cronometro().execute().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } catch (Exception e) {

                    }

                    //Sonido boton
                    mpInicia.start();

                    //Ponemos boton en no pulsado
                    guardar = true;
                    //Cambia texto boton a pausa
                    inicia.setText(R.string.pausa);

                    //Avisamos al usuario que comenzo a guardarse la ruta mediante un Toast
                    Toast aviso = Toast.makeText(getBaseContext(), R.string.Toast_comienza_guardado_ruta, Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.CENTER, 0, 0);
                    aviso.show();

                    //PAUSA en ruta
                } else {

                    //Sonido boton
                    mpApaga.start();

                    //Cambia texto boton a Reanudar ruta
                    inicia.setText(R.string.reanudar_ruta);
                    //Ponemos el boton en pulsado
                    guardar = false;

                    //Avisamos al usuario que el guardado esta en pause mediante un Toast
                    Toast toast = Toast.makeText(getBaseContext(), R.string.Toast_pausa_guardado_ruta, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }
        });

        //Escuchador boton finalizar,
        //Para el cronometro, lanza nueva activity pasandole los valores de ruta
        finaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sonido boton
                mpApaga.start();

                //Si aun no se a pulsado en empezar ruta
                //Indicamos al usuario que comience una ruta, Asi no se guardara una ruta vacia
                if (!empezado) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.Toast_NO_iniciado, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    //Mostramos alerta si deberas quiere el usuario finalizar la ruta
                    AlertDialog.Builder builder = new AlertDialog.Builder(CrearRutaActivity.this);

                    builder.setMessage(R.string.alerta_guardar).setTitle(R.string.alerta_guardar_titulo)
                            //Usamos recursos de android para texto cancelar
                            //Si cancela regresa
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            //Usamos recursos de android para texto cancelar
                            //Si hacepta lanza guardar ruta
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Pasamos parametros a la nueva activity
                                    // Array de ruta, texto en tiempo.
                                    Intent guardar = new Intent(CrearRutaActivity.this, GuardarRutaActivity.class);
                                    guardar.putExtra("LatLng", latlog_general);
                                    //Se deja preparada variable para futuro poner tiempo de la ruta
                                    guardar.putExtra("tiempo", tiempo.getText().toString());
                                    startActivity(guardar);
                                }

                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

        //..........................................................................................

    }

    protected void onDestroy() {
        super.onDestroy();

    }

    // Mientras la APP se encuentre en este estado no se apagará la pantalla
    // si pierde el foco o se pone en pausa pierde el estado y recupera el bloqueo de pantalla
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Variable mapa
        mMap = googleMap;
        //Zoom camara
        mMap.moveCamera(CameraUpdateFactory.zoomTo(1));

        //COMPRUEVA LOS PERMISOS DEL USUARIO PARA PONER LA LOCALIZACION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //Poner boton Localizacion
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            //Mostrar ubicacion
            mMap.setMyLocationEnabled(true);

        } else {
            // Mostrar razonamiento y solicitar permiso.
            AlertDialog.Builder builder = new AlertDialog.Builder(CrearRutaActivity.this);

            builder.setMessage(R.string.alerta_GPS_desactivado).setTitle(R.string.alerta_GPS_desactivado_titulo)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Abre las opciones de la localizazcion
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }

                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }


    /**
     * Clase listener de cordenadas GPS.
     * Guarda la ruta en un fichero temporal, se puede seleccionar numero de registros
     * a guardar en cada inserción.
     * Actualiza ruta en el mapa usando metodo PintarRuta() y centra ubicación.
     */
    private class MyLocationListener implements LocationListener {

        //Variable Array String para guardar ruta en fichero
        //Es reseteada al hacer el inciso en el fichero
        //Por eso no se utiliza Array Global
        private ArrayList<String> latlog_guardar = new ArrayList<String>();
        //Contador lineas a insertar en fichero
        int i = 1;

        //File con ruta destino
        File internalStorageDir = getFilesDir();
        //Obtiene directorio interno de la APP
        File ruta = new File(internalStorageDir, "ruta_tmp.txt");
        FileOutputStream fos = null;

        @Override
        public void onLocationChanged(Location loc) {

            //Si se a presionado el boton guardar comienza el guardado de la ruta
            //y tambien el dibujado de la ruta en el mapa
            if (guardar) {

                //Creamos el nuevo punto LatLng y enfocamos la camara en la nueva ubicacion
                LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);

                //Guardamos la lat y lng en el Array Global
                latlog_general.add(loc.getLatitude() + "|" + loc.getLongitude() + "\n");

                //Guardamos en Array para guardar en fichero
                //Esta Array se borra llegada la medida deseada de guardado
                latlog_guardar.add(loc.getLatitude() + "|" + loc.getLongitude() + "\n");

                //Guardamos en un fichero temporal las nuevas lat lng cada vez que se llene
                //Podemos determinar cada cuantos cambio se guarda en el fichero en la variable global
                if (i == tamaño_lineas_a_guardar) {
                    for (String aLista : latlog_guardar) {

                        // Create file output stream
                        try {
                            fos = new FileOutputStream(ruta, true);
                            // Write a line to the file
                            fos.write(aLista.getBytes());
                            // Close the file output stream
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //Limpimos Array al guardarla ya en fichero
                    latlog_guardar.clear();
                    //Reiniciamos el contador
                    i = 1;

                } else {
                    //Añadimos 1 al contador para llegar al maximo y empezar el guardado en fichero
                    i++;
                }

                //Si es pulsado el boton guardar comienza a dibujar la ruta en el mapa
                try {
                    //Ejecutamos el hilo que pinta la ruta en el mapa pasandole la Array
                    new PintarRuta().execute(latlog_general).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } catch (Exception e) {

                }

            }


        }

        @Override
        public void onProviderDisabled(String provider) {

            //Avisamos al usuario que el GPS esta desactivado mediate una alerta
            //Al hacer click en OK abre las opciones de localizacion
            //facilitando al usuario la activacion del GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(CrearRutaActivity.this);

            builder.setMessage(R.string.alerta_GPS_desactivado).setTitle(R.string.alerta_GPS_desactivado_titulo)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // Abre las opciones de la localizazcion
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }

                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public ArrayList getRuta() {
            return latlog_general;
        }
    }

    /**
     * Clase para dibujar la ruta en el mapa extends AsynTask.
     * Recibe un Array de String con la ruta en Lng y Lng.
     */
    class PintarRuta extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {


        public PintarRuta() {
        }

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
            for (String aLista : result) {

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
                //Añadimos el nuevo punto
                points.add(position);

            }

            // Añade todos los puntos a la ruta con LineOptions
            lineOptions.addAll(points);
            //Define tamaño de la linea
            lineOptions.width(10);
            //Define el color de la linea
            lineOptions.color(Color.BLUE);

            Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            // Añade la linea en el mapa si no es nula
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");

            }

        }

    }

}




