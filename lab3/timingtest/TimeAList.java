package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
         timeAListConstruction();

    }

    /** Create an array to store timings. Initiate stopwatch. Run addLast. Add the timing to the array.
     *  Then print the timing. Input: list of integers
     */

    public static void timeAListConstruction() {
        AList<Integer> Ns = ANumberList(1000,2000,4000,6000,8000,16000,32000, 64000, 128000, 100000, 10000000);

        AList<Double> times = new AList<>();

        for (int i = 0; i < Ns.size(); i++) {  // to index Ns (i.e. what size we are testing)
            // Init the stop watch, then proceed with adding until N.
            Stopwatch sw = new Stopwatch();

            // Create an empty list
            AList<Integer> L = new AList<>();
            int j = 0;

            // Set the size of current round, currNs.
            int currNs = Ns.get(i);

            while (j < currNs) {
                L.addLast(j);
                j += 1;
            }
            times.addLast(sw.elapsedTime()); // add timing to times list
        }

       printTimingTable(Ns, times, Ns);
    }

    /** Creates an AList of Ns where N = size of an array.
     */
    public static AList<Integer> ANumberList(int...numbers) {
        AList<Integer> Numbers = new AList<>();
        for (int number : numbers) {
            Numbers.addLast(number);
        }
        return Numbers;
    }
}
