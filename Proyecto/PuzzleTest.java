import org.junit.Test;
import static org.junit.Assert.*;

public class PuzzleTest {

    // Pruebas que deberían funcionar
    
    @Test
    public void testAddTileToValidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        assertNotNull(puzzle.findTile(0, 0));
        assertEquals("r", puzzle.findTile(0, 0).getColor());
    }

    @Test
    public void testDeleteTileFromValidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        puzzle.deleteTile(0, 0);
        assertNull(puzzle.findTile(0, 0));
    }

    @Test
    public void testRelocateTileToValidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        int[] from = {0, 0};
        int[] to = {1, 1};
        puzzle.relocateTile(from, to);
        assertNull(puzzle.findTile(0, 0));
        assertNotNull(puzzle.findTile(1, 1));
        assertEquals("r", puzzle.findTile(1, 1).getColor());
    }

    @Test
    public void testAddGlueToValidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        puzzle.addGlue(0, 0);
        assertTrue(puzzle.glue[0][0]);
    }

    @Test
    public void testDeleteGlueFromValidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        puzzle.addGlue(0, 0);
        puzzle.deleteGlue(0, 0);
        assertFalse(puzzle.glue[0][0]);
    }

    @Test
    public void testTiltUp() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(1, 0, "r");
        puzzle.addTile(2, 0, "b");
        puzzle.tilt('N');
        assertNotNull(puzzle.findTile(0, 0));
        assertNotNull(puzzle.findTile(1, 0));
        assertEquals("r", puzzle.findTile(0, 0).getColor());
        assertEquals("b", puzzle.findTile(1, 0).getColor());
    }

    @Test
    public void testMakeHoleInValidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.makeHole(1, 1);
        assertNull(puzzle.findTile(1, 1));
        assertTrue(puzzle.ok());
    }

    @Test
    public void testIsGoal() {
        char[][] starting = {{'r', 'b', 'y'}, {'v', '.', 'm'}, {'n', 'g', 'w'}};
        char[][] ending = {{'r', 'b', 'y'}, {'v', '.', 'm'}, {'n', 'g', 'w'}};
        Puzzle puzzle = new Puzzle(starting, ending);
        assertTrue(puzzle.isGoal());
    }

    @Test
    public void testActualArrangement() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        puzzle.addTile(1, 1, "b");
        char[][] arrangement = puzzle.actualArrangement();
        assertEquals('r', arrangement[0][0]);
        assertEquals('b', arrangement[1][1]);
        assertEquals('.', arrangement[0][1]);
    }

    @Test
    public void testFinish() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        puzzle.addGlue(0, 0);
        puzzle.finish();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertNull(puzzle.findTile(row, col));
                assertFalse(puzzle.glue[row][col]);
            }
        }
    }

    // Pruebas que no deberían funcionar

    @Test
    public void testAddTileToInvalidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(3, 0, "r");
        assertTrue(puzzle.ok());
    }

    @Test
    public void testDeleteTileFromInvalidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.deleteTile(3, 0);
        assertTrue(puzzle.ok());
    }

    @Test
    public void testRelocateTileToInvalidPosition() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(0, 0, "r");
        int[] from = {0, 0};
        int[] to = {3, 0};
        puzzle.relocateTile(from, to);
        assertTrue(puzzle.ok());
    }

    @Test
    public void testAddGlueToPositionWithoutTile() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addGlue(0, 0);
        assertTrue(puzzle.glue[0][0]);
    }

    @Test
    public void testDeleteGlueFromPositionWithTileNotGlued() {
        Puzzle puzzle = new Puzzle(3, 3);
        puzzle.addTile(1, 1, "r");
        puzzle.deleteGlue(1, 1);
        assertTrue(puzzle.glue[1][1]);
    }
}