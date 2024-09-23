import java.util.*;

/**
 * La clase Puzzle se encarga de manejar el estado del rompecabezas y las operaciones que se pueden realizar sobre él.
 * 
 * @author Angie Ramos and Cristian Polo
 * @version 1.0  (08 septiembre 2024)
 */


public class Puzzle {
    private int height;
    private int width;
    private int [][] startingArrangement;
    private int [][] endingArrangement;
    private boolean[][] glue;
    private boolean isVisible;
    private List<Tile> tiles;
    
    /**
     * Inicializa un rompecabezas 
     * @param h altura del rompecabezas #filas
     * @param w anchura del rompecabezas #columnas
     */
    public Puzzle(int h, int w) {
        this.height = h;
        this.width = w;
        this.startingArrangement = new int[h][w];
        this.endingArrangement = new int [h][w];
        this.glue = new boolean[h][w];
        this.isVisible = false;
        this.tiles = new ArrayList<>();
        initializeEmptyPuzzle();
    }
    
    /**
     * Inicializa un rompecabezas a partir de una disposicion final
     * @param ending disposición final deseada del rompecabezas representada como un arreglo bidimensional de enteros
     */
    public Puzzle(int[][] ending) {
        this.height = ending.length;
        this.width = ending[0].length;
        this.startingArrangement = new int [height][width];
        this.endingArrangement = ending;
        this.glue = new boolean[height][width];
        this.isVisible = false;
        this.tiles = new ArrayList<>();
        initializeEmptyPuzzle();
    }
    
    /**
     * inicializa un nuevo rompecabezas a partir de una disposición inicial y una disposición final dadas
     * @param starting disposicion inicial 
     * @param ending disposicion final 
     */
    public Puzzle(int[][] starting, int[][] ending) {
        this.height = starting.length;
        this.width = starting[0].length;
        this.startingArrangement = starting;
        this.endingArrangement = ending;
        this.glue = new boolean[height][width];
        this.isVisible = false;
        this.tiles = new ArrayList<>();
    }
    
