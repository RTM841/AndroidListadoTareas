package com.example.pantallatareas;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Tarea implements Parcelable {
    private long id;
    private static long contador = 0;
    public String nombreTarea;
    public int porcentajeTarea;
    public String fechaIni ;
    public  String fechaFin;
    public int diasTarea;
    public boolean prioritaria;

    public Tarea(String nombreTarea, int porcentajeTarea, String fechaIni, String fechaFin, int diasTarea, boolean prioritaria) {
        this.id = ++contador;
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.diasTarea = diasTarea;
        this.prioritaria = prioritaria;
    }

    public Tarea(String nombreTarea, int porcentajeTarea, String fechaIni, String fechaFin, int diasTarea) {
        this.id = ++contador;
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.diasTarea = diasTarea;
    }
    public Tarea(long id, String nombreTarea, int porcentajeTarea, String fechaIni, String fechaFin, int diasTarea) {
        this.id = ++contador;
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.diasTarea = diasTarea;
        this.prioritaria = prioritaria;
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

    public Tarea(String nombreTarea, int porcentajeTarea, String fecha) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fechaIni = fecha;
    }

    protected Tarea(Parcel in) {
        id = in.readLong();
        nombreTarea = in.readString();
        porcentajeTarea = in.readInt();
        fechaIni = in.readString();
        fechaFin = in.readString();
        diasTarea = in.readInt();
        prioritaria = in.readByte() != 0;
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
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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

    public static long getContador() {
        return contador;
    }

    public static void setContador(long contador) {
        Tarea.contador = contador;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeLong(this.id);
        parcel.writeString(this.nombreTarea);
        parcel.writeInt(this.porcentajeTarea);
        parcel.writeString(this.fechaIni);
        parcel.writeString(this.fechaFin);
        parcel.writeInt(this.diasTarea);
        parcel.writeByte((byte) (prioritaria ? 1 : 0));
    }
}
