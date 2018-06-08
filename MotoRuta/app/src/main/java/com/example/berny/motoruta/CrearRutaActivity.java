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
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import static android.content.ContentValues.TAG;

public class CrearRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<String> latlog_general;

    //Tamaño de lineas Lat|Lng/n a cada vez que e guarda en el fichero tmp
    private final int tamaño_lineas_a_guardar = 1;

    //Botones
    private Button inicia, finaliza;

    //Todo Sonido Botones
    //private MediaPlayer mpInicia;

    //TextView
    private TextView tiempo;

    //Estado del boton iniciar ruta
    private boolean guardar;

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
        latlog_general =  new ArrayList<String>();

        //Boton guardar sin pulsar
        guardar = false;

        //TODO Iniciamos sonido
        //mpInicia = MediaPlayer.create(this,R.raw.cbrarranca);

        //Inicia TextView Tiempo
        tiempo = (TextView) findViewById(R.id.tvTiempo);

        //Obtenemos la fecha del sistema
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

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
        //Llama al escuchador del gps
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);



        //-----------------     BOTONES            -------------------------------------------------

        inicia = (Button) findViewById(R.id.bIniciarRuta);
        finaliza = (Button) findViewById(R.id.bFinalizarRuta);


        //Escuchador de clicks en botone, inicia la ruta
        inicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Sonido boton
                //mpInicia.start();

                //Activar boton finalizar ruta
                finaliza.setEnabled(true);

                //Controlamos si el boton ya se a pulsado o no
                //Empieza ruta
                if (!guardar){

                    //Intenta lanzar el hilo del cronometro si no esta lanzado ya
                    try {

                       // new Cronometro().execute().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    }catch (Exception e){

                    }

                    //TODO poner pausa cronometro
                    //cronometro.pause();

                    //Ponemos boton en no pulsado
                    guardar = true;
                    //Cambia texto boton a pausa
                    inicia.setText(R.string.pausa);

                    //Avisamos al usuario que comenzo a guardarse la ruta mediante un Toast
                    Toast aviso = Toast.makeText(getBaseContext(), R.string.Toast_comienza_guardado_ruta , Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.CENTER, 0,0);
                    aviso.show();

                    //PAUSA
                }else {

                    //Cambia texto boton a Reanudar ruta
                    inicia.setText(R.string.reanudar_ruta);
                    //Ponemos el boton en pulsado
                    guardar = false;

                    //TODO Pausar el cronometro
                    //cronometro.resume();

                    //Avisamos al usuario que el guardado esta en pause mediante un Toast
                    Toast toast = Toast.makeText(getBaseContext(), R.string.Toast_pausa_guardado_ruta , Toast.LENGTH_SHORT);
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
                                guardar.putExtra("tiempo", tiempo.getText().toString());
                                startActivity(guardar);
                            }

                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //..........................................................................................

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        mMap.setBuildingsEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.zoomTo(1));

        //TODO permisos No detectados
        // Aquí, esta actividad es la actividad actual
        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // ¿Deberíamos mostrar una explicación?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Mostrar una expansión al usuario * asincrónicamente * - no bloquear
                 // este hilo esperando la respuesta del usuario! Después del usuario
                // ve la explicación, intente nuevamente para solicitar el permiso.
                    mMap.setMyLocationEnabled(true);

                    //Poner boton Localizacion
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);

            } else {

                //No se necesita explicación, podemos solicitar el permiso.


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        permissionCheck);

                //MY_PERMISSIONS_REQUEST_READ_CONTACTS es un
                // constante int definida por la aplicación. El método de devolución de llamada obtiene el
                // resultado de la solicitud.
            }
        }*/

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
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
     * Clase listener de cordenadas GPS
     */
    private class MyLocationListener implements LocationListener {


        private ArrayList<String> latlog_guardar = new ArrayList<String>();


        int i = 1;

        File internalStorageDir = getFilesDir();
        File ruta = new File(internalStorageDir, "ruta_tmp.txt");
        FileOutputStream fos = null;

        @Override
        public void onLocationChanged(Location loc) {



            //Si se a presionado el boton guardar comienza el guardado de la ruta
            //y tambien el dibujado de la ruta en el mapa
            if (guardar) {

                //Creamos el nuevo punto LatLng y enfocamos la camara en la nueva ubicacion
                LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                mMap.animateCamera(cameraUpdate);

                //Guardamos la lat y lng en el Array Global
                latlog_general.add( loc.getLatitude() + "|" + loc.getLongitude() + "\n");

                //Guardamos en Array para guardar en fichero
                //Esta Array se borra llegada la medida deseada de guardado
                latlog_guardar.add(loc.getLatitude() + "|" + loc.getLongitude() + "\n");

                //Guardar en fichero temporal carpeta tmp el fichero generado
                //al final cuando se guarde la ruta eliminar y cambiar el nombre
                //La app comprueva que no hay nada en la carpeta temp si explota la app se queda el archivo guardado

                //Guardamos en un fichero temporal las nuevas lat lng cada vez que se llene
                //Podemos determinar cada cuantos cambio se guarda en el fichero (i = 1000)
                if (i==tamaño_lineas_a_guardar) {
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
                    i=1;

                }else {
                    //Añadimos 1 al contador para llegar al maximo y empezar el guardado en fichero
                    i++;
                }

                Toast.makeText(
                        getBaseContext(),
                        "Locatio changed: Lat: " + loc.getLatitude() + " Lng: "
                                + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                String longitude = "Longitude: " + loc.getLongitude();
                Log.v(TAG, longitude);
                String latitude = "Latitude: " + loc.getLatitude();
                Log.v(TAG, latitude);

                //Si es pulsado el boton guardar comienza a dibujar la ruta en el mapa
                //Creamos instancia del hilo

                try {

                    //Ejecutamos el hilo que pinta la ruta en el mapa pasandole la Array
                    new PintarRuta().execute(latlog_general).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }catch (Exception e){

                }

            }


        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("GPS DESACTIVADO");


            //Avisamos al usuario que el GPS esta desactivado mediate una alerta
            //Al hacer click en OK abre las opciones de localizacion
            //facilitando al usuario la activacion del GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(CrearRutaActivity.this);

            builder.setMessage(R.string.alerta_GPS_desactivado).setTitle(R.string.alerta_GPS_desactivado_titulo)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            System.out.println("GPS ACTIVADO");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            System.out.println("GPS CAMBIO");
        }

        public ArrayList getRuta() {
            return latlog_general;
        }
    }

    /**
     * Clase para dibujar la ruta en el mapa con un hilo
     * Recibe un Array de String con Lng y Lng
     */
    class PintarRuta extends AsyncTask < ArrayList<String> , Void ,  ArrayList<String> > {


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
                //Añadimos el nuevo punto
                points.add(position);

                }

                // Añade todos los puntos a la ruta con LineOptions
                lineOptions.addAll(points);
                //Define tamaño de la linea
                lineOptions.width(10);
                //Define el color de la linea
                lineOptions.color(Color.BLUE);


                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            // Añade la linea en el mapa si no es nula
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");

            }


        }

    }

    /**
     * Clase para cronometrar el tiempo de ruta
     * Actualiza el TextField tfTiempo
     */
    class Cronometro extends AsyncTask < Void, String , Void > {

        boolean isPaused = true;

        @Override
        protected Void doInBackground(Void... voids) {


            int minutos;
            int segundos;
            int horas;

            String total;

            //Si el hilo es cancelado detiene el bucle y por tanto el contador
            while(!isCancelled()) {

                while(isPaused){


                    //Contador de Horas Minutos Segundos
                    for (horas = 0; horas < 100; horas++) {

                        for (minutos = 0; minutos < 60; minutos++) {

                            for (segundos = 0; segundos < 60; segundos++) {

                                //Genera un String con hh:mm:ss
                                total = (horas + " h: " + minutos + " m: " + segundos + " s");
                                esperaSegundo();
                                publishProgress(total);
                            }
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            //Actualiza el TextField con el String generado
            tiempo.setText(values[0]);

        }

        private void esperaSegundo() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void sleep(long sleepDuration)
        {
            try
            {
                Thread.sleep(sleepDuration);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        public void pause()
        {
            this.isPaused = true;
        }

        public void resume()
        {
            this.isPaused = false;
        }
    }


    }




