package juego;

import java.awt.Color;
import entorno.Entorno;

public class Roca {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    public Roca(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 30;
        this.alto = 50;
    }

    // Dibuja la roca en pantalla
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.GRAY);
    }

    // Verifica si Gondolf choca con esta roca
    public boolean colisionaCon(Gondolf gondolf) {
        double gx = gondolf.getX();
        double gy = gondolf.getY();
        double gancho = gondolf.getAncho();
        double galto = gondolf.getAlto();

        // Verificamos colisión de rectángulo con rectángulo
        return Math.abs(this.x - gx) < (this.ancho + gancho) / 2 &&
               Math.abs(this.y - gy) < (this.alto + galto) / 2;
    }

    // Getters útiles si los necesitás
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
}