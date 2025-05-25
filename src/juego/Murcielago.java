package juego;

import java.awt.Color;

import entorno.Entorno;

public class Murcielago {
	
	private double x;
	private double y;
	private double velocidad;
	private double tamanio;
	private boolean vivo;
	
	
	public Murcielago(double xInicial, double yInicial, double velocidad) {
		this.x = xInicial;
		this.y = yInicial;
		this.velocidad = velocidad;
		this.tamanio = 15;
		this.vivo = true;
	}
	
	// Metodo para dibujar a los murcielagos, de manera provisoria se dibuja un cirulo azul
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(x, y, tamanio, Color.blue);
	}
	
	// Metodo para que el murcielago se mueva automaticamente a el mago
	public void moverHacia(Gondolf gondolf) {
		double gondolfX = gondolf.getX() - x;
		double gondolfY = gondolf.getY() - y;
		double distancia = Math.sqrt(gondolfX * gondolfX + gondolfY * gondolfY); // Se calcula la distancia entre ambos
		if (distancia != 0) {
            x += (gondolfX / distancia) * velocidad; // Calcula y mueve hacia la direccion del mago en X
            y += (gondolfY / distancia) * velocidad; // Calcula y mueve hacia la direccion del mago en Y
        }
	}
	
	// Metodo que verifica la colision con otro objeto(Gondolf o hechizos)
	public boolean colisionaCon(double x, double y, double radio) {
        double dx = this.x - x;
        double dy = this.y - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < (this.tamanio / 2 + radio);
    }
	
	// Metodo que hace que el murcielago muera
	public void morir() {
        this.vivo = false;
    }
	
	
 // Getters
    public boolean getVivo() { return vivo; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadio() {return tamanio / 2;}
}
