package com.example.pantallatareas.actividades;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pantallatareas.Modelos.Tarea;
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


        Tarea tarea = getIntent().getParcelableExtra("tarea");



        txtNombre = findViewById(R.id.txtNombre);
        txtDias = findViewById(R.id.txtDiasFin);
        txtDescripcion = findViewById(R.id.txtDescrip);

        btDocu = findViewById(R.id.btDocumento);
        btImg = findViewById(R.id.btImagen);
        btaud = findViewById(R.id.btAudio);
        btvideo = findViewById(R.id.btVideo);


        txtNombre.setText(tarea.getNombreTarea());
        txtDias.setText(String.valueOf(tarea.getDiasTarea()));
        txtDescripcion.setText(tarea.getDescripcion());



        if (tarea.getURL_doc()== null)
        { btDocu.setText("No tiene");}
        else{ btDocu.setText(tarea.getURL_doc().toString());}


        if (tarea.getURL_img() == null)
        { btImg.setText("No tiene");}
        else{ btImg.setText(tarea.getURL_img().toString());}

        if (tarea.getURL_aud() == null)
        { btaud.setText("No tiene");}
        else{ btaud.setText(tarea.getURL_aud().toString());}


        if (tarea.getURL_vid() == null)
        { btvideo.setText("No tiene");}
        else{ btvideo.setText(tarea.getURL_vid().toString());}



    }


    private Tarea convertirStringATarea(String tareaString) {
        // Dividir la cadena utilizando un separador (puedes ajustar el separador según tus necesidades)
        String[] partes = tareaString.split(";");

        // Asumir que las partes están en el orden correcto
        String nombre = partes[0];
        int dias = Integer.parseInt(partes[1]);
        String descripcion = partes[2];
        String urlDoc = partes[3];
        String urlImg = partes[4];
        String urlAud = partes[5];
        String urlVid = partes[6];

        // Crear una instancia de Tarea con los valores obtenidos
        return new Tarea(nombre, dias, descripcion, urlDoc, urlImg, urlAud, urlVid);
    }

}
