public class TetrisGrid {
    private boolean[][] grid;
    public TetrisGrid(boolean [][] grid){
        this.grid = grid;
    }
    public void clearRows(){
        for (int i = 0; i < grid.length; ++i){
            boolean check = true;
            for (int j = 0; j < grid[i].length; ++j){
                if (!grid[i][j]){
                    check = false;
                    break;
                }
            }
            if (check)
                shiftRowsdown(i);
        }
    }

    private void shiftRowsdown(int row) {
        for (int i = row; i > 0; --i){
            for (int j = 0; j < grid[i].length; ++j) {
                grid[i][j] = grid[i - 1][j];
            }
        }
        for (int j = 0; j < grid[0].length; ++j){
            grid[0][j] = false;
        }
    }
    public boolean[][] getGrid(){
        return grid;
    }

    public static void main(String[] args) {
        boolean[][] grid = new boolean[][]{
                {false,false,true},
                {true,true,true},
                {true,true,false}
        };
        TetrisGrid tetrisGrid = new TetrisGrid(grid);
        tetrisGrid.clearRows();
        printGrid(tetrisGrid.getGrid());
    }

    private static void printGrid(boolean[][] grid) {
        for (int i = 0; i < grid.length; ++i){
            for (int j = 0; j < grid[i].length; ++j){
                System.out.print(grid[i][j] ? "1 " : "0 ");
            }
            System.out.println();
        }
    }
}