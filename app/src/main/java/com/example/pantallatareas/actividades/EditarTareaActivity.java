package com.example.pantallatareas.actividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.pantallatareas.Modelos.Tarea;
import com.example.pantallatareas.R;
import com.example.pantallatareas.fragmentos.FragmentoDos;
import com.example.pantallatareas.fragmentos.FragmentoUno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EditarTareaActivity extends AppCompatActivity implements FragmentoDos.ComunicacionFragmento1, FragmentoUno.ComunicacionFragmento{

    private Tarea tareaEditable;
    private CompartirViewModel tareaViewModel;
    private String titulo, descripcion;
    private String fechaCreacion, fechaObjetivo;

    private Integer progreso;
    private Boolean prioritaria;
    private FragmentManager fragmentManager;
    private final Fragment fragmento1 = new FragmentoUno();
    private final Fragment fragmento2 = new FragmentoDos();

    public EditarTareaActivity() throws ParseException {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_tarea);

        //Recibimos la tarea que va a ser editada
        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null) {
                this.tareaEditable = bundle.getParcelable("TareaEditable");
            }
        }catch (NullPointerException e){
            Log.e("Bundle recibido nulo", e.toString());
        }

        //Instanciamos el ViewModel
        tareaViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

       getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_frag,fragmento1).commit();

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
                findFragmentById(R.id.contenedor_frag)).getId();
        outState.putInt("fragmentoId", fragmentID);
    }

   /* public void onBotonCancelarClicked() {
        Intent aListado = new Intent();
        //Indicamos en el resultado que ha sido cancelada la actividad
        setResult(RESULT_CANCELED, aListado);
        //Volvemos a la actividad Listado
        finish();
    }*/

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

    int numD = numeroDias(fechaObjetivo);

    @Override
    public void onGuardar() {
        //Leemos los valores del formulario del fragmento 2
        descripcion = tareaViewModel.getGetDescrip().getValue();
        //Creamos un nuevo objeto tarea con los campos editados
        //Tarea tareaEditada = new Tarea(titulo, progreso, fechaCreacion,numD, descripcion, prioritaria);

        //Creamos un intent de vuelta para la actividad Listado
        Intent aListado = new Intent();
        //Creamos un Bundle para introducir la tarea editada
        Bundle bundle = new Bundle();
        //bundle.putParcelable("TareaEditada", (Parcelable) tareaEditada);
        aListado.putExtras(bundle);
        //Indicamos que el resultado ha sido OK
        setResult(RESULT_OK, aListado);

        //Volvemos a la actividad Listado
        finish();
    }


    public void cambiarFragmento(Fragment fragment){
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor_frag, fragment)
                    .commit();
        }
    }

    @Override
    public void siguiente() {

        //Leemos los valores del formulario del fragmento 1
        titulo = tareaViewModel.getNombre().getValue();
        fechaCreacion = tareaViewModel.getFechaIni().getValue();
        fechaObjetivo = tareaViewModel.getFechaFin().getValue();
        progreso = Integer.valueOf(tareaViewModel.getEstadoTarea().getValue());
        prioritaria = tareaViewModel.getPrioritariaValue();

        //Cambiamos el fragmento
        cambiarFragmento(fragmento2);
    }
}
