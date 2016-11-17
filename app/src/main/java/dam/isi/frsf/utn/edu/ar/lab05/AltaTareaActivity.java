package dam.isi.frsf.utn.edu.ar.lab05;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.dao.ProyectoDAO;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Proyecto;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Usuario;

public class AltaTareaActivity extends AppCompatActivity {
    ProyectoDAO dao;

    EditText descripcion;
    EditText horasEstimadas;
    SeekBar prioridad;
    Spinner responsable;
    Button btnGuardar;
    Button btnCancelar;
    Cursor usuarios;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_tarea);

        // linkear componentes del layout
        descripcion = (EditText) findViewById(R.id.descripcion);
        horasEstimadas= (EditText) findViewById(R.id.horasEstimadas);
        prioridad = (SeekBar) findViewById(R.id.prioridad);
        responsable = (Spinner) findViewById(R.id.responsable);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        //responsables
        dao = new ProyectoDAO(this);
        usuarios = dao.listarUsuarios();
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, usuarios, new String[] {"NOMBRE"}, new int[] { android.R.id.text1 }, 0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        responsable.setAdapter(adapter);

        //click guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("responsable",""+responsable.getSelectedItemId());
                Log.d("prioridad",""+prioridad.getProgress());
                Log.d("estimadas",horasEstimadas.getText().toString());

                Tarea tarea = new Tarea();
                tarea.setDescripcion(descripcion.getText().toString());
                tarea.setHorasEstimadas(Integer.valueOf(horasEstimadas.getText().toString()) + 1);
                tarea.setIdPrioridad(prioridad.getProgress());
                tarea.setIdResponsable((int) responsable.getSelectedItemId());

                dao.nuevaTarea(tarea);



            }
        });


    }
}
