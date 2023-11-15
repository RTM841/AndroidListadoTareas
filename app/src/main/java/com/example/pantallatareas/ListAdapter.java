package com.example.pantallatareas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
                holder.posicion = position;
                holder.bindData(datosTareas.get(position));
            }
        });
    }

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
