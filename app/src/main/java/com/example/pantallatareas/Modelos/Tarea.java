package com.example.pantallatareas.Modelos;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Anotación para decir que es una entidada de Room


@Entity
public class Tarea implements Parcelable {



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
    @ColumnInfo(name = "fechaCreación")
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

    public Tarea() {

    }

    public Tarea(String tareaString) {
    }

    public Tarea(String nombre, int dias, String descripcion, String urlDoc, String urlImg, String urlAud, String urlVid) {
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
        fechaCreacion = fechaCreacion.replaceAll("\\s", "");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                fecha = sdf.parse(fechaCreacion);
            } catch (Exception e) {
                Log.e("Error fecha","Parseo de fecha no válido");
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

    @Override
    public int describeContents() {
        return 0;
    }

    // Método para escribir los atributos a un Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombreTarea);
        dest.writeInt(porcentajeTarea);
        dest.writeLong(fechaIni.getTime());
        dest.writeLong(fechaFin.getTime());
        dest.writeInt(diasTarea);
        dest.writeByte((byte) (prioritaria ? 1 : 0));
        dest.writeString(descripcion);
        dest.writeString(URL_doc);
        dest.writeString(URL_img);
        dest.writeString(URL_aud);
        dest.writeString(URL_vid);
    }

    // Constructor Parcelable
    protected Tarea(Parcel in) {
        id = in.readLong();
        nombreTarea = in.readString();
        porcentajeTarea = in.readInt();
        fechaIni = new Date(in.readLong());
        fechaFin = new Date(in.readLong());
        diasTarea = in.readInt();
        prioritaria = in.readByte() != 0;
        descripcion = in.readString();
        URL_doc = in.readString();
        URL_img = in.readString();
        URL_aud = in.readString();
        URL_vid = in.readString();
    }



    // Campo CREATOR necesario para Parcelable
    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };



    // Constructor, getters y setters

    public static Comparator<Tarea> getNombreComparator(boolean ascendente) {
        return ascendente ? Comparator.comparing(Tarea::getNombreTarea) : Comparator.comparing(Tarea::getNombreTarea).reversed();
    }

    public static Comparator<Tarea> getFechaComparator(boolean ascendente) {
        return ascendente ? Comparator.comparing(Tarea::getFechaIni) : Comparator.comparing(Tarea::getFechaIni).reversed();
    }

    public static Comparator<Tarea> getNumeroDiasComparator(boolean ascendente) {
        return ascendente ? Comparator.comparingInt(Tarea::getDiasTarea) : Comparator.comparingInt(Tarea::getDiasTarea).reversed();
    }

    public static Comparator<Tarea> getProgresoComparator(boolean ascendente) {
        return ascendente ? Comparator.comparingInt(Tarea::getPorcentajeTarea) : Comparator.comparingInt(Tarea::getPorcentajeTarea).reversed();
    }
}
