package com.example.pantallatareas.fragmentos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.text.ParseException;
import java.util.Date;

public class FragmentoDos extends Fragment {

    private TextView textoDescipcion;
    private Button boton;
    private CompartirViewModel compartirViewModel;

    public FragmentoDos() {

    }

    public interface ComunicacionFragmento1{
        //Definimos los prototipos de los métodos que se han de implementar
        //en este caso hay dos métodos

        void onGuardar() throws ParseException;
    }

    private ComunicacionFragmento1 comunicador1;

    @Override
    public void onAttach(@NonNull Context context) {
        //Sobrescribimos para esto el método onAttach() donde recibimos el contexto (=Actividad)
        super.onAttach(context);
        if (context instanceof ComunicacionFragmento1) {  //Si la Actividad implementa la interfaz de comunicación
            comunicador1 = (ComunicacionFragmento1) context; //la Actividad se convierte en comunicador
        } else {
            throw new ClassCastException(context + " debe implementar interfaz de comunicación con el 1º fragmento");
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*//Obtenemos una referencia del ViewModel
         compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);

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
        compartirViewModel.getFechaFin().observe(FragmentoDos.this, fecha -> fechafin.setText(fecha.toString()));*/


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento2 = inflater.inflate(R.layout.fragment_fragmento_dos, container, false);
        compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);

        textoDescipcion = fragmento2.findViewById(R.id.txtDescripcion);
        textoDescipcion.setText(compartirViewModel.getGetDescrip().getValue());

        //boton.setOnClickListener(this::guardar);

        boton = fragmento2.findViewById(R.id.bt_guardar);
        boton.setOnClickListener( view -> {
            compartirViewModel.setDescip(textoDescipcion.getText().toString());
            try {
                comunicador1.onGuardar();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            getActivity().finish();//Guardo la descripción del fragmento
        });

        return fragmento2;
    }

   /* private void guardar(View view) {

        compartirViewModel.setDescip(textoDescipcion.getText().toString());
        comunicador1.onGuardar();
        getActivity().finish();
    }*/
}