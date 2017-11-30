package com.example.infot.calculadorahorizontal;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonMenos, buttonDivision,
            buttonMultiplicar, buttonMas, buttonC, buttonIgual;

    CheckBox onoff;


    float primernumero, segundonumero;

    boolean Suma, Resta, Multiplicacion, Division;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaro el Panel
        final TextView resultado = (TextView) findViewById(R.id.textViewPanel);
        // Declaro el checkbox
        final CheckBox onoff = (CheckBox) findViewById(R.id.checkBoxOn);
        // Color del Panel en azul
        resultado.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        // Declaro els botons

        button0 = (Button) findViewById(R.id.boton0);
        button1 = (Button) findViewById(R.id.boton1);
        button2 = (Button) findViewById(R.id.boton2);
        button3 = (Button) findViewById(R.id.boton3);
        button4 = (Button) findViewById(R.id.boton4);
        button5 = (Button) findViewById(R.id.boton5);
        button6 = (Button) findViewById(R.id.boton6);
        button7 = (Button) findViewById(R.id.boton7);
        button8 = (Button) findViewById(R.id.boton8);
        button9 = (Button) findViewById(R.id.boton9);
        buttonMas = (Button) findViewById(R.id.botonmas);
        buttonMenos = (Button) findViewById(R.id.botonmenos);
        buttonDivision = (Button) findViewById(R.id.botondividir);
        buttonMultiplicar = (Button) findViewById(R.id.botonmultiplicar);
        buttonIgual = (Button) findViewById(R.id.botonigual);
        buttonC =(Button) findViewById(R.id.buttonC);

        //Escuchador de checkbox encendio o apagado
        onoff.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Si el checkbox esta activo botones activados
                if (onoff.isChecked()){
                    resultado.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    resultado.setText(""); // Ponemos el panel de nuevo a null
                    button0.setEnabled(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    button4.setEnabled(true);
                    button5.setEnabled(true);
                    button6.setEnabled(true);
                    button7.setEnabled(true);
                    button8.setEnabled(true);
                    button9.setEnabled(true);
                    buttonC.setEnabled(true);
                    buttonMas.setEnabled(true);
                    buttonMenos.setEnabled(true);
                    buttonDivision.setEnabled(true);
                    buttonMultiplicar.setEnabled(true);
                    buttonIgual.setEnabled(true);

                }
                //Si no esta cheked inactivos
                else{
                    resultado.setTextColor(getResources().getColor(R.color.colorAccent));
                    resultado.setText("OFF");  //Colocamos Off en el panel
                    button0.setEnabled(false);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                    button5.setEnabled(false);
                    button6.setEnabled(false);
                    button7.setEnabled(false);
                    button8.setEnabled(false);
                    button9.setEnabled(false);
                    buttonC.setEnabled(false);
                    buttonMas.setEnabled(false);
                    buttonMenos.setEnabled(false);
                    buttonDivision.setEnabled(false);
                    buttonMultiplicar.setEnabled(false);
                    buttonIgual.setEnabled(false);
                }
            }
        });

        //Escuchador de clicks en botones, pone el numero del boton en el panel
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "1");
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "2");
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "3");
            }
        });
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "4");
            }
        });
        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "5");
            }
        });
        button6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "6");
            }
        });
        button7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "7");
            }
        });
        button8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "8");
            }
        });
        button9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "9");
            }
        });
        button0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText(resultado.getText() + "0");
            }
        });
        //Boton CE pone a 0 la variante principal y el Panel
        buttonC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText("");

            }
        });

        // Escuchador para el boton mas, encapsula en un float variable el texto del Pamel
        buttonMas.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si resultado esta en null pone el Panel en blanco
                if (resultado == null) {
                    resultado.setText("");
                    //Si no encapsula el texto en la variante primernumero
                } else {
                    primernumero = Float.parseFloat(resultado.getText() + "");
                    Suma = true;
                    resultado.setText(null);
                }
            }
        });

        //Escuchador boton menos
        buttonMenos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                primernumero = Float.parseFloat(resultado.getText() + "");
                Resta = true;
                resultado.setText(null);
            }
        });

        //Escuchador boton multiplicar
        buttonMultiplicar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                primernumero = Float.parseFloat(resultado.getText() + "");
                Multiplicacion = true;
                resultado.setText(null);
            }
        });
        //Escuchador boton Division
        buttonDivision.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                primernumero = Float.parseFloat(resultado.getText() + "");
                Division = true;
                resultado.setText(null);
            }
        });

        //Escuchador para el boton igual, comprueva por booleans que boton de operacion se apreto
        buttonIgual.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                segundonumero = Float.parseFloat(resultado.getText() + "");

                // Con los booleans mira que boton de operacion se apreto

                //Si es suma, suma el primer numero con el segundo
                if (Suma == true) {
                    resultado.setText(primernumero + segundonumero + "");
                    //Vuelve a poner el boolean en false
                    Suma = false;
                }
                //Lo mismo si resta
                if (Resta == true) {
                    resultado.setText(primernumero - segundonumero + "");
                    //Vuelve a poner el boolean en false
                    Resta = false;
                }
                //Lo mismo si multiplica
                if (Multiplicacion == true) {
                    resultado.setText(primernumero * segundonumero + "");
                    //Vuelve a poner el boolean en false
                    Multiplicacion = false;
                }
                //Lo mismo si divide
                if (Division == true) {
                    resultado.setText(primernumero / segundonumero + "");
                    //Vuelve a poner el boolean en false
                    Division = false;
                }
            }
        });
    }

}
