package com.example.byteconversor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Variables elementos de la interfaz
    private Spinner spinner1_apr;
    private Spinner spinner2_apr;
    private EditText editText1_apr;
    private EditText editText2_apr;
    private TextView textView1_apr;
    private TextView textView2_apr;

    private boolean cambiarOrden_apr = false;
    String entrada_apr;
    String salida_apr;
    String simbolo_apr;

    // Array de unidades
    private final String[] unidades_apr = {"", "BIT", "BYTE", "KILOBYTE", "MEGABYTE", "GIGABYTE", "TERABYTE", "PETABYTE", "EXABYTE", "ZETTABYTE", "YOTTABYTE", "BRONTOBYTE", "GEOPBYTE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignación de variables
        spinner1_apr = findViewById(R.id.spinner1);
        spinner2_apr = findViewById(R.id.spinner2);
        Button button1_apr = findViewById(R.id.button1);
        Button button2_apr = findViewById(R.id.button2);
        editText1_apr = findViewById(R.id.editTextNumber);
        editText2_apr = findViewById(R.id.editTextNumber2);
        textView1_apr = findViewById(R.id.textView);
        textView2_apr = findViewById(R.id.textView2);

        // Deshabilitar el segundo EditText
        editText2_apr.setEnabled(false);

        // Array Adapter de unidades en los spinners
        ArrayAdapter<String> adapter_apr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades_apr);
        adapter_apr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adapter a los spinners
        spinner1_apr.setAdapter(adapter_apr);
        spinner2_apr.setAdapter(adapter_apr);

        // Impedir que se pueda editar el segundo EditText
        //editText2_apr.setFocusable(false);


        // Botón de conversión 1
        button1_apr.setOnClickListener(v -> {
            cambiarOrden_apr = false;
            // Comprobar si se ha introducido un valor
            if (editText1_apr.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Introduzca un valor", Toast.LENGTH_SHORT).show();
                return;
            }
            // Comprobar que se ha seleccionado una unidad en los spinner 1 y 2
            if (spinner1_apr.getSelectedItem().toString().equals("") || spinner2_apr.getSelectedItem().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Seleccione una unidad", Toast.LENGTH_SHORT).show();
                return;
            }
            conversor();
        });

        // Botón de conversión 2
        button2_apr.setOnClickListener(v -> {
            cambiarOrden_apr = true;
            // Comprobar si se ha introducido un valor
            if (editText1_apr.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Introduzca un valor", Toast.LENGTH_SHORT).show();
                return;
            }
            // Comprobar que se ha seleccionado una unidad en los spinner 1 y 2
            if (spinner1_apr.getSelectedItem().toString().equals("") || spinner2_apr.getSelectedItem().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Seleccione una unidad", Toast.LENGTH_SHORT).show();
                return;
            }
            conversor();
        });
    }

    // Control de formato numérico
    private static final DecimalFormat decimal1_apr = new DecimalFormat("0.00");
    private static final DecimalFormat decimal2_apr = new DecimalFormat("0.00E0");

    // Map de factores de conversión
    private static final Map<String, Double> factores_apr = new HashMap<String, Double>() {{
        put("BIT", 1 / 8.0);
        put("BYTE", 1.0);
        put("KILOBYTE", 1024.0);
        put("MEGABYTE", 1024.0 * 1024.0);
        put("GIGABYTE", 1024.0 * 1024.0 * 1024.0);
        put("TERABYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0);
        put("PETABYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0);
        put("EXABYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0);
        put("ZETTABYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0);
        put("YOTTABYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0);
        put("BRONTOBYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0);
        put("GEOPBYTE", 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024);
    }};

    // Map de símbolos de unidades
    private static final Map<String, String> simbolos_apr = new HashMap<String, String>() {{
        put("BIT", "b");
        put("BYTE", "B");
        put("KILOBYTE", "KB");
        put("MEGABYTE", "MB");
        put("GIGABYTE", "GB");
        put("TERABYTE", "TB");
        put("PETABYTE", "PB");
        put("EXABYTE", "EB");
        put("ZETTABYTE", "ZB");
        put("YOTTABYTE", "YB");
        put("BRONTOBYTE", "BB");
        put("GEOPBYTE", "GB");
    }};

    // Método de conversión
    private double operacion(double cantidad_apr, String unidad1_apr, String unidad2_apr) {
        return cantidad_apr * factores_apr.get(unidad1_apr) / factores_apr.get(unidad2_apr);
    }

    @SuppressLint("SetTextI18n")
    private void conversor() {
        // Obtener datos de la interfaz
        double cantidad_apr = Double.parseDouble(editText1_apr.getText().toString());
        String unidad1_apr = spinner1_apr.getSelectedItem().toString();
        String unidad2_apr = spinner2_apr.getSelectedItem().toString();

        // Comprobar que se ha seleccionado una unidad en los spinner 1 y 2
        if (unidad1_apr.equals("NINGUNO") || unidad2_apr.equals("NINGUNO")) {
            Toast.makeText(this, "Seleccione una unidad", Toast.LENGTH_SHORT).show();
            return;
        }

        // Realizar la conversión en el orden adecuado
        double resultado_apr;
        if (cambiarOrden_apr) {
            resultado_apr = operacion(cantidad_apr, unidad2_apr, unidad1_apr);
            entrada_apr = unidad2_apr;
            salida_apr = unidad1_apr;
            simbolo_apr = simbolos_apr.get(unidad1_apr);

        } else {
            resultado_apr = operacion(cantidad_apr, unidad1_apr, unidad2_apr);
            entrada_apr = unidad1_apr;
            salida_apr = unidad2_apr;
            simbolo_apr = simbolos_apr.get(unidad2_apr);
        }

        // Mostrar el resultado
        textView1_apr.setText(entrada_apr + " =");
        textView2_apr.setText(salida_apr + " =");

        if (resultado_apr < 0.01) {
            editText2_apr.setText(decimal2_apr.format(resultado_apr) + " " + simbolo_apr);
        } else if (resultado_apr < 10000) {
            editText2_apr.setText(decimal1_apr.format(resultado_apr) + " " + simbolo_apr);
        } else {
            editText2_apr.setText(decimal2_apr.format(resultado_apr) + " " + simbolo_apr);
        }

        // Mostrar los textViews
        textView1_apr.setVisibility(View.VISIBLE);
        textView2_apr.setVisibility(View.VISIBLE);

        // imprimir resultado en consola logcat
        Log.d("resultado", String.valueOf(resultado_apr));
    }

}
