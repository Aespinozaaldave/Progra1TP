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

	private Gondolf gondolf; // Personaje principal (no se usa directamente, se usa "mago")
	private List<Pocion> pociones; // Lista de pociones activas en el juego

	private int totalGenerados = 0; // Cuántos murciélagos se han generado hasta ahora
	private int maxGenerados = 50; // Límite máximo de murciélagos a generar
	double velocidadMurcielagos = 2.0; // Velocidad de movimiento de los murciélagos
	private Murcielago[] enemigos = new Murcielago[10]; // Arreglo de enemigos activos
	private int tiempoUltimoMurcielago = 0; // Tiempo en que se generó el último enemigo
	private int intervalo = 1000; // Intervalo mínimo entre apariciones de murciélagos (en ticks)

	private Hechizo[] hechizos = new Hechizo[10]; // Hechizos activos

	private boolean juegoTerminado = false; // Estado del juego (ganado o perdido)
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

		// Verifica condiciones de fin de juego
		if (enemigosEliminados >= maxGenerados) {
			juegoTerminado = true;
			entorno.cambiarFont("Arial", 36, java.awt.Color.BLUE);
			entorno.escribirTexto("¡Has ganado!", 300, 300);
		}
		if (mago.getVida() <= 0) {
			juegoTerminado = true;
			entorno.cambiarFont("Arial", 36, java.awt.Color.RED);
			entorno.escribirTexto("¡Has perdido!", 300, 300);
		}

		// Si el juego sigue en curso...
		if (!juegoTerminado) {
			menu.actualizarSeleccion(entorno); // Verifica selección de hechizo
			menu.dibujar(entorno, mago.getVida(), mago.getMagia(), enemigosEliminados);
			mago.dibujar(entorno); // Dibuja al personaje principal
			mago.mover(entorno, rocas); // Permite movimiento, evitando rocas

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
						enemigos[i] = new Murcielago(pos[0], pos[1], velocidadMurcielagos);
						tiempoUltimoMurcielago = tiempoActual;
						totalGenerados++;
						break;
					}
				}
			}

			// Lanzamiento de hechizos con el mouse
			if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && !menu.mouseEstaEnMenu(entorno.mouseX())) {
				int seleccionado = menu.getHechizoSeleccionado();
				if (seleccionado != -1) {
					BotonHechizo boton = menu.getBoton(seleccionado);
					if (mago.tieneMagiaSuficiente(boton.getCostoMagia())) {
						for (int i = 0; i < hechizos.length; i++) {
							if (hechizos[i] == null) {
								// Crea hechizo según el botón seleccionado
								if (seleccionado == 0) {
									hechizos[i] = new Hechizo(
											mago.getX(), mago.getY(),
											entorno.mouseX(), entorno.mouseY(),
											20, boton.getCostoMagia(), Color.CYAN,
											entorno.tiempo(), 500);
								} else if (seleccionado == 1) {
									hechizos[i] = new Hechizo(
											entorno.mouseX(), entorno.mouseY(),
											entorno.mouseX(), entorno.mouseY(),
											35, boton.getCostoMagia(), Color.RED,
											entorno.tiempo(), 400);
								} else if (seleccionado == 2) {
									hechizos[i] = new Hechizo(
											entorno.mouseX(), entorno.mouseY(),
											entorno.mouseX(), entorno.mouseY(),
											60, boton.getCostoMagia(), Color.BLACK,
											entorno.tiempo(), 300);
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

						    // Genera una poción si está dentro de los límites visibles
						    if (m.getX() >= 0 && m.getX() <= anchoPantalla &&
						        m.getY() >= 0 && m.getY() <= altoPantalla) {
						        pociones.add(new Pocion(m.getX(), m.getY(), entorno.tiempo()));
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

