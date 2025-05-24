package juego;

import java.awt.Color;
import entorno.Entorno;

public class BotonHechizo {
    private String nombre;
    private int x;
    private int y;
    private Color color;
    private int costoMagia;
    private int ancho = 140;
    private int alto = 40;

    public BotonHechizo(String nombre, int x, int y, Color color, int costoMagia) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.color = color;
        this.costoMagia = costoMagia;
    }

    public void dibujar(Entorno entorno, boolean seleccionado) {
        Color fondo = seleccionado ? Color.YELLOW : color;
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, fondo);
        entorno.cambiarFont("Arial", 14, Color.BLACK);
        entorno.escribirTexto(nombre + " (" + costoMagia + ")", x - ancho/2 + 5, y + 5);
    }

    public boolean fuePresionado(int mouseX, int mouseY) {
        return mouseX >= x - ancho / 2 && mouseX <= x + ancho / 2 &&
               mouseY >= y - alto / 2 && mouseY <= y + alto / 2;
    }

    public int getCostoMagia() {
        return costoMagia;
    }

    public String getNombre() {
        return nombre;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}