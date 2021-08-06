package algorithms;

public final class RunThrough {
    public static double[] solve(int N,
                                 double[] aArray,
                                 double[] cArray,
                                 double[] bArray,
                                 double[] gArray) {
        double[] alphaArray = new double[N];
        double[] betaArray = new double[N];
        double[] vArray = new double[N + 1];
        alphaArray[0] = -bArray[0] / cArray[0];
        betaArray[0] = gArray[0] / cArray[0];
        
        for (int i = 1; i < N; i++) {
            alphaArray[i] = alphaNext(aArray[i - 1], cArray[i], bArray[i], alphaArray[i - 1]);
            betaArray[i] = betaNext(aArray[i - 1], cArray[i], bArray[i], alphaArray[i - 1], betaArray[i - 1], gArray[i]);
        }
        vArray[N] = (gArray[N] - aArray[N - 1] * betaArray[N - 1])
                / (aArray[N - 1] * alphaArray[N - 1] + cArray[N]);
        
        for (int i = N - 1; i >= 0; i--) {
            vArray[i] = alphaArray[i] * vArray[i + 1] + betaArray[i];
        }
        return vArray;
    }
    
    private static double alphaNext(double a, double c, double b, double alpha) {
        return -b / (a * alpha + c);
    }
    
    private static double betaNext(double a, double c, double b, double alpha, double beta, double g) {
        return (g - a * beta) / (a * alpha + c);
    }
}
