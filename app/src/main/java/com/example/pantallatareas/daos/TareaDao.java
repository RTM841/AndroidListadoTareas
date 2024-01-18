package com.example.pantallatareas.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pantallatareas.Modelos.Tarea;

import java.util.List;

//Anotación que indica que esto es un DAO de Rooom
@Dao
public interface TareaDao {

    //Anotación para consultar todas las tareas que ahí
    @Query("SELECT * FROM Tarea")
    LiveData<List<Tarea>> listadoTareas();

    //Anotacion que podemos utilizar para buscar una tarea por su id
    @Query("SELECT * FROM Tarea WHERE _id IN (:tareaIds)")
    LiveData<List<Tarea>> listadorPorId(int tareaIds);

    //Anotación para poder insertar una tarea
    @Insert
    //Metodo para hacerlo
    void insertarTarea(Tarea tarea);

    //Anotación para borra una tarea
    @Delete
    //Metodo
    void borrarTarea(Tarea tarea);

    //Anotación para actualizar la tarea
    @Update
    //Metodo
    void actualizarTarea(Tarea tarea);

    //Anotación para borrar tarea
    @Query("DELETE FROM Tarea WHERE _id = :tareaId")
    void borrarPorID(int tareaId);



    //Anotación para consultar tareas Prioritarias
    @Query("SELECT * FROM Tarea WHERE prioritaia = true")
    LiveData<List<Tarea>> listadorPrio();

    //Anotaciones para la pestaña de Estadísticas.

    //Anotación para contar el numero de tareas iniciadas
    @Query("SELECT COUNT(*) FROM Tarea WHERE progresoTarea >= 25")
    int listadorTareasInciadas();

    //Anotación de tareas finalizadas
    @Query("SELECT COUNT(*) FROM Tarea WHERE progresoTarea = 100")
    int listadoTareasFinalizadas();

    //Anotación para saber el promedio de los progresos
    @Query("SELECT AVG(progresoTarea) FROM Tarea")
    float obtenerPromedioProgreso();

    //Anotación para saber la fecha de finalización promedia
    @Query("SELECT AVG(diasTarea) FROM Tarea")
    float obtenerPromedioFechaObjetivo();

}
