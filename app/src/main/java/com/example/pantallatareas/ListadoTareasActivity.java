package com.example.pantallatareas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListadoTareasActivity extends AppCompatActivity {
    private List<Tarea> elements;
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar2 = Calendar.getInstance();
    private Date date = calendar.getTime();
    private Date date2 = calendar2.getTime();
    private Menu mimenu;
    private TareaAdapter tareaAdapter;

    private  Boolean filtprio = false;

    private Tarea tareaSeleccionada;

    private RecyclerView recyclerView;



    public void notificarCambiosEnAdaptador() {
        // Notifica al adaptador de que los datos han cambiado
        // (Asume que tienes una referencia al adaptador)
        tareaAdapter.notificarCambios();
    }


    @SuppressLint("MissingInflatedId")
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aplicarTema();
        setContentView(R.layout.activity_main);

        init();

        // Establece la fecha del calendario
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        calendar2.set(Calendar.YEAR, 2023);
        calendar2.set(Calendar.MONTH, 12);
        calendar2.set(Calendar.DAY_OF_MONTH, 25);
    }



    ActivityResultContract<Intent, ActivityResult> contracto = new ActivityResultContracts.StartActivityForResult();
    ActivityResultCallback<ActivityResult> respuesta = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                Intent intenDevuelto = result.getData();
                Tarea tareaD = (Tarea) intenDevuelto.getExtras().get("TareaSeteada");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    elements.add(tareaD);
                }
                tareaAdapter.notifyDataSetChanged();

            }

        }
    };

    ActivityResultLauncher<Intent> lan = registerForActivityResult(contracto, respuesta);


    public void init(){
        String formatoFecha = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String formatoFecha2 = new SimpleDateFormat("dd/MM/yyyy").format(date2);
        elements = new ArrayList<>();

        elements.add(new Tarea("b", 50, "12/03/2024",12,"Tarea1", true));
        elements.add(new Tarea("a", 25, "24/01/2024",25, "Tarea2", false));
        elements.add(new Tarea("alache", 25, "13/07/2024", 10, "Tarea3",false));

        if (elements.isEmpty()){
            Toast.makeText(this, "No hay tareas", Toast.LENGTH_SHORT).show();}



       tareaAdapter = new TareaAdapter(elements, this);
        recyclerView = findViewById(R.id.recyclerVistaTareas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tareaAdapter);

        registerForContextMenu(recyclerView);

        // Obtén la preferencia de orden desde SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String preferenciaOrden = prefs.getString("tipoCriterio", "nombre"); // Cambia "nombre" por la opción predeterminada

        // Crea el comparador correspondiente según la preferencia seleccionada
        Comparator<Tarea> comparador = null;

        switch (preferenciaOrden) {
            case "nombre":
                comparador = new NombreTareaComparator();
                break;
            case "numeroDias":
                comparador = new DiasTareaComparator();
                break;
            case "progreso":
                comparador = new PorcentajeComparator();
            case "fecha":
                comparador = new FechaComparator();
            default:
                // Manejo predeterminado si la preferencia no es reconocida
                break;
        }

// Ordena la lista de tareas usando el comparador seleccionado
        if (comparador != null) {
            Collections.sort(elements, comparador);
        }

// Actualiza tu RecyclerView con la lista ordenada
        tareaAdapter.notifyDataSetChanged();
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
        lan.launch(intent);
        return true;
    }else if(id==R.id.Prio){
        prioritarias();
    } else if (id==R.id.bt_preferencias) {
        Toast.makeText(this, "Has seleccioando Preferencias", Toast.LENGTH_SHORT).show();
        // Cuando se selecciona la opción del menú, inflar la SegundaActividad
        Intent intent = new Intent(this, SettingsActivity.class);
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


    public void prioritarias(){
        filtprio = !filtprio;

        if (filtprio){
            tareaAdapter = new TareaAdapter(listPrio(), this);
        }else{
            tareaAdapter = new TareaAdapter(elements, this);
        }

        recyclerView.setAdapter(tareaAdapter);

    }

    public List<Tarea> listPrio(){
        List<Tarea> tareaPrio = new ArrayList<>();

        for (Tarea T: elements) {
            if (T.isPrioritaria()){
                tareaPrio.add(T);
            }
        }
        return tareaPrio;
    }

    public void aplicarTema() {
        boolean modoOscuro = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("modo_oscuro", true);

        if (modoOscuro) {

        }else {
            setTheme(R.style.AppTheme_Dark);
            aplicarTamanoLetra();
        }
        // Escuchar cambios en la preferencia del modo oscuro
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
                    if ("modo_oscuro".equals(key)) {
                        recreate(); // Reiniciar la actividad para aplicar el nuevo tema
                    }
                });
    }


    public void aplicarTamanoLetra() {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizePreference = sharedPreferences.getString("tipoLetra", "normal");

        switch (fontSizePreference) {
            case "small":
                configuration.fontScale = 0.8f;
                break;
            case "normal":
                configuration.fontScale = 1f;
                break;
            case "large":
                configuration.fontScale = 3f;
                break;
            default:
                configuration.fontScale = 1f;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public void actualizarConfiguracion() {
        // Actualiza la configuración según tus necesidades
        // Recrea la actividad para aplicar los cambios
        recreate();
    }


}





class NombreTareaComparator implements Comparator<Tarea> {
    @Override
    public int compare(Tarea tarea1, Tarea tarea2) {
        return tarea1.getNombreTarea().compareTo(tarea2.getNombreTarea());
    }
}

class DiasTareaComparator implements Comparator<Tarea> {
    @Override
    public int compare(Tarea tarea1, Tarea tarea2) {
        return Integer.compare(tarea1.getDiasTarea(), tarea2.getDiasTarea());
    }
}

class PorcentajeComparator implements  Comparator<Tarea>{
    @Override
    public int compare(Tarea tarea1, Tarea tarea2){
        return Integer.compare(tarea1.getPorcentajeTarea(), tarea2.getPorcentajeTarea());
    }
}


class FechaComparator implements  Comparator<Tarea> {
    @Override
    public int compare(Tarea tarea1, Tarea tarea2) {
        // Asumiendo que las fechas son en formato String, debes convertirlas a objetos Date para una comparación adecuada
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date fecha1 = dateFormat.parse(tarea1.getFechaIni());
            Date fecha2 = dateFormat.parse(tarea2.getFechaIni());
            return fecha1.compareTo(fecha2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}