import java.util.*;

/**
 * La clase Puzzle se encarga de manejar el estado del rompecabezas y las operaciones que se pueden realizar sobre él.
 * 
 * @author Angie Ramos and Cristian Polo
 * @version 1.0  (08 septiembre 2024)
 */
public class Puzzle {
    private Tile[][] tiles;  // Matriz para almacenar las baldosas
    private int rows;        // Número de filas del rompecabezas
    private int columns;     // Número de columnas del rompecabezas
    private boolean[][] glue; // Matriz que indica si las baldosas están pegadas
    private boolean isVisible; // Indica si el rompecabezas es visible
    private int[][] starting; // Disposición inicial del rompecabezas
    private int[][] ending;   // Disposición final del rompecabezas

    /**
     * Constructor que inicializa el rompecabezas con un número específico de filas y columnas.
     * 
     * @param h número de filas del rompecabezas
     * @param w número de columnas del rompecabezas
     */
    public Puzzle(int h, int w) {
        this.rows = h;
        this.columns = w;
        tiles = new Tile[rows][columns];  // Inicializa la matriz de baldosas
        this.glue = new boolean[rows][columns]; // Inicializa la matriz de pegamento
        this.isVisible = true; // Establece la visibilidad inicial
    }
    
    /**
     * Constructor sobrecargado que crea un Puzzle a partir de una matriz de posiciones.
     * 
     * @param ending una matriz de enteros que representa las posiciones de las baldosas.
     *               Se asume que un valor de 0 significa que no hay baldosa en esa posición.
     */
    public Puzzle(int[][] ending) {
        int rows = ending.length;
        int columns = ending[0].length;
        this.tiles = new Tile[rows][columns];

        // Inicializa las baldosas en función de la matriz proporcionada
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (ending[row][col] != 0) { // Asumiendo que 0 significa que no hay baldosa
                    String color = "black"; // Define un color por defecto o alguna lógica para el color
                    this.tiles[row][col] = new Tile(row, col, color);
                    this.tiles[row][col].makeVisible(); // Hacer visible la baldosa si se necesita
                }
            }
        }
    }
    
    /**
     * Constructor sobrecargado que crea un Puzzle a partir de matrices de posiciones inicial y final.
     * 
     * @param starting una matriz de enteros que representa las posiciones iniciales de las baldosas.
     * @param ending una matriz de enteros que representa las posiciones finales de las baldosas.
     */
    public Puzzle(int[][] starting, int[][] ending) {
        int rows = starting.length;
        int columns = starting[0].length;
        this.tiles = new Tile[rows][columns];

        // Inicializa las baldosas en función de la matriz de inicio
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (starting[row][col] != 0) { // Asumiendo que 0 significa que no hay baldosa
                    String color = "black"; // Define un color por defecto o alguna lógica para el color
                    this.tiles[row][col] = new Tile(row, col, color);
                    this.tiles[row][col].makeVisible(); // Hacer visible la baldosa si se necesita
                }
            }
        }
    }

    /**
     * Añade una baldosa en una posición específica dentro del rompecabezas.
     * 
     * @param row fila en la que se añadirá la baldosa
     * @param column columna en la que se añadirá la baldosa
     * @param color color de la baldosa
     */
    public void addTile(int row, int column, String color) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            Tile tile = new Tile(row, column, color);
            tiles[row][column] = tile; // Añade la baldosa en la posición especificada
        } else {
            System.out.println("Posición inválida."); // Mensaje de error si la posición no es válida
        }
    }

    /**
     * Elimina una baldosa de una posición específica dentro del rompecabezas.
     * 
     * @param row fila de la baldosa a eliminar
     * @param column columna de la baldosa a eliminar
     */
    public void deleteTile(int row, int column) {
        if (isValidPosition(row, column)) {
            if (tiles[row][column] != null) {
                tiles[row][column].makeInvisible();  // Hace invisible la baldosa
                tiles[row][column] = null;           // Elimina la baldosa
            } else {
                System.out.println("No hay baldosa en esta posición."); // Mensaje si no hay baldosa
            }
        } else {
            System.out.println("Posición inválida."); // Mensaje de error si la posición no es válida
        }
    }

    /**
     * Mueve una baldosa de una posición a otra.
     * 
     * @param from un arreglo que contiene la fila y columna de la posición actual de la baldosa
     * @param to un arreglo que contiene la nueva fila y columna a la que se moverá la baldosa
     */
    public void relocateTile(int[] from, int[] to) {
        int currentRow = from[0];
        int currentColumn = from[1];
        int newRow = to[0];
        int newColumn = to[1];
        
        if (isValidPosition(newRow, newColumn) && isValidPosition(currentRow, currentColumn)) {
            Tile tile = tiles[currentRow][currentColumn];
            if (tile != null) {
                tile.moveTo(newRow, newColumn); // Mueve la baldosa a la nueva posición
                tiles[newRow][newColumn] = tile; // Coloca la baldosa en la nueva posición
                tiles[currentRow][currentColumn] = null;  // Vacía la posición anterior
            } else {
                System.out.println("No hay baldosa en esta posición."); // Mensaje si no hay baldosa
            }
        } else {
            System.out.println("Movimiento no permitido."); // Mensaje si el movimiento no es válido
        }
    }
    
    /**
     * Agrega pegante en la posición especificada del rompecabezas.
     * 
     * @param row fila a la que se agrega pegante
     * @param column columna a la que se agrega pegante
     */
    public void addGlue(int row, int column) {
        if (isValidPosition(row, column) && tiles[row][column] != null && !glue[row][column]) {
            glue[row][column] = true; // Marca la posición como pegada
            // Aquí se puede agregar lógica para representar visualmente el pegante
        } else {
            System.err.println("Posición no válida para agregar pegante"); // Mensaje de error
        }
    }
    
    /**
     * Elimina pegante en la posición especificada del rompecabezas.
     * 
     * @param row fila a la que se le elimina el pegante
     * @param column columna a la que se le elimina el pegante
     */
    public void deleteGlue(int row, int column) {
        if (isValidPosition(row, column) && tiles[row][column] != null && glue[row][column]) {
            glue[row][column] = false; // Marca la posición como no pegada
            // Aquí se puede agregar lógica para representar visualmente la eliminación del pegante
        } else {
            System.err.println("Posición no válida para eliminar pegante"); // Mensaje de error
        }
    }
    
    /**
     * Inclina el rompecabezas en la dirección especificada.
     * 
     * @param direction dirección en la que se inclinará el rompecabezas (ej. 'N', 'S', 'E', 'O')
     */
    public void tilt(char direction) {
        // Aquí se puede agregar lógica para inclinar el rompecabezas en la dirección establecida
    }
    
    /**
     * Verifica si la disposición actual del rompecabezas coincide con la disposición final deseada.
     * 
     * @return true si la disposición actual es igual a la final, false en caso contrario
     */
    public boolean isGoal() {
        return Arrays.deepEquals(starting, ending); // Compara las matrices de inicio y fin
    }

    /**
     * Devuelve la disposición actual del rompecabezas.
     * Recorre la matriz de baldosas y crea una nueva matriz de enteros que representa la disposición actual.
     * Cada celda de la matriz resultante contendrá el valor de la baldosa (por ejemplo, un número que representa el color o tipo de baldosa)
     * o 0 si no hay ninguna baldosa en esa posición.
     * 
     * @return una matriz de enteros que representa la disposición actual del rompecabezas
     */
    public int[][] actualArrangement() {
        int[][] arrangement = new int[rows][columns]; // Matriz para almacenar la disposición actual
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (tiles[row][col] != null) {
                    arrangement[row][col] = 1; // Suponiendo que 1 representa la presencia de una baldosa
                } else {
                    arrangement[row][col] = 0; // 0 representa la ausencia de baldosa
                }
            }
        }
        
        return arrangement; // Devuelve la matriz que representa la disposición actual
    }

    /**
     * Hace visibles todas las baldosas en el rompecabezas.
     */
    public void makeVisible() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (tiles[row][col] != null) {
                    tiles[row][col].makeVisible();  // Hace visible la baldosa
                }
            }
        }
    }
    
    /**
     * Hace invisibles todas las baldosas en el rompecabezas.
     */
    public void makeInvisible() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (tiles[row][col] != null) {
                    tiles[row][col].makeInvisible();  // Hace invisible la baldosa
                }
            }
        }
    }
    
    /**
     * Termina el simulador del rompecabezas y reinicia su estado.
     * Este método vacía las matrices de baldosas y pegamento,
     * y establece las variables en su estado inicial.
     */
    public void finish() {
        // Mensaje indicando que el simulador se está reiniciando
        System.out.println("Reiniciando el estado del rompecabezas...");
    
        // Vaciar la matriz de baldosas
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                tiles[row][col] = null; // Eliminar las baldosas
                starting[row][col] = 0;
                ending[row][col] = 0;
            }
        }
    
        // Vaciar la matriz de pegamento
        for (int row = 0; row < rows; row++) {
            Arrays.fill(glue[row], false); // Establecer todo a false
        }
    
        // Establecer la visibilidad en true para las nuevas baldosas
        isVisible = true;
    
        // Mensaje final indicando que el estado ha sido reiniciado
        System.out.println("El estado del rompecabezas ha sido reiniciado. Listo para una nueva partida.");
    }

    /**
     * Verifica si una posición es válida dentro de los límites del rompecabezas.
     * 
     * @param row fila a verificar
     * @param column columna a verificar
     * @return true si la posición es válida, false en caso contrario
     */
    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
    
    /**
     * Busca una baldosa en la lista de baldosas que se encuentra en una posición específica.
     * Recorre la lista de baldosas "tiles" y compara la fila y la columna de cada baldosa con las coordenadas proporcionadas. Si 
     * encuentra una baldosa en la posición especificada, la devuelve; si no se encuentra ninguna baldosa en esa posición, devuelve "null".
     * 
     * @param row fila de la posición en la que se busca la baldosa
     * @param column columna de la posición en la que se busca la baldosa
     * @return la baldosa encontrada en la posición especificada o null si no hay ninguna baldosa
     */
    public Tile findTile(int row, int column) {
        if (isValidPosition(row, column)) {
            return tiles[row][column]; // Devuelve la baldosa en la posición especificada
        }
        return null; // Devuelve null si la posición no es válida o no hay baldosa
    }

}
