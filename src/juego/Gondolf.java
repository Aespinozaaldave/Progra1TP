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
	public void mover(Entorno entorno, Roca[] rocas) {
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
	    
	    // Verifica que no se salga de los límites (zona jugable de 600x600)
        boolean dentroDePantalla = nuevoX - ancho / 2 >= 0 &&
                                   nuevoX + ancho / 2 <= 600 &&
                                   nuevoY - alto / 2 >= 0 &&
                                   nuevoY + alto / 2 <= 600;
                                   
	    if (dentroDePantalla) {
            // Creamos un "Gondolf simulado" en la nueva posición
            Gondolf simulado = new Gondolf(nuevoX, nuevoY, magia, vida);
            simulado.magoSimulado(ancho, alto); // copiamos tamaño

            // Verificamos si colisionaría con alguna roca
            boolean colisiona = false;
            for (int i = 0; i < rocas.length; i++) {
                Roca r = rocas[i];
                if (r != null && r.colisionaCon(simulado)) {
                    colisiona = true;
                    break;
                }
            }

            // Solo actualiza la posición si no colisiona
            if (!colisiona) {
                this.x = nuevoX;
                this.y = nuevoY;
            }
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
	
	// Crear mago simulado para verificar colision con rocas
	public void magoSimulado(double ancho, double alto) {
	    this.ancho = ancho;
	    this.alto = alto;
	}
	
	// Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getVida() { return vida; }
    public int getMagia() { return magia; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }

}
