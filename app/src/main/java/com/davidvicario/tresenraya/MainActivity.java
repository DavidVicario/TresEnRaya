package com.davidvicario.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Variable para las cajas
    private TextView textV;
    private Integer boton [];

    //Variable para ubicar el tablero
    private int tablero [] = new int[]{
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
    };

    //Variables para el estado de la partida y ficha puesta
    private int estado = 0;
    private int fichaPuesta = 0;

    //Variables para turno del jugador y linea ganadora
    private int turno = 1;
    private int linea [] = new int [] {-1, -1, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ocultar barra de aplicaciones
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        //Caja de texto de victoria/derrota
        textV = (TextView) findViewById(R.id.text1);
        textV.setVisibility(View.INVISIBLE);

        //Botones del juego
        boton = new Integer []{
                R.id.Boton1, R.id.Boton2, R.id.Boton3,
                R.id.Boton4, R.id.Boton5, R.id.Boton6,
                R.id.Boton7, R.id.Boton8, R.id.Boton9,
        };
    }

    //Funcion para jugador principal, el usuario.
    public void jugador(View v){
        if (estado == 0){
            turno = 1;
            int nBoton = Arrays.asList(boton).indexOf(v.getId());
            if (tablero[nBoton] == 0){
                v.setBackgroundResource(R.drawable.circulo);
                tablero [nBoton] = 1;
                fichaPuesta += 1;
                estado = estadoPartida();
                terminarPartida();
                if (estado == 0){
                    turno = -1;
                    jugIA();
                    fichaPuesta += 1;
                    estado = estadoPartida();
                    terminarPartida();
                }
            }
        }
    }

    //Funcion del jugador AI, pone fichas aleatorias
    public void jugIA(){
        Random ran = new Random();
        int pos = ran.nextInt(tablero.length);
        while (tablero[pos] != 0){
            pos = ran.nextInt(tablero.length);
        }
        Button b = (Button) findViewById(boton[pos]);
        b.setBackgroundResource(R.drawable.equis);
        tablero [pos] = -1;
    }

    //Fucnion para establecer las opciones de victoria/derrota
    public int estadoPartida(){
        int nEstado = 0;
        if (Math.abs(tablero[0] + tablero[1] + tablero[2]) == 3){
            linea = new int[]{0,1,2};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[3] + tablero[4] + tablero[5]) == 3){
            linea = new int[]{3,4,5};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[6] + tablero[7] + tablero[8]) == 3){
            linea = new int[]{6,7,8};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[0] + tablero[3] + tablero[6]) == 3){
            linea = new int[]{0,3,6};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[1] + tablero[4] + tablero[7]) == 3){
            linea = new int[]{1,4,7};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[2] + tablero[5] + tablero[8]) == 3){
            linea = new int[]{2,5,8};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[0] + tablero[4] + tablero[8]) == 3){
            linea = new int[]{0,4,8};
            nEstado = 1*turno;
        } else if (Math.abs(tablero[2] + tablero[4] + tablero[6]) == 3){
            linea = new int[]{2,4,6};
            nEstado = 1*turno;
        } else if (fichaPuesta == 9){
            nEstado = 2;
        }
        return nEstado;
    }

    //Función para fin del juego, marcar linea ganadora (si la hay) y el resultado.
    public void terminarPartida(){
        int victoria = R.drawable.circulovic;
        if (estado == 1 || estado == -1){
            if (estado == 1){
                textV.setVisibility(View.VISIBLE);
                textV.setTextColor(Color.GREEN);
                textV.setText("¡¡HAS GANADO!!");
            } else {
                textV.setVisibility(View.VISIBLE);
                textV.setTextColor(Color.RED);
                textV.setText("¡¡HAS PERDIDO!!");
                victoria = R.drawable.equisvic;
            }
            for (int i = 0; i < linea.length; i++){
                Button b = findViewById(boton[linea[i]]);
                b.setBackgroundResource(victoria);
            }
        } else if (estado == 2){
            textV.setVisibility(View.VISIBLE);
            textV.setTextColor(Color.BLACK);
            textV.setText("¡¡EMPATE!!");
        }
    }
}