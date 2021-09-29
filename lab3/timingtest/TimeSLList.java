package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = ANumberList(1000,2000,4000,6000,8000,16000, 32000, 64000, 128000);

        AList<Double> times = new AList<>();

        for (int i = 0; i < Ns.size(); i++) {  // to index Ns (i.e. what size we are testing)

            // Create an empty list
            SLList<Integer> L = new SLList<>();
            int j = 0;

            // Create list of size N
            int currNs = Ns.get(i);

            while (j < currNs) {
                L.addLast(j);
                j += 1;
            }

            // Init the stop watch, then proceed with getLast, M times.
            Stopwatch sw = new Stopwatch();
            int k = 0;
            while (k < 10000) {
                L.getLast();
                k += 1;
            }
            times.addLast(sw.elapsedTime()); // add timing to times list
        }


        // Create List of Mops
        int m = 0;
        AList<Integer> Ops = new AList<>();

        while (m < Ns.size()) {
            Ops.addLast(10000);
            m += 1;
        }


        printTimingTable(Ns, times, Ops );
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
