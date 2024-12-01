package com.example.ikergomezrubio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class calculadora extends AppCompatActivity {

    private TextView pantalla;
    private final StringBuilder entrada = new StringBuilder();
    private double primerOperando = 0;
    private String operador = null;
    private boolean nuevaOperacion = true;
    private final DecimalFormat formato = new DecimalFormat("0.######");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        pantalla = findViewById(R.id.textView);
        configurarBotones();
    }

    private void configurarBotones() {
        int[] idsBotones = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnSumar, R.id.btnRestar, R.id.btnMultiplicar, R.id.btnDividir,
                R.id.btnLimpiar, R.id.btnBorrar, R.id.btnIgual, R.id.btnRaiz, R.id.btnPi, R.id.btnSigno
        };

        for (int id : idsBotones) {
            findViewById(id).setOnClickListener(this::onClicBoton);
        }
    }

    private void onClicBoton(View vista) {
        Button boton = (Button) vista;
        String textoBoton = boton.getText().toString();

        switch (textoBoton) {
            case "C":
                resetearCalculadora();
                break;

            case "⌫":
                borrarUltimoCaracter();
                break;

            case "√":
                aplicarRaizCuadrada();
                break;

            case "π":
                aplicarValorPi();
                break;

            case "+/-":
                cambiarSigno();
                break;

            case "+":
            case "-":
            case "x":
            case "/":
                aplicarOperador(textoBoton);
                break;

            case "=":
                calcularResultadoFinal();
                break;

            default:
                agregarEntrada(textoBoton);
                break;
        }
    }

    private void resetearCalculadora() {
        entrada.setLength(0);
        primerOperando = 0;
        operador = null;
        nuevaOperacion = true;
        pantalla.setText("0");
    }

    private void borrarUltimoCaracter() {
        if (entrada.length() > 0) {
            entrada.deleteCharAt(entrada.length() - 1);
            pantalla.setText(entrada.length() > 0 ? entrada.toString() : "0");
        }
    }

    private void aplicarRaizCuadrada() {
        try {
            // Usar el valor de entrada o el primer operando si la entrada está vacía
            double valor = entrada.length() > 0 ? Double.parseDouble(entrada.toString()) : primerOperando;

            if (valor < 0) {
                pantalla.setText("Error"); // No se puede calcular la raíz cuadrada de un número negativo
                resetearCalculadora();
                return;
            }

            // Calcular la raíz cuadrada y mostrar el resultado
            valor = Math.sqrt(valor);
            mostrarResultado(valor);

            // Actualizar el primer operando con el valor calculado
            primerOperando = valor;
            entrada.setLength(0); // Limpiar la entrada para evitar conflictos
            nuevaOperacion = true; // Listo para una nueva operación
            operador = null; // Asegurar que no haya un operador pendiente
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
            resetearCalculadora();
        }
    }

    private void aplicarValorPi() {
        double valor = Math.PI;
        mostrarResultado(valor);

        primerOperando = valor;
        operador = null;
    }
    private void aplicarOperador(String nuevoOperador) {
        try {
            if (entrada.length() > 0) {
                // Convertir la entrada actual al primer operando
                double valorActual = Double.parseDouble(entrada.toString());
                if (operador != null) {
                    // Si ya hay un operador pendiente, calcular el resultado previo
                    primerOperando = calcularResultado(primerOperando, valorActual, operador);
                    mostrarResultado(primerOperando);
                } else {
                    primerOperando = valorActual;
                }
                entrada.setLength(0); // Limpiar la entrada
            }

            // Establecer el nuevo operador
            operador = nuevoOperador;
            nuevaOperacion = true; // Preparar para el siguiente operando
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
            resetearCalculadora();
        }
    }

    private void calcularResultadoFinal() {
        try {
            if (operador != null && entrada.length() > 0) {
                // Si hay operador y entrada, realizar el cálculo
                double segundoOperando = Double.parseDouble(entrada.toString());
                primerOperando = calcularResultado(primerOperando, segundoOperando, operador);
                mostrarResultado(primerOperando);
                operador = null; // Reiniciar el operador después de calcular
            } else {
                // Si no hay segundo operando, mostrar el valor actual
                mostrarResultado(primerOperando);
            }
            nuevaOperacion = true; // Preparar para la siguiente operación
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
            resetearCalculadora();
        }
    }

    private double calcularResultado(double primerOperando, double segundoOperando, String operador) {
        try {
            switch (operador) {
                case "+":
                    return primerOperando + segundoOperando;
                case "-":
                    return primerOperando - segundoOperando;
                case "x":
                    return primerOperando * segundoOperando;
                case "/":
                    if (segundoOperando == 0) {
                        throw new ArithmeticException("División por cero");
                    }
                    return primerOperando / segundoOperando;
                default:
                    return primerOperando; // Si no hay operador, devolver el primer operando
            }
        } catch (ArithmeticException e) {
            pantalla.setText("Error");
            resetearCalculadora();
            return 0;
        }
    }


    private void cambiarSigno() {
        if (entrada.length() > 0) {
            double valor = -1 * Double.parseDouble(entrada.toString());
            mostrarResultado(valor);
        }
    }

    private void agregarEntrada(String texto) {
        if (nuevaOperacion) {
            entrada.setLength(0);
            nuevaOperacion = false;
        }
        entrada.append(texto);
        pantalla.setText(entrada.toString());
    }

    private void mostrarResultado(double valor) {
        String resultado = formato.format(valor);
        pantalla.setText(resultado);
        entrada.setLength(0);
        entrada.append(resultado); // Sincronizar entrada con el resultado mostrado
    }
}
