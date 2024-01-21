package com.example.pantallatareas.actividades;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditarTareaActivity extends AppCompatActivity implements FragmentoDos.ComunicacionFragmento1, FragmentoUno.ComunicacionFragmento{

    private Tarea tareaEditable;
    private CompartirViewModel tareaViewModel;
    private String nombreTarea, fechaIni, fechaFin, progesoBarra, descripcion, urlDocumento, urlImagen, urlAudio, urlVideo, titulo;

    private Boolean esPrio;
    private Integer progreso, idPro, numeroProgreso;
    private Boolean prioritaria;
    private FragmentManager fragmentManager;
    private FragmentoUno fragmento1;
    private final Fragment fragmento2 = new FragmentoDos();

    private TextView tituloT;

    private Spinner progresoTarea;

    private CheckBox prio;

    private Button siguiente;

    private BaseDatosApp baseDatosApp;

    Tarea tarea;





    public EditarTareaActivity() throws ParseException {
    }


    @SuppressLint({"MissingInflatedId", "RestrictedApi"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //tituloT = findViewById(R.id.txtVTarea);
        //tituloT.setText("Editar Tarea");

        tarea = getIntent().getParcelableExtra("tareaEditar");
        fragmento1 = FragmentoUno.newInstance(tarea);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos,fragmento1).commit();
        baseDatosApp = BaseDatosApp.getInstance(getActivity(this).getApplicationContext());
        setContentView(R.layout.activity_navigation);

        //Instanciamos el ViewModel
        tareaViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int fragmentID = Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.contenedorFragmentos)).getId();
        outState.putInt("fragmentoId", fragmentID);
    }

    @Override
    public void onGuardar() throws ParseException {

        nombreTarea =  tareaViewModel.getNombre().getValue();
        fechaIni =  tareaViewModel.getFechaIni().getValue().toString();
        fechaFin = tareaViewModel.getFechaFin().getValue().toString();
        progesoBarra = tareaViewModel.getEstadoTarea().getValue();
        descripcion = tareaViewModel.getGetDescrip().getValue();
        esPrio = tareaViewModel.getPrioritariaValue();
        urlDocumento = tareaViewModel.geturlDocumento().getValue();
        urlImagen = tareaViewModel.getUrlImagen().getValue();
        urlAudio = tareaViewModel.getUrlAudio().getValue();
        urlVideo = tareaViewModel.getUrlVideo().getValue();

        int numD = numeroDias(fechaFin);
        Tarea tarealistadoE = null;
        tarealistadoE = new Tarea(nombreTarea.toString(), barraProgreso(progesoBarra), fechaIni, fechaFin, numD, esPrio, descripcion.toString(), urlDocumento, urlImagen, urlAudio, urlVideo);

        //Creamos un objeto de la clase que realiza la inserción en un hilo aparte Executor
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new InsertarProducto(tarealistadoE));
        executor.execute(new BorrarTarea(tarea));


    finish();

    }


    public void cambiarFragmento(Fragment fragment){
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor_frag, fragment)
                    .commit();
        }
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

    @Override
    public void siguiente() {

    }

    public int numeroDias(String fecha) throws ParseException {
        Date date1 = convertirStringADate(fecha);
        Date date2 = new Date(); // La fecha actual

        // Calcula la diferencia en milisegundos
        long diferenciaEnMilisegundos = Math.abs(date2.getTime() - date1.getTime());

        // Convierte la diferencia de milisegundos a días
        int diferenciaEnDias = (int) (diferenciaEnMilisegundos / (24 * 60 * 60 * 1000));

        TextView resultadoTextView = new TextView(this);


// Establece el resultado en el TextView
        resultadoTextView.setText(String.valueOf(diferenciaEnDias));

        return diferenciaEnDias;
    }

    private Date convertirStringADate(String fecha) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Elimina espacios en blanco de la cadena de fecha
        fecha = fecha.replaceAll("\\s", "");
        //dateFormat.parse(fecha);
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


    class BorrarTarea implements Runnable {

        private Tarea tarea;

        public BorrarTarea(Tarea tarea) {
            this.tarea = tarea;
        }

        @Override
        public void run() {
            baseDatosApp.tareaDao().borrarTarea(tarea);
        }
    }
}
