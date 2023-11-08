package com.example.pantallatareas;

import java.util.Date;
import java.util.Objects;

public class Tarea {
    private long id;
    private static long contador = 0;


    public String nombreTarea;
    public int porcentajeTarea;
    public String fecha ;
    public int diasTarea;
    public boolean prioritaria;

    public Tarea(String nombreTarea, int porcentajeTarea, String fecha, int diasTarea, boolean prioritaria) {
        this.id = ++contador;
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fecha = fecha;
        this.diasTarea = diasTarea;
        this.prioritaria = prioritaria;
    }

    public Tarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }
    public Tarea(String nombreTarea, int porcentajeTarea) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
    }

    public Tarea(String nombreTarea, int porcentajeTarea, String fecha) {
        this.nombreTarea = nombreTarea;
        this.porcentajeTarea = porcentajeTarea;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
