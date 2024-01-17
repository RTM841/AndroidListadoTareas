package com.example.pantallatareas.actividades;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pantallatareas.R;
import com.example.pantallatareas.basedatos.BaseDatosApp;

public class DetalleActivity extends AppCompatActivity {

    private TextView txtNombre, txtDias , txtDescripcion;
    private Button btDocu, btImg, btaud, btvideo;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.detalle);

        txtNombre = findViewById(R.id.txtNombre);
        txtDias = findViewById(R.id.txtDiasFin);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        btDocu = findViewById(R.id.btDocumento);

        btDocu.setText("Hola");
    }

}
