import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * La clase Puzzle se encarga de manejar el estado del rompecabezas y las operaciones que se pueden realizar sobre él.
 * 
 * @author Angie Ramos and Cristian Polo
 * @version 1.0  (08 septiembre 2024)
 */
public class Puzzle {
    private Tile[][] tiles, tilesf;
    private int h, hf;
    private int w, wf;
    protected boolean[][] glue;
    private boolean isVisible;
    private char[][] starting;
    private char[][] ending;
    private boolean lastActionSuccessful;
    
    /**
     * Constructor que inicializa el rompecabezas con un número específico de filas y columnas.
     * 
     * @param h número de filas del rompecabezas
     * @param w número de columnas del rompecabezas
     */
    public Puzzle(int h, int w) {
        this.h = h;
        this.w = w;
        tiles = new Tile[h][w];
        this.glue = new boolean[h][w];
        this.isVisible = true;
        this.lastActionSuccessful = true;
        makeVisible();
    }
    
    /**
     * Constructor sobrecargado que crea un Puzzle a partir de una matriz de posiciones.
     * 
     * @param ending una matriz de enteros que representa las posiciones de las baldosas.
     * Se asume que un valor de 0 significa que no hay baldosa en esa posición.
     */
    public Puzzle(char[][] ending) {
        this.h = ending.length;
        this.w = ending[0].length;
        tiles = new Tile[h][w];
        tilesf = new Tile[h][w];
        this.glue = new boolean[h][w];
        this.isVisible = true;
        
        // Inicializa las baldosas en función de la matriz proporcionada
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (ending[row][col] != '.') {
                    String color = Character.toString(ending[row][col]);
                    addTile(row, col, color);
                }
                else {
                    this.tiles[row][col] = null;
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
    public Puzzle(char[][] starting, char[][] ending) {
        this.h = starting.length;
        this.w = starting[0].length;
        this.hf = ending.length;
        this.wf = ending[0].length;
        tiles = new Tile[h][w];
        tilesf = new Tile[h][w];
        this.glue = new boolean[h][w];
        this.isVisible = true;
    
        // Crea el tablero inicial
        this.starting = new char[this.h][this.w];
        for (int row = 0; row < this.h; row++) {
            for (int col = 0; col < this.w; col++) {
                if (starting[row][col] != '.') {
                    String color = Character.toString(starting[row][col]);
                    addTile(row, col, color);
                }
            }
        }
        
        // Crea el tablero final
        this.ending = new char[this.hf][this.wf];
        for (int row = 0; row < this.h; row++) {
            for (int col = 0; col < this.w; col++) {
                if (ending[row][col] != '.') {
                    String color = Character.toString(ending[row][col]);
                    addTilef(row, col, color);
                }
            }
        }
        
        // Creación del tablero final de manera gráfica
        JFrame frame = new JFrame("Ending");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = 0; row < ending.length; row++) {
                    for (int col = 0; col < ending[row].length; col++) {
                        if (ending[row][col] != '.') {
                            String colorString = Character.toString(ending[row][col]);
                            Color color;
                
                            if (colorString.equals("r"))
                                color = Color.red;
                            else if (colorString.equals("n"))
                                color = Color.black;
                            else if (colorString.equals("b"))
                                color = Color.blue;
                            else if (colorString.equals("y"))
                                color = Color.yellow;
                            else if (colorString.equals("v"))
                                color = Color.green;
                            else if (colorString.equals("m"))
                                color = Color.magenta;
                            else if (colorString.equals("w"))
                                color = Color.white;
                            else if (colorString.equals("g"))
                                color = Color.gray;
                            else
                                color = Color.black;
                
                            g.setColor(color);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(col * 30, row * 30, 30, 30);
                        g.setColor(Color.BLACK);
                        g.drawRect(col * 30, row * 30, 30, 30);
                    }
                }
            }
        };
        
        panel.setPreferredSize(new Dimension(1000,1000));
        frame.add(panel);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Añade una baldosa en una posición específica dentro del rompecabezas.
     * 
     * @param row fila en la que se añadirá la baldosa
     * @param column columna en la que se añadirá la baldosa
     * @param color color de la baldosa
     */
    public void addTile(int row, int column, String color) {
        if (isValidPosition(row, column)) {
            if (tiles[row][column] == null) {
                Tile tile = new Tile(row, column, color);
                tiles[row][column] = tile;
                makeVisible();
                lastActionSuccessful = true;
            } else {
                System.out.println("Ya existe una baldosa en esta posición");
                lastActionSuccessful = false;
            }
        } else {
            System.out.println("Posición inválida.");
            lastActionSuccessful = false;
        }
    }
    
    /**
     * Añade una baldosa en una posición específica dentro del rompecabezas de la matriz final.
     * 
     * @param row fila en la que se añadirá la baldosa
     * @param column columna en la que se añadirá la baldosa
     * @param color color de la baldosa
     */
    private void addTilef(int row, int column, String color) {
        if (isValidPosition(row, column)) {
            if (tilesf[row][column] == null) {
                Tile tile = new Tile(row, column, color);
                tilesf[row][column] = tile;
            } else {
                System.out.println("Ya existe una baldosa en esta posición");
            }
        } else {
            System.out.println("Posición inválida.");
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
                tiles[row][column].makeInvisible();
                tiles[row][column] = null;
                makeVisible();
                lastActionSuccessful = true;
            } else {
                System.out.println("No hay baldosa en esta posición.");
                lastActionSuccessful = false;
            }
        } else {
            System.out.println("Posición inválida.");
            lastActionSuccessful = false;
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
                if (tiles[newRow][newColumn] == null) {
                    tile.moveTo(newRow, newColumn);
                    tiles[newRow][newColumn] = tile;
                    tiles[currentRow][currentColumn] = null;
                    makeVisible();
                    lastActionSuccessful = true;
                } else {
                    System.out.println("Ya existe una baldosa en la posición nueva");
                    lastActionSuccessful = false;
                }
            } else {
                System.out.println("No hay baldosa en esta posición.");
                lastActionSuccessful = false;
            }
        } else {
            System.out.println("Movimiento no permitido.");
            lastActionSuccessful = false;
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
            glue[row][column] = true;
            tiles[row][column].changeColor("g");
            
            // Aplicar pegamento a las baldosas vecinas
            int[][] neighbors = {
                {row - 1, column},
                {row + 1, column},
                {row, column - 1},
                {row, column + 1}
            };
            
            for (int[] neighbor : neighbors) {
                int neighborRow = neighbor[0];
                int neighborColumn = neighbor[1];
                if (isValidPosition(neighborRow, neighborColumn) && tiles[neighborRow][neighborColumn] != null) {
                    glue[neighborRow][neighborColumn] = true;
                    tiles[neighborRow][neighborColumn].changeColor("g");
                }
            }
            makeVisible();
        } else {
            System.err.println("Posición no válida para agregar pegante");
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
            glue[row][column] = false;
            tiles[row][column].restoreOriginalColor();
    
            // También elimina el pegamento en las baldosas vecinas
            int[][] neighbors = {
                {row - 1, column},
                {row + 1, column},
                {row, column - 1},
                {row, column + 1}
            };
    
            for (int[] neighbor : neighbors) {
                int neighborRow = neighbor[0];
                int neighborColumn = neighbor[1];
                if (isValidPosition(neighborRow, neighborColumn) && 
                    tiles[neighborRow][neighborColumn] != null) {
                    glue[neighborRow][neighborColumn] = false;
                    tiles[neighborRow][neighborColumn].restoreOriginalColor();
                }
            }            
            makeVisible();     
        } else {
            System.err.println("Posición no válida para eliminar pegante");
        }
    }
    
    /**
     * Inclina el rompecabezas en la dirección especificada.
     * 
     * @param direction dirección en la que se inclinará el rompecabezas (Norte: 'N', Sur: 'S', Este: 'E', Oeste:'O')
     */
    public void tilt(char direction) {
        switch (direction) {
            case 'N':
                for (int col = 0; col < w; col++) {
                    for (int row = 1; row < h; row++) {
                        moveTileUp(row, col);
                    }
                }
                break;
            case 'S':
                for (int col = 0; col < w; col++) {
                    for (int row = h - 2; row >= 0; row--) {
                        moveTileDown(row, col);
                    }
                }
                break;
            case 'E':
                for (int row = 0; row < h; row++) {
                    for (int col = w - 2; col >= 0; col--) {
                        moveTileRight(row, col);
                    }
                }
                break;
            case 'O': // Mover baldosas hacia la izquierda
                for (int row = 0; row < h; row++) {
                    for (int col = 1; col < w; col++) {
                        moveTileLeft(row, col);
                    }
                }
                break;
            default:
                System.err.println("Dirección inválida. Usa 'N', 'S', 'E' o 'O'.");
                return;
        }
    
        makeVisible();
    }
    
    private void moveTileUp(int row, int col) {
        if (tiles[row][col] != null && !glue[row][col]) {
            int newRow = row;
            while (newRow > 0 && tiles[newRow - 1][col] == null) {
                newRow--;
            }
            if (newRow != row) {
                tiles[newRow][col] = tiles[row][col];
                tiles[row][col] = null;
                tiles[newRow][col].moveTo(newRow, col);
            }
        }
    }
    
    private void moveTileDown(int row, int col) {
        if (tiles[row][col] != null && !glue[row][col]) {
            int newRow = row;
            while (newRow < h - 1 && tiles[newRow + 1][col] == null) {
                newRow++;
            }
            if (newRow != row) {
                tiles[newRow][col] = tiles[row][col];
                tiles[row][col] = null;
                tiles[newRow][col].moveTo(newRow, col);
            }
        }
    }
    
    private void moveTileRight(int row, int col) {
        if (tiles[row][col] != null && !glue[row][col]) {
            int newCol = col;
            while (newCol < w - 1 && tiles[row][newCol + 1] == null) {
                newCol++;
            }
   
            if (newCol != col) {
                tiles[row][newCol] = tiles[row][col];
                tiles[row][col] = null;
                tiles[row][newCol].moveTo(row, newCol);
            }
        }
    }
    
     private void moveTileLeft(int row, int col) {
        if (tiles[row][col] != null && !glue[row][col]) {
            int newCol = col;
            while (newCol > 0 && tiles[row][newCol - 1] == null) {
                newCol--;
            }
 
            if (newCol != col) {
                tiles[row][newCol] = tiles[row][col];
                tiles[row][col] = null;
                tiles[row][newCol].moveTo(row, newCol);
            }
        }
    }
    
    /**
     * Verifica si la disposición actual del rompecabezas coincide con la disposición final deseada.
     * 
     * @return true si la disposición actual es igual a la final, false en caso contrario
     */
    public boolean isGoal() {
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {

                if (tiles[row][col] != null && tilesf[row][col] != null) {

                    if (!tiles[row][col].getColor().equals(tilesf[row][col].getColor())) {
 
                    }
                }
  
                else if ((tiles[row][col] == null && tilesf[row][col] != null) || 
                         (tiles[row][col] != null && tilesf[row][col] == null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Devuelve la disposición actual del rompecabezas.
     * Recorre la matriz de baldosas y crea una nueva matriz de enteros que representa la disposición actual.
     * Cada celda de la matriz resultante contendrá el valor de la baldosa (por ejemplo, un número que representa el color o tipo de baldosa)
     * o 0 si no hay ninguna baldosa en esa posición.
     * 
     * @return una matriz de enteros que representa la disposición actual del rompecabezas
     */
    public char[][] actualArrangement() {
        char[][] arrangement = new char[h][w];
        
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (tiles[row][col] != null) {
                    arrangement[row][col] = tiles[row][col].getColor().charAt(0);
                } else {
                    arrangement[row][col] = '.';
                }
            }
        }
        
        // Imprimir la matriz de caracteres
        System.out.println("Disposición actual del rompecabezas:");
        for (char[] row : arrangement) {
            System.out.println(Arrays.toString(row));
        }
        
        return arrangement;
    }
    
    /**
     * Termina el simulador del rompecabezas y reinicia su estado.
     * Este método vacía las matrices de baldosas y pegamento,
     * y establece las variables en su estado inicial.
     */
    public void finish() {
        makeInvisible();
        
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (tiles[row][col] != null) {
                    tiles[row][col] = null;
                }
                glue[row][col] = false;
            }
        }
        
        if (starting != null) {
            for (int row = 0; row < h; row++) {
                for (int col = 0; col < w; col++) {
                    starting[row][col] = 0;
                }
            }
        }
        
        if (ending != null) {
            for (int row = 0; row < hf; row++) {
                for (int col = 0; col < wf; col++) {
                    ending[row][col] = 0;
                }
            }
        }
        
        isVisible = false;
        Canvas canvas = Canvas.getCanvas();
        makeVisible();
        System.out.println("El rompecabezas ha sido reiniciado completamente.");
    }
    
    /**
     * Verifica si una posición es válida dentro de los límites del rompecabezas.
     * 
     * @param row fila a verificar
     * @param column columna a verificar
     * @return true si la posición es válida, false en caso contrario
     */
    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < h && column >= 0 && column < w;
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
            return tiles[row][column];
        }
        return null;
    }

    /**
     * Dibuja las baldosas sobre el tablero, superpuestas a las celdas vacías.
     */
    public void makeVisible() {
        Canvas canvas = Canvas.getCanvas();
        // Dibuja las baldosas
        for (int row = 0; row < h; row++) {
            for (int column = 0; column < w; column++) {
                if (tiles[row][column] != null) {
                    tiles[row][column].makeVisible();
                }
            }
        }
        // Dibuja las celdas encima de las baldosas
        for (int row = 0; row < h; row++) {
            for (int column = 0; column < w; column++) {
                int x = column * 30;
                int y = row * 30;
  
                canvas.drawLine(x, y, x + 30, y);
                canvas.drawLine(x + 30, y, x + 30, y + 30);
                canvas.drawLine(x, y + 30, x + 30, y + 30);
                canvas.drawLine(x, y, x, y + 30);
            }
        }
    }
    
    /**
     * Hace invisibles todas las baldosas en el rompecabezas.
     */
    public void makeInvisible() {
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (tiles[row][col] != null) {
                    tiles[row][col].makeInvisible();
                }
            }
        }
    }
    
    /**
     * Verifica si la última acción realizada fue exitosa.
     * @return true si la última acción fue exitosa, false en caso contrario
     */
    public boolean ok() {
        return lastActionSuccessful;
    }
    
    /**
     * Crea un agujero en la posición especificada del rompecabezas.
     * Solo se puede crear un agujero en una posición vacía.
     * 
     * @param row fila en la que se creará el agujero
     * @param column columna en la que se creará el agujero
     */
    public void makeHole(int row, int column) {
        if (isValidPosition(row, column)) {
            
            if (tiles[row][column] != null) {
                System.out.println("No se puede crear un agujero en una posición ocupada por una baldosa.");
                lastActionSuccessful = false;
                return;
            }

            Hole hole = new Hole(row, column);
            hole.makeVisible();
            makeVisible();
            lastActionSuccessful = true;
        } else {
            System.out.println("Posición inválida para crear el agujero.");
            lastActionSuccessful = false;
        }
    }
}