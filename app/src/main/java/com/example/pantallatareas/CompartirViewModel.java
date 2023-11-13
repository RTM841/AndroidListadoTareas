package com.example.pantallatareas;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompartirViewModel extends ViewModel {

    //Para guardar el nombre de la tarea
    private final MutableLiveData<String> nombre = new MutableLiveData<>();

    public void setNombre(String nomb){
        nombre.setValue(nomb);
    }

    public  MutableLiveData<String> getNombre(){return nombre;}


    //Para guardar la fecha de inicio
    private final MutableLiveData<String> fechaIni = new MutableLiveData<>();

    public void setFechaIni(String fechaI){fechaIni.setValue(fechaI);}
    public  MutableLiveData<String> getFechaIni(){return fechaIni;}

    //Para guardar la fecha final
    private final MutableLiveData<String> fechaFin = new MutableLiveData<>();

    public void setFechaFin(String fechaF){
        fechaFin.setValue(fechaF);
    }

    public  MutableLiveData<String> getFechaFin(){return fechaFin;}

    //Para guardar Spinner de estado de la tarea

    private final MutableLiveData<String> estadoTarea = new MutableLiveData<>();

    public void setEstadoTarea(String estado){
        estadoTarea.setValue(estado);
    }

    //Para guardar la descripción

    public  MutableLiveData<String> getEstadoTarea(){return estadoTarea;}

    //Para guardar la descripcion
    private final MutableLiveData<String> Descrip = new MutableLiveData<>();

    public void setDescip(String descripcion){
        Descrip.setValue(descripcion);
    }

    public  MutableLiveData<String> getGetDescrip(){return Descrip;}



}
