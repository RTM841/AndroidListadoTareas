package com.example.pantallatareas;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.pantallatareas.fragmentos.FragmentoDos;
import com.example.pantallatareas.fragmentos.FragmentoUno;

public class CrearTareasActivity extends AppCompatActivity {

    private FragmentoUno fragmentoUno;
    private FragmentoDos fragmentoDos;
    private CompartirViewModel compartirViewModel;
    private FragmentManager fragmentManager;
    private Button continuar;
    private Button cancelar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        compartirViewModel = new ViewModelProvider(this).get(CompartirViewModel.class);

        fragmentoUno = new FragmentoUno();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos,fragmentoUno).commit();

    }

}
