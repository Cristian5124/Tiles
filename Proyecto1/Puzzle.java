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
    private Tile[][] tiles;
    private int h, hf;
    private int w, wf;
    private boolean[][] glue;
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
        this.glue = new boolean[h][w];
        this.isVisible = true;
    
        // Crea el tablero inicial
        this.starting = new char[this.h][this.w];
        for (int row = 0; row < this.h; row++) {
            for (int col = 0; col < this.w; col++) {
                if (starting[row][col] != '.') {
                    String color = Character.toString(starting[row][col]);
                    addTile(row, col, color);
                    this.starting[row][col] = 1; // Marca la presencia de una baldosa
                } else {
                    this.starting[row][col] = 0; // Marca la ausencia de una baldosa
                }
            }
        }
        
        // Creación del tablero final
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
            glue[row][column] = true; // Marca la posición como pegada
            tiles[row][column].changeColor("g"); // Cambiar el color de la baldosa a gris
            // Aplicar pegamento a las baldosas vecinas
            int[][] neighbors = {
                {row - 1, column}, // Arriba
                {row + 1, column}, // Abajo
                {row, column - 1}, // Izquierda
                {row, column + 1}  // Derecha
            };
            
            for (int[] neighbor : neighbors) {
                int neighborRow = neighbor[0];
                int neighborColumn = neighbor[1];
                if (isValidPosition(neighborRow, neighborColumn) && tiles[neighborRow][neighborColumn] != null) {
                    glue[neighborRow][neighborColumn] = true;
                    tiles[neighborRow][neighborColumn].changeColor("g"); // Cambia el color del borde de las baldosas vecinas
                }
            }
            makeVisible();
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
            glue[row][column] = false; // Quita el pegamento
            tiles[row][column].restoreOriginalColor(); // Restaura el color original de la baldosa
    
            // También elimina el pegamento en las baldosas vecinas
            int[][] neighbors = {
                {row - 1, column}, // Arriba
                {row + 1, column}, // Abajo
                {row, column - 1}, // Izquierda
                {row, column + 1}  // Derecha
            };
    
            for (int[] neighbor : neighbors) {
                int neighborRow = neighbor[0];
                int neighborColumn = neighbor[1];
                if (isValidPosition(neighborRow, neighborColumn) && 
                    tiles[neighborRow][neighborColumn] != null) {
                    glue[neighborRow][neighborColumn] = false;
                    tiles[neighborRow][neighborColumn].restoreOriginalColor(); // Restaura color original
                }
            }            
            makeVisible(); // Hace visible el cambio            
        } else {
            System.err.println("Posición no válida para eliminar pegante"); // Mensaje de error
        }
    }
    
    /**
     * Inclina el rompecabezas en la dirección especificada.
     * 
     * @param direction dirección en la que se inclinará el rompecabezas (Norte: 'N', Sur: 'S', Este: 'E', Oeste:'O')
     */
    public void tilt(char direction) {
        switch (direction) {
            case 'N': // Mover baldosas hacia arriba
                for (int col = 0; col < w; col++) {
                    for (int row = 1; row < h; row++) {
                        moveTileUp(row, col);
                    }
                }
                break;
            case 'S': // Mover baldosas hacia abajo
                for (int col = 0; col < w; col++) {
                    for (int row = h - 2; row >= 0; row--) {
                        moveTileDown(row, col);
                    }
                }
                break;
            case 'E': // Mover baldosas hacia la derecha
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
    
        // Actualizar el tablero
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
        if (tiles[row][col] != null && !glue[row][col]) {  // Verifica si hay una baldosa y no está pegada
            int newCol = col;
            // Mover la baldosa lo más lejos posible hacia la derecha
            while (newCol < w - 1 && tiles[row][newCol + 1] == null) {
                newCol++;
            }
            // Si la baldosa ha cambiado de posición
            if (newCol != col) {
                tiles[row][newCol] = tiles[row][col];  // Mover la baldosa a la nueva posición
                tiles[row][col] = null;  // Dejar la posición original vacía
                tiles[row][newCol].moveTo(row, newCol);  // Actualizar la visualización/movimiento de la baldosa
            }
        }
    }
    
     private void moveTileLeft(int row, int col) {
        if (tiles[row][col] != null && !glue[row][col]) {  // Verifica si hay una baldosa y no está pegada
            int newCol = col;
            // Mover la baldosa lo más lejos posible hacia la izquierda
            while (newCol > 0 && tiles[row][newCol - 1] == null) {
                newCol--;
            }
            // Si la baldosa ha cambiado de posición
            if (newCol != col) {
                tiles[row][newCol] = tiles[row][col];  // Mover la baldosa a la nueva posición
                tiles[row][col] = null;  // Dejar la posición original vacía
                tiles[row][newCol].moveTo(row, newCol);  // Actualizar la visualización/movimiento de la baldosa
            }
        }
    }
    
    /**
     * Verifica si la disposición actual del rompecabezas coincide con la disposición final deseada.
     * 
     * @return true si la disposición actual es igual a la final, false en caso contrario
     */
    public boolean isGoal() {
        if (ending == null) {
            return false;
        }
        
        if (h != hf || w != wf) {
            return false;
        }

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Tile currentTile = tiles[row][col];

                if ((currentTile == null && ending[row][col] != '.') ||
                    (currentTile != null && ending[row][col] == '.')) {
                    return false;
                }

                if (currentTile != null) {
                    String currentColor = currentTile.getColor();
                    char expectedColor = ending[row][col];
                    
                    if (!currentColor.equals(String.valueOf(expectedColor))) {
                        return false;
                    }
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
    private int[][] actualArrangement() {
        int[][] arrangement = new int[h][w]; // Matriz para almacenar la disposición actual
    
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
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
     * Termina el simulador del rompecabezas y reinicia su estado.
     * Este método vacía las matrices de baldosas y pegamento,
     * y establece las variables en su estado inicial.
     */
    public void finish() {
        // 1. Primero hacer invisibles todas las baldosas existentes
        makeInvisible();
        
        // 2. Eliminar referencias a todas las baldosas y limpiar el pegamento
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (tiles[row][col] != null) {
                    tiles[row][col] = null;
                }
                glue[row][col] = false;
            }
        }
        
        // 3. Reiniciar las matrices de starting y ending si existen
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
        
        // 4. Reiniciar el estado de visibilidad
        isVisible = false;
        
        // 5. Limpiar el canvas
        Canvas canvas = Canvas.getCanvas();
        // Necesitamos redibujar el canvas vacío
        makeVisible();
        
        // 6. Imprimir mensaje de confirmación
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
            return tiles[row][column]; // Devuelve la baldosa en la posición especificada
        }
        return null; // Devuelve null si la posición no es válida o no hay baldosa
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
                    tiles[row][column].makeVisible(); // Si hay una baldosa, la hace visible
                }
            }
        }
        // Dibuja las celdas encima de las baldosas
        for (int row = 0; row < h; row++) {
            for (int column = 0; column < w; column++) {
                int x = column * 30;
                int y = row * 30;
                // Dibuja un rectángulo vacío para cada celda
                canvas.drawLine(x, y, x + 30, y); // línea superior
                canvas.drawLine(x + 30, y, x + 30, y + 30); // derecha
                canvas.drawLine(x, y + 30, x + 30, y + 30); // inferior
                canvas.drawLine(x, y, x, y + 30); // izquierda
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
                    tiles[row][col].makeInvisible();  // Hace invisible la baldosa
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
}