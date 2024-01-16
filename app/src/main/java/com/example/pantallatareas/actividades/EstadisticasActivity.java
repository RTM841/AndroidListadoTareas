package com.example.pantallatareas.actividades;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pantallatareas.R;
import com.example.pantallatareas.basedatos.BaseDatosApp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EstadisticasActivity  extends AppCompatActivity {

    private BaseDatosApp baseDatosApp;

    private TextView tInici, tFin, prompro, promFechObj;

    private Button btVovler;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.estadisticas);

        baseDatosApp = BaseDatosApp.getInstance(getActivity(this).getApplicationContext());

        tInici = findViewById(R.id.numeroIn);
        tFin  = findViewById(R.id.numeroFin);
        prompro = findViewById(R.id.numeroProPro);
        promFechObj = findViewById(R.id.numeroFechPro);
        btVovler = findViewById(R.id.btVolver);
        btVovler.setOnClickListener(this::volver);



        inicio();

    }

    @SuppressLint("RestrictedApi")
    public void volver(View view){
        getActivity(this).finish();
    }



    public void inicio(){

        //Listado de Tareas iniciadas
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new listar1());

        //Listado de Tareas Finalizadas
        Executor executor2 = Executors.newSingleThreadExecutor();
        executor2.execute(new listar2());

        //Promedio de Progresos de las tareas
        Executor executor3 = Executors.newSingleThreadExecutor();
        executor3.execute(new listar3());

        //Promedio de Fehcas objetivo
        Executor executor4 = Executors.newSingleThreadExecutor();
        executor4.execute(new listar4());
    }

    class listar1 implements Runnable {

        @Override
        public void run() {

            int resultado = obtenerResultado();
            tInici.setText(String.valueOf(resultado));
        }

        private int obtenerResultado() {
            return baseDatosApp.tareaDao().listadorTareasInciadas();
        }
    }

    class listar2 implements Runnable {
        @Override
        public void run() {

            int resultado = obtenerResultado();
            tFin.setText(String.valueOf(resultado));
        }

        private int obtenerResultado() {
            return baseDatosApp.tareaDao().listadoTareasFinalizadas();
        }

    }

    class listar3 implements Runnable {

        @Override
        public void run() {

            int resultado = obtenerResultado();
            prompro.setText(String.valueOf(resultado));
        }

        private int obtenerResultado() {
            return (int) baseDatosApp.tareaDao().obtenerPromedioProgreso();
        }
    }

    class listar4 implements Runnable {

        @Override
        public void run() {

            int resultado = obtenerResultado();
            promFechObj.setText(String.valueOf(resultado));
        }

        private int obtenerResultado() {
            return (int) baseDatosApp.tareaDao().obtenerPromedioFechaObjetivo();
        }
    }
}
