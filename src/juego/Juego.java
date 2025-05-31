package juego;

// Importación de herramientas necesarias
import entorno.Herramientas;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import entorno.Entorno;
import entorno.InterfaceJuego;

// Clase principal del juego
public class Juego extends InterfaceJuego {
	private Entorno entorno; // Entorno gráfico del juego
	private Image fondo; // Imagen de fondo

	// Enum que representa los distintos estados del juego
	private enum EstadoJuego {
	    MENU,     // Pantalla de inicio
	    JUGANDO,  // El juego está activo
	    TERMINADOGANADO,// El juego terminó (victoria)
	    TERMINADODERROTA// El juego terminó (derrota)
	}
	
	// Permite volver todos los valores nuevamente a su inicio
	private void reiniciarJuego() {
	    // Reinicia el estado del juego
	    estado = EstadoJuego.JUGANDO;

	    // Reinicia al mago
	    mago = new Gondolf(anchoPantalla / 2 - 100, altoPantalla / 2, magiaInicial, vidaInicial);

	    // Reinicia enemigos
	    enemigos = new Murcielago[10];
	    totalGenerados = 0;
	    tiempoUltimoMurcielago = 0;

	    // Reinicia hechizos
	    hechizos = new Hechizo[10];

	    // Reinicia pociones
	    pociones.clear();

	    // Reinicia contador de enemigos eliminados
	    enemigosEliminados = 0;

	    // Reinicia menú
	    menu.deseleccionar();

	    // (Opcional) Si querés reiniciar la posición de las rocas:
	    rocas[0] = new Roca(200, 300);
	    rocas[1] = new Roca(400, 150);
	    rocas[2] = new Roca(300, 500);
	    rocas[3] = new Roca(100, 200);
	    rocas[4] = new Roca(500, 300);
	}

	// Estado actual del juego
	private EstadoJuego estado = EstadoJuego.MENU;
	
	private List<Pocion> pociones; // Lista de pociones activas en el juego

	private int totalGenerados = 0; // Cuántos murciélagos se han generado hasta ahora
	private int maxGenerados = 50; // Límite máximo de murciélagos a generar
	double velocidadMurcielagos = 2.0; // Velocidad de movimiento de los murciélagos
	private Murcielago[] enemigos = new Murcielago[10]; // Arreglo de enemigos activos
	private int tiempoUltimoMurcielago = 0; // Tiempo en que se generó el último enemigo
	private int intervalo = 1000; // Intervalo mínimo entre apariciones de murciélagos (en ticks)

	private Hechizo[] hechizos = new Hechizo[10]; // Hechizos activos
	

	int anchoPantalla = 800;
	int altoPantalla = 600;

	private Roca[] rocas = new Roca[5]; // Obstáculos en el escenario

	// Estado inicial de Gondolf
	int magiaInicial = 100;
	int vidaInicial = 100;
	Gondolf mago = new Gondolf(anchoPantalla / 2 - 100, altoPantalla / 2, magiaInicial, vidaInicial);

	Menu menu = new Menu(600, 200, altoPantalla); // Menú lateral de hechizos
	private int enemigosEliminados = 0; // Contador de enemigos derrotados

	// Constructor principal del juego
	Juego() {
		// Inicializa el entorno gráfico
		this.entorno = new Entorno(this, "El camino de Gondolf - Grupo 7 - v1", anchoPantalla, altoPantalla);

		// Carga y ajusta el fondo
		this.fondo = Herramientas.cargarImagen("imagenes/fondo amarillo.png");
		this.fondo = redimensionarImagen(this.fondo, anchoPantalla, altoPantalla);

		// Inicializa la lista de pociones
		pociones = new ArrayList<>();

		// Crea las rocas en posiciones fijas
		rocas[0] = new Roca(200, 300);
		rocas[1] = new Roca(400, 150);
		rocas[2] = new Roca(300, 500);
		rocas[3] = new Roca(100, 200);
		rocas[4] = new Roca(500, 300);

		this.entorno.iniciar(); // Inicia el ciclo principal del entorno
	}

	// Redimensiona una imagen al tamaño especificado
	private Image redimensionarImagen(Image imagen, int ancho, int alto) {
		return imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	}

	// Genera una posición aleatoria fuera de los bordes de la pantalla
	private double[] generarPosicionAleatoriaFueraDePantalla() {
		int borde = (int) (Math.random() * 4); // Elige un borde al azar
		double x = 0;
		double y = 0;

		// Según el borde, genera coordenadas fuera del área visible
		if (borde == 0) {
			x = Math.random() * 600;
			y = -20;
		} else if (borde == 1) {
			x = Math.random() * 600;
			y = altoPantalla + 20;
		} else if (borde == 2) {
			x = -20;
			y = Math.random() * altoPantalla;
		} else if (borde == 3) {
			x = 620;
			y = Math.random() * altoPantalla;
		}
		double[] posicion = { x, y };
		return posicion;
	}

