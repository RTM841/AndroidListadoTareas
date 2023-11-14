package com.example.pantallatareas;

import android.content.Intent;
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
        setContentView(R.layout.activity_navigation);

        compartirViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

        fragmentoUno = new FragmentoUno();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos,fragmentoUno).commit();

    }

    @Override
    public void onGuardar(){

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
        fechaIni = compartirViewModel.getFechaIni().getValue();
        fechaFin = compartirViewModel.getFechaFin().getValue();
        progesoBarra = compartirViewModel.getEstadoTarea().getValue();
        descripcion = compartirViewModel.getGetDescrip().getValue();




        Tarea tarealistado = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int contadorID = 1;

            tarealistado = new Tarea(nombreTarea.toString(), barraProgreso(progesoBarra), fechaIni);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int numeroDias(String fechainicio, String fechafinal) throws ParseException {

        LocalDate localDate1 = LocalDate.parse(fechainicio, DateTimeFormatter.ISO_DATE);
        LocalDate localDate2 = LocalDate.parse(fechafinal, DateTimeFormatter.ISO_DATE);
        int diferenciaEnDias = Math.abs(localDate1.until(localDate2).getDays());

    return  diferenciaEnDias;
    }


}
