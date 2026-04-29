package matriculas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaTest {

    @Mock
    private Estudiante estudianteMock;

    @Mock
    private Curso cursoMock;

    @Mock
    private PeriodoAcademico periodoMock;

     
    // PRUEBA UNITARIA 1
    // Verifica que el estado inicial de una matrícula sea "Activa"
     
    @Test
    @DisplayName("PU-01: La matrícula queda en estado Activa al registrarse")
    void matriculaDeberiaQuedarEnEstadoActiva() {
        // Arrange
        when(estudianteMock.getNombre()).thenReturn("Ana Torres");
        when(cursoMock.getNombre()).thenReturn("Cálculo I");
        when(cursoMock.getCreditos()).thenReturn(4);
        when(cursoMock.getCostoPorCredito()).thenReturn(50.0);
        when(periodoMock.getNombre()).thenReturn("2026-I");

        // Act
        Matricula matricula = new Matricula(estudianteMock, cursoMock, periodoMock);

        // Assert
        assertEquals("Activa", matricula.getEstado(),
                "El estado inicial debe ser 'Activa'");
    }

     
    // PRUEBA UNITARIA 2
    // Verifica que se aplique el descuento del 5% para cursos con 5+ créditos
     
    @Test
    @DisplayName("PU-02: Se aplica descuento del 5% para cursos con 5 o más créditos")
    void deberiaAplicarDescuentoConCincoCreditosOMas() {
        // Arrange
        when(estudianteMock.getNombre()).thenReturn("Luis Rios");
        when(cursoMock.getNombre()).thenReturn("Estructuras");
        when(cursoMock.getCreditos()).thenReturn(5);
        when(cursoMock.getCostoPorCredito()).thenReturn(80.0);
        when(periodoMock.getNombre()).thenReturn("2026-I");

        double costoEsperado = 5 * 80.0 * 0.95; // 380.0

        // Act
        Matricula matricula = new Matricula(estudianteMock, cursoMock, periodoMock);

        // Assert
        assertEquals(costoEsperado, matricula.getCostoTotal(), 0.001,
                "El costo total debe reflejar el descuento del 5% para cursos con 5+ créditos");
        verify(cursoMock, atLeastOnce()).getCreditos();
        verify(cursoMock, atLeastOnce()).getCostoPorCredito();
    }

     
    // PRUEBA UNITARIA 3
    // Verifica que NO se permita retirar después de los 7 días
     
    @Test
    @DisplayName("PU-03: El retiro es rechazado si han pasado más de 7 días desde la matrícula")
    void retiroDeberiaFallarDespuesDeSieteDias() {
        // Arrange
        when(estudianteMock.getNombre()).thenReturn("María Chávez");
        when(cursoMock.getNombre()).thenReturn("Derecho Civil");
        when(cursoMock.getCreditos()).thenReturn(4);
        when(cursoMock.getCostoPorCredito()).thenReturn(60.0);
        when(periodoMock.getNombre()).thenReturn("2026-I");

        LocalDate fechaMatriculaFija = LocalDate.of(2026, 4, 1);
        Matricula matricula = new Matricula(estudianteMock, cursoMock, periodoMock, fechaMatriculaFija);

        // Act – intento de retiro al día 8
        matricula.retirarCurso(fechaMatriculaFija.plusDays(8));

        // Assert
        assertEquals("Activa", matricula.getEstado(),
                "El estado NO debe cambiar si el retiro se intenta después de los 7 días");
    }

     
    // PRUEBA INTEGRAL
    // Flujo completo con objetos reales: crear matrícula → retirar dentro del plazo → verificar estado final y fecha de retiro registrada
     
    @Test
    @DisplayName("PI-01: Flujo completo — matrícula activa y retiro exitoso dentro de los 7 días")
    void flujoCompletoMatriculaYRetiroExitoso() {
        // Arrange – objetos reales para validar integración entre clases
        Estudiante estudiante = new Estudiante("Carlos", "Mendoza", "U004", "carlos@uni.edu", "Medicina");
        Curso curso = new Curso("Anatomía I", "MED101", 6, 100.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 31));

        LocalDate fechaMatriculaFija = LocalDate.of(2026, 4, 20);
        LocalDate fechaRetiro = fechaMatriculaFija.plusDays(5);

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo, fechaMatriculaFija);

        // Assert intermedio – estado activo y costo con descuento
        assertEquals("Activa", matricula.getEstado(),
                "La matrícula recién creada debe estar Activa");
        assertEquals(6 * 100.0 * 0.95, matricula.getCostoTotal(), 0.001,
                "El costo debe incluir el descuento del 5%");

        // Act – retiro dentro del plazo
        matricula.retirarCurso(fechaRetiro);

        // Assert final
        assertEquals("Retirada", matricula.getEstado(),
                "El estado debe cambiar a 'Retirada' tras un retiro válido");
        assertEquals(fechaRetiro, matricula.getFechaRetiro(),
                "La fecha de retiro debe quedar registrada correctamente");
    }
}
