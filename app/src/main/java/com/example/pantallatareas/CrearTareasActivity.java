package com.example.pantallatareas;

import android.content.Intent;
import android.location.GnssMeasurementRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.pantallatareas.fragmentos.FragmentoDos;
import com.example.pantallatareas.fragmentos.FragmentoUno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CrearTareasActivity extends AppCompatActivity implements FragmentoDos.ComunicacionFragmento1{

    private FragmentoUno fragmentoUno;
    private CompartirViewModel compartirViewModel;
    private FragmentManager fragmentManager;
    private Button continuar;
    private Button cancelar;
    private String nombreTarea, fechaIni, fechaFin, progesoBarra, descripcion;
    private Integer numDias, numeroProgreso;
    private Tarea tarealistado;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_navigation);

        compartirViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

        fragmentoUno = new FragmentoUno();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos,fragmentoUno).commit();

    }

    @Override
    public void onGuardar() throws ParseException {

       /* if (nombreTarea == null ) {
            Toast.makeText(this, "¡Nombre nulo!", Toast.LENGTH_SHORT).show();
        }
        if ( fechaIni == null ) {
            Toast.makeText(this, "¡fechaIninulo!", Toast.LENGTH_SHORT).show();
        }

        if ( fechaFin == null ) {
            Toast.makeText(this, "¡fechafINnULO!", Toast.LENGTH_SHORT).show();
        }
        if ( progesoBarra == null ) {
            Toast.makeText(this, "¡progrsoBarraNulo!", Toast.LENGTH_SHORT).show();
        }
        if ( descripcion == null ) {
            Toast.makeText(this, "¡descripcionNuelo!", Toast.LENGTH_SHORT).show();
        }*/

        nombreTarea = compartirViewModel.getNombre().getValue();
        fechaIni = compartirViewModel.getFechaIni().getValue().toString();
        fechaFin = compartirViewModel.getFechaFin().getValue().toString();
        progesoBarra = compartirViewModel.getEstadoTarea().getValue();
        descripcion = compartirViewModel.getGetDescrip().getValue();


        int numD = numeroDias(fechaIni, fechaFin);

        Tarea tarealistado = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int contadorID = 1;

            tarealistado = new Tarea(nombreTarea.toString(), barraProgreso(progesoBarra), fechaIni, numD);
        }



        Intent intentVuelta = new Intent();
        intentVuelta.putExtra("TareaSeteada", tarealistado);
        setResult(RESULT_OK, intentVuelta);
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


    public int numeroDias(String fechainicio, String fechafinal) throws ParseException {

        Date date1 = convertirStringADate(fechainicio);
        Date date2 = convertirStringADate(fechafinal);

        // Calcula la diferencia en milisegundos
        long diferenciaEnMilisegundos = Math.abs(date2.getTime() - date1.getTime());

        // Convierte la diferencia de milisegundos a días
        int diferenciaEnDias = (int) (diferenciaEnMilisegundos / (24 * 60 * 60 * 1000));


    return  diferenciaEnDias;
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


}
