package com.example.pantallatareas.basedatos;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.pantallatareas.Modelos.Tarea;
import com.example.pantallatareas.convertidores.Converters;
import com.example.pantallatareas.daos.TareaDao;

//Anotación que indica que esta clase es una base de datos de ROOM
//que guarda entidades de la cale Tarea
@Database(entities = {Tarea.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class BaseDatosApp extends RoomDatabase {

    //Usamo el patrón SINGLETON para poder asegurarnos de
    //nuestra base de datos no está duplicada
    private static BaseDatosApp INSTANCIA;

    public static BaseDatosApp getInstance(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(
                            context.getApplicationContext(),
                            BaseDatosApp.class,
                            "dbTareas")
                    .build();
        }
        return INSTANCIA;
    }

    public static void destroyInstance() {
        INSTANCIA = null;
    }

    public abstract TareaDao tareaDao();

    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}
