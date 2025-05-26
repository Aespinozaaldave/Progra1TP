package juego;

import java.awt.Color;
import entorno.Entorno;

public class Hechizo {
    private double x;
    private double y;
    private double direccionX;
    private double direccionY;
    private double velocidad;
    private double radio;
    private int costoMagia;
    private Color color;
    private int duracion; // duración en milisegundos
    private int tickInicial;
    
    public Hechizo(double xInicial, double yInicial, double destinoX, double destinoY,  double radio, int costoMagia, Color color, int tickInicial, int duracion) {
    	this.x = xInicial;
        this.y = yInicial;
        this.velocidad = 5.0;
        this.radio = radio;
        this.costoMagia = costoMagia;
        this.color = color;
        this.tickInicial = tickInicial;
        this.duracion = duracion;
        
        // Calcula la dirección normalizada hacia el mouse
        double dx = destinoX - xInicial;
        double dy = destinoY - yInicial;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        
       // Calcula la direccion del hechizo en caso de que exista distancia
        if (distancia != 0) {
            this.direccionX = dx / distancia;
            this.direccionY = dy / distancia;
        } else {
            this.direccionX = 0;
            this.direccionY = 0;
        }
    }
    
    // Mueve el hechizo hacia adonde le digamos
    public void mover() {
        x += direccionX * velocidad;
        y += direccionY * velocidad;
    }
    
    public boolean estaDentroDePantalla(int ancho, int alto) {
        return x >= 0 && x <= ancho && y >= 0 && y <= alto;
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
