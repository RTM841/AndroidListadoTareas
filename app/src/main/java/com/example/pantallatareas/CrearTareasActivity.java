package com.example.pantallatareas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.pantallatareas.fragmentos.FragmentoDos;
import com.example.pantallatareas.fragmentos.FragmentoUno;

public class CrearTareasActivity extends AppCompatActivity implements FragmentoDos.ComunicacionFragmento1{

    private FragmentoUno fragmentoUno;
    private CompartirViewModel compartirViewModel;
    private FragmentManager fragmentManager;
    private Button continuar;
    private Button cancelar;
    private String nombreTarea, fechaIni, fechaFin, progesoBarra, descripcion;
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            tarealistado = new Tarea(nombreTarea.toString(), 50);
        }



        Intent intentVuelta = new Intent();
        intentVuelta.putExtra("TareaSeteada", tarealistado);
        setResult(RESULT_OK, intentVuelta);
        finish();

    }

}
