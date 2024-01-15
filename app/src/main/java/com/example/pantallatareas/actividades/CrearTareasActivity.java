package com.example.pantallatareas.actividades;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;


import com.example.pantallatareas.Modelos.Tarea;
import com.example.pantallatareas.R;
import com.example.pantallatareas.basedatos.BaseDatosApp;
import com.example.pantallatareas.fragmentos.FragmentoDos;
import com.example.pantallatareas.fragmentos.FragmentoUno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CrearTareasActivity extends AppCompatActivity implements FragmentoDos.ComunicacionFragmento1{

    private FragmentoUno fragmentoUno;
    private CompartirViewModel compartirViewModel;
    private FragmentManager fragmentManager;
    private Button continuar;
    private Button cancelar;
    private String nombreTarea, fechaIni, fechaFin, progesoBarra, descripcion, urlDocumento, urlImagen, urlAudio, urlVideo;

private EditText titulo;
    private Boolean esPrio;
    private Integer numDias, numeroProgreso;
    private Tarea tarealistado;

    private BaseDatosApp baseDatosApp;
    private Context activity;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_navigation);

        compartirViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

        fragmentoUno = new FragmentoUno();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos,fragmentoUno).commit();

        baseDatosApp = BaseDatosApp.getInstance(getActivity(this).getApplicationContext());



    }

    @Override
    public void onGuardar() throws ParseException {



        nombreTarea = compartirViewModel.getNombre().getValue();
        fechaIni = compartirViewModel.getFechaIni().getValue().toString();
        fechaFin = compartirViewModel.getFechaFin().getValue().toString();
        progesoBarra = compartirViewModel.getEstadoTarea().getValue();
        descripcion = compartirViewModel.getGetDescrip().getValue();
        esPrio = compartirViewModel.getPrioritariaValue();
        urlDocumento = compartirViewModel.geturlDocumento().getValue();
        urlImagen = compartirViewModel.getUrlImagen().getValue();
        urlAudio = compartirViewModel.getUrlAudio().getValue();
        urlVideo = compartirViewModel.getUrlVideo().getValue();






        int numD = numeroDias(fechaFin);

        Tarea tarealistado = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int contadorID = 1;
            if (barraProgreso(progesoBarra) == 100) {

                TextView tachado = new TextView(this);

                tachado.setText(nombreTarea);
                Paint paint = tachado.getPaint();
                paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                paint.setAntiAlias(false);

                tarealistado = new Tarea(nombreTarea, barraProgreso(progesoBarra), fechaIni, 0, descripcion.toString(), esPrio);


            } else {
                tarealistado = new Tarea(nombreTarea.toString(), barraProgreso(progesoBarra), fechaIni, numD, descripcion.toString(), esPrio);

            }
        }


        //Creamos el objeto que vamos a insertar
        tarealistado = new Tarea(nombreTarea.toString(), barraProgreso(progesoBarra), fechaIni, fechaFin, numD, esPrio, descripcion.toString());

        //Creamos un objeto de la clase que realiza la inserción en un hilo aparte Executor
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new InsertarProducto(tarealistado));



        //Intent intentVuelta = new Intent();
        //intentVuelta.putExtra("TareaSeteada", tarealistado);
        //setResult(RESULT_OK, intentVuelta);
        finish();

    }

    public int barraProgreso(String nombre){
        if (nombre.equals("No iniciada")){
            numeroProgreso = 0;
        } else if (nombre.equals("Iniciada")) {
            numeroProgreso = 25;
        } else if (nombre.equals("Avanzada")) {
            numeroProgreso = 50;
        } else if (nombre.equals("Casi finalizada")) {
            numeroProgreso = 75;
        }else{
            numeroProgreso = 100;
        }
        return numeroProgreso;
    }




    public int numeroDias(String fecha) throws ParseException {
        Date date1 = convertirStringADate(fecha);
        Date date2 = new Date(); // La fecha actual

        // Calcula la diferencia en milisegundos
        long diferenciaEnMilisegundos = Math.abs(date2.getTime() - date1.getTime());

        // Convierte la diferencia de milisegundos a días
        int diferenciaEnDias = (int) (diferenciaEnMilisegundos / (24 * 60 * 60 * 1000));

        TextView resultadoTextView = new TextView(this);



        // Verifica si la resta es negativa
        if (diferenciaEnDias < 0) {
            // Si es negativa, establece el texto en rojo
            resultadoTextView.setTextColor(Color.RED);
        } else {
            // Si no es negativa, establece el texto en el color predeterminado (por ejemplo, negro)
            resultadoTextView.setTextColor(Color.GREEN);
        }

// Establece el resultado en el TextView
        resultadoTextView.setText(String.valueOf(diferenciaEnDias));

        return diferenciaEnDias;
    }

    private Date convertirStringADate(String fecha) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
        dateFormat.parse(fecha);
        try {
            return dateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    //Clase que inserta un objeto producto en la base de datos usando un hilo diferente al principal.
    class InsertarProducto implements Runnable {

        private Tarea tarea;

        public InsertarProducto(Tarea tarea) {
            this.tarea = tarea;
        }

        @Override
        public void run() {
            baseDatosApp.tareaDao().insertarTarea(tarea);
        }
    }



}
