package juego; // Declara que esta clase pertenece al paquete 'juego'

import java.awt.Color; // Importa la clase Color para usar colores
import entorno.Entorno; // Importa la clase Entorno para interactuar con el motor gráfico

public class Menu {
    // Posición X del menú (en la pantalla)
    private int x;

    // Ancho y alto del menú
    private int ancho;
    private int alto;

    // Arreglo de botones que representan hechizos
    private BotonHechizo[] botones;

    // Índice del hechizo actualmente seleccionado (-1 si no hay ninguno)
    private int hechizoSeleccionado;

    // Constructor del menú
    public Menu(int x, int ancho, int alto) {
        // Inicializa la posición y dimensiones del menú
        this.x = x;
        this.ancho = ancho;
        this.alto = alto;

        // Inicializa el arreglo de botones con capacidad para 3 hechizos
        this.botones = new BotonHechizo[3];

        // Calcula el centro horizontal del menú
        int centroX = x + ancho / 2;

        // Crea los botones de hechizos con sus nombres, posiciones, colores y costos de magia
        botones[0] = new BotonHechizo("Disparo Magico", centroX, 150, Color.CYAN, 0); // Hechizo gratis
        botones[1] = new BotonHechizo("Bola De Fuego", centroX, 220, Color.RED, 20); // Cuesta 20 de magia
        botones[2] = new BotonHechizo("Agujero Negro", centroX, 290, Color.DARK_GRAY, 50); // Cuesta 50 de magia

        // Por defecto, no hay hechizo seleccionado
        hechizoSeleccionado = -1;
    }

    // Dibuja el menú completo en la pantalla
    public void dibujar(Entorno entorno, int vida, int magia, int kills) {
        // Dibuja el fondo del menú como un rectángulo oscuro
        entorno.dibujarRectangulo(x + ancho / 2, alto / 2, ancho, alto, 0, new Color(30, 30, 30));

        // Cambia la fuente para escribir texto informativo
        entorno.cambiarFont("Arial", 16, Color.WHITE);

        // Muestra la vida del jugador
        entorno.escribirTexto("VIDA: " + vida, x + 10, 30);

        // Muestra la cantidad de magia disponible
        entorno.escribirTexto("MAGIA: " + magia, x + 10, 60);

        // Muestra la cantidad de enemigos eliminados
        entorno.escribirTexto("ELIMINADOS: " + kills, x + 10, 90);

        // Dibuja todos los botones de hechizo
        for (int i = 0; i < botones.length; i++) {
            // Verifica si este botón está seleccionado
            boolean seleccionado = (i == hechizoSeleccionado);

            // Dibuja el botón con el estilo correspondiente (resaltado si está seleccionado)
            botones[i].dibujar(entorno, seleccionado);
        }
    }

    // Actualiza qué hechizo está seleccionado cuando el jugador hace clic
    public void actualizarSeleccion(Entorno entorno) {
        // Verifica si se presionó el botón izquierdo del mouse
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
            // Obtiene la posición del mouse
            int mouseX = entorno.mouseX();
            int mouseY = entorno.mouseY();

            // Recorre los botones para ver si alguno fue presionado
            for (int i = 0; i < botones.length; i++) {
                // Si el botón fue presionado con el mouse, lo selecciona
                if (botones[i].fuePresionado(mouseX, mouseY)) {
                    hechizoSeleccionado = i;
                }
            }
        }
    }

    // Devuelve el índice del hechizo actualmente seleccionado
    public int getHechizoSeleccionado() {
        return hechizoSeleccionado;
    }

    // Deselecciona cualquier hechizo
    public void deseleccionar() {
        hechizoSeleccionado = -1;
    }

    // Devuelve el botón de hechizo en la posición indicada
    public BotonHechizo getBoton(int indice) {
        return botones[indice];
    }

    // Verifica si el mouse está dentro del área del menú
    public boolean mouseEstaEnMenu(int mouseX) {
        return mouseX >= x;
    }
}
