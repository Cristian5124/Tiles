import java.awt.*;                                                             //proporciona clases para la creación de interfaces gráficas de usuario (GUI) y para el manejo de gráficos y eventos 

/**
 * la clase Tile Representa una baldosa en el rompecabezas. Utiliza 
 * la clase Rectangle del proyecto shapes para dibujar la baldosa en el Canvas
 * 
 * @author Angie Ramos and Cristian Polo
 * @version 1.0  (08 septiembre 2024)
 */


public class Tile {
    private int row;
    private int column;
    private String color;
    private Rectangle shape;

    /**
     * crea una baldosa con posición, color y tamaño específico
     * @param row fila en la que se ubica la baldosa
     * @param column columna en la que se ubica la baldosa
     * @param color color de la baldosa 
     */
    public Tile(int row, int column, String color) {          
        this.row = row;
        this.column = column;
        this.color = color;
        this.shape = new Rectangle();
        this.shape.changeColor(color);
        this.shape.changeSize(20, 20);                           // Tamaño fijo para las baldosas
        this.updatePosition();
    }

    /**
     * Actualiza la posicion de la baldosa segun su columna y fila.
     */
    public void updatePosition() {
        int x = column * 30;                                     // Espaciado entre baldosas
        int y = row * 30;
        this.shape.moveTo(x, y);
    }

    /**
     * Hace visible la baldosa 
     */
    public void makeVisible() {
        this.shape.makeVisible();
    }

    /**
     * Hace invisible la baldosa 
     */
    public void makeInvisible() {
        this.shape.makeInvisible();
    }

    /**
     * Actualiza la posicion de la baldosa 
     * @param newRow nueva fila en la que se posiciona la baldosa
     * @param newColumn nueva columna a la que se mueve la baldosa
     */
    public void moveTo(int newRow, int newColumn) {
        this.row = newRow;
        this.column = newColumn;
        this.updatePosition();
    }
    
    /**
     * Obtiene la fila actual en la que se encuentra la baldosa.
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene la columna actual en la que se encuentra la baldosa.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Obtiene el color actual de la baldosa.
     */
    public String getColor() {
        return color;
    }
}
