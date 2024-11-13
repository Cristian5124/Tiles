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
        super();
        this.row = row;
        this.column = column;
        this.changeColor("n");
        this.changeSize(30, 30);
        this.updatePosition();
    }

    /**
     * Actualiza la posición del agujero según su fila y columna.
     */
    public void updatePosition() {
        int x = column * 30;
        int y = row * 30;
        super.moveTo(x, y);
    }

    /**
     * Hace visible el agujero.
     */
    public void makeVisible() {
        super.makeVisible();
    }

    /**
     * Hace invisible el agujero.
     */
    public void makeInvisible() {
        super.makeInvisible();
    }

    /**
     * Mueve el agujero a una nueva posición.
     * 
     * @param newRow nueva fila en la que se posiciona el agujero
     * @param newColumn nueva columna a la que se mueve el agujero
     */
    public void moveTo(int newRow, int newColumn) {
        this.row = newRow;
        this.column = newColumn;
        updatePosition();
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
