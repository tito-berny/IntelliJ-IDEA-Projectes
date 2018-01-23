package com.example.bernabe.pelota_hilos_persistencia_sonidos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void ayuda(View view){

        Intent intencion =new Intent(this, AyudaActivity.class);

        startActivity(intencion);
    }

    /**
     * Asigna la dificultad del juego segun el boton pulsado por el usuario
     * @param vista
     */
    public void dificultad(View vista) {

        //Recoge ek texto del boton pulsado por el usuario
        String dific = (String) ((Button) vista).getText();

        int dificultad = 1;

        //Si pulsa sobre el boton Standard pone la dificultad en 2
        if (dific.equals("Standard")) dificultad=2;
        //Si pulsa sobre el boton Dificult pone la dificultad en 3
        if (dific.equals("Dificult")) dificultad=3;

        //PAsamos los datos de la dificultad
        Intent in = new Intent(this, Gestion.class);

        in.putExtra("DIFICULTAD", dificultad);

        //Empezamos el juego
        //startActivity(in);

        //Si esperamos un resulado la la actividad como es el caso de RECORD
        //iniciaremos la actividad con ForResult i le pasaremos 1 codigo de solucitud
        startActivityForResult(in,1);

    }

    /**
     * Sobreescribimos el metodo para capturar los parametros que pasamos entre aplicaciones(Recor)
     * @param peticion Lo damos al hacer  startActivityForResult(in,1);
     * @param codigo Si la activity elhilo a terminado correctamente
     * @param puntuacion El numero de toques que recibimos de la actividad
     */
    protected void onActivityResult(int peticion, int codigo, Intent puntuacion){

        //Si la peticion no es 1 o el sultado de la activity no es ok (no a terminado)
        if(peticion != 1 || codigo != RESULT_OK) return;

        int resultado = puntuacion.getIntExtra("PUNTUACION",0);

        //Si el resultado es mayor del record
        if (resultado > record){
            //El nuevo record sera el resultado obtenido
            record = resultado;

            TextView caja = (TextView)findViewById(R.id.TVRecord);

            //Escribe el record en el TF
            caja.setText("Record : "+record);

            //Guardamos el nuevo record
            guardarRecord();

        }else {

            String puntuacionPartida = " " + resultado;

            Toast miToast = Toast.makeText(this, puntuacionPartida, Toast.LENGTH_SHORT);

            miToast.setGravity(Gravity.CENTER, 0,0);

            miToast.show();
        }


    }

    /**
     *Se ejecuta cuando terminemos la partida,Le indicamos que lea el record en el momento que la activity sale del pause
     */
    public void onResume(){
        super.onResume();

        //Se encarga de leer el record
        leerRecord();

    }

    /**
     * Guarda los datos del recor en el momento que la activity sale del pause
     */
    private void guardarRecord() {

        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor mieditor = datos.edit();

        //guardamos con una key y los datos a guardar en este caso la variable record
        mieditor.putInt("RECORD", record);

    }

    /**
     * Lee los datos guardados anteriormente en guardarRecord para usarlos en onResume();
     */
    private void leerRecord() {

        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);

        //Recuperamos los datos
        record = datos.getInt("RECORD", 0);

        TextView caja =(TextView)findViewById(R.id.TVRecord);

        //Escribimos en el TF
        //caja.setText("Record: "+ record);
    }
}
