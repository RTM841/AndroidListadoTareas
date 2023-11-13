package com.example.pantallatareas.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pantallatareas.CompartirViewModel;
import com.example.pantallatareas.R;

import java.util.Date;

public class FragmentoDos extends Fragment {

    private TextView textoNombreTarea, fechaini, fechafin, textoDescipcion;
    private Button boton;
    private CompartirViewModel compartirViewModel;

    public FragmentoDos() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obtenemos una referencia del ViewModel
        CompartirViewModel compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);

        //Creamos un observador (de String) para implementar el método onChanged()
        Observer<String> observadorNombre = new Observer<String>() {
            @Override
            public void onChanged(String text) {
                textoNombreTarea.setText(text);

            }
        };

        //Asignamos un observador al MutableLiveData
        compartirViewModel.getNombre().observe(this, observadorNombre);

        //Creamos un observador (de Date) para implementar el método onChanged()
        Observer<Date> observadorFechaInicio = new Observer<Date>() {
            @Override
            public void onChanged(Date fecha) {
                fechaini.setText(fecha.toString());
            }
        };
        //Asignamos un observador al MutableLiveData
        compartirViewModel.getFechaIni().observe(FragmentoDos.this, fecha -> fechaini.setText(fecha.toString()));

        //Creamos un observador (de Date) para implementar el método onChanged()
        Observer<Date> observadorFechaFin = new Observer<Date>() {
            @Override
            public void onChanged(Date fecha) {
                fechafin.setText(fecha.toString());
            }
        };
        //Asignamos un observador al MutableLiveData
        compartirViewModel.getFechaFin().observe(FragmentoDos.this, fecha -> fechafin.setText(fecha.toString()));


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento2 = inflater.inflate(R.layout.fragment_fragmento_dos, container, false);

        boton = fragmento2.findViewById(R.id.bt_guardar);
        boton.setOnClickListener( view -> {
            compartirViewModel.setDescip(textoDescipcion.toString());//Guardo la descripción del fragmento
        });

        return fragmento2;
    }
}