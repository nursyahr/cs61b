package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove() {
        // Create lists.
        BuggyAList<Integer> BL = new BuggyAList<>();
        AListNoResizing<Integer> NR = new AListNoResizing<>();

        // add
        BL.addLast(4);
        BL.addLast(5);
        BL.addLast(6);

        NR.addLast(4);
        NR.addLast(5);
        NR.addLast(6);


        // remove
        int x = BL.removeLast();
        int y = NR.removeLast();

        x = BL.removeLast();
        y = NR.removeLast();

        x = BL.removeLast();
        y = NR.removeLast();

        assertEquals(x,y); // can actually put above calls intois
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);


            if (operationNumber == 0) {
                // addLast
                    int randVal = StdRandom.uniform(0, 100);
                    L.addLast(randVal);
                    M.addLast(randVal);


            } else if (operationNumber == 1) {
                if (L.size() > 0) {
                    // removeLast
                    assertEquals(L.removeLast(),M.removeLast());
                }
            } else if (operationNumber == 2) {
                if (L.size() > 0) {
                    // getLast
                    assertEquals(L.getLast(),M.getLast());
                }

            } else if (operationNumber == 3) {
                    // size
                    assertEquals(L.size(),M.size());

                }
            }
        }
    }




