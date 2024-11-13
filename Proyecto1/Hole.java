/**
 * La clase Hole representa un agujero en el rompecabezas, hereda de Rectangle
 * para utilizar sus métodos de dibujo y manipulación. Un agujero es un espacio 
 * vacío en el rompecabezas que puede eliminar baldosas que se encuentren en 
 * su posición.
 * 
 * @author Angie Ramos and Cristian Polo
 * @version 1.0 (08 septiembre 2024)
 */
public class Hole extends Rectangle {
    private int row;
    private int column;

    /**
     * Crea un agujero con posición específica.
     * 
     * @param row fila en la que se ubica el agujero
     * @param column columna en la que se ubica el agujero
     */
    public Hole(int row, int column) {
        super(); // Llama al constructor de Rectangle
        this.row = row;
        this.column = column;
        this.changeColor("black"); // Cambia el color del agujero a negro
        this.changeSize(30, 30); // Tamaño fijo para el agujero
        this.updatePosition(); // Actualiza la posición inicial del agujero
    }

    /**
     * Actualiza la posición del agujero según su fila y columna.
     */
    public void updatePosition() {
        int x = column * 30; // Espaciado entre baldosas (asumiendo un tamaño de 30x30)
        int y = row * 30;
        super.moveTo(x, y); // Usa el método de Rectangle para mover el agujero
    }

    /**
     * Hace visible el agujero.
     */
    public void makeVisible() {
        super.makeVisible(); // Llama al método de Rectangle
    }

    /**
     * Hace invisible el agujero.
     */
    public void makeInvisible() {
        super.makeInvisible(); // Llama al método de Rectangle
    }

    /**
     * Mueve el agujero a una nueva posición.
     * 
     * @param newRow nueva fila en la que se posiciona el agujero
     * @param newColumn nueva columna a la que se mueve el agujero
     */
    public void moveTo(int newRow, int newColumn) {
        this.row = newRow;      // Actualiza la fila
        this.column = newColumn; // Actualiza la columna
        updatePosition();        // Llama a updatePosition para mover el agujero
    }

    /**
     * Obtiene la fila actual en la que se encuentra el agujero.
     * 
     * @return la fila actual del agujero
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene la columna actual en la que se encuentra el agujero.
     * 
     * @return la columna actual del agujero
     */
    public int getColumn() {
        return column;
    }
}
