package com.uso.guia7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

import static com.uso.guia7.MainActivity.NAME_FILE;
import static com.uso.guia7.MainActivity.configuracion;

public class Juego extends AppCompatActivity {
    EditText edtRespuesta;
    TextView edtBienvenido, edtIntento;
    TextInputLayout contenedor_Respuesta;
    private SharedPreferences datosJuego;
    public static final String KEY_NUMERO = "numero";
    public static final String KEY_PUNTAJE = "puntaje";
    public static  int numero = 0;
    public static int aleatorio = 0;
    public static int intento = 0;
    public static int puntaje=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        if (getSupportActionBar() != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().setTitle("Juego");
            }

            this.edtRespuesta = findViewById(R.id.edtRespuesta);
            this.edtBienvenido = findViewById(R.id.edtBienvenido);
            this.edtIntento = findViewById(R.id.edtIntento);
            this.contenedor_Respuesta = findViewById(R.id.contenedor_Respuesta);
            this.datosJuego = getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE);
            cargar();
            generarAleatorio();
            guardarNumero();
            guardarPuntaje();

            String nombre = configuracion.getString(Configuracion.KEY_NOMBRE, "");

            this.edtBienvenido.setText("Bienvenido " + nombre);
        }

    }

    public void clickAceptarDatos(View v) {
        if (!this.edtRespuesta.getText().toString().isEmpty()) {
            intento = intento + 1;
            this.edtIntento.setText("Intento #" + Integer.toString(intento));
            if (this.edtRespuesta.getText().toString().equals(Integer.toString(aleatorio))) {
                Toast.makeText(this, "Felicidades!!!", Toast.LENGTH_LONG).show();
                puntaje=puntaje+10;
                guardarPuntaje();
                intento = 0;
                aleatorio=0;
                numero=0;
                guardarNumero();
                finish();
            } else {
                contenedor_Respuesta.setError("Respuesta Incorrecta, Intertar nuevamente!!!");
                puntaje=puntaje-1;
                guardarPuntaje();
            }
            this.edtRespuesta.setText("");
        } else {
            Toast.makeText(this, "Escriba un numero", Toast.LENGTH_LONG).show();
        }
    }
    private void guardarNumero(){
        SharedPreferences.Editor editor = datosJuego.edit();
        editor.putInt(KEY_NUMERO, aleatorio);
        editor.apply();
        aleatorio=datosJuego.getInt(KEY_NUMERO, 0);
    }

    private void guardarPuntaje(){
        SharedPreferences.Editor editor = datosJuego.edit();
        editor.putInt(KEY_PUNTAJE, puntaje);
        editor.apply();
    }

    private void generarAleatorio() {
        if(aleatorio==0) {
            Random numAleatorio = new Random();
            aleatorio = 1 + numAleatorio.nextInt(10);
        }else{
            cargar();
        }
    }
    private void cargar(){
        aleatorio=datosJuego.getInt(KEY_NUMERO, 0);
        puntaje=datosJuego.getInt(KEY_PUNTAJE, 10);
    }
}
