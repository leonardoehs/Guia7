package com.uso.guia7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.uso.guia7.Juego.aleatorio;
import static com.uso.guia7.Juego.numero;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static SharedPreferences configuracion;
    public static final String NAME_FILE = "RecordarDatos";

    Button btnJugar, btnPuntaje, btnRespuesta, btnDatos,btnConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportActionBar().hide();
                btnConfiguracion=findViewById(R.id.btnConfiguracion);
                btnConfiguracion.setOnClickListener(this);
            } else {
                getSupportActionBar().setTitle("Inicio");
            }

        }
        btnJugar = findViewById(R.id.btnJugar);
        btnPuntaje = findViewById(R.id.btnPuntaje);
        btnRespuesta = findViewById(R.id.btnRespuesta);
        btnDatos = findViewById(R.id.btnDatos);
        btnJugar.setOnClickListener(this);
        btnPuntaje.setOnClickListener(this);
        btnRespuesta.setOnClickListener(this);
        btnDatos.setOnClickListener(this);


        configuracion = this.getSharedPreferences(NAME_FILE, MODE_PRIVATE);

    }

    private void validarNombre() {
        String nombre = configuracion.getString(Configuracion.KEY_NOMBRE, "");
        if (nombre.equals("")) {
            Toast.makeText(this, "Es necesario agregar su nombre!!!", Toast.LENGTH_SHORT).show();
        } else {
            Intent juego = new Intent(this, Juego.class);
            startActivity(juego);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        crearMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        this.ItemSelected(item);
        return true;
    }

    private void ItemSelected(MenuItem item) {
        //Validamos que item se presiono
        switch (item.getItemId()) {
            case 0:
                //Iniciamos la configuraciones
                Intent frmConfiguracion = new Intent(this, Configuracion.class);
                startActivity(frmConfiguracion);
                break;
        }
    }

    private void crearMenu(Menu menu) {
        //Item de Configuración
        MenuItem item = menu.add(0, 0, 0, "Nombre");
        item.setAlphabeticShortcut('C');
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJugar: {
                validarNombre();
            }
            break;
            case R.id.btnPuntaje: {
                String nombre = configuracion.getString(Configuracion.KEY_NOMBRE, "");
                if (nombre.equals("")) {
                    Toast.makeText(this, "Es necesario agregar su nombre!!!", Toast.LENGTH_SHORT).show();
                } else {
                    int puntuacion = configuracion.getInt(Juego.KEY_PUNTAJE, 10);
                    new AlertDialog.Builder(this)
                            .setTitle("Puntuacion")
                            .setMessage(nombre + " su puntuacion es: " + Integer.toString(puntuacion))
                            .setNeutralButton("OK", null)
                            .show();
                }
            }
            break;
            case R.id.btnRespuesta: {

                int alea = configuracion.getInt(Juego.KEY_NUMERO, 0);
                if (alea != 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("Respuesta")
                            .setMessage(Integer.toString(alea))
                            .setNeutralButton("OK", null)
                            .show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Advertencia")
                            .setMessage("El juego no se a iniciado")
                            .setNeutralButton("OK", null)
                            .show();
                }
            }
            break;
            case R.id.btnDatos: {
                Intent datos = new Intent(this, Datos.class);
                startActivity(datos);
            }
            break;
            case R.id.btnConfiguracion:{
                Intent frmConfiguracion = new Intent(this, Configuracion.class);
                startActivity(frmConfiguracion);
            }
            break;
        }
    }
}
