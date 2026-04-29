package matriculas;

import java.time.LocalDate;

public class PeriodoAcademico {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public PeriodoAcademico(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() { return nombre; }
}
