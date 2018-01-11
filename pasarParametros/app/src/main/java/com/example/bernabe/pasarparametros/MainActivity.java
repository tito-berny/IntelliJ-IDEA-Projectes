package com.example.bernabe.pasarparametros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DECLARAR EN CADA FUNCION NO SE PUEDE HACER GLOBAL
        resultado = findViewById(R.id.textViewResultado);
        EditText num1 = (EditText) findViewById(R.id.num1);
        EditText num2 = (EditText) findViewById(R.id.num2);

     //PARA ASIGNAR UN INT A UN TEXTVIEW NECESITAS ENCADENAR UN STRING
     resultado.setText(""+ 0);

     num1.setText(""+0);
     num2.setText(""+0);

    }

    public void aSumar (View view){

        TextView resultado = (TextView) findViewById(R.id.textViewResultado);

        Intent i = new Intent(this, resultadoClass.class);

        EditText num1 = (EditText) findViewById(R.id.num1);
        EditText num2 = (EditText) findViewById(R.id.num2);

        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());

        i.putExtra("numero1",n1);
        i.putExtra("numero2",n2);

        startActivity(i);

        int res = n1 + n2;

       resultado.setText(""+ res);
    }

    //----------------- GUARDAR DATOS CUANDO GIRA EL MOVIL------------------------------------------

    //sobreescribimos metodo heredado de Activity, necesita un Bundle
    public void onSaveInstanceState (Bundle estado){

        //Guardamos un Int en el Bundle, necesita una etiqueta (String) y el Int
        estado.putInt("cuenta", 5);

        //Le pasamos el Bundle al Metodo del padre (Activity), queda guardado el Bundle en memoria
        super.onSaveInstanceState(estado);
    }


    public void onRestoreInstanceState (Bundle estado){

        TextView resultado = (TextView) findViewById(R.id.textViewResultado);

        //Recuperamos el Bundle guardado
        super.onRestoreInstanceState(estado);

        //Asignamos la variable de tipo Int a el Bundle con la clave cuenta
        int contador = estado.getInt("cuenta");

        //mostramos en el TextView la Variable recuperada
        resultado.setText(""+ contador);
    }

}
