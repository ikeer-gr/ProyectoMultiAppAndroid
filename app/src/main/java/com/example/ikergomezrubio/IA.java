package com.example.ikergomezrubio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IA {

    private static final Random random = new Random();

    public int[] jugar(String[][] tablero, String turno, String dificultad) {
        switch (dificultad.toLowerCase()) {
            case "fácil":
                return movimientoAleatorio(tablero);
            case "medio":
                return bloquearOGanar(tablero, turno);
            case "difícil":
                return minimax(tablero, turno, turno).getMovimiento();
            default:
                return movimientoAleatorio(tablero); // Por defecto, jugamos en fácil.
        }
    }

    // Método para un movimiento aleatorio (Nivel fácil).
    private int[] movimientoAleatorio(String[][] tablero) {
        List<int[]> movimientosDisponibles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == null) {
                    movimientosDisponibles.add(new int[]{i, j});
                }
            }
        }
        return movimientosDisponibles.get(random.nextInt(movimientosDisponibles.size()));
    }

    // Método para bloquear al oponente o ganar (Nivel medio).
    private int[] bloquearOGanar(String[][] tablero, String turno) {
        String oponente = turno.equals("X") ? "O" : "X";

        // Primero, verifica si puede ganar.
        int[] movimiento = buscarMovimientoGanador(tablero, turno);
        if (movimiento != null) {
            return movimiento;
        }

        // Si no puede ganar, intenta bloquear al oponente.
        movimiento = buscarMovimientoGanador(tablero, oponente);
        if (movimiento != null) {
            return movimiento;
        }

        // Si no hay nada que bloquear o ganar, mueve aleatoriamente.
        return movimientoAleatorio(tablero);
    }

    private int[] buscarMovimientoGanador(String[][] tablero, String jugador) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == null) {
                    tablero[i][j] = jugador; // Simula el movimiento.
                    if (verificarGanador(tablero, jugador)) {
                        tablero[i][j] = null; // Deshaz el movimiento.
                        return new int[]{i, j};
                    }
                    tablero[i][j] = null; // Deshaz el movimiento.
                }
            }
        }
        return null;
    }

    // Método minimax (Nivel difícil).
    private Resultado minimax(String[][] tablero, String turnoActual, String turnoIA) {
        String oponente = turnoIA.equals("X") ? "O" : "X";

        if (verificarGanador(tablero, turnoIA)) {
            return new Resultado(10);
        } else if (verificarGanador(tablero, oponente)) {
            return new Resultado(-10);
        } else if (esEmpate(tablero)) {
            return new Resultado(0);
        }

        List<Resultado> resultados = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == null) {
                    tablero[i][j] = turnoActual;
                    Resultado resultado = minimax(tablero, turnoActual.equals("X") ? "O" : "X", turnoIA);
                    resultado.setMovimiento(new int[]{i, j});
                    resultados.add(resultado);
                    tablero[i][j] = null; // Deshaz el movimiento.
                }
            }
        }

        return turnoActual.equals(turnoIA)
                ? resultados.stream().max(Resultado::compareTo).orElseThrow()
                : resultados.stream().min(Resultado::compareTo).orElseThrow();
    }

    private boolean verificarGanador(String[][] tablero, String jugador) {
        for (int i = 0; i < 3; i++) {
            if ((tablero[i][0] != null && tablero[i][0].equals(jugador) &&
                    tablero[i][1] != null && tablero[i][1].equals(jugador) &&
                    tablero[i][2] != null && tablero[i][2].equals(jugador)) ||
                    (tablero[0][i] != null && tablero[0][i].equals(jugador) &&
                            tablero[1][i] != null && tablero[1][i].equals(jugador) &&
                            tablero[2][i] != null && tablero[2][i].equals(jugador))) {
                return true;
            }
        }

        return (tablero[0][0] != null && tablero[0][0].equals(jugador) &&
                tablero[1][1] != null && tablero[1][1].equals(jugador) &&
                tablero[2][2] != null && tablero[2][2].equals(jugador)) ||
                (tablero[0][2] != null && tablero[0][2].equals(jugador) &&
                        tablero[1][1] != null && tablero[1][1].equals(jugador) &&
                        tablero[2][0] != null && tablero[2][0].equals(jugador));
    }

    private boolean esEmpate(String[][] tablero) {
        for (String[] fila : tablero) {
            for (String celda : fila) {
                if (celda == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Clase auxiliar para almacenar los resultados del minimax.
    private static class Resultado implements Comparable<Resultado> {
        private int puntuacion;
        private int[] movimiento;

        public Resultado(int puntuacion) {
            this.puntuacion = puntuacion;
        }

        public int getPuntuacion() {
            return puntuacion;
        }

        public int[] getMovimiento() {
            return movimiento;
        }

        public void setMovimiento(int[] movimiento) {
            this.movimiento = movimiento;
        }

        @Override
        public int compareTo(Resultado o) {
            return Integer.compare(this.puntuacion, o.puntuacion);
        }
    }
}
