package juego;

import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

// Clase que representa un murciélago enemigo
public class Murcielago {

	private double x;          // Posición horizontal del murciélago
	private double y;          // Posición vertical del murciélago
	private double velocidad;  // Velocidad de movimiento
	private double tamanio;    // Tamaño del murciélago (usado para colisiones)
	private boolean vivo;      // Estado de vida: true = vivo, false = muerto
	private Image imagen;      // Imagen visual del murciélago

	// Constructor: inicializa la posición, velocidad, estado y carga la imagen
	public Murcielago(double xInicial, double yInicial, double velocidad) {
		this.x = xInicial;
		this.y = yInicial;
		this.velocidad = velocidad;
		this.tamanio = 15;   // Define un tamaño base
		this.vivo = true;    // Al crearse, el murciélago está vivo

		// Carga y escala la imagen del murciélago a 50x50 píxeles
		this.imagen = new ImageIcon("src/imagenes/murcielago 2.0.png")
			.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	}

	// Método para dibujar el murciélago si está vivo
	public void dibujar(Entorno entorno) {
		if (vivo) {
			entorno.dibujarImagen(imagen, x, y, 0);
		}
	}

	// Método que hace que el murciélago se mueva hacia la posición de Gondolf
	public void moverHacia(Gondolf gondolf) {
		if (!vivo) return; // Si está muerto, no se mueve

		// Calcula el vector de dirección desde el murciélago hacia Gondolf
		double gondolfX = gondolf.getX() - x;
		double gondolfY = gondolf.getY() - y;

		// Calcula la distancia al personaje
		double distancia = Math.sqrt(gondolfX * gondolfX + gondolfY * gondolfY);

		// Normaliza el vector y lo multiplica por la velocidad para mover al murciélago
		if (distancia != 0) {
			x += (gondolfX / distancia) * velocidad;
			y += (gondolfY / distancia) * velocidad;
		}
	}

	// Verifica si colisiona con un punto dado (x, y) con cierto radio (como un hechizo)
	public boolean colisionaCon(double x, double y, double radio) {
		double dx = this.x - x;
		double dy = this.y - y;
		double distancia = Math.sqrt(dx * dx + dy * dy);
		return distancia < (this.tamanio / 2 + radio); // Colisión circular
	}

	// Cambia el estado del murciélago a muerto
	public void morir() {
		this.vivo = false;
	}

	// Getters

	public boolean getVivo() {
		return vivo;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRadio() {
		return tamanio / 2; // Retorna el radio, útil para colisiones
	}
}

