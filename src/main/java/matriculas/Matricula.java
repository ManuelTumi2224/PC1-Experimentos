package matriculas;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Matricula {
    private Estudiante estudiante;
    private Curso curso;
    private PeriodoAcademico periodo;
    private LocalDate fechaMatricula;
    private String estado; // "Activa", "Retirada", "Finalizada"
    private LocalDate fechaRetiro;
    private double costoTotal;

    public Matricula(Estudiante estudiante, Curso curso, PeriodoAcademico periodo) {
        this(estudiante, curso, periodo, LocalDate.now());
    }

    // Constructor para pruebas: permite inyectar la fecha de matrícula
    public Matricula(Estudiante estudiante, Curso curso, PeriodoAcademico periodo, LocalDate fechaMatricula) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.periodo = periodo;
        this.fechaMatricula = fechaMatricula;
        this.estado = "Activa";

        this.costoTotal = curso.getCreditos() * curso.getCostoPorCredito();

        if (curso.getCreditos() >= 5) {
            this.costoTotal = this.costoTotal * 0.95;
        }

        System.out.println("Matrícula registrada para " + estudiante.getNombre() +
                " en el curso " + curso.getNombre() +
                " del periodo " + periodo.getNombre() +
                ". Créditos: " + curso.getCreditos() +
                ". Costo total: S/" + costoTotal);
    }

    public void retirarCurso(LocalDate fechaRetiro) {
        long diasDesdeMatricula = ChronoUnit.DAYS.between(fechaMatricula, fechaRetiro);

        if (diasDesdeMatricula <= 7) {
            this.estado = "Retirada";
            this.fechaRetiro = fechaRetiro;
            System.out.println("Retiro realizado correctamente. Se aplicará devolución del 70%: S/"
                    + (costoTotal * 0.70));
        } else {
            System.out.println("No se puede retirar el curso. El retiro solo está permitido dentro de los primeros 7 días.");
        }
    }

    public void finalizarCurso() {
        this.estado = "Finalizada";
        System.out.println("Curso finalizado para el estudiante " + estudiante.getNombre());
    }

    public double getCostoTotal() { return costoTotal; }
    public String getEstado()     { return estado; }
    public LocalDate getFechaRetiro() { return fechaRetiro; }
}
