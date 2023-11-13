package com.example.pantallatareas;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Tarea> elements;
    Calendar calendar = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    Date date = calendar.getTime();
    Date date2 = calendar2.getTime();

    Menu mimenu;



    private RecyclerView rv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // Establece la fecha del calendario
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        calendar2.set(Calendar.YEAR, 2023);
        calendar2.set(Calendar.MONTH, 12);
        calendar2.set(Calendar.DAY_OF_MONTH, 25);

        rv = findViewById(R.id.recyclerVistaTareas);
        ListAdapter adaptador = new ListAdapter(elements, this);
        rv.setLayoutManager( LinearLayoutManager.VERTICAL);
        rv.setAdapter(adaptador);


    }


    public void init(){
        String formatoFecha = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String formatoFecha2 = new SimpleDateFormat("dd/MM/yyyy").format(date2);
        elements = new ArrayList<>();

        elements.add(new Tarea("Tarea01", 50, formatoFecha));
        elements.add(new Tarea("Tarea02", 25, formatoFecha2));

        if (elements.isEmpty()){
            Toast.makeText(this, "No hay tareas", Toast.LENGTH_SHORT).show();}



        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerVistaTareas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(listAdapter);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;


    }

    public boolean onOptionsItemSelected(MenuItem opcion_menu){

    int id = opcion_menu.getItemId();

    if (id==R.id.AcercaDe)
    {
        Toast.makeText(this, "Has seleccioando Acerca De", Toast.LENGTH_SHORT).show();
        showDialog(this);
        return true;
    } else if (id==R.id.Salir)
    {
        findViewById(R.id.Salir).setOnClickListener(view -> finish());
        Toast.makeText(this, "Has seleccioando salir", Toast.LENGTH_SHORT).show();
        return true;
    }else if(id == R.id.AgreTarea)
    {
        Toast.makeText(this, "Has seleccioando añadir tarea", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CrearTareasActivity.class);
        startActivity(intent);
        return true;
    }

    return super.onOptionsItemSelected(opcion_menu);
    }


    public static void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Acerca de");
        builder.setMessage(
                "Aplicación: TrassTarea\n" +
                        "Centro: IES Trassierra\n" +
                        "Autor: Rubén\n" +
                        "Año: 2023"
        );
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}