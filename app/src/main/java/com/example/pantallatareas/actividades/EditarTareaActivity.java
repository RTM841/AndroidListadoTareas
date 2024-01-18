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





    public EditarTareaActivity() throws ParseException {
    }


    @SuppressLint({"MissingInflatedId", "RestrictedApi"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //tituloT = findViewById(R.id.txtVTarea);
        //tituloT.setText("Editar Tarea");

        Tarea tarea = getIntent().getParcelableExtra("tareaEditar");
        fragmento1 = FragmentoUno.newInstance(tarea);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos,fragmento1).commit();
        baseDatosApp = BaseDatosApp.getInstance(getActivity(this).getApplicationContext());
        setContentView(R.layout.activity_navigation);

        //Recibimos la tarea que va a ser editada
       /* Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null) {
                this.tareaEditable = bundle.getParcelable("TareaEditable");
            }
        }catch (NullPointerException e){
            Log.e("Bundle recibido nulo", e.toString());
        }*/

        //Instanciamos el ViewModel
        tareaViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);





       /*nombreTarea = findViewById(R.id.editTetxtTituloTarea);
       fechaIni = findViewById(R.id.editTextFechaCreacion);
       fechaFin = findViewById(R.id.editTextFechaFinal);
       progresoTarea = findViewById(R.id.spinerProgreso);
       prio = findViewById(R.id.cbPrioritaria);




       nombreTarea.setText(tarea.getNombreTarea());
       fechaIni.setText(tarea.getFechaIni());
       fechaFin.setText(tarea.getFechaFin());
       progresoTarea.setSelection(barraProgreso(tarea.getPorcentajeTarea()));
       if (tarea.isPrioritaria()){
           prio.isChecked();
       }*/

        //Si hay estado guardado
        /*if (savedInstanceState != null) {
            // Recuperar el ID o información del fragmento
            int fragmentId = savedInstanceState.getInt("fragmentoId", -1);

            if (fragmentId != -1) {
                // Usar el ID o información para encontrar y restaurar el fragmento
                cambiarFragmento(Objects.requireNonNull(fragmentManager.findFragmentById(fragmentId)));
            }else{
                //Si no tenemos ID de fragmento cargado, cargamos el primer fragmento
                cambiarFragmento(fragmento1);
            }
        }else{
            //Si no hay estado guardado, cargamos el primer fragmento
            cambiarFragmento(fragmento1);
            //Escribimos valores en el ViewModel
            tareaViewModel.setNombre(tareaEditable.getNombreTarea());
            tareaViewModel.setFechaIni(tareaEditable.getFechaIni());
            tareaViewModel.setFechaFin(tareaEditable.getFechaFin());
            tareaViewModel.setEstadoTarea(String.valueOf(tareaEditable.getPorcentajeTarea()));
            tareaViewModel.setPrioritaria(tareaEditable.isPrioritaria());
            tareaViewModel.setDescip(tareaEditable.getDescripcion());
        }*/
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int fragmentID = Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.contenedorFragmentos)).getId();
        outState.putInt("fragmentoId", fragmentID);
    }

   /* public void onBotonCancelarClicked() {
        Intent aListado = new Intent();
        //Indicamos en el resultado que ha sido cancelada la actividad
        setResult(RESULT_CANCELED, aListado);
        //Volvemos a la actividad Listado
        finish();
    }*/

   /* private Date convertirStringADate(String fecha) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
        dateFormat.parse(fecha);
        try {
            return dateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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

    int numD = numeroDias(fechaObjetivo);*/

    @Override
    public void onGuardar() throws ParseException {
        nombreTarea = tareaViewModel.getNombre().getValue();
        fechaIni = tareaViewModel.getFechaIni().getValue().toString();
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


    finish();

    }


    public void cambiarFragmento(Fragment fragment){
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor_frag, fragment)
                    .commit();
        }
    }

    /*@Override
    public void siguiente() {

        //Leemos los valores del formulario del fragmento 1
        titulo = tareaViewModel.getNombre().getValue();
        fechaCreacion = tareaViewModel.getFechaIni().getValue();
        fechaObjetivo = tareaViewModel.getFechaFin().getValue();
        progreso = Integer.valueOf(tareaViewModel.getEstadoTarea().getValue());
        prioritaria = tareaViewModel.getPrioritariaValue();

        //Cambiamos el fragmento
        cambiarFragmento(fragmento2);
    }*/


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
