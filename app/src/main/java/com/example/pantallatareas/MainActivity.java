package com.example.pantallatareas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity  extends AppCompatActivity {

    private Button boton;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_incial);

        boton = findViewById(R.id.btInicio);
        boton.setOnClickListener(this::abrirListado);

    }

    public  void abrirListado(View view){
        // Crea un Intent para abrir la nueva actividad
        Intent intent = new Intent(MainActivity.this, ListadoTareasActivity.class);

        // Inicia la nueva actividad
        startActivity(intent);
    }

}
