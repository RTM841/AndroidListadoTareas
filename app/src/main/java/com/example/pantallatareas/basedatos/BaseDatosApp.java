package com.example.pantallatareas.basedatos;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.pantallatareas.Modelos.Tarea;

//Anotación que indica que esta clase es una base de datos de ROOM
//que guarda entidades de la cale Tarea
@Database(entities = {Tarea.class}, version = 1, exportSchema = false)
public class BaseDatosApp extends RoomDatabase {

    //Usamo el patrón SINGLETON
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
