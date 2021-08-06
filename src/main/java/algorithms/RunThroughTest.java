package algorithms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.DoubleStream;

public class RunThroughTest {
    private static int N;
    private static double[][] testResult;
    
    private static double[][] getTestResult(int N) {
        if (testResult != null && RunThroughTest.N == N) {
            return testResult;
        }
        RunThroughTest.N = N;
        double[] exactSolution = DoubleStream
                .iterate(1.0, d -> d + 1.0)
                .limit(N + 1)
                .toArray();
        double[] aArray = DoubleStream
                .iterate(1.0, d -> d + 1.0)
                .limit(N)
                .toArray();
        double[] cArray = DoubleStream
                .iterate(1.0, d -> d + 1.0)
                .limit(N + 1)
                .toArray();
        double[] bArray = DoubleStream
                .iterate(1.0, d -> d + 1.0)
                .limit(N)
                .toArray();
        double[] gArray = new double[N + 1];
        gArray[0] = cArray[0] * exactSolution[0] + bArray[0] * exactSolution[1];
        gArray[N] = aArray[N - 1] * exactSolution[N - 1] + cArray[N] * exactSolution[N];
        for (int i = 1; i < N; i++) {
            gArray[i] = aArray[i - 1] * exactSolution[i - 1]
                    + cArray[i] * exactSolution[i]
                    + bArray[i] * exactSolution[i + 1];
        }
        double[] calculatedSolution = RunThrough.solve(N, aArray, cArray, bArray, gArray);
        testResult = new double[][]{exactSolution, calculatedSolution};
        return testResult;
    }
    
    public static void printResult(int N, OutputStream out) {
        try {
            out.write(("Solutions for N = " + N + ":\nexact\t\tcalculated\t\tepsilon\n").getBytes());
            double[][] result = getTestResult(N);
            for (int i = 0; i < N + 1; i++) {
                out.write((String.format("%.1e", result[0][i]) + "\t\t"
                        + String.format("%.1e", result[1][i]) + "\t\t\t"
                        + String.format("%.1e", Math.abs(result[0][i] - result[1][i])) + "\n")
                        .getBytes());
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
