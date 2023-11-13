package com.example.pantallatareas.fragmentos;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pantallatareas.CompartirViewModel;
import com.example.pantallatareas.DatePickerFragment;
import com.example.pantallatareas.R;

import java.util.Calendar;


public class FragmentoUno extends Fragment implements View.OnClickListener {

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

   private FragmentoDos fragmentoDos;

    private CompartirViewModel compartirViewModel;

    private EditText editTitulo, editfechaincio, editfechafin;

    private Spinner barra;

    private CheckBox prioritaria;

    private Button button;

    public FragmentoUno() {

    }


    /*public static FragmentoUno newInstance(String param1, String param2) {
        FragmentoUno fragment = new FragmentoUno();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentoDos = new FragmentoDos();
        compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmento1 = inflater.inflate(R.layout.fragment_fragmento_uno, container, false);

        editfechaincio = (EditText) fragmento1.findViewById(R.id.editTextFechaCreacion);
        editfechaincio.setOnClickListener(this);

        editfechafin = (EditText) fragmento1.findViewById(R.id.editTextFechaFinal);
        editfechafin.setOnClickListener(this);

        String[] progresbarra = {"No iniciada", "Iniciada", "Avanzada", "Casi finalizada","Finalizada"};

        barra = fragmento1.findViewById(R.id.spinerProgreso);

        barra.setAdapter(new ArrayAdapter<String>(fragmento1.getContext(), android.R.layout.simple_spinner_dropdown_item, progresbarra));

        editTitulo = fragmento1.findViewById(R.id.editTetxtTituloTarea);
        editTitulo.setText(compartirViewModel.getNombre().getValue()); //Leemos del ViewModel por si hay algo


        button = fragmento1.findViewById(R.id.bt_siguiente);
        button.setOnClickListener(this::siguiente);



        return fragmento1;
    }

    private void siguiente(View view) {
        compartirViewModel.setNombre(editTitulo.getText().toString());
        compartirViewModel.setFechaIni(editfechaincio.getText().toString());
        compartirViewModel.setFechaFin(editfechafin.getText().toString());//Escribimos en el ViewModel
        compartirViewModel.setEstadoTarea(barra.getSelectedItem().toString());
        Toast.makeText(requireContext(), "¡Enviado!", Toast.LENGTH_SHORT).show();

        /*requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.segundo_fragment, new FragmentoDos()).addToBackStack(null).commit();
        View fragmentContanier1 = requireActivity().findViewById(R.id.primer_fragment);
        View fragmetnContainer2 = requireActivity().findViewById(R.id.segundo_fragment);

        fragmentContanier1.setVisibility(view.GONE);
        fragmetnContainer2.setVisibility(view.VISIBLE);*/

        // Cambiar al FragmentoDos al hacer clic en el botón "Siguiente"
        /*FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragmentos, fragmentoDos);
        fragmentTransaction.addToBackStack(null); // Opcional: Agregar a la pila de retroceso
        fragmentTransaction.commit();*/

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, new FragmentoDos()).commit();


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.editTextFechaCreacion){showDatePickerDialog1();}
        else if(view.getId() == R.id.editTextFechaFinal){showDatePickerDialog2();}

    }

    private void showDatePickerDialog1() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                editfechaincio.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private void showDatePickerDialog2() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                editfechafin.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }




}