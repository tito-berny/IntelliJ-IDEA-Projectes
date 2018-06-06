package com.example.berny.motoruta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.berny.motoruta.DataBase.ConexionSQLiteHelper;
import com.example.berny.motoruta.Utilidades.Utilidades;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class BuscarRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //public ConexionSQLiteHelper con;

    private RatingBar ratingBar;

    private Button anterior, siguiente;

    LocationManager locationManager;


    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ruta);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mRuta);
        mapFragment.getMapAsync(this);

        //onMapReady(mMap);

        anterior = (Button) findViewById(R.id.bAnterior);
        siguiente = (Button) findViewById(R.id.bSiguiente);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);


        //-----------------     BOTONES            -------------------------------------------------


        //Escuchador de clicks en botones lanza vista buscar ruta
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent buscar = new Intent(BuscarRutaActivity.this, MapsActivity.class);

                //buscar.putExtra("conexion", (Parcelable) con);

                startActivity(buscar);

            }
        });

        //Escuchador de clicks en botones, pone 0 todos los valores
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crear = new Intent(BuscarRutaActivity.this, CrearRutaActivity.class);

                startActivity(crear);

            }
        });

        //..........................................................................................

        //SQLiteDatabase db = con.getReadableDatabase();

/*
            TextView alias = (TextView) findViewById(R.id.tvAlias);
            TextView email = (TextView) findViewById(R.id.tvEmail);
            TextView moto = (TextView) findViewById(R.id.tvMoto);
            TextView pass = (TextView) findViewById(R.id.tvPass);
            TextView nivel = (TextView) findViewById(R.id.tvNivel);

            con = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME, null, Utilidades.DATABASE_VERSION);

            SQLiteDatabase db = con.getReadableDatabase();

            //con = getIntent().getExtras().getString("conexion");

            Cursor c = db.rawQuery(" SELECT alias,pass FROM tbl_usuario WHERE id='1' ", null);

            if (c.moveToFirst()) {
                do {
                    // Passing values
                    String column1 = c.getString(0);
                    String column2 = c.getString(1);

                    alias.setText(column1);
                    pass.setText(column2);


                    // Do something Here with values
                } while (c.moveToNext());
            }
            c.close();
            db.close();

*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng efds = new LatLng(51.5, -0.1);

        // Add a thin red line from London to New York.
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5)
                .color(Color.BLUE));

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(efds).title("Marca en Sydney"));


        //Determina la posicion de la camara
       // CameraPosition CameraPosition = new CameraPosition(efds,10,55,5);
        //mueve la camara a la posicion
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(efds, 10));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(efds));


        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //    return;
        //}
        //mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));

        //Poner en nuestra ubi
       // mMap.setMyLocationEnabled(true);

        //Quitar boton Localizacion
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        //mMap.setBuildingsEnabled(true);
    }

}
