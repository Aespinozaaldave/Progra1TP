package juego; // Define el paquete al que pertenece esta clase

// Importación de clases necesarias para gráficos y entorno
import java.awt.Color;
import entorno.Entorno;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Hechizo {
    // Posición actual del hechizo
    private double x;
    private double y;

    // Dirección de movimiento del hechizo (normalizada)
    private double direccionX;
    private double direccionY;

    // Velocidad del hechizo
    private double velocidad;

    // Tamaño del hechizo (radio para dibujo y colisión)
    private double radio;

    // Costo en magia para lanzar el hechizo
    private int costoMagia;

    // Color del hechizo (usado si no se carga imagen)
    private Color color;

    // Duración del hechizo en milisegundos
    private int duracion;

    // Tiempo (tick) en que se lanzó el hechizo
    private int tickInicial;

    // Imagen del hechizo
    private Image imagenHechizo;

    // Constructor del hechizo
    public Hechizo(
        double xInicial, double yInicial,        // Posición inicial del hechizo
        double destinoX, double destinoY,        // Coordenadas destino (por ejemplo, mouse)
        double radio,                            // Radio del hechizo
        int costoMagia,                          // Cuánto mana gasta
        Color color,                             // Color del hechizo
        int tickInicial,                         // Tiempo en que se creó
        int duracion,                            // Cuánto dura en pantalla
        String rutaImagen						// Ruta de la imagen a utilizar
    ) {
        // Inicializa variables básicas
        this.x = xInicial;
        this.y = yInicial;
        this.velocidad = 5.0;
        this.radio = radio;
        this.costoMagia = costoMagia;
        this.color = color;
        this.tickInicial = tickInicial;
        this.duracion = duracion;

        // Carga la imagen del hechizo y la escala al tamaño adecuado
        this.imagenHechizo = new ImageIcon(rutaImagen)
            .getImage()
            .getScaledInstance((int)(radio * 2), (int)(radio * 2), Image.SCALE_SMOOTH);

        // Calcula la dirección del hechizo hacia el destino
        double dx = destinoX - xInicial;
        double dy = destinoY - yInicial;
        double distancia = Math.sqrt(dx * dx + dy * dy); // Distancia entre origen y destino

        // Si la distancia es mayor que 0, normaliza la dirección
        if (distancia != 0) {
            this.direccionX = dx / distancia;
            this.direccionY = dy / distancia;
        } else {
            // Si la distancia es 0 (origen = destino), no se mueve
            this.direccionX = 0;
            this.direccionY = 0;
        }
    }

    // Mueve el hechizo en la dirección previamente calculada
    public void mover() {
        x += direccionX * velocidad;
        y += direccionY * velocidad;
    }

    // Verifica si el hechizo sigue dentro de los límites de la pantalla
    public boolean estaDentroDePantalla(int ancho, int alto) {
        return x >= 0 && x <= ancho && y >= 0 && y <= alto;
    }

    // Dibuja el hechizo en pantalla (imagen o círculo si la imagen no está cargada)
    public void dibujar(Entorno entorno) {
        if (imagenHechizo != null) {
            entorno.dibujarImagen(imagenHechizo, x, y, 0);
        } else {
            entorno.dibujarCirculo(x, y, radio * 2, color); // Dibuja un círculo si no hay imagen
        }
    }

    // Verifica si el hechizo todavía está activo en base al tiempo actual
    public boolean estaActivo(int tiempoActual) {
        return (tiempoActual - tickInicial) <= duracion;
    }

    // Verifica si el hechizo está colisionando con un murciélago
    public boolean afectaA(Murcielago m) {
        double dx = m.getX() - x;
        double dy = m.getY() - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        // Si la distancia entre los centros es menor que la suma de los radios, hay colisión
        return distancia < (radio + m.getRadio());
    }

    // Métodos getter para acceder a variables privadas

    public int getCostoMagia() {
        return costoMagia;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadio() {
        return radio;
    }
}
