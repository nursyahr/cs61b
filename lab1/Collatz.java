/** Class that prints the Collatz sequence starting from a given number.
 *  @author syah
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        int x = 0;
        if (n % 2 == 0) {
            x = n /2;
        } else {
            x = 3 * n + 1;
        }
        return x;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

