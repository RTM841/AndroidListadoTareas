package com.example.pantallatareas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    public void aplicarTemaOscuro() {
        boolean modoOscuro = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("modo_oscuro", true);

        if (modoOscuro) {
            AppCompatDelegate.setDefaultNightMode(modoOscuro ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    public void aplicarTamanoLetra() {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizePreference = sharedPreferences.getString("tipoLetra", "normal");

        switch (fontSizePreference) {
            case "small":
                configuration.fontScale = 0.8f;
                break;
            case "normal":
                configuration.fontScale = 1f;
                break;
            case "large":
                configuration.fontScale = 3f;
                break;
            default:
                configuration.fontScale = 1f;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //aplicarTemaOscuro();
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

        // Escucha cambios en las preferencias
       /* PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
                    // Notifica a ListadoTareasActivity sobre el cambio
                    Intent intent = new Intent("com.example.ACTION_PREFERENCIAS_CAMBIADAS");
                    sendBroadcast(intent);
                });*/
    }


    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener  {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            // Register the listener

        }

        @Override
        public void onResume() {
            super.onResume();
            // Register the listener when the fragment is visible
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            // Unregister the listener when the fragment is not visible
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @NonNull String key) {
            // Aquí puedes aplicar lógica para manejar cambios en preferencias específicas
            if (key.equals("modo_oscuro")) {
                boolean modoOscuro = sharedPreferences.getBoolean("modo_oscuro", true);


                    AppCompatDelegate.setDefaultNightMode(modoOscuro ? AppCompatDelegate.MODE_NIGHT_NO
                            :  AppCompatDelegate.MODE_NIGHT_YES);
                    // Aplicar tu tema oscuro personalizado
                    //requireActivity().setTheme(R.style.AppTheme_Dark);
                }
             else if (key.equals("tipoLetra")) {
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                String fontSizePreference = sharedPreferences.getString("tipoLetra", "normal");

                switch (fontSizePreference) {
                    case "small":
                        configuration.fontScale = 0.8f;
                        break;
                    case "normal":
                        configuration.fontScale = 1f;
                        break;
                    case "large":
                        configuration.fontScale = 3f;
                        break;
                    default:
                        configuration.fontScale = 1f;
                }
                resources.updateConfiguration(configuration, displayMetrics);
            }
            requireActivity().recreate();

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