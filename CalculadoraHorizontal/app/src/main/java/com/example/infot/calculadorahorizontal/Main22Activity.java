package com.example.infot.calculadorahorizontal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main22Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);

        Bundle datos = getIntent().getExtras();

        TextView t =(TextView) findViewById(R.id.textView);

        Float num1 = datos.getFloat("resultado");

        t.setText(""+num1);
    }
}
