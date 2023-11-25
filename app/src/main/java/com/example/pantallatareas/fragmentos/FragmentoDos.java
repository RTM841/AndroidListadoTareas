package com.example.pantallatareas.fragmentos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
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
    private Button boton, boton2;
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

        boton2 = fragmento2.findViewById(R.id.bt_volver);
        boton2.setOnClickListener(this::volver);

        boton = fragmento2.findViewById(R.id.bt_guardar);
        boton.setOnClickListener( view -> {
            if(TextUtils.isEmpty(textoDescipcion.getText())){mostrarAlertDialog("Por favor, completa todos los campos.");}
            else{
                compartirViewModel.setDescip(textoDescipcion.getText().toString());
                try {
                    comunicador1.onGuardar();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                getActivity().finish();//Guardo la descripción del fragmento
            }

        });

        return fragmento2;
    }


    private void volver(View view){

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, new FragmentoUno()).commit();


    }

    public void mostrarAlertDialog(String mensaje) {
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

   /* private void guardar(View view) {

        compartirViewModel.setDescip(textoDescipcion.getText().toString());
        comunicador1.onGuardar();
        getActivity().finish();
    }*/
}