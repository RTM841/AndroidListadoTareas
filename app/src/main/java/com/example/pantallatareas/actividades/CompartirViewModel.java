package com.example.pantallatareas.actividades;

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

    //Para número de dias
    private final MutableLiveData<Integer> numDias = new MutableLiveData<>();

    public void setNumDias(Integer numeroDias){numDias.setValue(numeroDias);
    }

    public  MutableLiveData<Integer> getNumDias(){return numDias;}


    private final MutableLiveData<Boolean> prioritaria = new MutableLiveData<>();

    public void setPrioritaria(boolean prio){prioritaria.setValue(prio);
    }

    public boolean getPrioritariaValue(){
        if (prioritaria.getValue() != null){
            return prioritaria.getValue();
        }else{
            return false;
        }
    }



    //Para la URL del documento
    private final MutableLiveData<String> urlDocumento = new MutableLiveData<>();

    public void setUrlDocumento(String urlD){
        urlDocumento.setValue(urlD);
    }

    public  MutableLiveData<String> geturlDocumento(){return urlDocumento;}


    //Para la URL de imagen

    private final MutableLiveData<String> urlImagen = new MutableLiveData<>();

    public void setUrlImagen(String urlI){
        urlImagen.setValue(urlI);
    }

    public  MutableLiveData<String> getUrlImagen(){return urlImagen;}

    //Para la URL del audio

    private final MutableLiveData<String> urlAudio = new MutableLiveData<>();

    public void setUrlAudio(String urlA){
        urlAudio.setValue(urlA);
    }

    public  MutableLiveData<String> getUrlAudio(){return urlAudio;}

    //Para la URL del video

    private final MutableLiveData<String> urlVideo = new MutableLiveData<>();

    public void setUrlVideo(String url){
        urlVideo.setValue(url);
    }

    public  MutableLiveData<String> getUrlVideo(){return urlVideo;}



}
