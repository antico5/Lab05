package dam.isi.frsf.utn.edu.ar.lab05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.dao.ProyectoDAO;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;

public class BuscarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText minutosDesvio;
    private CheckBox cbFinalizada;
    private Button btnBuscar;
    private TextView rdoBusqueda;
    private ProyectoDAO proyectoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        minutosDesvio = (EditText) findViewById(R.id.minutosDesvio);
        cbFinalizada = (CheckBox) findViewById(R.id.cbFinalizada);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        rdoBusqueda = (TextView) findViewById(R.id.rdoBusqueda);
        btnBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() != R.id.btnBuscar) {
            return;
        }


        Boolean terminadas = cbFinalizada.isChecked();
        Integer desvio = Integer.parseInt(minutosDesvio.getText().toString());

        Log.d("finalizada", terminadas.toString());

        proyectoDAO = new ProyectoDAO(this);
        proyectoDAO.open();
        List<Tarea> lista = proyectoDAO.listarDesviosPlanificacion(terminadas, desvio);
        String texto = "";
        for(Tarea tarea : lista) {
            texto += tarea.getDescripcion() +
                    " Horas estimadas: " + tarea.getHorasEstimadas() +
                    " Desvio: " + Math.abs(tarea.getMinutosTrabajados() - (tarea.getHorasEstimadas() * 60)) +
                    "\n";
        }
        rdoBusqueda.setLines(lista.size());
        rdoBusqueda.setText((texto.equals("")) ? "No se encontraron resultados" : texto);
    }
}
