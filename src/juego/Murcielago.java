package juego;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

public class Murcielago {
	
	private double x;
	private double y;
	private double velocidad;
	private double tamanio;
	private boolean vivo;
	private Image imagen;  // Imagen del murcielago
	
	public Murcielago(double xInicial, double yInicial, double velocidad) {
		this.x = xInicial;
		this.y = yInicial;
		this.velocidad = velocidad;
		this.tamanio = 15;
		this.vivo = true;
		
		// Cargo y escalo la imagen a 50x50 pixeles
	    this.imagen = new ImageIcon("src/imagenes/murcielago.png").getImage()
	                      .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	}
	
	// Metodo para dibujar el murcielago con la imagen
	public void dibujar(Entorno entorno) {
		// Dibuja la imagen centrada en (x,y) sin rotación
		entorno.dibujarImagen(imagen, x, y, 0);
	}
	
	public void moverHacia(Gondolf gondolf) {
		double gondolfX = gondolf.getX() - x;
		double gondolfY = gondolf.getY() - y;
		double distancia = Math.sqrt(gondolfX * gondolfX + gondolfY * gondolfY);
		if (distancia != 0) {
            x += (gondolfX / distancia) * velocidad;
            y += (gondolfY / distancia) * velocidad;
        }
	}
	
	public boolean colisionaCon(double x, double y, double radio) {
        double dx = this.x - x;
        double dy = this.y - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < (this.tamanio / 2 + radio);
    }
	
	public void morir() {
        this.vivo = false;
    }
	
    // Getters
    public boolean getVivo() { return vivo; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadio() { return tamanio / 2; }
}