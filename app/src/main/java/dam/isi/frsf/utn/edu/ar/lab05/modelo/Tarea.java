package dam.isi.frsf.utn.edu.ar.lab05.modelo;

/**
 * Created by mdominguez on 06/10/16.
 */
public class Tarea {

    private String descripcion;
    private Integer id;
    private Integer horasEstimadas;
    private Integer minutosTrabajados;
    private Boolean finalizada;
    private Proyecto proyecto;
    private Prioridad prioridad;
    private Integer idPrioridad;
    private Usuario responsable;
    private Integer idResponsable;

    public Tarea() {
        descripcion = "";
        id = 0;
        finalizada = false;
        horasEstimadas = 0;
        minutosTrabajados = 0;
        idPrioridad = 1;
        idResponsable = 1;
    }

    public Tarea(Integer id, String descripcion, Integer horasEstimadas, Integer minutosTrabajados, Boolean finalizada, Proyecto proyecto, Prioridad prioridad, Usuario responsable) {
        this.id = id;
        this.horasEstimadas = horasEstimadas;
        this.minutosTrabajados = minutosTrabajados;
        this.finalizada = finalizada;
        this.proyecto = proyecto;
        this.prioridad = prioridad;
        this.idPrioridad = 1;
        this.idResponsable = 1;
        this.responsable = responsable;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(Integer horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public Integer getMinutosTrabajados() {
        return minutosTrabajados;
    }

    public void setMinutosTrabajados(Integer minutosTrabajados) {
        this.minutosTrabajados = minutosTrabajados;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        this.idPrioridad = prioridad.getId();
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
        this.idResponsable = responsable.getId();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Integer idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String toString(){
        return descripcion + " " + horasEstimadas + " " + minutosTrabajados + " " + finalizada + " " + idResponsable + " " + idPrioridad;
    }
}
