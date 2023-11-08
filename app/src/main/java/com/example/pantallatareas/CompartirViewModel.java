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
    private final MutableLiveData<DatePickerFragment> fechaIni = new MutableLiveData<>();

    public void setFechaIni(DatePickerFragment fechaIn){fechaIni.setValue(fechaIn);}

    public  MutableLiveData<DatePickerFragment> getFechaIni(){return fechaIni;}


    private final MutableLiveData<DatePickerFragment> fechaFin = new MutableLiveData<>();

    public void setFechaFin(DatePickerFragment fechaF){
        fechaFin.setValue(fechaF);
    }

    public  MutableLiveData<DatePickerFragment> getFechaFin(){return fechaFin;}


    private final MutableLiveData<Spinner> estadoTarea = new MutableLiveData<>();

    public void setEstadoTarea(Spinner estado){
        estadoTarea.setValue(estado);
    }

    public  MutableLiveData<Spinner> getEstadoTarea(){return estadoTarea;}



}
