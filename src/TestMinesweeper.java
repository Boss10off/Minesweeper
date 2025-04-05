public class TestMinesweeper {
    static int successfulTests = 0;
    static int testNumber = 0;
    public static void main(String[] args) {
        testCoordinatedMiningCenter();
        testMineInCornerTopLeft();
        testMineInCornerBottomRight();
        testMineCount();
        testLeftClick();
    }
    //test output
    static void assertEqual(int height, int length, int reality, int expectation) {
        if (reality == expectation) {
            success();
        }
        else {
            System.out.println("Test Nr " + (testNumber+1) +" Failed: Failure at height x" + height + " and length y" + length + " reality: " + reality + ", but the expectation is:  " + expectation);
        }
        testNumber++;
    }
    static void assertEqualMineCount(int height, int length, int reality, int expectation) {
        if (reality == expectation) {
            success();
        }
        else {
            System.out.println("Test Nr" + (testNumber+1) + " Failed: Failure with an " + height + " by " + length + " Array: Placed Mines: " + reality + ", Expectation is:  " + expectation);
        }
        testNumber++;
    }
    static void assertEqualLeftClick(int height, int length, boolean reality, boolean expectation) {
        if (reality == expectation) {
            success();
        }
        else {
            System.out.println("Test Nr" + (testNumber+1) + " Failed: Failure at " + height + " by " + length + " Array:" + reality + ", Expectation is:  " + expectation);
        }
        testNumber++;
    }
    //Test - expected result on Coordinates
    static void testCoordinatedMiningCenter() {
        System.out.println(" ");
        System.out.println("Test for a mine in the center of an array at x1 y1. ");
        Minefield testField = new Minefield(4, 3, 0);
        testField.placeMines(1,1);
        testField.scanMines();
        testComparison(testField, 0,0,1);
        testComparison(testField, 0,1,1);
        testComparison(testField, 0,2,1);
        testComparison(testField, 1,0,1);
        testComparison(testField, 1,1,1);
        testComparison(testField, 1,2,1);
        testComparison(testField, 2,0,1);
        testComparison(testField, 2,1,1);
        testComparison(testField, 2,2,1);
        testComparison(testField, 3,0,0);
        testComparison(testField, 3,1,0);
        testComparison(testField, 3,2,0);

        System.out.println("Successful tests: " + successfulTests + " out of " + testNumber + " tests");
        System.out.println("Visual shown ->");
        System.out.println(" ");
        successfulTests = 0;
        testNumber = 0;
        testField.gameVisualOutput();
    }
    static void testMineInCornerTopLeft() {
        System.out.println(" ");
        System.out.println("Test for a mine in the Corner of an array at x0 y0. ");
        Minefield testField = new Minefield(4, 3, 0);
        testField.placeMines(0,0);
        testField.scanMines();
        testComparison(testField, 0,0,1);
        testComparison(testField, 0,1,1);
        testComparison(testField, 0,2,0);
        testComparison(testField, 1,0,1);
        testComparison(testField, 1,1,1);
        testComparison(testField, 1,2,0);
        testComparison(testField, 2,0,0);
        testComparison(testField, 2,1,0);
        testComparison(testField, 2,2,0);
        testComparison(testField, 3,0,0);
        testComparison(testField, 3,1,0);
        testComparison(testField, 3,2,0);

        System.out.println("Successful tests: " + successfulTests + " out of " + testNumber + " tests");
        System.out.println("Visual shown ->");
        System.out.println(" ");
        successfulTests = 0;
        testNumber = 0;
        testField.gameVisualOutput();
    }
    static void testMineInCornerBottomRight() {
        System.out.println(" ");
        System.out.println("Test for a mine in the Corner of an array at x3 y2. ");
        Minefield testField = new Minefield(4, 3, 0);
        testField.placeMines(3,2);
        testField.scanMines();

        testComparison(testField, 0,0,0);
        testComparison(testField, 0,1,0);
        testComparison(testField, 0,2,0);
        testComparison(testField, 1,0,0);
        testComparison(testField, 1,1,0);
        testComparison(testField, 1,2,0);
        testComparison(testField, 2,0,0);
        testComparison(testField, 2,1,1);
        testComparison(testField, 2,2,1);
        testComparison(testField, 3,0,0);
        testComparison(testField, 3,1,1);
        testComparison(testField, 3,2,1);

        System.out.println("Successful tests: " + successfulTests + " out of " + testNumber + " tests");
        System.out.println("Visual shown ->");
        System.out.println(" ");
        successfulTests = 0;
        testNumber = 0;
        testField.gameVisualOutput();
    }
    static void testMineCount(){
        System.out.println(" ");
        System.out.println("Test for the Count of mines in array.");

        testMineCountComparison(10,10,20,20);
        testMineCountComparison(10,10,100,100);
        testMineCountComparison(10,10,120,100);
        testMineCountComparison(10,10,0,0);
        testMineCountComparison(1000,1000,100,100);
        testMineCountComparison(1000,1000,0,0);
        testMineCountComparison(1000,1000,10000,10000);
        testMineCountComparison(1000,1000,1000000,1000000);
        testMineCountComparison(1000,1000,10000000,1000000);

        System.out.println("Successful tests: " + successfulTests + " out of " + testNumber + " tests");
        System.out.println(" ");
        successfulTests = 0;
        testNumber = 0;
    }
    static void testLeftClick(){
        System.out.println(" ");
        System.out.println("Test for the Opening of fields for Left click at [0,2] in array.");

        Minefield testField = new Minefield(5, 5, 0);
        testField.placeMines(2,0);
        testField.placeMines(2,1);
        testField.placeMines(2,2);
        testField.placeMines(2,3);
        testField.placeMines(2,4);
        testField.scanMines();
        testField.leftClicked(0,2);
        testField.gameVisualOutput();

        testLeftClickComparison(testField,0,0,true);
        testLeftClickComparison(testField,0,1,true);
        testLeftClickComparison(testField,0,2,true);
        testLeftClickComparison(testField,0,3,true);
        testLeftClickComparison(testField,0,4,true);
        testLeftClickComparison(testField,1,0,true);
        testLeftClickComparison(testField,1,1,true);
        testLeftClickComparison(testField,1,2,true);
        testLeftClickComparison(testField,1,3,true);
        testLeftClickComparison(testField,1,4,true);
        testLeftClickComparison(testField,2,0,false);
        testLeftClickComparison(testField,2,1,false);
        testLeftClickComparison(testField,2,2,false);
        testLeftClickComparison(testField,2,3,false);
        testLeftClickComparison(testField,2,4,false);
        testLeftClickComparison(testField,3,0,false);
        testLeftClickComparison(testField,3,1,false);
        testLeftClickComparison(testField,3,2,false);
        testLeftClickComparison(testField,3,3,false);
        testLeftClickComparison(testField,3,4,false);
        testLeftClickComparison(testField,4,0,false);
        testLeftClickComparison(testField,4,1,false);
        testLeftClickComparison(testField,4,2,false);
        testLeftClickComparison(testField,4,3,false);
        testLeftClickComparison(testField,4,4,false);
        //a Second Click
        System.out.println("Test for the Opening of fields for Left click at [4,4] in array.");
        testField.leftClicked(4,4);
        testField.gameVisualOutput();

        testLeftClickComparison(testField,0,0,true);
        testLeftClickComparison(testField,0,1,true);
        testLeftClickComparison(testField,0,2,true);
        testLeftClickComparison(testField,0,3,true);
        testLeftClickComparison(testField,0,4,true);
        testLeftClickComparison(testField,1,0,true);
        testLeftClickComparison(testField,1,1,true);
        testLeftClickComparison(testField,1,2,true);
        testLeftClickComparison(testField,1,3,true);
        testLeftClickComparison(testField,1,4,true);
        testLeftClickComparison(testField,2,0,false);
        testLeftClickComparison(testField,2,1,false);
        testLeftClickComparison(testField,2,2,false);
        testLeftClickComparison(testField,2,3,false);
        testLeftClickComparison(testField,2,4,false);
        testLeftClickComparison(testField,3,0,true);
        testLeftClickComparison(testField,3,1,true);
        testLeftClickComparison(testField,3,2,true);
        testLeftClickComparison(testField,3,3,true);
        testLeftClickComparison(testField,3,4,true);
        testLeftClickComparison(testField,4,0,true);
        testLeftClickComparison(testField,4,1,true);
        testLeftClickComparison(testField,4,2,true);
        testLeftClickComparison(testField,4,3,true);
        testLeftClickComparison(testField,4,4,true);

        System.out.println("Successful tests: " + successfulTests + " out of " + testNumber + " tests");
        System.out.println(" ");
        successfulTests = 0;
        testNumber = 0;
    }
    //Comparison Procedures
    static void testComparison(Minefield testField, int height, int length, int expectation) {
        int nearbyMines = testField.searchNearbyMines(height,length);
        assertEqual(height,length, nearbyMines, expectation);
    }
    static void testMineCountComparison(int height, int length, int mineCount, int expectationMineCount) {
        Minefield testField = new Minefield(height, length, mineCount);
        int realityMineCount = testField.mineCounter();
        assertEqualMineCount(height,length,realityMineCount,expectationMineCount);
    }
    static void testLeftClickComparison(Minefield testField, int height, int length, boolean expectationOpen) {
        boolean open = testField.getOpen(height, length);
        assertEqualLeftClick(height,length,open,expectationOpen);
    }
    static void success(){
        successfulTests++;
    }
}

