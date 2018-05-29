package com.example.berny.motoruta;

import android.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class GuardarRutaActivity extends FragmentActivity implements OnMapReadyCallback {

    private MainActivity Mapa;
    private GoogleMap mMap;

    private RatingBar ratingBar;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_ruta);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);


        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        guardar = (Button) findViewById(R.id.bGuardar);



        //-----------------     BOTONES            -------------------------------------------------


        //Escuchador de clicks en botones lanza vista buscar ruta
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Float estrellas = ratingBar.getRating();
                System.out.println(estrellas.toString());
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5)
                .color(Color.RED));
    }


}
