package juego;

import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

// Clase que representa al personaje principal, Gondolf
public class Gondolf {
	private double x; // Posición horizontal del personaje
	private double y; // Posición vertical del personaje
	private double velocidad; // Velocidad de movimiento
	private int magia; // Puntos de magia actuales
	private int vida; // Puntos de vida actuales
	private double ancho; // Ancho del personaje (para colisiones)
	private double alto; // Alto del personaje (para colisiones)

	// Imágenes del personaje según la dirección de movimiento
	private Image imagenIzquierda;
	private Image imagenDerecha;
	private Image imagenArriba;
	private Image imagenAbajo;
	private Image imagenActual;  // Imagen que se está usando actualmente
	private Image imagenFrente;  // Imagen cuando está quieto
	
	// Constructor: inicializa la posición, atributos y carga las imágenes del mago
	public Gondolf(double xInicial, double yInicial, int magiaInicial, int vidaInicial) {
		this.x = xInicial;
		this.y = yInicial;
		this.velocidad = 5.0;
		this.magia = magiaInicial;
		this.vida = vidaInicial;
		this.ancho = 16;
		this.alto = 30;

		// Carga y escala las imágenes del mago
		this.imagenIzquierda = new ImageIcon("src/imagenes/mago izquierda 2.0.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.imagenDerecha   = new ImageIcon("src/imagenes/mago derecha 2.0.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.imagenArriba    = new ImageIcon("src/imagenes/mago espalda 2.0.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.imagenAbajo     = new ImageIcon("src/imagenes/mago abajo 2.0.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.imagenFrente    = new ImageIcon("src/imagenes/mago quieto.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
      
        this.imagenActual = imagenFrente; // Imagen inicial (quieto)
	}

	// Método para mover al personaje con teclas y detectar colisiones con rocas
	public void mover(Entorno entorno, Roca[] rocas) {
		double nuevoX = x;
	    double nuevoY = y;
	    boolean seMovio = false; // Bandera para saber si se presionó una tecla

	    // Movimiento hacia arriba
	    if (entorno.estaPresionada('W') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
	        nuevoY -= velocidad;
	        this.imagenActual = imagenArriba;
	        seMovio = true;
	    }

	    // Movimiento hacia abajo
	    if (entorno.estaPresionada('S') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
	        nuevoY += velocidad;
	        this.imagenActual = imagenAbajo;
	        seMovio = true;
	    }

	    // Movimiento hacia la izquierda
	    if (entorno.estaPresionada('A') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	        nuevoX -= velocidad;
	        this.imagenActual = imagenIzquierda;
	        seMovio = true;
	    }

	    // Movimiento hacia la derecha
	    if (entorno.estaPresionada('D') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	        nuevoX += velocidad;
	        this.imagenActual = imagenDerecha;
	        seMovio = true;
	    }

	    // Si no se presionó ninguna tecla, muestra la imagen de frente
	    if (!seMovio) {
	        this.imagenActual = imagenFrente;
	    }

	    // Verifica si la nueva posición está dentro de los límites de la pantalla
	    boolean dentroDePantalla = nuevoX - ancho / 2 >= 0 &&
                                   nuevoX + ancho / 2 <= 600 &&
                                   nuevoY - alto / 2 >= 0 &&
                                   nuevoY + alto / 2 <= 600;

	    if (dentroDePantalla) {
            // Crea una instancia simulada para verificar colisiones
            Gondolf simulado = new Gondolf(nuevoX, nuevoY, magia, vida);
            simulado.magoSimulado(ancho, alto);

            boolean colisiona = false;

            // Verifica si la posición colisiona con alguna roca
            for (int i = 0; i < rocas.length; i++) {
                Roca r = rocas[i];
                if (r != null && r.colisionaCon(simulado)) {
                    colisiona = true;
                    break;
                }
            }

            // Si no hay colisión, actualiza la posición del personaje
            if (!colisiona) {
                this.x = nuevoX;
                this.y = nuevoY;
            }
        }
	}

	// Dibuja al personaje en pantalla con su imagen actual
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenActual, x, y, 0);
	}

	// Reduce la vida al recibir daño
	public void recibirDanio(int cantidad) {
		vida -= cantidad;
		if (vida < 0) {
			vida = 0;
		}
	}

	// Aumenta la vida al recibir una poción
	public void recibirVida(int cantidad) {
	    vida += cantidad;
	    if (vida > 100) { // Límite máximo
	        vida = 100;
	    }
	}

	// Verifica si tiene suficiente magia para lanzar un hechizo
	public boolean tieneMagiaSuficiente(int costo) {
        return magia >= costo;
    }

	// Resta la cantidad de magia usada en un hechizo
	public void lanzarHechizo (int cantidad) {
		if (tieneMagiaSuficiente(cantidad)) {
			magia -= cantidad;
		}
	}

	// Aumenta la magia (por ejemplo, al matar enemigos)
	public void incrementaMagia(int cantidad) {
		magia += cantidad;
	}

	// Método para ajustar el tamaño del mago simulado (usado en colisiones)
	public void magoSimulado(double ancho, double alto) {
	    this.ancho = ancho;
	    this.alto = alto;
	}

	// Incrementa vida y limita a 100 si se excede (duplicado innecesario del método anterior)
	public void incrementaVida(int cantidad) {
	    this.vida += cantidad;
	    if (this.vida > 100) {
	        this.vida = 100;
	    } 
	}

	// Métodos para obtener atributos del personaje
    public double getX() { return x; }
    public double getY() { return y; }
    public int getVida() { return vida; }
    public int getMagia() { return magia; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
}

