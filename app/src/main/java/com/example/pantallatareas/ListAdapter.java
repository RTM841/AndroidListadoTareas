package com.example.pantallatareas;

import android.annotation.SuppressLint;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Tarea> datosTareas;
    private LayoutInflater inflador;
    private Context context;

    public ListAdapter(List<Tarea> itemTarea, Context context)
    {
        this.inflador = LayoutInflater.from(context);
        this.context = context;
        this.datosTareas = itemTarea;
    }

    @Override
    public  ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflador.inflate(R.layout.vista_tareas, null);
        return new ListAdapter.ViewHolder(view);


    }


    @Override
    public int getItemCount(){return datosTareas.size(); }


    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {

        holder.bindData(datosTareas.get(position));
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
                menu.findItem(R.id.bt_descripcion).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Acción que se realiza cuando se hace clic en la opción 1
                        Toast.makeText(context.getApplicationContext(), "Has selecccionado la opción de descripción", Toast.LENGTH_LONG).show();
                        return true;
                    }
                });

                menu.findItem(R.id.bt_descripcion).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Acción que se realiza cuando se hace clic en la opción de mostrar título
                        long tareaId = (long) v.getTag(R.id.tarea_id_tag);
                        mostrarTituloDeTarea(tareaId);
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

    private void mostrarTituloDeTarea(long tareaId) {
        // Buscar la tarea correspondiente en la lista de datos
        Tarea tareaSeleccionada = buscarTareaPorId(tareaId);

        // Verificar si la tarea se encontró antes de realizar la acción
        if (tareaSeleccionada != null) {
            String tituloTarea = tareaSeleccionada.nombreTarea;
            Toast.makeText(context.getApplicationContext(), "Título de la tarea: " + tituloTarea, Toast.LENGTH_LONG).show();
        }
    }



    /*@Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.bt_descripcion) {
            Toast.makeText(context.getApplicationContext(), "Has pulsado el botón descripción", Toast.LENGTH_LONG).show();

        }
        return super.onContextItemSelected(item);
    }*/

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
          barraProgreso.setProgress(item.porcentajeTarea);
          nombreTarea.setText(item.getNombreTarea());
          fecha.setText(item.getFechaIni());
          numDias.setText(String.valueOf(item.diasTarea));

      }

    }

}
