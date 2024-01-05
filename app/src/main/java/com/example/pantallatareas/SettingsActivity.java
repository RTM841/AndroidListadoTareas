package com.example.pantallatareas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    public void aplicarTemaOscuro() {
        boolean modoOscuro = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("modo_oscuro", false);

        if (modoOscuro) {
            setTheme(R.style.AppTheme_Dark);
            aplicarTamanoLetraOscuro();
            // Cambiado a R.style.AppTheme_Dark
        }else{aplicarTamanoLetra();}

        // Escuchar cambios en la preferencia del modo oscuro
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
                    if ("modo_oscuro".equals(key)) {
                        recreate(); // Reiniciar la actividad para aplicar el nuevo tema
                    }
                });
    }


    public void aplicarTamanoLetra() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizePreference = sharedPreferences.getString("tipoLetra", "normal");

        switch (fontSizePreference) {
            case "small":
                setTheme(R.style.Base_Theme_PantallaTareas_FuentePequena);
                break;
            case "normal":
                setTheme(R.style.Base_Theme_PantallaTareas_FuenteMediana);
                break;
            case "large":
                setTheme(R.style.Base_Theme_PantallaTareas_FuenteGrande);
                break;
            default:
                setTheme(R.style.Base_Theme_PantallaTareas_FuenteMediana);
        }
    }

    public void aplicarTamanoLetraOscuro() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizePreference = sharedPreferences.getString("tipoLetra", "normal");

        switch (fontSizePreference) {
            case "small":
                setTheme(R.style.AppTheme_Dark_FuentePequena);
                break;
            case "normal":
                setTheme(R.style.AppTheme_Dark_FuenteMediana);
                break;
            case "large":
                setTheme(R.style.AppTheme_Dark_FuenteGrande);
                break;
            default:
                setTheme(R.style.AppTheme_Dark_FuenteMediana);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aplicarTemaOscuro();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si se selecciona el botón atrás de la barra de menú,
        // cerramos la actividad Preferencias
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}