package com.example.berny.motoruta;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.berny.motoruta.DataBase.ConexionSQLiteHelper;
import com.example.berny.motoruta.Utilidades.Utilidades;

public class MainActivity extends Activity {

    //Conexion con la BBDD
    public ConexionSQLiteHelper con;
    //Botones de la vista
    private Button buscarRuta, crearRuta;
    //Imagen hace referancia al gif
    private ImageView imageView;

    //Controla si hay alguna ruta guardada en la tabla tbl_ruta de la BBDD
    boolean alguna_ruta_guardada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recibe datos de Splash (si hay alguna ruta guardada
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            //tfTiempo.setText(tiempo);
            alguna_ruta_guardada = parametros.getBoolean("ruta_guardada");

        }

        //------------------------BASE DATOS -------------------------------------------------------

        //INICIAMOS LA CONECSION LA BBDD Creara las tablas si es la 1 vez que se ejecuta
        con = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null, Utilidades.DATABASE_VERSION);

        //TODO Si se quiere provar la tabla tbl_usuario de la BBDD
        //crearUsuarioSQL();

        //----------------------  ANIMACION  Aventura ----------------------------------------------
        //Asignamos ImageView
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.loading);
        //Ponemos animacion en ImageView
        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
        frameAnimation.start();


        //-----------------     BOTONES            -------------------------------------------------

        buscarRuta = (Button) findViewById(R.id.bBuscarRuta);
        crearRuta = (Button) findViewById(R.id.bCrearRuta);

        //Escuchador de clicks en boton lanza vista Buscar Ruta
        buscarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Controlamos si hay alguna ruta en la BBDD almacenada
                if (alguna_ruta_guardada) {

                    //Lanzamos BuscarRutaActivity
                    Intent buscar = new Intent(MainActivity.this, BuscarRutaActivity.class);
                    //Iniciamos actividad
                    startActivity(buscar);

                 //Avisamos al usuario que cree una nueva ruta ya que no hay ninguna guardada para mostrar
                }else {

                    Toast toast = Toast.makeText(getBaseContext(), R.string.Toast_NO_iniciado, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        //Escuchador de clicks en boton Crear Ruta
        crearRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Lanza Actividad CrearRuta
                Intent crear = new Intent(MainActivity.this, CrearRutaActivity.class);
                startActivity(crear);

            }
        });

        //..........................................................................................
    }

    /**
     * Crea un usuario en la BBDD.
     * Por ahora a hardCode pero se deja preparado para futuro.
     * Obtendra datos de un formulario y hara el insert.
     */
    private void crearUsuarioSQL() {

        //Escribe en la BBDD
        SQLiteDatabase db = con.getWritableDatabase();
        //Activa las Foreing_keys de la BBDD
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

        String alias="Manolete";
        String email="manolo@gtr.es";
        String pass="passeo";
        String moto="yamaha R1";
        int nivel=2;

        //Insert into usuario
        String insert = "INSERT INTO tbl_usuario (alias, email, pass, moto, nivel)" +
                " VALUES ('"+alias+"','"+email+"','"+pass+"','"+moto+ "',"+ nivel+");";
        db.execSQL(insert);

        //Cerramos conecsion
        db.close();
    }
}
