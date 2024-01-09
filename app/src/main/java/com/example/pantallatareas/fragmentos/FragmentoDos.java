package com.example.pantallatareas.fragmentos;

import static android.app.Activity.RESULT_OK;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pantallatareas.CompartirViewModel;
import com.example.pantallatareas.R;

import java.text.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FragmentoDos extends Fragment {

    private TextView textoDescipcion;
    private Button boton, boton2, btDocumentos, btImagenes, btAudios, btVídeos;
    private CompartirViewModel compartirViewModel;
    private static final int PICK_DOCUMENT_REQUEST = 1;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1;

    public FragmentoDos() {

    }


    public interface ComunicacionFragmento1 {
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

        btDocumentos = fragmento2.findViewById(R.id.bt_documentos);
        btDocumentos.setOnClickListener(this::selecDocumento);

        btImagenes = fragmento2.findViewById(R.id.bt_imagen);
        btImagenes.setOnClickListener(this::selecImagen);

        btAudios = fragmento2.findViewById(R.id.bt_audio);
        btAudios.setOnClickListener(this::selecAudio);

        btVídeos = fragmento2.findViewById(R.id.bt_video);
        btVídeos.setOnClickListener(this::selecVideo);

        //boton.setOnClickListener(this::guardar);

        boton2 = fragmento2.findViewById(R.id.bt_volver);
        boton2.setOnClickListener(this::volver);

        boton = fragmento2.findViewById(R.id.bt_guardar);
        boton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(textoDescipcion.getText())) {
                mostrarAlertDialog("Por favor, completa todos los campos.");
            } else {
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


    private void volver(View view) {

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

    public void selecDocumento(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("text/*");

        startActivityForResult(intent, PICK_DOCUMENT_REQUEST);
    }

    public void selecImagen(View view) {

    }

    public void selecAudio(View view) {

    }

    public void selecVideo(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            // Verificar si el permiso fue concedido
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, realizar la operación deseada
                // Puedes llamar a copyFileToInternalStorage() aquí
            } else {
                // Permiso denegado, manejar de acuerdo a tus necesidades
                Toast.makeText(requireContext(), "¡Enviado!", Toast.LENGTH_SHORT).show();
                ;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_DOCUMENT_REQUEST && resultCode == RESULT_OK && data != null) {
            // Aquí manejas el resultado de la selección del documento
            Uri selectedDocumentUri = data.getData();

            // Ahora puedes realizar acciones con el URI del documento seleccionado
            // Por ejemplo, copiarlo a la carpeta deseada en la memoria interna
            copyFileToInternalStorage(selectedDocumentUri);
        }
    }

    private void copyFileToInternalStorage(Uri sourceUri) {
        try {
            // Abre un flujo de entrada desde el Uri proporcionado
            InputStream inputStream = requireContext().getContentResolver().openInputStream(sourceUri);

            // Define la ubicación de destino en la memoria interna
            String destinationPath = "/data/data/com.example.pantallatareas/documentos/documento.txt";

            // Abre un flujo de salida hacia la ubicación de destino
            OutputStream outputStream = new FileOutputStream(destinationPath);

            // Copia los datos del InputStream al OutputStream
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Cierra los flujos
            inputStream.close();
            outputStream.close();

            // Notificar al usuario que la operación fue exitosa
            Toast.makeText(requireContext(), "Archivo guardado correctamente en la carpeta interna", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir durante la copia del archivo
            Toast.makeText(requireContext(), "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}