    /**
     * Inicializa el rompecabezas vacío
     */
    private void initializeEmptyPuzzle() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(startingArrangement[i], '.');
            Arrays.fill(endingArrangement[i], '.');
            Arrays.fill(glue[i], false);
        }
    }

    /**
     * Agrega una baldosa al rompecabezas en la posición especificada por la fila y columna,y con el color indicado. Si la posición es válida, se actualiza la disposición inicial,
     * se crea la baldosa y se añade a la lista de baldosas. Si el rompecabezas es visible, la baldosa también se muestra en pantalla.
     * @param row fila en la que se agregará la baldosa
     * @param column columna en la que se agregará la baldosa
     * @param color color de la baldosa, como cadena de texto
     */
    public void addTile(int row, int column, String color) {
        if (isValidPosition(row, column)) {
            startingArrangement[row][column] = color.charAt(0);
            Tile tile = new Tile(row, column, color);
            tiles.add(tile);
            if (isVisible) {
                tile.makeVisible();
            }
        } else {
            System.err.println("Posicion no valida para agregar una baldosa");              //muestra un mensaje de error si la posición no es válida
        }
    }
    
    /**
     * Elimina una baldosa del rompecabezas en la posición específicada. Si la posición es válida y existe una baldosa en esa ubicación, 
     * se actualiza la disposición inicial, se hace invisible la baldosa y se elimina de la lista de baldosas
     * @param row fila que corresponde a la baldosa que se desea eliminar
     * @param column columna que corresponde a la baldosa que se desea eliminar
     */
    public void deleteTile(int row, int column) {
        if (isValidPosition(row, column)) {
            startingArrangement[row][column] = '.';
            Tile tile = findTile(row, column);
            if (tile != null) {
                tile.makeInvisible();
                tiles.remove(tile);
            }
        } else {
            System.err.println("Posicion no válida para eliminar una baldosa");
        }
    }
    
    /**
     * Reubica una baldosa dentro del rompecabezas, moviéndola de una posición inicial a una nueva posición especificada. Si ambas 
     * posiciones son válidas, la disposición inicial se actualiza y la baldosa se mueve
     * @param from arreglo de enteros que representa la posición inicial [fila, columna] desde donde se moverá la baldosa
     * @param to " posición a la que se moverá la baldosa
     */
    public void relocateTile(int[] from, int[] to) {
        if (isValidPosition(from[0], from[1]) && isValidPosition(to[0], to[1])) {
            
                if (startingArrangement[from[0]][from[1]] == '.') {
            System.err.println("No hay una baldosa en la posicion de origen. No se puede reubicar.");
            return;
        }
        
            if (startingArrangement[to[0]][to[1]] != '.') {
            System.err.println("Ya hay una baldosa en la posición destino. No se puede reubicar.");
            return;
        }
        
            startingArrangement[to[0]][to[1]] = startingArrangement[from[0]][from[1]];
            startingArrangement[from[0]][from[1]] = '.';
            Tile tile = findTile(from[0], from[1]);
            if (tile != null) {
                tile.moveTo(to[0], to[1]);
            }
        } else {
            System.err.println("Posicion no valida para reubicar una baldosa");
        }
    }
    
    /**
     * Agrega pegante en la posición especificada del rompecabezas
     * @param row fila a la que se agrega pegante
     * @param column columna a la que se agrega pegante
     */
    public void addGlue(int row, int column) {
        if (isValidPosition(row, column) && startingArrangement[row][column] != '.' && glue[row][column] == false) {
            glue[row][column] = true;
            //¿cómo poner para que se represente visualmente el pegante?
        } else {
            System.err.println("Posicion no valida para agregar pegante");
        }
    }
    
    /**
     * Elimina pegante en la posición especificada del rompecabezas
     * @param row fila a la que se le elimina el pegante
     * @param column columna a la que se le elimina el pegante
     */
    public void deleteGlue(int row, int column) {
        if (isValidPosition(row, column) && startingArrangement[row][column] != '.' && glue[row][column] == true) {
            glue[row][column] = false;
            //¿cómo poner para que se elimine visualmente el pegante?
        } else {
            System.err.println("Posicion no valida para eliminar pegante");
        }
    }

    public void tilt(char direction) {
        //¿cómo poner para que se incline el rompecabezas en la direccion establecida?
    }
    
    /**
     * Verifica si la disposición actual del rompecabezas coincide con la disposición final deseada
     */
    public boolean isGoal() {
        return Arrays.deepEquals(startingArrangement, endingArrangement);
    }
    
    /**
     * Devuelve la disposición actual del rompecabezas
     */
    public int [][] actualArrangement() {
        return startingArrangement;
    }
    
    /**
     * Hace visible el rompecabezas y todas sus baldosas
     */
    public void makeVisible() {
        isVisible = true;
        for (Tile tile : tiles) {
            tile.makeVisible();
        }
    }
    
    /**
     * Hace invisible el rompecabezas y todas sus baldosas
     */
    public void makeInvisible() {
        isVisible = false;
        for (Tile tile : tiles) {
            tile.makeInvisible();
        }
    }

    public void finish() {
        
    }
    
    /**
     * Verifica si una posición específica dentro del rompecabezas es válida
     * Una posición es considerada válida si tanto la "row" como la "column" están dentro de los límites permitidos del rompecabezas, 
     * es decir, si la fila y la columna se encuentran dentro del rango definido por las dimensiones del rompecabezas ("height" y "width")
     * @param row  fila de la posición a verificar
     * @param column columna de la posición a verificar.
     */
    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }
    
    /**
     * Busca una baldosa en la lista de baldosas que se encuentra en una posición específica
     * Recorre la lista de baldosas "tiles" y compara la fila y la columna de cada baldosa con las coordenadas proporcionadas. Si 
     * encuentra una baldosa en la posición especificada, la devuelve, si no se encuentra ninguna baldosa en esa posición, devuelve "null"
     * @param row fila de la posición en la que se busca la baldosa
     * @param column columna de la posición en la que se busca la baldosa
     */
    private Tile findTile(int row, int column) {
        for (Tile tile : tiles) {
            if (tile.getRow() == row && tile.getColumn() == column) {
                return tile;
            }
        }
        return null;
    }
}