	// Método principal que se ejecuta en cada "tick" del juego
	public void tick() {
		int tiempoActual = entorno.tiempo(); // Tiempo actual del entorno
		// Dibuja el fondo centrado
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
		if (!(estado == EstadoJuego.JUGANDO)) {
	        // Dibuja fondo
	        //entorno.dibujarRectangulo(anchoPantalla / 2, altoPantalla / 2, anchoPantalla, altoPantalla, 0, Color.LIGHT_GRAY);

	        // Título del juego
			if (estado == EstadoJuego.MENU) {
				entorno.cambiarFont("Arial", 36, Color.BLACK);
				entorno.escribirTexto("El camino de Gondolf", anchoPantalla / 2 - 180, 150);
			}
	        // Coordenadas de botones
	        int botonJugarX = anchoPantalla / 2;
	        int botonJugarY = 250;
	        int botonSalirX = anchoPantalla / 2;
	        int botonSalirY = 350;
	        int anchoBoton = 200;
	        int altoBoton = 60;
	        
	        
	        //Textos dependiendo si se gano o perdio el juego
	        if (estado == EstadoJuego.TERMINADOGANADO) {
	        	entorno.cambiarFont("Arial", 36, Color.GREEN);
		        entorno.escribirTexto("¡Has ganado!", anchoPantalla / 2 - 110, 150);
	        }
	        if (estado == EstadoJuego.TERMINADODERROTA) {
	        	entorno.cambiarFont("Arial", 36, Color.RED);
		        entorno.escribirTexto("¡Has perdido!", anchoPantalla / 2 - 110, 150);
	        }

	        // Botón JUGAR
	        entorno.dibujarRectangulo(botonJugarX, botonJugarY, anchoBoton, altoBoton, 0, Color.GREEN);
	        entorno.cambiarFont("Arial", 24, Color.BLACK);
	        entorno.escribirTexto("JUGAR", botonJugarX - 40, botonJugarY + 10);

	        // Botón SALIR
	        entorno.dibujarRectangulo(botonSalirX, botonSalirY, anchoBoton, altoBoton, 0, Color.RED);
	        entorno.cambiarFont("Arial", 24, Color.BLACK);
	        entorno.escribirTexto("SALIR", botonSalirX - 35, botonSalirY + 10);

	        // Detectar clics del mouse
	        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
	            int mouseX = entorno.mouseX();
	            int mouseY = entorno.mouseY();

	            // Si se hizo clic en "Jugar"
	            if (mouseX >= botonJugarX - anchoBoton / 2 && mouseX <= botonJugarX + anchoBoton / 2 &&
	                mouseY >= botonJugarY - altoBoton / 2 && mouseY <= botonJugarY + altoBoton / 2) {
	                reiniciarJuego();
	            }

	            // Si se hizo clic en "Salir"
	            if (mouseX >= botonSalirX - anchoBoton / 2 && mouseX <= botonSalirX + anchoBoton / 2 &&
	                mouseY >= botonSalirY - altoBoton / 2 && mouseY <= botonSalirY + altoBoton / 2) {
	                System.exit(0); // Cierra el programa
	            }
	            
	        }

	        return; // No ejecuta más del tick si está en el menú
	    }
		if (estado == EstadoJuego.JUGANDO) {
			// Verifica condiciones de fin de juego
			if (enemigosEliminados >= maxGenerados) {
				estado = EstadoJuego.TERMINADOGANADO;
			}
			if (mago.getVida() <= 0) {
				estado = EstadoJuego.TERMINADODERROTA;
			}

			menu.actualizarSeleccion(entorno); // Verifica selección de hechizo
			menu.dibujar(entorno, mago.getVida(), mago.getMagia(), enemigosEliminados);
			mago.dibujar(entorno); // Dibuja al personaje principal
			mago.mover(entorno, rocas); // Permite movimiento, evitando rocas
			double velocidad = velocidadMurcielagos + 0.5 * (enemigosEliminados / 15); // Se incremetna la velocidad 0.5 cada 15 enemigos eliminados

			// Dibuja todas las rocas
			for (int i = 0; i < rocas.length; i++) {
				if (rocas[i] != null) {
					rocas[i].dibujar(entorno);
				}
			}

			// Generación de murciélagos enemigos
			if (tiempoActual - tiempoUltimoMurcielago >= intervalo && totalGenerados < maxGenerados) {
				for (int i = 0; i < enemigos.length; i++) {
					if (enemigos[i] == null) {
						double[] pos = generarPosicionAleatoriaFueraDePantalla();
						enemigos[i] = new Murcielago(pos[0], pos[1], velocidad);
						tiempoUltimoMurcielago = tiempoActual;
						totalGenerados++;
						
						break;
					}
				}
			}

			// Lanzamiento de hechizos con el mouse
			String rutaImagen = ""; // Ruta de la imagen de hechizos
			if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !menu.mouseEstaEnMenu(entorno.mouseX())) {
				int seleccionado = menu.getHechizoSeleccionado();
				if (seleccionado != -1) {
					BotonHechizo boton = menu.getBoton(seleccionado);
					if (mago.tieneMagiaSuficiente(boton.getCostoMagia())) {
						for (int i = 0; i < hechizos.length; i++) {
							if (hechizos[i] == null) {
								// Crea hechizo según el botón seleccionado
								if (seleccionado == 0) {
									rutaImagen = "src/imagenes/explosionn.png";
									hechizos[i] = new Hechizo(mago.getX(), mago.getY(), entorno.mouseX(),
											entorno.mouseY(), 20, boton.getCostoMagia(), Color.CYAN, entorno.tiempo(),
											500, rutaImagen);
								} else if (seleccionado == 1) {
									rutaImagen = "src/imagenes/fuego2.png";
									hechizos[i] = new Hechizo(entorno.mouseX(), entorno.mouseY(), entorno.mouseX(),
											entorno.mouseY(), 35, boton.getCostoMagia(), Color.RED, entorno.tiempo(),
											400, rutaImagen);
								} else if (seleccionado == 2) {
									rutaImagen = "src/imagenes/agujeroNegro.png";
									hechizos[i] = new Hechizo(entorno.mouseX(), entorno.mouseY(), entorno.mouseX(),
											entorno.mouseY(), 60, boton.getCostoMagia(), Color.BLACK, entorno.tiempo(),
											300, rutaImagen);
								}
								mago.lanzarHechizo(boton.getCostoMagia());
								menu.deseleccionar();
								break;
							}
						}
					}
				}
			}
			
