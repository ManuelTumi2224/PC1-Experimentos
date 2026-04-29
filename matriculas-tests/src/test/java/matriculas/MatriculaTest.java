package matriculas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MatriculaTest {

    // PRUEBA UNITARIA 1
    // Verifica que el estado inicial de una matrícula sea "Activa"
    @Test
    @DisplayName("PU-01: La matrícula queda en estado Activa al registrarse")
    void matriculaDeberiaQuedarEnEstadoActiva() {
        // Arrange
        Estudiante estudiante = new Estudiante("Ana", "Torres", "U001", "ana@uni.edu", "Sistemas");
        Curso curso = new Curso("Cálculo I", "MAT101", 4, 50.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 31));

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo);

        // Assert
        assertEquals("Activa", matricula.getEstado(),
                "El estado inicial debe ser 'Activa'");
    }


    // PRUEBA UNITARIA 2
    // Verifica que se aplique el descuento del 5% para cursos con 5+ créditos
     
    @Test
    @DisplayName("PU-02: Se aplica descuento del 5% para cursos con 5 o más créditos")
    void deberiAplicarDescuentoConCincoCreditosOMas() {
        // Arrange
        Estudiante estudiante = new Estudiante("Luis", "Rios", "U002", "luis@uni.edu", "Civil");
        Curso curso = new Curso("Estructuras", "CIV301", 5, 80.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 31));

        double costoEsperado = 5 * 80.0 * 0.95; // 380.0

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo);

        // Assert
        assertEquals(costoEsperado, matricula.getCostoTotal(), 0.001,
                "El costo total debe reflejar el descuento del 5% para cursos con 5+ créditos");
    }

     
    // PRUEBA UNITARIA 3
    // Verifica que NO se permita retirar después de los 7 días
     
    @Test
    @DisplayName("PU-03: El retiro es rechazado si han pasado más de 7 días desde la matrícula")
    void retiroDeberiaFallarDespuesDeSieteDias() {
        // Arrange
        Estudiante estudiante = new Estudiante("María", "Chávez", "U003", "maria@uni.edu", "Derecho");
        Curso curso = new Curso("Derecho Civil", "DER201", 4, 60.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 31));

        LocalDate fechaMatriculaFija = LocalDate.of(2026, 4, 1);
        Matricula matricula = new Matricula(estudiante, curso, periodo, fechaMatriculaFija);

        // Act
        matricula.retirarCurso(fechaMatriculaFija.plusDays(8));

        // Assert
        assertEquals("Activa", matricula.getEstado(),
                "El estado NO debe cambiar si el retiro se intenta después de los 7 días");
    }

     
    // PRUEBA INTEGRAL
    // Flujo completo: crear matrícula → retirar dentro del plazo → verificar estado final y que la fecha de retiro quede registrada
     
    @Test
    @DisplayName("PI-01: Flujo completo — matrícula activa y retiro exitoso dentro de los 7 días")
    void flujoCompletoMatriculaYRetiroExitoso() {
        // Arrange
        Estudiante estudiante = new Estudiante("Carlos", "Mendoza", "U004", "carlos@uni.edu", "Medicina");
        Curso curso = new Curso("Anatomía I", "MED101", 6, 100.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 31));

        LocalDate fechaMatriculaFija = LocalDate.of(2026, 4, 20);
        LocalDate fechaRetiro = fechaMatriculaFija.plusDays(5);

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo, fechaMatriculaFija);

        // Verificación intermedia: estado activo tras crear
        assertEquals("Activa", matricula.getEstado(),
                "La matrícula recién creada debe estar Activa");

        // Costo con descuento del 5% (6 créditos >= 5)
        double costoEsperado = 6 * 100.0 * 0.95; // 570.0
        assertEquals(costoEsperado, matricula.getCostoTotal(), 0.001,
                "El costo debe incluir el descuento del 5%");

        // Retiro dentro de los 7 días
        matricula.retirarCurso(fechaRetiro);

        // Assert final
        assertEquals("Retirada", matricula.getEstado(),
                "El estado debe cambiar a 'Retirada' tras un retiro válido");
        assertEquals(fechaRetiro, matricula.getFechaRetiro(),
                "La fecha de retiro debe quedar registrada correctamente");
    }
}
