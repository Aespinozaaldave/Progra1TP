package juego;

import java.awt.Color;
import entorno.Entorno;

public class Hechizo {
    private double x;
    private double y;
    private double radio;
    private int costoMagia;
    private Color color;
    private int duracion; // duración en milisegundos
    private int tickInicial;
    
    public Hechizo(double x, double y, double radio, int costoMagia, Color color, int tickInicial, int duracion) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.costoMagia = costoMagia;
        this.color = color;
        this.tickInicial = tickInicial;
        this.duracion = duracion;
    }
    
    // Dibuja un cirulo que representa al hechizo
    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(x, y, radio * 2, color);
    }
    
    // Devuelve true si el hechizo todavía está activo
    public boolean estaActivo(int tiempoActual) {
        return (tiempoActual - tickInicial) <= duracion;
    }
    // Devuelve true si el hechizo afecta al murciélago
    public boolean afectaA(Murcielago m) {
        double dx = m.getX() - x;
        double dy = m.getY() - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < (radio + m.getRadio());
    }
    
    // Getters
    public int getCostoMagia() {return costoMagia;}
    public double getX() {return x;}
    public double getY() {return y;}
    public double getRadio() {return radio;}
}
