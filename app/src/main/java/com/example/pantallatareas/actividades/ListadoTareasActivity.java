package com.example.pantallatareas.actividades;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pantallatareas.Modelos.Tarea;
import com.example.pantallatareas.R;
import com.example.pantallatareas.adaptadores.TareaAdapter;
import com.example.pantallatareas.basedatos.BaseDatosApp;
import com.example.pantallatareas.daos.TareaDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListadoTareasActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private List<Tarea> elements = new ArrayList<>();
    private TareaDao tareaDao;

    private LiveData<List<Tarea>> tareas;
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar2 = Calendar.getInstance();
    private Date date = calendar.getTime();
    private Date date2 = calendar2.getTime();
    private Menu mimenu;
    private TareaAdapter tareaAdapter;

    private Boolean filtprio = false;

    private Tarea tareaSeleccionada;

    private RecyclerView recyclerView;
    private BaseDatosApp baseDatosApp;



    public void notificarCambiosEnAdaptador() {
        // Notifica al adaptador de que los datos han cambiado
        // (Asume que tienes una referencia al adaptador)
        tareaAdapter.notificarCambios();
    }




    @SuppressLint({"MissingInflatedId", "RestrictedApi"})
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        baseDatosApp = BaseDatosApp.getInstance(getActivity(this).getApplicationContext());


        init();

        // Registra la actividad como listener de cambios en las preferencias
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Establece la fecha del calendario
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        calendar2.set(Calendar.YEAR, 2023);
        calendar2.set(Calendar.MONTH, 12);
        calendar2.set(Calendar.DAY_OF_MONTH, 25);
    }

    public LiveData<List<Tarea>> getTareas() {
        return tareas;
    }


    ActivityResultContract<Intent, ActivityResult> contracto = new ActivityResultContracts.StartActivityForResult();
    ActivityResultCallback<ActivityResult> respuesta = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intenDevuelto = result.getData();
                Tarea tareaD = (Tarea) intenDevuelto.getExtras().get("TareaSeteada");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    elements.add(tareaD);
                }
                tareaAdapter.notifyDataSetChanged();

            }

        }
    };

    ActivityResultLauncher<Intent> lan = registerForActivityResult(contracto, respuesta);


    public void init() {
        tareas = baseDatosApp.getInstance(this).tareaDao().listadoTareas();

        tareaAdapter = new TareaAdapter(this, elements, baseDatosApp);
        recyclerView = findViewById(R.id.recyclerVistaTareas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tareaAdapter);

        registerForContextMenu(recyclerView);


        // Observa los cambios en la lista de tareas
        tareas.observe(this, tareaAdapter::setDatosTareas);
    }

    // Método para notificar cambios en las preferencias al adaptador
    private void notificarCambiosDePreferencias() {
        if (tareaAdapter != null) {
            notificarPreferencias();
            tareaAdapter.setDatosTareas(elements);
            tareaAdapter.notifyDataSetChanged();
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem opcion_menu) {

        int id = opcion_menu.getItemId();

        if (id == R.id.AcercaDe) {
            Toast.makeText(this, "Has seleccioando Acerca De", Toast.LENGTH_SHORT).show();
            showDialog(this);
            return true;
        } else if (id == R.id.Salir) {
            findViewById(R.id.Salir).setOnClickListener(view -> finish());
            Toast.makeText(this, "Has seleccioando salir", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.AgreTarea) {
            Toast.makeText(this, "Has seleccioando añadir tarea", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CrearTareasActivity.class);
            lan.launch(intent);
            return true;
        } else if (id == R.id.Prio) {
            prioritarias();
        } else if (id == R.id.bt_preferencias) {
            Toast.makeText(this, "Has seleccioando Preferencias", Toast.LENGTH_SHORT).show();
            // Cuando se selecciona la opción del menú, inflar la SegundaActividad
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.bt_estadisticas) {
            Toast.makeText(this, "Has seleccionado estadisticas", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EstadisticasActivity.class);
            startActivity(intent);
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


    public void prioritarias() {
        filtprio = !filtprio;

        if (filtprio) {
            tareas = baseDatosApp.getInstance(this).tareaDao().listadorPrio();


            tareaAdapter = new TareaAdapter(this, elements,baseDatosApp);
            recyclerView = findViewById(R.id.recyclerVistaTareas);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(tareaAdapter);

            registerForContextMenu(recyclerView);


            // Observa los cambios en la lista de tareas
            tareas.observe(this, tareaAdapter::setDatosTareas);
        } else {

            tareas = baseDatosApp.getInstance(this).tareaDao().listadoTareas();


            tareaAdapter = new TareaAdapter(this, elements, baseDatosApp);
            recyclerView = findViewById(R.id.recyclerVistaTareas);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(tareaAdapter);

            registerForContextMenu(recyclerView);


            // Observa los cambios en la lista de tareas
            tareas.observe(this, tareaAdapter::setDatosTareas);
        }

        recyclerView.setAdapter(tareaAdapter);

    }

    public List<Tarea> listPrio() {
        List<Tarea> tareaPrio = new ArrayList<>();

        for (Tarea T : elements) {
            if (T.isPrioritaria()) {
                tareaPrio.add(T);
            }
        }
        return tareaPrio;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        // Llama a tu método notificarPreferencias inicialmente
        notificarCambiosDePreferencias();

        // Actualiza directamente los datos del adaptador
        if (tareaAdapter != null) {
            tareaAdapter.setDatosTareas(elements);
            tareaAdapter.notifyDataSetChanged(); // Asegúrate de notificar el cambio al adaptador
        }

        notificarCambiosEnAdaptador();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Desregistra el listener al salir de la actividad para evitar posibles pérdidas de memoria
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }


    public void notificarPreferencias(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String preferenciaOrden = prefs.getString("tipoCriterio", "nombre"); // Cambia "nombre" por la opción predeterminada7
        Boolean ordenPreferencia = prefs.getBoolean("orden_criterios", true);
        String preferncialetra = prefs.getString("tipoLetra", "nombre");

        if (ordenPreferencia) {
            switch (preferenciaOrden) {
                case "nombre":
                    Collections.sort(elements, Tarea.getNombreComparator(true));
                    break;
                case "numeroDias":
                    Collections.sort(elements, Tarea.getNumeroDiasComparator(true));
                    break;
                case "progreso":
                    Collections.sort(elements, Tarea.getProgresoComparator(true));
                    break;
                case "fecha":
                    Collections.sort(elements, Tarea.getFechaComparator(true));
                    break;
                default:
                    // Manejo predeterminado si la preferencia no es reconocida
                    break;
            }
        } else if (!ordenPreferencia) {
            switch (preferenciaOrden) {
                case "nombre":
                    Collections.sort(elements, Tarea.getNombreComparator(false));
                    break;
                case "numeroDias":
                    Collections.sort(elements, Tarea.getNumeroDiasComparator(false));
                    break;
                case "progreso":
                    Collections.sort(elements, Tarea.getProgresoComparator(false));
                    break;
                case "fecha":
                    Collections.sort(elements, Tarea.getFechaComparator(false));
                    break;
                default:
                    // Manejo predeterminado si la preferencia no es reconocida
                    break;
            }
        }

            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();

            switch (preferncialetra) {
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
        recreate();

    }
}