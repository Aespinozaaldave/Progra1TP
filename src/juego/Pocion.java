package juego;

// Importa clases para manejo de imágenes
import java.awt.Image;
import javax.swing.ImageIcon;

// Importa la clase Entorno 
import entorno.Entorno;

public class Pocion {
    // Coordenadas de la poción en pantalla
    private double x;
    private double y;

    // Tiempo en que se creó la poción (en milisegundos)
    private int tiempoCreacion;

    // Indica si la poción es visible en pantalla
    private boolean visible;

    // Constante que define cuánto tiempo la poción permanece visible (6 segundos)
    private final int DURACION_MS = 6000;

    // Radio de detección para saber si Gondolf tocó la poción
    private final int RADIO = 10;

    // Imagen de la poción
    private Image imagen;

    // Constructor: inicializa la posición, tiempo de creación, visibilidad y la imagen
    public Pocion(double x, double y, int tiempoCreacion) {
        this.x = x;
        this.y = y;
        this.tiempoCreacion = tiempoCreacion;
        this.visible = true;

        // Carga la imagen desde archivo, la escala a 100x100 píxeles y la guarda
        this.imagen = new ImageIcon("src/imagenes/pocion de vida.png")
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    }

    // Dibuja la imagen de la poción si está visible
    public void dibujar(Entorno entorno) {
        if (visible) {
            entorno.dibujarImagen(imagen, x, y, 0); // 0 es la rotación
        }
    }

    // Método que se llama constantemente para actualizar el estado de la poción
    public void actualizar(int tiempoActual, Gondolf gondolf) {
        // Si no está visible, no hace nada
        if (!visible) return;

        // Si pasó más tiempo del permitido, la poción desaparece
        if (tiempoActual - tiempoCreacion > DURACION_MS) {
            visible = false;
            return;
        }

        // Calcula la distancia entre Gondolf y la poción
        double dx = gondolf.getX() - x;
        double dy = gondolf.getY() - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        // Si Gondolf está suficientemente cerca (colisión), le da vida
        if (distancia < RADIO + 20) {
            int vida = gondolf.getVida();     // Vida actual de Gondolf
            int maxVida = 100;                // Vida máxima posible
            int incremento = (int)(maxVida * 0.1); // 10% de vida máxima

            // Si Gondolf no tiene vida completa, le aumenta la vida
            if (vida < maxVida) {
                gondolf.incrementaVida(incremento);
            }

            // Una vez recogida, la poción desaparece
            visible = false;
        }
    }

    // Devuelve si la poción está visible o no (útil para control desde otras clases)
    public boolean estaVisible() {
        return visible;
    }
}
///La clase Pocion se encarga de mostrar una poción en el juego por un tiempo limitado.
///Si Gondolf se acerca, la poción le restaura vida y luego desaparece.