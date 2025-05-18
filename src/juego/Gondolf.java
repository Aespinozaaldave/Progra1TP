package juego;
import java.awt.Color; //Se importa de forma provisoria para dibujar el circulo que representa al mago
import entorno.Entorno;

public class Gondolf {
	private double x;
	private double y;
	private double velocidad;
	private int magia;
	private int vida;
	private double ancho;
	private double alto;
	
	public Gondolf(double xInicial, double yInicial) {
		 this.x = xInicial;
		 this.y = yInicial;
		 this.velocidad = 3.0;
		 this.magia = 100;
		 this.vida = 100;
		 this.ancho = 20;
		 this.alto = 20;
	 }
	// Metodo para mover al mago al presionar teclas "WASD" o flechas
	public void mover(Entorno entorno) {
		if (entorno.estaPresionada('W') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			y -= velocidad;
		}
		if (entorno.estaPresionada('S') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			y += velocidad;
		}
		if (entorno.estaPresionada('A') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			x -= velocidad;
		}
		if (entorno.estaPresionada('D') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			x += velocidad;
		}
	}
	// Metodo para dibujar al mago, de manera provisoria se dibuja un cirulo rojo
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(x, y, alto, Color.red);
	}
	// Metodo para recibir daño
	public void recibirDanio(int cantidad) {
		vida -= cantidad;
		if (vida < 0) {
			vida = 0;
		}
	}
	// Verifica que el mago tenga la cantidad suficiente de magia para lanzar un hechizo
	public boolean tieneMagiaSuficiente(int costo) {
        return magia >= costo;
    }
	// Lanza hechizo
	public void lanzarHechizo (int cantidad) {
		if (tieneMagiaSuficiente(cantidad)) {
			magia -= cantidad;
		}
	}
	// Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getVida() { return vida; }
    public int getMagia() { return magia; }


}
