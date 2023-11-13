package com.example.pantallatareas;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Tarea implements Serializable {
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
}
