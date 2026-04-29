package matriculas;

public class Estudiante {
    private String nombre;
    private String apellido;
    private String codigo;
    private String email;
    private String carrera;

    public Estudiante(String nombre, String apellido, String codigo, String email, String carrera) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo = codigo;
        this.email = email;
        this.carrera = carrera;
    }

    public String getNombre() { return nombre; }
    public String getCodigo()  { return codigo; }
    public String getCarrera() { return carrera; }
}
