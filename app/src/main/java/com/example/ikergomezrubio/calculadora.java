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
        double valorPi = Math.PI;

        // Asignar π directamente al primer operando
        primerOperando = valorPi;

        // Mostrar π como símbolo o número en la pantalla
        pantalla.setText("π"); // Opcional: mostrar "π" en vez de su valor numérico

        // Limpiar entrada pero no sincronizar con el valor redondeado
        entrada.setLength(0);

        nuevaOperacion = false; // Preparar para nuevas operaciones
    }



    private void aplicarOperador(String nuevoOperador) {
        try {
            if (entrada.length() > 0) {
                primerOperando = Double.parseDouble(entrada.toString());
            }

            operador = nuevoOperador; // Establecer operador actual
            nuevaOperacion = true;   // Preparar para el siguiente número
            entrada.setLength(0);    // Limpiar entrada
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
            resetearCalculadora();
        }
    }

    private void calcularResultadoFinal() {
        if (operador != null) {
            double segundoOperando;

            // Detectar si se usa π como segundo operando
            if ("π".equals(entrada.toString())) {
                segundoOperando = Math.PI;
            } else if (entrada.length() > 0) {
                segundoOperando = Double.parseDouble(entrada.toString());
            } else {
                segundoOperando = primerOperando; // Si no hay entrada, usar el primer operando
            }

            // Realizar el cálculo
            primerOperando = calcularResultado(primerOperando, segundoOperando, operador);

            // Mostrar el resultado y preparar para nueva operación
            mostrarResultado(primerOperando);
            operador = null; // Resetear operador
            nuevaOperacion = true;
        } else if (entrada.length() == 0) {
            // Si no hay operador ni entrada, mostrar el primer operando
            mostrarResultado(primerOperando);
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
        if (valor == Math.PI) {
            pantalla.setText("π");
        } else {
            String resultado = formato.format(valor);
            pantalla.setText(resultado);
        }

        entrada.setLength(0); // Limpiar la entrada
        entrada.append(valor); // Sincronizar con el valor completo
    }




}
