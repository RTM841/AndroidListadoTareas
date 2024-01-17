package com.example.pantallatareas.Modelos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Anotación para decir que es una entidada de Room
@Entity
public class Tarea {

    //Anotación para indicar la clave primaria de la tabla
    @PrimaryKey(autoGenerate = true)
    //Anotación para asignar el atributo al campo de la tabla
    @ColumnInfo(name = "_id")
    private long id;

    //Anotación para asignar el atributo al campo de la tabla
    @NotNull
    @ColumnInfo(name = "nombreTarea")
    public String nombreTarea;


    @ColumnInfo(name = "progresoTarea", defaultValue = "0")
    public int porcentajeTarea;
    @NotNull
    @ColumnInfo(name = "fechaCreación", defaultValue = "CURRENT_TIMESTAMP")
    public Date fechaIni ;

    @NotNull
    @ColumnInfo(name = "fechaObjetivo")
    public  Date fechaFin;

    @NotNull
    @ColumnInfo(name = "diasTarea")
    public int diasTarea;


    @ColumnInfo(name = "prioritaia", defaultValue = "false")
    public boolean prioritaria;

    @ColumnInfo(name = "descripcion")
    public String descripcion;
    @ColumnInfo(name = "UrlDocuemntos")
    public String URL_doc;
    @ColumnInfo(name = "UrlImagenes")
    public String URL_img;
    @ColumnInfo(name = "UrlAudio")
    public String URL_aud;
    @ColumnInfo(name = "UrlVideo")
    public String URL_vid;

    public Tarea(String nombreTarea, int porcentajeTarea, String fechaIni, String fechaFin, int diasTarea, boolean prioritaria, String descripcion, String urlDocumento, String urlImagen, String urlAudio, String urlVideo) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = validarFecha(fechaIni);
        this.fechaFin = validarFecha(fechaFin);
        this.diasTarea = diasTarea;
        this.prioritaria = prioritaria;
        this.descripcion = descripcion;
        this.URL_doc = urlDocumento;
        this.URL_img = urlImagen;
        this.URL_aud = urlAudio;
        this.URL_vid = urlVideo;
    }

    public Tarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public Tarea() {

    }
    public Tarea(String nombreTarea, int porcentajeTarea) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
    }

    public Tarea(String nombreTarea, int porcentajeTarea, String fecha, boolean prioritaria) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = validarFecha(fecha);
        this.prioritaria = prioritaria;
    }



    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public int getPorcentajeTarea() {
        return porcentajeTarea;
    }

    public void setPorcentajeTarea(int porcentajeTarea) {
        this.porcentajeTarea = porcentajeTarea;
    }

    public int getDiasTarea() {
        return diasTarea;
    }

    public void setDiasTarea(int diasTarea) {
        this.diasTarea = diasTarea;
    }

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFechaIni() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fechaIni);
    }


    public String getFechaFin() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fechaFin);
    }


    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getURL_doc() {
        return URL_doc;
    }

    public void setURL_doc(String URL_doc) {
        this.URL_doc = URL_doc;
    }

    public String getURL_img() {
        return URL_img;
    }

    public void setURL_img(String URL_img) {
        this.URL_img = URL_img;
    }

    public String getURL_aud() {
        return URL_aud;
    }

    public void setURL_aud(String URL_aud) {
        this.URL_aud = URL_aud;
    }

    public String getURL_vid() {
        return URL_vid;
    }

    public void setURL_vid(String URL_vid) {
        this.URL_vid = URL_vid;
    }

    public Date validarFecha(@NonNull String fechaCreacion){
        Date fecha = new Date(); //Para evitar devolver null
        if (validarFormatoFecha(fechaCreacion)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                fecha = sdf.parse(fechaCreacion);
            } catch (Exception e) {
                Log.e("Error fecha","Parseo de fecha no válido");
            }
        } else {
            Log.e("Error fecha","Formato de fecha no válido");
        }
        return fecha;
    }

    private boolean validarFormatoFecha(@NonNull String fecha) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fecha);
        return matcher.matches();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarea)) return false;
        Tarea tarea = (Tarea) o;
        return id == tarea.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
