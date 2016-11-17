package dam.isi.frsf.utn.edu.ar.lab05.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.modelo.Prioridad;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Usuario;

/**
 * Created by mdominguez on 06/10/16.
 */
public class ProyectoDAO {

    private static final String _SQL_TAREAS_X_PROYECTO = "SELECT " + ProyectoDBMetadata.TABLA_TAREAS_ALIAS + "." + ProyectoDBMetadata.TablaTareasMetadata._ID + " as " + ProyectoDBMetadata.TablaTareasMetadata._ID +
            ", " + ProyectoDBMetadata.TablaTareasMetadata.TAREA +
            ", " + ProyectoDBMetadata.TablaTareasMetadata.HORAS_PLANIFICADAS +
            ", " + ProyectoDBMetadata.TablaTareasMetadata.MINUTOS_TRABAJADOS +
            ", " + ProyectoDBMetadata.TablaTareasMetadata.FINALIZADA +
            ", " + ProyectoDBMetadata.TablaTareasMetadata.PRIORIDAD +
            ", " + ProyectoDBMetadata.TABLA_PRIORIDAD_ALIAS + "." + ProyectoDBMetadata.TablaPrioridadMetadata.PRIORIDAD + " as " + ProyectoDBMetadata.TablaPrioridadMetadata.PRIORIDAD_ALIAS +
            ", " + ProyectoDBMetadata.TablaTareasMetadata.RESPONSABLE +
            ", " + ProyectoDBMetadata.TABLA_USUARIOS_ALIAS + "." + ProyectoDBMetadata.TablaUsuariosMetadata.USUARIO + " as " + ProyectoDBMetadata.TablaUsuariosMetadata.USUARIO_ALIAS +
            " FROM " + ProyectoDBMetadata.TABLA_PROYECTO + " " + ProyectoDBMetadata.TABLA_PROYECTO_ALIAS + ", " +
            ProyectoDBMetadata.TABLA_USUARIOS + " " + ProyectoDBMetadata.TABLA_USUARIOS_ALIAS + ", " +
            ProyectoDBMetadata.TABLA_PRIORIDAD + " " + ProyectoDBMetadata.TABLA_PRIORIDAD_ALIAS + ", " +
            ProyectoDBMetadata.TABLA_TAREAS + " " + ProyectoDBMetadata.TABLA_TAREAS_ALIAS +
            " WHERE " + ProyectoDBMetadata.TABLA_TAREAS_ALIAS + "." + ProyectoDBMetadata.TablaTareasMetadata.PROYECTO + " = " + ProyectoDBMetadata.TABLA_PROYECTO_ALIAS + "." + ProyectoDBMetadata.TablaProyectoMetadata._ID + " AND " +
            ProyectoDBMetadata.TABLA_TAREAS_ALIAS + "." + ProyectoDBMetadata.TablaTareasMetadata.RESPONSABLE + " = " + ProyectoDBMetadata.TABLA_USUARIOS_ALIAS + "." + ProyectoDBMetadata.TablaUsuariosMetadata._ID + " AND " +
            ProyectoDBMetadata.TABLA_TAREAS_ALIAS + "." + ProyectoDBMetadata.TablaTareasMetadata.PRIORIDAD + " = " + ProyectoDBMetadata.TABLA_PRIORIDAD_ALIAS + "." + ProyectoDBMetadata.TablaPrioridadMetadata._ID + " AND " +
            ProyectoDBMetadata.TABLA_TAREAS_ALIAS + "." + ProyectoDBMetadata.TablaTareasMetadata.PROYECTO + " = ?";

    private ProyectoOpenHelper dbHelper;
    SQLiteDatabase db;

    public ProyectoDAO(Context c) {
        this.dbHelper = new ProyectoOpenHelper(c);
    }

    public SQLiteDatabase open() {
        return this.open(false);
    }

    public SQLiteDatabase open(Boolean toWrite) {
        if (toWrite) {
            db = dbHelper.getWritableDatabase();
            return db;
        } else {
            db = dbHelper.getReadableDatabase();
            return db;
        }
    }

    public void close(){
        if(db != null)
            db.close();
    }

    public Tarea buscarTarea(Integer id){
        Log.d("ID_TAREA", id.toString());
        SQLiteDatabase db = open();
        Tarea tarea = new Tarea();
        String sql = "select _id, DESCRIPCION, HORAS_PLANIFICADAS, MINUTOS_TRABAJDOS, ID_PRIORIDAD, ID_RESPONSABLE, FINALIZADA from TAREA where _id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id.toString()});
        cursor.moveToFirst();
        tarea.setId(cursor.getInt(0));
        tarea.setDescripcion(cursor.getString(1));
        tarea.setHorasEstimadas(cursor.getInt(2));
        tarea.setMinutosTrabajados(cursor.getInt(3));
        tarea.setIdPrioridad(cursor.getInt(4));
        tarea.setIdResponsable(cursor.getInt(5));
        tarea.setFinalizada(cursor.getInt(6) == 1);
        Log.d("Tarea", tarea.toString());
        return tarea;
    }


