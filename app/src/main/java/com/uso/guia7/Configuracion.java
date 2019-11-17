package com.uso.guia7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static com.uso.guia7.MainActivity.NAME_FILE;

public class Configuracion extends AppCompatActivity {

    //Declaracion de constantes estaticas
    public static final String KEY_NOMBRE = "nombre";

    //Declaracion de variables
    private TextInputLayout contenedorNombre;
    private EditText edtNombre;
    private Button btnGuardar;
    private SharedPreferences recordarNombre;
    public String nombre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Configuración");
        }

        this.btnGuardar = findViewById(R.id.btnGuardar);
        this.contenedorNombre = findViewById(R.id.contenedor_Nombre);
        this.edtNombre = findViewById(R.id.edtNombre);

        this.recordarNombre = getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE);
        cargarNombre();

        this.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNombre();
            }
        });
    }

    public void guardarNombre() {
        String nom = edtNombre.getText().toString().trim();
        boolean a = validarNombre(nom);

        if (a) {
            SharedPreferences.Editor editor = recordarNombre.edit();
            editor.putString(KEY_NOMBRE, edtNombre.getText().toString());
            editor.apply();
            finish();
        }
    }

    public void cargarNombre() {
        this.nombre = recordarNombre.getString(KEY_NOMBRE, "");
        this.edtNombre.setText(nombre);
    }

    private boolean validarNombre(String nombre) {
        if (nombre.length() < 1 || edtNombre.getText().toString().equals("")) {
            this.contenedorNombre.setError("Por favor ingrese su nombre!!!");
            this.contenedorNombre.requestFocus();
            return false;
        } else {
            this.contenedorNombre.setError(null);
        }
        return true;
    }
}
