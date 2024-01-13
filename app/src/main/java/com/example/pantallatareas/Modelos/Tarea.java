package com.example.pantallatareas.Modelos;

import static java.time.LocalDate.*;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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


    public int porcentajeTarea;
    @NotNull
    @ColumnInfo(name = "fechaCreación")
    public Date fechaIni ;

    @NotNull
    @ColumnInfo(name = "fechaObjetivo")
    public  Date fechaFin;
    public int diasTarea;
    public boolean prioritaria;

    public String descripcion;

    public Tarea(String nombreTarea, int porcentajeTarea, String fechaIni, String fechaFin, int diasTarea, boolean prioritaria, String descripcion) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = validarFecha(fechaIni);
        this.fechaFin = validarFecha(fechaFin);
        this.diasTarea = diasTarea;
        this.prioritaria = prioritaria;
        this.descripcion = descripcion;
    }

    //Constructor completo
    public Tarea(int id, String nombreTarea, int porcentajeTarea, String fechaIni, int diasTarea, String descripcion, boolean prioritaria) {
        this.id = id;
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = validarFecha(fechaIni);
        this.diasTarea = diasTarea;
        this.descripcion = descripcion;
        this.prioritaria = prioritaria;
    }


    public Tarea(String nombreTarea, int porcentajeTarea, String fechaIni, int diasTarea, String descripcion, boolean prioritaria) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = validarFecha(fechaIni);
        this.diasTarea = diasTarea;
        this.descripcion = descripcion;
        this.prioritaria = prioritaria;
    }
    public Tarea(long id, String nombreTarea, int porcentajeTarea, String fechaIni, String fechaFin, int diasTarea, String descripcion) {
        this.id = id;
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = validarFecha(fechaIni);
        this.fechaFin = validarFecha(fechaFin);
        this.diasTarea = diasTarea;
        this.descripcion = descripcion;
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

    protected Tarea(Parcel in) {
        id = in.readLong();
        nombreTarea = in.readString();
        porcentajeTarea = in.readInt();
        long tmpFechaCreacion = in.readLong();
        fechaIni = tmpFechaCreacion == -1 ? null : new Date(tmpFechaCreacion);
        long tmpFechaObjetivo = in.readLong();
        fechaFin = tmpFechaObjetivo == -1 ? null : new Date(tmpFechaObjetivo);
        diasTarea = in.readInt();
        prioritaria = in.readByte() != 0;
        descripcion = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeLong(this.id);
        parcel.writeString(this.nombreTarea);
        parcel.writeInt(this.porcentajeTarea);
        parcel.writeLong(this.fechaIni != null ? this.fechaIni.getTime() : -1);
        parcel.writeLong(this.fechaFin != null ? this.fechaFin.getTime() : -1);
        parcel.writeInt(this.diasTarea);
        parcel.writeByte((byte) (prioritaria ? 1 : 0));
        parcel.writeString(this.descripcion);
    }
}
