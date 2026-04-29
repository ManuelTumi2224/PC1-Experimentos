package matriculas;

public class Curso {
    private String nombre;
    private String codigo;
    private int creditos;
    private double costoPorCredito;

    public Curso(String nombre, String codigo, int creditos, double costoPorCredito) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.creditos = creditos;
        this.costoPorCredito = costoPorCredito;
    }

    public String getNombre()          { return nombre; }
    public int    getCreditos()        { return creditos; }
    public double getCostoPorCredito() { return costoPorCredito; }
}