			// Lógica de enemigos (murciélagos)
			for (int i = 0; i < enemigos.length; i++) {
				Murcielago m = enemigos[i];
				if (m != null && m.getVivo()) {
					m.moverHacia(mago);
					if (m.getX() < 600) {
						m.dibujar(entorno);
					}
					// Verifica colisión con el mago
					if (m.colisionaCon(mago.getX(), mago.getY(), 20)) {
						mago.recibirDanio(10);
						enemigos[i] = null;
						enemigosEliminados++;
					}
				}
			}

			// Lógica de los hechizos
			for (int i = 0; i < hechizos.length; i++) {
				Hechizo h = hechizos[i];
				if (h != null) {
					if (!h.estaDentroDePantalla(600, altoPantalla)) {
						hechizos[i] = null;
					}
					h.dibujar(entorno);
					h.mover();

					// Verifica si el hechizo impacta un enemigo
					for (int j = 0; j < enemigos.length; j++) {
						Murcielago m = enemigos[j];
						if (m != null && m.getVivo() && h.afectaA(m)) {
							m.morir(); // Marca el murciélago como muerto

							// Genera una poción cada 5 enemigos eliminados si está dentro de los límites
							// visibles
							if (enemigosEliminados % 5 == 0 && enemigosEliminados > 0) {
								if (m.getX() >= 0 && m.getX() <= anchoPantalla && m.getY() >= 0
										&& m.getY() <= altoPantalla) {
									pociones.add(new Pocion(m.getX(), m.getY(), entorno.tiempo()));
								}
							}

							enemigos[j] = null;
							enemigosEliminados++;

							// Recupera un poco de magia
							if (mago.getMagia() < magiaInicial) {
								mago.incrementaMagia((int) (magiaInicial * 0.1));
							}
						}
					}

					if (!h.estaActivo(tiempoActual)) {
						hechizos[i] = null;
					}
				}
			}

			// Lógica de las pociones
			Iterator<Pocion> it = pociones.iterator();
			while (it.hasNext()) {
				Pocion p = it.next();
				p.dibujar(entorno); // Muestra la poción
				p.actualizar(tiempoActual, mago); // Verifica si Gondolf la recoge
				if (!p.estaVisible()) {
					it.remove(); // Elimina la poción si ya fue usada o expiró
				}
			}
			
		}
		
	}

	// Método principal para iniciar el juego
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

