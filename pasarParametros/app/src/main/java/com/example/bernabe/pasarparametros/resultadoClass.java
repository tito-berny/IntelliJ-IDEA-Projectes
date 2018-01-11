package com.example.bernabe.pasarparametros;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by bernabe on 8/1/18.
 */

public class resultadoClass extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado);


        Bundle datos = getIntent().getExtras();

        int num1=datos.getInt("numero1");
        int num2=datos.getInt("numero2");

        int resultado = num1 + num2;

        TextView t =(TextView) findViewById(R.id.textView);

        t.setText("El resultado es : " + resultado);

    }

}
