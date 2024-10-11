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

    /**
     * Crea una baldosa con posición, color y tamaño específico.
     * 
     * @param row fila en la que se ubica la baldosa
     * @param column columna en la que se ubica la baldosa
     * @param color color de la baldosa
     */
    public Tile(int row, int column, String color) {
        super(); // Llama al constructor de Rectangle
        this.row = row;
        this.column = column;
        this.color = color;
        this.changeColor(color); // Cambia el color de la baldosa
        this.changeSize(30, 30); // Tamaño fijo para las baldosas
        this.updatePosition(); // Actualiza la posición inicial
    }

    /**
     * Actualiza la posición de la baldosa según su fila y columna.
     */
    public void updatePosition() {
        int x = column * 30; // Espaciado entre baldosas (asumiendo un tamaño de 30x30)
        int y = row * 30;
        super.moveTo(x, y); // Usa el método de Rectangle para mover
    }

    /**
     * Hace visible la baldosa.
     */
    public void makeVisible() {
        super.makeVisible(); // Llama al método de Rectangle
    }

    /**
     * Hace invisible la baldosa.
     */
    public void makeInvisible() {
        super.makeInvisible(); // Llama al método de Rectangle
    }

    /**
     * Mueve la baldosa a una nueva posición.
     * 
     * @param newRow nueva fila en la que se posiciona la baldosa
     * @param newColumn nueva columna a la que se mueve la baldosa
     */
    public void moveTo(int newRow, int newColumn) {
        this.row = newRow;      // Actualiza la fila
        this.column = newColumn; // Actualiza la columna
        updatePosition();        // Llama a updatePosition para mover la baldosa
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
