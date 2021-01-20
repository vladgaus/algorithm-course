import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // size of grid
    private int n;

    // private sizw of side
    private int size;

    // can open node?
    private boolean canOpen;

    // count of grid
    private int count;

    // grid
    private int[] grid;

    // count opened sites
    private int countOpened;

    // Weighted Quick Union type
    private WeightedQuickUnionUF uf;

    // get position from coordinates row and column
    private int fromCoordsToPos(int row, int col) {
        return (col - 1) + (row - 1) * size;
    }

    // can open?
    private boolean canOpen(int row, int col) {
        return ((row > 0 && row <= size) && (col > 0 && col <= size));
    }

    // validate coordinates
    private void validate(int row, int col) {
        canOpen = canOpen(row, col);
        if (!canOpen) {
            throw new java.lang.IllegalArgumentException("row index out of grid");
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("N must be > 0");

        // set count
        count = n * n;

        // set size
        size = n;

        // create grid
        grid = new int[count];

        // set count opened to 0
        countOpened = 0;

        // init tree
        // size like grid but added 2 nodes - in top and in bottom
        uf = new WeightedQuickUnionUF(count + 2);
        wqfFull = new WeightedQuickUnionUF(gridSquared + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        int pos = fromCoordsToPos(row, col);

        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        countOpened++;
        grid[pos] = 1;

        // checking right side and create union
        if (col < size && isOpen(row, col + 1)) {
            int posNext = fromCoordsToPos(row, col + 1);
            uf.union(posNext, pos);
        }

        // checking left side and create union
        if (col > 1 && isOpen(row, col - 1)) {
            int posPrev = fromCoordsToPos(row, col - 1);
            uf.union(posPrev, pos);
        }

        // checking top side and create union
        if (row < size) {
            if (isOpen(row + 1, col)) {
                int posTop = fromCoordsToPos(row + 1, col);
                uf.union(posTop, pos);
            }
        } else {
            uf.union(pos, count + 1);
        }

        // checking bottom side and create union
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                int posBottom = fromCoordsToPos(row - 1, col);
                uf.union(posBottom, pos);
            }
        } else {
            uf.union(pos, count);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int pos = fromCoordsToPos(row, col);
        return (grid[pos] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return (uf.find(count) == uf.find(fromCoordsToPos(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return (uf.find(count) == uf.find(count + 1));
    }

    // test client (optional)
    public static void main(String[] args) {

        int size = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        System.out.println(argCount);
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            System.out.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);
            if (percolation.percolates()) {
                System.out.printf("%nThe System percolates %n");
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            System.out.println("Does not percolate %n");
        }

        /*Percolation perc = new Percolation(6);
        perc.open(1, 1);
        perc.open(1, 5);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(2, 6);
        perc.open(3, 3);
        perc.open(3, 4);
        perc.open(3, 5);
        perc.open(4, 3);
        perc.open(5, 4);
        perc.open(5, 5);
        perc.open(6, 5);

        perc.open(3, 6);
        perc.open(5, 3);
        perc.open(1, 3);

        System.out.println("Perlocated?");
        boolean c3 = perc.percolates();
        System.out.println(c3);*/

        /*Percolation perc = new Percolation(3);

        System.out.println("Open row 1 column 1");
        perc.open(1, 1);
        System.out.println("Open row 1 column 3");
        perc.open(1, 3);
        System.out.println("Open row 2 column 2");
        perc.open(2, 2);
        System.out.println("Open row 2 column 3");
        perc.open(2, 3);
        System.out.println("Open row 3 column 3");
        perc.open(3, 3);

        System.out.println("Check open");
        System.out.println("1, 1: " + perc.isOpen(1, 1));
        System.out.println("1, 2: " + perc.isOpen(1, 2));
        System.out.println("1, 3: " + perc.isOpen(1, 3));
        System.out.println("2, 1: " + perc.isOpen(2, 1));
        System.out.println("2, 2: " + perc.isOpen(2, 2));
        System.out.println("2, 3: " + perc.isOpen(2, 3));
        System.out.println("3, 1: " + perc.isOpen(3, 1));
        System.out.println("3, 2: " + perc.isOpen(3, 2));
        System.out.println("3, 3: " + perc.isOpen(3, 3));
        System.out.println("Check full");
        System.out.println("1, 1: " + perc.isFull(1, 1));
        System.out.println("1, 2: " + perc.isFull(1, 2));
        System.out.println("1, 3: " + perc.isFull(1, 3));
        System.out.println("2, 1: " + perc.isFull(2, 1));
        System.out.println("2, 2: " + perc.isFull(2, 2));
        System.out.println("2, 3: " + perc.isFull(2, 3));
        System.out.println("3, 1: " + perc.isFull(3, 1));
        System.out.println("3, 2: " + perc.isFull(3, 2));
        System.out.println("3, 3: " + perc.isFull(3, 3));

        System.out.println("Perlocated?");
        boolean c3 = perc.percolates();
        System.out.println(c3);*/

        /*Percolation perc = new Percolation(5);

        perc.open(1, 1);
        perc.open(1, 5);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(2, 5);
        perc.open(3, 5);
        perc.open(4, 2);
        perc.open(4, 4);
        perc.open(4, 5);
        perc.open(5, 3);
        perc.open(5, 4);

        System.out.println("Check open");
        System.out.println(perc.isOpen(1, 1));
        System.out.println(perc.isOpen(1, 2));
        System.out.println(perc.isOpen(1, 3));
        System.out.println(perc.isOpen(1, 4));
        System.out.println(perc.isOpen(1, 5));
        System.out.println(perc.isOpen(2, 1));
        System.out.println(perc.isOpen(2, 2));
        System.out.println(perc.isOpen(2, 3));
        System.out.println(perc.isOpen(2, 4));
        System.out.println(perc.isOpen(2, 5));
        System.out.println(perc.isOpen(3, 1));
        System.out.println(perc.isOpen(3, 2));
        System.out.println(perc.isOpen(3, 3));
        System.out.println(perc.isOpen(3, 4));
        System.out.println(perc.isOpen(3, 5));
        System.out.println(perc.isOpen(4, 1));
        System.out.println(perc.isOpen(4, 2));
        System.out.println(perc.isOpen(4, 3));
        System.out.println(perc.isOpen(4, 4));
        System.out.println(perc.isOpen(4, 5));
        System.out.println(perc.isOpen(5, 1));
        System.out.println(perc.isOpen(5, 2));
        System.out.println(perc.isOpen(5, 3));
        System.out.println(perc.isOpen(5, 4));
        System.out.println(perc.isOpen(5, 5));
        System.out.println("Check full");
        System.out.println(perc.isFull(1, 1));
        System.out.println(perc.isFull(1, 2));
        System.out.println(perc.isFull(1, 3));
        System.out.println(perc.isFull(1, 4));
        System.out.println(perc.isFull(1, 5));
        System.out.println(perc.isFull(2, 1));
        System.out.println(perc.isFull(2, 2));
        System.out.println(perc.isFull(2, 3));
        System.out.println(perc.isFull(2, 4));
        System.out.println(perc.isFull(2, 5));
        System.out.println(perc.isFull(3, 1));
        System.out.println(perc.isFull(3, 2));
        System.out.println(perc.isFull(3, 3));
        System.out.println(perc.isFull(3, 4));
        System.out.println(perc.isFull(3, 5));
        System.out.println(perc.isFull(4, 1));
        System.out.println(perc.isFull(4, 2));
        System.out.println(perc.isFull(4, 3));
        System.out.println(perc.isFull(4, 4));
        System.out.println(perc.isFull(4, 5));
        System.out.println(perc.isFull(5, 1));
        System.out.println(perc.isFull(5, 2));
        System.out.println(perc.isFull(5, 3));
        System.out.println(perc.isFull(5, 4));
        System.out.println(perc.isFull(5, 5));

        System.out.println("Perlocated?");
        boolean c3 = perc.percolates();
        System.out.println(c3);*/



        /*
        Percolation perc = new Percolation(5);
        perc.open(4, 4);
        perc.open(2, 2);
        perc.open(1, 4);

        perc.open(3, 2);
        perc.open(4, 2);
        perc.open(4, 1);
        perc.open(5, 1);
        perc.open(1, 2);

        System.out.println("checking is full row 1, column 1:");
        boolean c = perc.isOpen(1, 1);
        System.out.println(c);

        System.out.println("checking is full row 1, column 4:");
        boolean c2 = perc.isOpen(1, 4);
        System.out.println(c2);

        System.out.println("checking is full row 2, column 2:");
        boolean c22 = perc.isOpen(2, 2);
        System.out.println(c22);

        System.out.println("Perlocated?");
        boolean c3 = perc.percolates();
        System.out.println(c3);
        */


        //boolean c1 = perc.uf.connected(perc.ijTo1D(1, 1), perc.ijTo1D(2, 1));
        //boolean c2 = perc.percolates();


        //StdOut.println(c1);
        //StdOut.println(c2);

        /*
        int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("%nThe System percolates %n");
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }
        * */
    }
}
