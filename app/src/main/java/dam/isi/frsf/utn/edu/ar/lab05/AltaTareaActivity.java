package dam.isi.frsf.utn.edu.ar.lab05;

import android.content.Intent;
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
    Button btnContacto;
    Cursor usuarios;
    SimpleCursorAdapter adapter;

    Integer idTarea;
    Tarea tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_tarea);

        idTarea = getIntent().getIntExtra("ID_TAREA", 0);
        dao = new ProyectoDAO(this);

        // linkear componentes del layout
        descripcion = (EditText) findViewById(R.id.descripcion);
        horasEstimadas= (EditText) findViewById(R.id.horasEstimadas);
        prioridad = (SeekBar) findViewById(R.id.prioridad);
        responsable = (Spinner) findViewById(R.id.responsable);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnContacto = (Button) findViewById(R.id.btnContacto);

        //Spinner responsables
        usuarios = dao.listarUsuarios();
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, usuarios, new String[] {"NOMBRE"}, new int[] { android.R.id.text1 }, 0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        responsable.setAdapter(adapter);

        //llenar los campos
        if(editando()){
            tarea = dao.buscarTarea(idTarea);
            descripcion.setText(tarea.getDescripcion());
            horasEstimadas.setText(tarea.getHorasEstimadas().toString());
            prioridad.setProgress(tarea.getIdPrioridad() - 1);
            responsable.setSelection(tarea.getIdResponsable() - 1);
        }

        //click guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("responsable",""+responsable.getSelectedItemId());
                Log.d("prioridad",""+prioridad.getProgress());
                Log.d("estimadas",horasEstimadas.getText().toString());

                if(!editando())
                    tarea = new Tarea();

                tarea.setDescripcion(descripcion.getText().toString());
                tarea.setHorasEstimadas(Integer.valueOf(horasEstimadas.getText().toString()));
                tarea.setIdPrioridad(prioridad.getProgress() +1);
                tarea.setIdResponsable((int) responsable.getSelectedItemId());

                if(editando())
                    dao.actualizarTarea(tarea);
                else
                    dao.nuevaTarea(tarea);
                finish();

            }
        });

        //click cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();

            }
        });


    }

    private boolean editando(){
        return idTarea != 0;
    }
}
