package juego;

import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

public class Gondolf {
	private double x;
	private double y;
	private double velocidad;
	private int magia;
	private int vida;
	private double ancho;
	private double alto;

	private Image imagenGondolf; // Imagen del mago

	public Gondolf(double xInicial, double yInicial, int magiaInicial, int vidaInicial) {
		this.x = xInicial;
		this.y = yInicial;
		this.velocidad = 5.0;
		this.magia = magiaInicial;
		this.vida = vidaInicial;
		this.ancho = 16;
		this.alto = 30;
		this.imagenGondolf = new ImageIcon("src/imagenes/gondolf.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		

	}

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

        boolean dentroDePantalla = nuevoX - ancho / 2 >= 0 &&
                                   nuevoX + ancho / 2 <= 600 &&
                                   nuevoY - alto / 2 >= 0 &&
                                   nuevoY + alto / 2 <= 600;

	    if (dentroDePantalla) {
            Gondolf simulado = new Gondolf(nuevoX, nuevoY, magia, vida);
            simulado.magoSimulado(ancho, alto);

            boolean colisiona = false;
            for (int i = 0; i < rocas.length; i++) {
                Roca r = rocas[i];
                if (r != null && r.colisionaCon(simulado)) {
                    colisiona = true;
                    break;
                }
            }

            if (!colisiona) {
                this.x = nuevoX;
                this.y = nuevoY;
            }
        }
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenGondolf, x, y, 0); // Dibuja imagen en lugar del rectángulo
	}

	public void recibirDanio(int cantidad) {
		vida -= cantidad;
		if (vida < 0) {
			vida = 0;
		}
	}

	public boolean tieneMagiaSuficiente(int costo) {
        return magia >= costo;
    }

	public void lanzarHechizo (int cantidad) {
		if (tieneMagiaSuficiente(cantidad)) {
			magia -= cantidad;
		}
	}

	public void incrementaMagia(int cantidad) {
		magia += cantidad;
	}

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
