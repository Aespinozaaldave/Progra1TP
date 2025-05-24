package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	// Metodo para generar posiciones fuera de la pantalla para generar murcielagos
	private double[] generarPosicionAleatoriaFueraDePantalla() {
	    int borde = (int)(Math.random() * 4); // 0: arriba, 1: abajo, 2: izquierda, 3: derecha
	    double x = 0;
	    double y = 0;

	    if (borde == 0) { // arriba
	        x = Math.random() * 600;
	        y = -20;
	    } else if (borde == 1) { // abajo
	        x = Math.random() * 600;
	        y = altoPantalla + 20;
	    } else if (borde == 2) { // izquierda
	        x = -20;
	        y = Math.random() * altoPantalla;
	    } else if (borde == 3) { // derecha
	        x = 620; // justo antes del menú
	        y = Math.random() * altoPantalla;
	    }
	    return new double[]{x, y};
	}

	// Variables de murcielagos
	private int totalGenerados = 0;
	private int maxGenerados = 50;
	double velocidadMurcielagos = 2.0;
	private Murcielago[] enemigos = new Murcielago[10];
	private int tiempoUltimoMurcielago = 0;
	private int intervalo = 1000;
	
	
	//Variables de juego
	private boolean juegoTerminado = false;
	int anchoPantalla = 800;
	int altoPantalla = 600;
	
	
	// Variable de mago
	Gondolf mago = new Gondolf(anchoPantalla/2,altoPantalla/2); // Se crea el objeto mago con posicion definida en el centro
	
	// Variables de menu
	Menu menu = new Menu(600, 200, altoPantalla);
	private int enemigosEliminados = 0;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "El camino de Gondolf - Grupo 7 - v1", anchoPantalla, altoPantalla);
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		
		// Verifica que el mago este vivo
		if (mago.getVida()<=0) {
		    juegoTerminado = true;
		}
		// Verifica el tiempo en milisegundos por cada tick
		int tiempoActual = entorno.tiempo();
		
		// Si el mago esta vivo continua el juego
		if (!juegoTerminado) {
			menu.actualizarSeleccion(entorno);
			menu.dibujar(entorno, mago.getVida(), mago.getMagia(), enemigosEliminados);
			mago.dibujar(entorno); // Se dibuja al mago en la pantalla
			mago.mover(entorno); // Metodo para mover al mago
		
			// Generador de murcielagos cuando en pantalla 10 o menos murcielagos y menos de 50 en total
			if (tiempoActual - tiempoUltimoMurcielago >= intervalo && totalGenerados < maxGenerados) {
			    for (int i = 0; i < enemigos.length; i++) {
			        if (enemigos[i] == null) {
			            double[] pos = generarPosicionAleatoriaFueraDePantalla();
			            enemigos[i] = new Murcielago(pos[0], pos[1], velocidadMurcielagos);
			            tiempoUltimoMurcielago = tiempoActual;
			            totalGenerados++;
			            break;
			        }
			    }
			}
		
			for (int i = 0; i < enemigos.length; i++) {
		        Murcielago m = enemigos[i];
		        
		        // Se verifica que cada murcielago siga vivo
		        if (m != null && m.getVivo()) {
		            m.moverHacia(mago);
		            
		            // Los murcielagos se dibujan si no estan en la zona del menu
		            if (m.getX() < 600) {
		                m.dibujar(entorno); 
		            }
	
		            // Colisión con Gondolf
		            if (m.colisionaCon(mago.getX(), mago.getY(), 20)) {
		                mago.recibirDanio(10);
		                enemigos[i] = null; // lo eliminamos
		                enemigosEliminados++;
		                continue; // no chequeamos hechizos si ya murió
		            }
		        }
		    }
		}
		// Si el mago muerte muestra el siguiente mensaje en pantalla
		else {
	        entorno.cambiarFont("Arial", 36, java.awt.Color.RED);
	        entorno.escribirTexto("¡Has perdido!", 300, 300);
	    }

	} 
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
