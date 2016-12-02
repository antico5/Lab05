package dam.isi.frsf.utn.edu.ar.lab05;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import dam.isi.frsf.utn.edu.ar.lab05.dao.EjemploPermisos;

public class BuscarContactosActivity extends AppCompatActivity {

    EditText buscarContactos;
    ListView lvContactos;
    Button btnBuscarContactos;

    private boolean flagPermisoPedido;
    private static final int PERMISSION_REQUEST_CONTACT =999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_contactos);

        buscarContactos = (EditText) findViewById(R.id.buscarContacto);
        lvContactos = (ListView) findViewById(R.id.lvContactos);
        btnBuscarContactos = (Button) findViewById(R.id.btnBuscarContacto);

        btnBuscarContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForContactPermission();
            }
        });
    }

    public void buscarContactos(String nombreBuscado){
        JSONArray arr = new JSONArray();
        final StringBuilder resultado = new StringBuilder();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        // consulta ejemplo buscando por nombre visualizado en los contactos agregados
        Cursor c =this.getContentResolver().query(uri, null, ContactsContract.Contacts.DISPLAY_NAME+" LIKE '"+nombreBuscado+"%'", null, sortOrder);
        int count = c.getColumnCount();
        int fila = 0;
        String[] columnas= new String[count];
        try {
            while(c.moveToNext()) {
                JSONObject unContacto = new JSONObject();
                for(int i = 0; (i < count );  i++) {
                    if(fila== 0)columnas[i]=c.getColumnName(i);
                    unContacto.put(columnas[i],c.getString(i));
                }
                Log.d("TEST-ARR",unContacto.toString());
                arr.put(fila,unContacto);
                fila++;
                Log.d("TEST-ARR","fila : "+fila);

                // elegir columnas de ejemplo
                resultado.append(unContacto.get("name_raw_contact_id")+" - "+unContacto.get("display_name")+System.getProperty("line.separator"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TEST-ARR",arr.toString());
    }

    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(BuscarContactosActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(BuscarContactosActivity.this,
                        Manifest.permission.CALL_PHONE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BuscarContactosActivity.this);
                    builder.setTitle("Permisos Peligrosos!!!");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Puedo acceder a un permiso peligroso???");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            flagPermisoPedido=true;
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.GET_ACCOUNTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                } else {
                    flagPermisoPedido=true;
                    ActivityCompat.requestPermissions(BuscarContactosActivity.this,
                            new String[]
                                    {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.GET_ACCOUNTS}
                            , PERMISSION_REQUEST_CONTACT);
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("ESCRIBIR_JSON","req code"+requestCode+ " "+ Arrays.toString(permissions)+" ** "+Arrays.toString(grantResults));
        switch (requestCode) {
            case BuscarContactosActivity.PERMISSION_REQUEST_CONTACT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String busqueda = buscarContactos.getText().toString();
                    buscarContactos(busqueda);
                } else {
                    Toast.makeText(BuscarContactosActivity.this, "No permission for contacts", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }
}
