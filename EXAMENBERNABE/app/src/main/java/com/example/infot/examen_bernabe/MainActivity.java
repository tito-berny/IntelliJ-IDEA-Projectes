package com.example.infot.examen_bernabe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button calculo, reset, info;
    CheckBox encendido, Ceuro, Cdolar, Clibra;
    EditText Teuro, Tdolar, Tlibra,Tcontador ;

    Float numeroEuro, numeroDolar, numeroLibra;
    int contador=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculo = (Button) findViewById(R.id.buttonCalculo);
        reset = (Button) findViewById(R.id.buttonReset);
        info = (Button) findViewById(R.id.buttonInfo);

        encendido = (CheckBox) findViewById(R.id.checkBoxEncendido);
        Ceuro = (CheckBox) findViewById(R.id.checkBoxEuro);
        Cdolar = (CheckBox) findViewById(R.id.checkBoxDolar);
        Clibra = (CheckBox) findViewById(R.id.checkBoxLibra);

        Teuro = (EditText) findViewById(R.id.editTextEuro);
        Tdolar = (EditText) findViewById(R.id.editTextDolar);
        Tlibra = (EditText) findViewById(R.id.editTextLibra);

        Tcontador = (EditText) findViewById(R.id.editTextContador);




        //Escuchador de checkbox encendio o apagado
        encendido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Si el checkbox esta activo botones activados
                if (encendido.isChecked()) {

                    calculo.setEnabled(true);
                    reset.setEnabled(true);
                    info.setEnabled(true);
                    Ceuro.setEnabled(true);
                    Cdolar.setEnabled(true);
                    Clibra.setEnabled(true);
                    Teuro.setEnabled(true);
                    Tdolar.setEnabled(true);
                    Tlibra.setEnabled(true);
                    Tcontador.setEnabled(true);

                } else {
                    calculo.setEnabled(false);
                    reset.setEnabled(false);
                    info.setEnabled(false);
                    Ceuro.setEnabled(false);
                    Cdolar.setEnabled(false);
                    Clibra.setEnabled(false);
                    Teuro.setEnabled(false);
                    Tdolar.setEnabled(false);
                    Tlibra.setEnabled(false);
                    Tcontador.setEnabled(false);
                }
            }
        });


        //Escuchador de clicks en botones,calcula
        calculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                contador=contador++;
               Tcontador.setText(""+contador);

                if (Ceuro.isChecked()){

                    numeroEuro = Float.parseFloat(Teuro.getText() + "");
                    numeroLibra = Float.parseFloat(Teuro.getText() + "");
                    numeroDolar = Float.parseFloat(Teuro.getText() + "");

                    double Libra = 1.12;
                    double dolar = 0.83;

                    Libra = numeroEuro * Libra;
                    dolar = numeroEuro * dolar;

                    Tlibra.setText(""+Libra);
                    Tdolar.setText(""+dolar);

                }else {
                    Teuro.setText("0");
                }

                if (Cdolar.isChecked()){



                }else {
                    Tdolar.setText("0");
                }
                if (Clibra.isChecked()){


                }else {
                    Tlibra.setText("0");
                }

            }
        });

        //Escuchador de clicks en botones, pone 0 todos los valores
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Teuro.setText("0");
                Tdolar.setText("0");
                Tlibra.setText("0");

            }
        });

        //Escuchador de clicks en botones, pone 0 todos los valores
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nom = new Intent(MainActivity.this, Main2Activity.class);
                Tcontador.setText("0");

                Float i =Float.parseFloat(Tcontador.getText() + "");

                nom.putExtra("resultado", i);
                startActivity(nom);
            }
        });

    }

}





