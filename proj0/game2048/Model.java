package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author syah
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        // Top Row = stays put
        // Other pieces can move up if: 1) emptySpaceExists or 2) same number
        // Go by columns.
        // Dont have to iterate over row 3.
        /** 1. Check if top row ( == b.size() )
         *  2. Helper fun for column: Check if EmptySpaceExists + Check if SameNumber
         *  4. Update score with merged tile (sum of initial numbers i.e. x * 2)
         */
        int col;
        int addScore = 0;

        for (col = 0; col < board.size(); col++) {
             addScore += moveColumn(col);
        }

        changed = true;
        score += addScore;

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    public int moveColumn(int col) {
        int updateScore = 0;
        int totalRows = board.size();
        int currRow;
        int newRow;
        // no of rows is size
        for (currRow = totalRows - 1; currRow > 0; currRow -= 1) {
            Tile currTile = board.tile(col, currRow);
            // if the current tile is empty, ignore.
            if (currTile != null) {
                newRow = RowToMove(currTile, col, currRow); // give me the index of the row to move to
                int temp = currTile.value(); // store the current value of the tile
                currTile.move(col, newRow); // currTile has moved.

                Tile MovedToTile = board.tile(col, newRow);// create the tile it moved to, to check the value.

                if (MovedToTile.value() != temp) {
                    updateScore += currTile.value();
                }

            }
        }
        return updateScore;
        }



    /**
     * for aboveTile; true: not empty, same number && merged once only | false: empty, not same number
     * aboveTile increment the row only if first return true
     */

    public int RowToMove(Tile currTile, int col, int currRow) {

        int AboveTileRow = currRow + 1;

        // Create a flexible aboveTile
        Tile aboveTile = board.tile(col, AboveTileRow);

        // Move up empty spaces
        while (aboveTile == null) {
            aboveTile = board.tile(col, AboveTileRow + 1);
        }

        // If the tile is the same, update the row of currTile by merging.
        if (aboveTile.value() == currTile.value()) {
            currRow += 1;
        }

        return currRow;
    }






    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        /** first for loop gets me the row index. {0,0,0,0}
         * second for loop access each column in the row.
         * tile (col, row)
         */
        int x = 0;
        outerloop:
        for (int i = 0; i < b.size(); i += 1) { // row = i
            for (int j = 0; j < b.size(); j += 1) { // col = j
                if (b.tile(j, i) == null) {
                    x += 1;
                    break outerloop;
                }
            }
        }

        if (x != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int x = 0;

        for (int i = 0; i < b.size(); i += 1) { // row = i
            for (int j = 0; j < b.size(); j += 1) { // col = j
                if (b.tile(j, i) != null) {
                    if ( b.tile(j,i).value() == Model.MAX_PIECE) {
                        x += 1;
                    }
                }
            }
        }

        if (x != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {

        if (emptySpaceExists(b)) {
            return true;
        }
        if (adjacentTrue(b)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param b
     * @return true if there is a move adjacent
     */
    public static boolean adjacentTrue(Board b) {
        int x = 0;
        for (int i = 0; i < b.size(); i ++) { // row is i
            for (int j = 0; j < b.size(); j ++) { // col

                if (b.tile(j, i) != null) { // if its not empty
                    // check on top
                    if (i + 1 < b.size() && b.tile(j, i).value() == b.tile(j, i+1  ).value()) {
                        x += 1;
                    }

                    // check below
                    else if (i - 1 > 0 && b.tile(j, i).value() == b.tile(j, i-1  ).value()) {
                        x += 1;
                    }

                    // check right
                    else if (j+1 < b.size() && b.tile(j, i).value() == b.tile(j+1, i  ).value()) {
                        x += 1;
                    }

                    // check left
                    else if  (j - 1 > 0 && b.tile(j, i).value() == b.tile(j-1, i  ).value()) {
                        x += 1;
                    }

                }


            }
        }
        if (x == 0) {
            return false;
        } else {
            return true;
        }
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
