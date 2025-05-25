package juego;

import java.awt.Color;
import entorno.Entorno;

public class Menu {
    private int x;
    private int ancho;
    private int alto;
    
    private BotonHechizo[] botones;
    private int hechizoSeleccionado;

    public Menu(int x, int ancho, int alto) {
        this.x = x;
        this.ancho = ancho;
        this.alto = alto;
        this.botones = new BotonHechizo[2]; // Podés aumentar este número
        int centroX = x + ancho / 2; // x es la posición de inicio del menú

        // Creamos dos botones de ejemplo
        botones[0] = new BotonHechizo("Hechizo 1", centroX, 150, Color.CYAN, 0); // gratis
        botones[1] = new BotonHechizo("Hechizo 2", centroX, 220, Color.MAGENTA, 20); // cuesta 20 magia

        hechizoSeleccionado = -1; // ninguno seleccionado
    }

    public void dibujar(Entorno entorno, int vida, int magia, int kills) {
        // Fondo del menú
        entorno.dibujarRectangulo(x + ancho / 2, alto / 2, ancho, alto, 0, new Color(30, 30, 30));

        // Títulos e info
        entorno.cambiarFont("Arial", 16, Color.WHITE);
        entorno.escribirTexto("VIDA: " + vida, x + 10, 30);
        entorno.escribirTexto("MAGIA: " + magia, x + 10, 60);
        entorno.escribirTexto("ELIMINADOS: " + kills, x + 10, 90);

        // Botones
        for (int i = 0; i < botones.length; i++) {
            boolean seleccionado = (i == hechizoSeleccionado);
            botones[i].dibujar(entorno, seleccionado);
        }
    }

    public void actualizarSeleccion(Entorno entorno) {
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
            int mouseX = entorno.mouseX();
            int mouseY = entorno.mouseY();
            for (int i = 0; i < botones.length; i++) {
                if (botones[i].fuePresionado(mouseX, mouseY)) {
                    hechizoSeleccionado = i;
                }
            }
        }
    }

    public int getHechizoSeleccionado() {
        return hechizoSeleccionado;
    }

    public void deseleccionar() {
        hechizoSeleccionado = -1;
    }

    public BotonHechizo getBoton(int indice) {
        return botones[indice];
    }

    public boolean mouseEstaEnMenu(int mouseX) {
        return mouseX >= x;
    }
}