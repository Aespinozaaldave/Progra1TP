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
	
	public Gondolf(double xInicial, double yInicial, int magiaInicial, int vidaInicial) {
		 this.x = xInicial;
		 this.y = yInicial;
		 this.velocidad = 5.0;
		 this.magia = magiaInicial;
		 this.vida = vidaInicial;
		 this.ancho = 16;
		 this.alto = 30;
	 }
	// Metodo para mover al mago al presionar teclas "WASD" o flechas
	public void mover(Entorno entorno) {
		double nuevoX = x;
	    double nuevoY = y;
	    if (entorno.estaPresionada('W') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
	        nuevoY -= velocidad;
	    }
	    if (entorno.estaPresionada('S') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
	        nuevoY += velocidad;
	    }
	    if (entorno.estaPresionada('A') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	        nuevoX -= velocidad;
	    }
	    if (entorno.estaPresionada('D') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	        nuevoX += velocidad;
	    }
		// Limites: no se sale de la pantalla ni entra al menú
	    if (nuevoX - ancho / 2 >= 0 && nuevoX + ancho / 2 <= 600) {
	        x = nuevoX;
	    }
	    if (nuevoY - alto / 2 >= 0 && nuevoY + alto / 2 <= 600) {
	        y = nuevoY;
	    }
	}
	// Metodo para dibujar al mago, de manera provisoria se dibuja un cirulo rojo
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho,alto,0, Color.red);
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
	// Incrementa magia
	public void incrementaMagia(int cantidad) {
		magia += cantidad;
	}
	// Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getVida() { return vida; }
    public int getMagia() { return magia; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }

}
