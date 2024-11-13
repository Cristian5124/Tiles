/**
 * La clase Tile representa una baldosa en el rompecabezas, hereda de Rectangle
 * para utilizar sus métodos de dibujo y manipulación.
 * 
 * @author Angie Ramos and Cristian Polo
 * @version 1.0  (08 septiembre 2024)
 */
public class Tile extends Rectangle {
    private int row;
    private int column;
    private String color;
    private String originalColor;

    /**
     * Crea una baldosa con posición, color y tamaño específico.
     * 
     * @param row fila en la que se ubica la baldosa
     * @param column columna en la que se ubica la baldosa
     * @param color color de la baldosa
     */
    public Tile(int row, int column, String color) {
        super();
        this.row = row;
        this.column = column;
        this.color = color;
        this.originalColor = color;
        this.changeColor(color);
        this.changeSize(30, 30);
        this.updatePosition();
    }

    /**
     * Restaura el color original de la baldosa
     */
    public void restoreOriginalColor() {
        this.color = this.originalColor;
        this.changeColor(this.originalColor);
    }

    /**
     * Obtiene el color original de la baldosa
     */
    public String getOriginalColor() {
        return originalColor;
    }


    /**
     * Actualiza la posición de la baldosa según su fila y columna.
     */
    public void updatePosition() {
        int x = column * 30;
        int y = row * 30;
        super.moveTo(x, y);
    }

    /**
     * Hace visible la baldosa.
     */
    public void makeVisible() {
        super.makeVisible();
    }

    /**
     * Hace invisible la baldosa.
     */
    public void makeInvisible() {
        super.makeInvisible();
    }

    /**
     * Mueve la baldosa a una nueva posición.
     * 
     * @param newRow nueva fila en la que se posiciona la baldosa
     * @param newColumn nueva columna a la que se mueve la baldosa
     */
    public void moveTo(int newRow, int newColumn) {
        this.row = newRow;
        this.column = newColumn;
        updatePosition();
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
