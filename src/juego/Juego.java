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
	    double[] posicion = {x, y};
	    return posicion;
	}

	// Variables de murcielagos
	private int totalGenerados = 0;
	private int maxGenerados = 50;
	double velocidadMurcielagos = 2.0;
	private Murcielago[] enemigos = new Murcielago[10];
	private int tiempoUltimoMurcielago = 0;
	private int intervalo = 1000;
	
	// Variables de hechizos
	private Hechizo[] hechizos = new Hechizo[10]; // máximo 10 hechizos activos al mismo tiempo
	
	
	//Variables de juego
	private boolean juegoTerminado = false;
	int anchoPantalla = 800;
	int altoPantalla = 600;
	
	
	// Variable de mago
	int magiaInicial = 100;
	int vidaInicial = 100;
	Gondolf mago = new Gondolf(anchoPantalla/2,altoPantalla/2, magiaInicial, vidaInicial); // Se crea el objeto mago con posicion definida en el centro
	
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
		// Verifica el tiempo en milisegundos por cada tick
				int tiempoActual = entorno.tiempo();
				
		// Verifica si se han eliminado a todos los murcielagos, si es asi muestra un mensaje de victoria
		if (enemigosEliminados >= maxGenerados) {
			juegoTerminado = true;
			entorno.cambiarFont("Arial", 36, java.awt.Color.BLUE);
	        entorno.escribirTexto("¡Has ganado!", 300, 300);
		}
		// Verifica que el mago este vivo y si no lo esta muesta un mensaje de derrota
		if (mago.getVida()<=0) {
		    juegoTerminado = true;
		    entorno.cambiarFont("Arial", 36, java.awt.Color.RED);
	        entorno.escribirTexto("¡Has perdido!", 300, 300);
		}
		
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
			
			// Si se hizo clic con el mouse y el clic no está en el menú
			if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !menu.mouseEstaEnMenu(entorno.mouseX())) {
			    int seleccionado = menu.getHechizoSeleccionado();
			    if (seleccionado != -1) {
			        BotonHechizo boton = menu.getBoton(seleccionado);

			        // Verificamos si Gondolf tiene magia suficiente
			        if (mago.tieneMagiaSuficiente(boton.getCostoMagia())) {
			            // Buscar espacio libre en el arreglo de hechizos
			            for (int i = 0; i < hechizos.length; i++) {
			                if (hechizos[i] == null) {
			                    // Crear nuevo hechizo en la posición del clic
			                	
			                	// Si el boton seleccionado es Hechizo 1
			                	if (seleccionado == 0) {
				                    hechizos[i] = new Hechizo(
				                        entorno.mouseX(),
				                        entorno.mouseY(),
				                        20, // radio del hechizo (Personalizable)
				                        boton.getCostoMagia(),
				                        Color.ORANGE,
				                        entorno.tiempo(),
				                        200 // Duracion del hechizo (Personalizable)
				                    	);
			                	}
			                	// Si el boton seleccionado es Hechizo 2
			                	else {
			                		hechizos[i] = new Hechizo(
					                        entorno.mouseX(),
					                        entorno.mouseY(),
					                        40, // radio del hechizo (Personalizable)
					                        boton.getCostoMagia(),
					                        Color.MAGENTA,
					                        entorno.tiempo(),
					                        400 // Duracion del hechizo (Personalizable)
					                    	);
			                	}
			                    // Gastar magia
			                    mago.lanzarHechizo(boton.getCostoMagia());
			                    menu.deseleccionar(); // quitar selección
			                    break;
			                }
			            }
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
			
			for (int i = 0; i < hechizos.length; i++) {
			    Hechizo h = hechizos[i];
			    if (h != null) {
			        h.dibujar(entorno);

			        // Elimina murciélagos si están en el área de efecto
			        for (int j = 0; j < enemigos.length; j++) {
			            Murcielago m = enemigos[j];
			            if (m != null && m.getVivo() && h.afectaA(m)) {
			                enemigos[j] = null;
			                enemigosEliminados++;
			                if(mago.getMagia() < magiaInicial) { // Se verifica que la magia no sea mayor que la maxima
			                	mago.incrementaMagia((int)(magiaInicial*0.1)); // Se incrementa un 10% por murcielago eliminado
			                }
			            }
			        }

			        // Eliminar el hechizo si ya expiró
			        if (!h.estaActivo(entorno.tiempo())) {
			            hechizos[i] = null;
			        }
			    }
			}
		}
	} 
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