    public Cursor listaTareas(Integer idProyecto){
        SQLiteDatabase db = open();
        Cursor cursorPry = db.rawQuery("SELECT "+ProyectoDBMetadata.TablaProyectoMetadata._ID+ " FROM "+ProyectoDBMetadata.TABLA_PROYECTO,null);
        Integer idPry= 0;
        if(cursorPry.moveToFirst()){
            idPry=cursorPry.getInt(0);
        }
        Cursor cursor = null;
        Log.d("LAB05-MAIN","PROYECTO : _"+idPry.toString()+" - "+ _SQL_TAREAS_X_PROYECTO);
        cursor = db.rawQuery(_SQL_TAREAS_X_PROYECTO,new String[]{idPry.toString()});
        return cursor;
    }

    public void nuevaTarea(Tarea t){
        String sql = "INSERT INTO " + ProyectoDBMetadata.TABLA_TAREAS
                + " ("+ ProyectoDBMetadata.TablaTareasMetadata.TAREA + ","
                + ProyectoDBMetadata.TablaTareasMetadata.HORAS_PLANIFICADAS + ","
                + ProyectoDBMetadata.TablaTareasMetadata.MINUTOS_TRABAJADOS + ","
                + ProyectoDBMetadata.TablaTareasMetadata.PRIORIDAD + ","
                + ProyectoDBMetadata.TablaTareasMetadata.RESPONSABLE + ","
                + ProyectoDBMetadata.TablaTareasMetadata.PROYECTO + ","
                + ProyectoDBMetadata.TablaTareasMetadata.FINALIZADA + ")"
                + " VALUES(?,?,?,?,?,?,?)";
        db = open(true);
        db.execSQL(sql, new String[]{t.getDescripcion(), t.getHorasEstimadas().toString(), "0", t.getIdPrioridad().toString(), t.getIdResponsable().toString(), "1", t.getFinalizada().toString()});
        db.close();

    }

    public void actualizarTarea(Tarea t){
        String sql = "UPDATE TAREA SET DESCRIPCION=?, HORAS_PLANIFICADAS=?, MINUTOS_TRABAJDOS=?, ID_PRIORIDAD=?, ID_RESPONSABLE=?, ID_PROYECTO=?, FINALIZADA=? WHERE _id=?";
        db = open(true);
        db.execSQL(sql, new String[]{t.getDescripcion(), t.getHorasEstimadas().toString(), "0", t.getIdPrioridad().toString(), t.getIdResponsable().toString(), "1", t.getFinalizada().toString(), t.getId().toString()});
        db.close();
    }

    public void borrarTarea(Integer id){
        String sql = "DELETE FROM TAREA WHERE _id = ?";
        db = open(true);
        db.execSQL(sql, new String[] {id.toString()});
        db.close();

    }

    public List<Prioridad> listarPrioridades(){
        return null;
    }

    public Cursor listarUsuarios(){
        String sql = "SELECT _ID, NOMBRE FROM USUARIOS";
        db = open();
        //Cursor cursor = db.query(true,"USUARIOS", new String[]{"_id", "nombre"},null,null,null,null,null,null);
        Cursor cursor = db.rawQuery(sql,null);
        Log.d("asd", "RESULTADOS: " + cursor.getCount());
        for(String columna : cursor.getColumnNames()){
            Log.d("asd", "COLUMNA: " + columna);
        }
        /*List<Usuario> usuarios= new ArrayList<Usuario>();
        cursor.moveToFirst();
        do {
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));

            usuarios.add(usuario);
        } while (cursor.moveToNext());

        return usuarios;*/
        return cursor;
    }

    public void finalizar(Integer idTarea){
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put(ProyectoDBMetadata.TablaTareasMetadata.FINALIZADA,1);
        db = open(true);
        db.update(ProyectoDBMetadata.TABLA_TAREAS, valores, "_id=?", new String[]{idTarea.toString()});
        db.close();
    }

    public List<Tarea> listarDesviosPlanificacion(Boolean soloTerminadas,Integer desvioMaximoMinutos){
        // retorna una lista de todas las tareas que tardaron más (en exceso) o menos (por defecto)
        // que el tiempo planificado.
        // si la bandera soloTerminadas es true, se busca en las tareas terminadas, sino en todas.
        return null;
    }


    public void agregarMinutos(int id, long minutos) {
        //Establecemos los campos-valores a actualizar
        db = open(true);
        db.execSQL("UPDATE TAREA SET MINUTOS_TRABAJDOS=(MINUTOS_TRABAJDOS+?) WHERE _ID=?;",new String[]{String.valueOf(minutos), String.valueOf(id)});
        db.close();
    }
}
