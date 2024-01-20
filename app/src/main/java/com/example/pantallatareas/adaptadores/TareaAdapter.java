package com.example.pantallatareas.adaptadores;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantallatareas.Modelos.Tarea;
import com.example.pantallatareas.R;
import com.example.pantallatareas.actividades.DetalleActivity;
import com.example.pantallatareas.actividades.EditarTareaActivity;
import com.example.pantallatareas.actividades.ListadoTareasActivity;
import com.example.pantallatareas.basedatos.BaseDatosApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolder>{

    private List<Tarea> datosTareas;

    private LiveData<List<Tarea>> tareasLista;
    private BaseDatosApp baseDatosApp;
    private LayoutInflater inflador;
    private Context context;
    private Tarea tareaSeleccionada;

    private int posicion;


    private ArrayList<Tarea> tareas = new ArrayList<>();

    public void notificarCambios() {
        // Notifica al adaptador de que los datos han cambiado
        notifyDataSetChanged();
    }

    public TareaAdapter(Context context, List<Tarea> datos, BaseDatosApp baseDatosApp)
    {
        this.datosTareas = datos;
        inflador = LayoutInflater.from(context);
        this.context = context;
        this.baseDatosApp = baseDatosApp;
    }



    public List<Tarea> getDatosTareas(){return datosTareas;}

    public void setDatosTareas(List<Tarea> datosTarea){this.datosTareas = datosTarea; notifyDataSetChanged();}

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public  TareaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflador.inflate(R.layout.vista_tareas, null);
        return new TareaAdapter.ViewHolder(view);

    }


    @Override
    public int getItemCount(){return datosTareas.size(); }

    public void notificarPreferencias(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String preferenciaOrden = prefs.getString("tipoCriterio", "nombre"); // Cambia "nombre" por la opción predeterminada7
        Boolean ordenPreferencia = prefs.getBoolean("orden_criterios", true);

        if (ordenPreferencia) {
            switch (preferenciaOrden) {
                case "nombre":
                    Collections.sort(datosTareas, Tarea.getNombreComparator(true));
                    break;
                case "numeroDias":
                    Collections.sort(datosTareas, Tarea.getNumeroDiasComparator(true));
                    break;
                case "progreso":
                    Collections.sort(datosTareas, Tarea.getProgresoComparator(true));
                    break;
                case "fecha":
                    Collections.sort(datosTareas, Tarea.getFechaComparator(true));
                    break;
                default:
                    // Manejo predeterminado si la preferencia no es reconocida
                    break;
            }
        } else if (!ordenPreferencia) {
            switch (preferenciaOrden) {
                case "nombre":
                    Collections.sort(datosTareas, Tarea.getNombreComparator(false));
                    break;
                case "numeroDias":
                    Collections.sort(datosTareas, Tarea.getNumeroDiasComparator(false));
                    break;
                case "progreso":
                    Collections.sort(datosTareas, Tarea.getProgresoComparator(false));
                    break;
                case "fecha":
                    Collections.sort(datosTareas, Tarea.getFechaComparator(false));
                    break;
                default:
                    // Manejo predeterminado si la preferencia no es reconocida
                    break;
            }
        }

    }




    @Override
    public void onBindViewHolder(final TareaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        notificarPreferencias();
        Tarea tarea = datosTareas.get(position);

        holder.bindData(tarea);
        holder.posicion = position;
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu_contextual,menu);

                // Guardar el ID de la tarea en el tag del itemView
                long tareaId = datosTareas.get(position).getId();
                v.setTag(R.id.tarea_id_tag, tareaId);

                holder.posicion = position;
                holder.bindData(datosTareas.get(position));

                // Agrega un OnMenuItemClickListener al menú contextual
                menu.findItem(R.id.bt_detalle).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Acción que se realiza cuando se hace clic en la opción de mostrar título
                        long tareaId = (long) v.getTag(R.id.tarea_id_tag);
                        mostrarDescrpicion(tareaId);
                        return true;
                    }
                });

                menu.findItem(R.id.bt_borrar).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Acción que se realiza cuando se hace clic en la opción de mostrar título
                        long tareaId = (long) v.getTag(R.id.tarea_id_tag);
                        borrarTarea(tareaId);
                        return true;
                    }
                });

                menu.findItem(R.id.bt_editar).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Acción que se realiza cuando se hace clic en la opción de editar
                        long tareaId = (long) v.getTag(R.id.tarea_id_tag);
                        editarTarea(tareaId);
                        return true;
                    }
                });

            }
        });



    }

    private Tarea buscarTareaPorId(long tareaId) {
        for (Tarea tarea : datosTareas) {
            if (tarea.getId() == tareaId) {
                return tarea;
            }
        }
        return null; // Retorna null si la tarea no se encuentra
    }

    private Tarea mostrarDescrpicion(long tareaId) {
        // Buscar la tarea correspondiente en la lista de datos
        Tarea tareaSeleccionada = buscarTareaPorId(tareaId);

        Intent intent = new Intent(context, DetalleActivity.class);
        intent.putExtra("tarea", (Parcelable) tareaSeleccionada);
        context.startActivity(intent);

        return tareaSeleccionada;
    }

    private Tarea editarTarea(long tareaId) {
        // Buscar la tarea correspondiente en la lista de datos
        Tarea tareaSeleccionada = buscarTareaPorId(tareaId);

        Intent intent = new Intent(context, EditarTareaActivity.class);
        intent.putExtra("tareaEditar", (Parcelable) tareaSeleccionada);
        context.startActivity(intent);

        return tareaSeleccionada;
    }


    public void borrarTarea(long idTarea) {
        int posicionTarea = -1;

        // Buscar la posición de la tarea en la lista
        for (int i = 0; i < datosTareas.size(); i++) {
            if (datosTareas.get(i).getId() == idTarea) {
                posicionTarea = i;
                break;
            }
        }

        // Verificar si la tarea se encontró antes de realizar la acción
        if (posicionTarea != -1) {
            Tarea tareaSelec = datosTareas.get(posicionTarea);

            String tituloTarea = tareaSelec.nombreTarea;

            //Creamos un objeto de la clase que realiza la inserción en un hilo aparte Executor
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new BorrarTarea(tareaSelec));


            Toast.makeText(context.getApplicationContext(), "La tarea borrada es:" + tituloTarea, Toast.LENGTH_LONG).show();


            // Notificar al adaptador que la tarea ha sido eliminada
            notifyItemRemoved(posicionTarea);
        }



    }






    //Contrato para el lanzador hacia la actividad EditarTareaActivity
    ActivityResultContract<Tarea, Tarea> contratoEditar = new ActivityResultContract<Tarea, Tarea>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Tarea tarea) {
            Intent intent = new Intent(context, EditarTareaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("TareaEditable", (Parcelable) tarea);
            intent.putExtras(bundle);
            return intent;
        }

        @Override
        public Tarea parseResult(int i, @Nullable Intent intent) {
            if (i == Activity.RESULT_OK && intent != null) {
                try {
                    return (Tarea) Objects.requireNonNull(intent.getExtras()).get("TareaEditada");
                } catch (NullPointerException e) {
                    Log.e("Error en intent devuelto", Objects.requireNonNull(e.getLocalizedMessage()));
                }
            }else if(i == Activity.RESULT_CANCELED){
                Toast.makeText(context.getApplicationContext(),R.string.action_canceled, Toast.LENGTH_SHORT).show();
            }
            return null; // Devuelve null si no se pudo obtener una Tarea válida.
        }
    };

    //Respuesta para el lanzador hacia la actividad EditarTareaActivity
    ActivityResultCallback<Tarea> resultadoEditar = new ActivityResultCallback<Tarea>() {
        @Override
        public void onActivityResult(Tarea tareaEditada) {
            if (tareaEditada != null) {
                //Seteamos el id de la tarea recibida para que coincida con el de la tarea editada
                tareaEditada.setId(tareaSeleccionada.getId());

                //Procedemos a la sustitución de la tarea editada por la seleccionada.
                int posicionEnColeccion = tareas.indexOf(tareaSeleccionada);
                tareas.remove(tareaSeleccionada);
                tareas.add(posicionEnColeccion, tareaEditada);

                //Comunicamos que la tarea ha sido editada al usuario
                Toast.makeText(context.getApplicationContext(), R.string.tarea_edit, Toast.LENGTH_SHORT).show();
            }
        }
    };

    //Registramos el lanzador hacia la actividad EditarTareaActivity con el contrato y respuesta personalizados
    //ActivityResultLauncher<Long> lanzadorActividadEditar = registerForActivityResult(contratoEditar, resultadoEditar);



    public void setItems(List<Tarea> items){ datosTareas = items;}

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iconImage;
        TextView nombreTarea;

        TextView numDias;

        ProgressBar barraProgreso;

        TextView fecha;

        int posicion;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.imagenEstrella);
            nombreTarea = itemView.findViewById(R.id.textoTarea);
            barraProgreso = itemView.findViewById(R.id.ProGresBar_barra);
            fecha = itemView.findViewById(R.id.textoFecha);
            numDias = itemView.findViewById(R.id.textoNumDias);
        }

      void bindData(final Tarea item){
          barraProgreso.setProgress(item.getPorcentajeTarea());
          nombreTarea.setText(item.getNombreTarea());
          fecha.setText(item.getFechaIni());
          numDias.setText(String.valueOf(item.getDiasTarea()));

         if (item.isPrioritaria()){
              iconImage.setImageResource(R.drawable.baseline_star_24);
              iconImage.setVisibility(View.VISIBLE);
          }else{
              iconImage.setImageResource(R.drawable.baseline_star_outline_24);
              iconImage.setVisibility(View.VISIBLE);
          }

      }

    }

    //Clase que inserta un objeto producto en la base de datos usando un hilo diferente al principal.
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

    class PorcentajeComparator implements Comparator<Tarea> {
        @Override
        public int compare(Tarea tarea1, Tarea tarea2) {
            return Integer.compare(tarea1.getPorcentajeTarea(), tarea2.getPorcentajeTarea());
        }
    }


    class FechaComparator implements Comparator<Tarea> {
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

}
