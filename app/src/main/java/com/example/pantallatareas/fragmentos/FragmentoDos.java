package com.example.pantallatareas.fragmentos;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.getIntent;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pantallatareas.Modelos.Tarea;
import com.example.pantallatareas.actividades.CompartirViewModel;
import com.example.pantallatareas.R;
import com.example.pantallatareas.actividades.EditarTareaActivity;
import com.example.pantallatareas.basedatos.BaseDatosApp;

import java.io.File;
import java.text.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FragmentoDos extends Fragment {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private TextView textoDescipcion;
    private Tarea tarea;
    private Button boton, boton2, btDocumentos, btImagenes, btAudios, btVídeos;
    private CompartirViewModel compartirViewModel;
    private static final int PICK_DOCUMENT_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private static final int PICK_AUDIO_REQUEST = 3;

    private static final int PICK_VIDEO_REQUEST = 4;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1;

    private BaseDatosApp baseDatosApp;

    private EditarTareaActivity editarTareaActivity;

    public FragmentoDos() {

    }

    public static FragmentoDos newInstance(Tarea tarea) {
        FragmentoDos fragmento = new FragmentoDos();
        Bundle args = new Bundle();
        args.putParcelable("tareaEditar2", tarea);
        fragmento.setArguments(args);
        return fragmento;
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

        if (getArguments() != null) {
            tarea = getArguments().getParcelable("tareaEditar2");
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento2 = inflater.inflate(R.layout.fragment_fragmento_dos, container, false);
        compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);
        baseDatosApp = BaseDatosApp.getInstance(getActivity().getApplicationContext());



        // Actualizar vistas con los datos de la tarea
        if (tarea != null) {
           textoDescipcion.setText(tarea.getDescripcion());
        }

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
        boton.setTag("Clase1");
        boton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(textoDescipcion.getText())) {
                mostrarAlertDialog("Por favor, completa todos los campos.");
            } else {
                String tag = (String) view.getTag();
                if ("Clase1".equals(tag))
                {
                    compartirViewModel.setDescip(textoDescipcion.getText().toString());
                    try {
                        //Nos vamos al método OnGuardar para poder hacer la acción
                        comunicador1.onGuardar();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }


                }else if("Clase2".equals(tag))
                {
                    try {
                        editarTareaActivity.onGuardar();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
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

        intent.setType("*/*");

        startActivityForResult(intent, PICK_DOCUMENT_REQUEST);
    }

    public void selecImagen(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void selecAudio(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("audio/*");


        startActivityForResult(intent, PICK_AUDIO_REQUEST);

    }

    public void selecVideo(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("video/*");

        startActivityForResult(intent, PICK_VIDEO_REQUEST);

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
            copyFileToInternalStorageDocumentos(selectedDocumentUri);
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            // Aquí manejas el resultado de la selección del documento
            Uri selectedImagenUri = data.getData();

            // Ahora puedes realizar acciones con el URI del documento seleccionado
            // Por ejemplo, copiarlo a la carpeta deseada en la memoria interna
            copyFileToInternalStorageImagenes(selectedImagenUri);
        } else if (requestCode == PICK_AUDIO_REQUEST) {
            // Aquí manejas el resultado de la selección del documento
            Uri selectedAudioUri = data.getData();

            // Ahora puedes realizar acciones con el URI del documento seleccionado
            // Por ejemplo, copiarlo a la carpeta deseada en la memoria interna
            copyFileToInternalStorageAudios(selectedAudioUri);
        } else if (requestCode == PICK_VIDEO_REQUEST) {
            // Aquí manejas el resultado de la selección del documento
            Uri selectedVideoUri = data.getData();

            // Ahora puedes realizar acciones con el URI del documento seleccionado
            // Por ejemplo, copiarlo a la carpeta deseada en la memoria interna
            copyFileToInternalStorageVideos(selectedVideoUri);
        }
    }



    private void copyFileToInternalStorageDocumentos(Uri sourceUri) {
        try {
            // Abre un flujo de entrada desde el Uri proporcionado
            InputStream inputStream = requireContext().getContentResolver().openInputStream(sourceUri);

            // Obtiene el nombre del archivo desde la Uri
            String fileName = getFileNameFromUri(sourceUri);

            // Obtiene el directorio de archivos internos específico de la aplicación
            File internalDir = requireContext().getFilesDir();

            // Crea la ruta de destino dentro del directorio de archivos internos
            String destinationPath = internalDir.getAbsolutePath() + File.separator + fileName;

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
            compartirViewModel.setUrlDocumento(destinationPath);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir durante la copia del archivo
            Toast.makeText(requireContext(), "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFileToExternalStorageDocumentos(Uri sourceUri) {
        try {
            // Verificar si se tiene permiso de escritura en el almacenamiento externo
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Solicitar permisos si no se tienen
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                return;
            }

            // Abrir un flujo de entrada desde el Uri proporcionado
            InputStream inputStream = requireContext().getContentResolver().openInputStream(sourceUri);

            // Obtener el nombre del archivo desde la Uri
            String fileName = getFileNameFromUri(sourceUri);

            // Obtener el directorio de almacenamiento externo
            File externalDir = Environment.getExternalStorageDirectory();

            // Crear la ruta de destino dentro del directorio de almacenamiento externo
            String destinationPath = externalDir.getAbsolutePath() + File.separator + fileName;

            // Abrir un flujo de salida hacia la ubicación de destino
            OutputStream outputStream = new FileOutputStream(destinationPath);

            // Copiar los datos del InputStream al OutputStream
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Cerrar los flujos
            inputStream.close();
            outputStream.close();

            // Notificar al usuario que la operación fue exitosa
            Toast.makeText(requireContext(), "Documento guardado correctamente en la tarjeta SD", Toast.LENGTH_SHORT).show();
            compartirViewModel.setUrlVideo(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir durante la copia del archivo
            Toast.makeText(requireContext(), "Error al guardar el archivo en la tarjeta SD", Toast.LENGTH_SHORT).show();
        }
    }



    @SuppressLint("Range")
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void copyFileToInternalStorageImagenes(Uri sourceUri) {
        try {
            // Abre un flujo de entrada desde el Uri proporcionado
            InputStream inputStream = requireContext().getContentResolver().openInputStream(sourceUri);

            // Obtiene el nombre del archivo desde la Uri
            String fileName = getFileNameFromUri(sourceUri);

            // Obtiene el directorio de archivos internos específico de la aplicación
            File internalDir = requireContext().getFilesDir();

            // Crea la ruta de destino dentro del directorio de archivos internos
            String destinationPath = internalDir.getAbsolutePath() + File.separator + fileName;

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
            Toast.makeText(requireContext(), "Imagen guardada correctamente en la carpeta interna", Toast.LENGTH_SHORT).show();
            compartirViewModel.setUrlImagen(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir durante la copia del archivo
            Toast.makeText(requireContext(), "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFileToInternalStorageAudios(Uri sourceUri) {
        try {
            // Abre un flujo de entrada desde el Uri proporcionado
            InputStream inputStream = requireContext().getContentResolver().openInputStream(sourceUri);

            // Obtiene el nombre del archivo desde la Uri
            String fileName = getFileNameFromUri(sourceUri);

            // Obtiene el directorio de archivos internos específico de la aplicación
            File internalDir = requireContext().getFilesDir();

            // Crea la ruta de destino dentro del directorio de archivos internos
            String destinationPath = internalDir.getAbsolutePath() + File.separator + fileName;

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
            Toast.makeText(requireContext(), "Audio guardada correctamente en la carpeta interna", Toast.LENGTH_SHORT).show();
            compartirViewModel.setUrlAudio(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir durante la copia del archivo
            Toast.makeText(requireContext(), "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }



    private void copyFileToInternalStorageVideos(Uri sourceUri) {
        try {
            // Abre un flujo de entrada desde el Uri proporcionado
            InputStream inputStream = requireContext().getContentResolver().openInputStream(sourceUri);

            // Obtiene el nombre del archivo desde la Uri
            String fileName = getFileNameFromUri(sourceUri);

            // Obtiene el directorio de archivos internos específico de la aplicación
            File internalDir = requireContext().getFilesDir();

            // Crea la ruta de destino dentro del directorio de archivos internos
            String destinationPath = internalDir.getAbsolutePath() + File.separator + fileName;

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
            Toast.makeText(requireContext(), "Video guardada correctamente en la carpeta interna", Toast.LENGTH_SHORT).show();
            compartirViewModel.setUrlVideo(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir durante la copia del archivo
            Toast.makeText(requireContext(), "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }
    }




}