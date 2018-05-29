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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CrearRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button inicia, finaliza;
    Float lat, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ruta);
        // Obtenga el SupportMapFragment y reciba notificaciones cuando el mapa esté listo para ser utilizado.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);


        //Clases del GPS
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();

        //Permisos del GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Llama al escuchador del gps
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);


        //-----------------     BOTONES            -------------------------------------------------

        inicia = (Button) findViewById(R.id.bIniciarRuta);
        finaliza = (Button) findViewById(R.id.bFinalizarRuta);


        //Escuchador de clicks en botones lanza vista buscar ruta
        inicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (inicia.getText().equals(R.string.iniciar_ruta)) {

                    inicia.setText(R.string.pausa);
                }

                if (inicia.getText().equals(R.string.pausa)) {
                    inicia.setText(R.string.reanudar_ruta);
                }

                finaliza.setEnabled(true);


            }
        });

        //Escuchador de clicks en botones, pone 0 todos los valores
        finaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyLocationListener my = new MyLocationListener();

                Intent guardar = new Intent(CrearRutaActivity.this, GuardarRutaActivity.class);

                //buscar.putExtra("conexion", (Parcelable) con);

                startActivity(guardar);


                //ArrayList<String> lat = locationListener.getRuta();
            }
        });

        //..........................................................................................

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marca en Sydney"));
        //float zoom = 10;
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        //Poner en nuestra ubi
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // DIBUJAR LINEA DE UN PUNTO A OTRO red line from London to New York.
       /* Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5)
                .color(Color.RED));*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {


        ArrayList<String> latlog = new ArrayList<String>();

        int i = 0;

        File internalStorageDir = getFilesDir();
        File ruta = new File(internalStorageDir, "ruta_tmp.txt");
        FileOutputStream fos = null;

        @Override
        public void onLocationChanged(Location loc) {


            latlog.add( loc.getLongitude() + "|" + loc.getLatitude() + "\n");


            //Guardar en fichero temporal carpeta tmp el fichero generado
            //al final cuando se guarde la ruta eliminar y cambiar el nombre
            //La app comprueva que no hay nada en la carpeta temp si explota la app se queda el archivo guardado


            if (i==100) {
                for (String aLista : latlog) {

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

                latlog.clear();
                i=0;

            }else i++;


            Toast.makeText(
                    getBaseContext(),
                    "Locatio changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);

        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("GPS DESACTIVADO");

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
            return latlog;
        }
    }
}


