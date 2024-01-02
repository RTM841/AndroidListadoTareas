package com.example.pantallatareas.fragmentos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.text.TextUtils;
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

import java.text.ParseException;


public class FragmentoUno extends Fragment implements View.OnClickListener {

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

   private FragmentoDos fragmentoDos;

    private CompartirViewModel compartirViewModel;

    private EditText editTitulo, editfechaincio, editfechafin, titulo;


    private Spinner barra;

    private CheckBox prioritaria;
    private boolean esPriotaria;

    private Button button1 , button2;

    public FragmentoUno() {

    }

    public interface ComunicacionFragmento{
        //Definimos los prototipos de los métodos que se han de implementar
        //en este caso hay dos métodos

        void siguiente();
    }



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

        prioritaria = fragmento1.findViewById(R.id.cbPrioritaria);
        if (prioritaria.isChecked()){
            esPriotaria = true;
        }else{
            esPriotaria = false;
        }
        prioritaria.setChecked(compartirViewModel.getPrioritariaValue());


        button1 = fragmento1.findViewById(R.id.bt_siguiente);
        button1.setOnClickListener(this::siguiente);

        button2 = fragmento1.findViewById(R.id.bt_cancelar);
        button2.setOnClickListener(this::cancelar);

        titulo = fragmento1.findViewById(R.id.editTetxtTituloTarea);


        return fragmento1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        editTitulo.setText(compartirViewModel.getNombre().getValue());
        editfechaincio.setText(compartirViewModel.getFechaIni().getValue());
        editfechafin.setText(compartirViewModel.getFechaFin().getValue());

    }

    private void siguiente(View view) {


        if (TextUtils.isEmpty(editTitulo.getText()) || TextUtils.isEmpty(editfechaincio.getText()) || TextUtils.isEmpty(editfechafin.getText())){
            mostrarAlertDialog("Por favor, completa todos los campos.");
        }else{
            compartirViewModel.setNombre(editTitulo.getText().toString());
            compartirViewModel.setFechaIni(editfechaincio.getText().toString());
            compartirViewModel.setFechaFin(editfechafin.getText().toString());//Escribimos en el ViewModel
            compartirViewModel.setEstadoTarea(barra.getSelectedItem().toString());
            compartirViewModel.setPrioritaria(prioritaria.isChecked());
            Toast.makeText(requireContext(), "¡Enviado!", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, new FragmentoDos()).commit();
        }

    }


    private void mostrarAlertDialog(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(mensaje)
                .setTitle("Campos Vacíos")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Acción cuando se hace clic en el botón Aceptar
                        dialog.dismiss(); // Cierra el diálogo
                    }
                });

        builder.create().show();
    }
    private void cancelar(View view){
        getActivity().finish();
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