package first.tests;

import algorithms.RunThrough;

import java.util.stream.DoubleStream;

public abstract class Test {
    private final int N;
    private final double r0;
    private final double rN;
    private final double y1;
    private final double y2;
    
    private final double h;
    private double[] aArray;
    private double[] cArray;
    private double[] bArray;
    private double[] gArray;
    
    private final double[] exactSolution;
    private final double[] approximateSolution;
    private final double maxEpsilon;
    
    public Test(int N, double r0, double rN) {
        this.N = N;
        this.r0 = r0;
        this.rN = rN;
        this.y1 = u(r0);
        this.y2 = u(rN);
        h = (rN - r0) / N;
        exactSolution = getExactSolution();
        approximateSolution = getApproximateSolution();
        maxEpsilon = calculateMaxEpsilon();
    }
    
    public double[] getApproximateSolution() {
        if (approximateSolution != null) {
            return approximateSolution;
        }
        fill();
        return RunThrough.solve(N, aArray, cArray, bArray, gArray);
    }
    
    private void fill() {
        aArray = new double[N];
        aArray[N - 1] = 0.0;
        cArray = new double[N + 1];
        cArray[0] = 1.0;
        cArray[N] = 1.0;
        bArray = new double[N];
        bArray[0] = 0.0;
        gArray = new double[N + 1];
        gArray[0] = y1;
        gArray[N] = y2;
        for (int i = 1; i < N; i++) {
            aArray[i - 1] = aCoefficient(i);
            cArray[i] = cCoefficient(i);
            bArray[i] = bCoefficient(i);
            gArray[i] = gCoefficient(i);
        }
    }
    
    private double aCoefficient(int i) {
        double r = r0 + (i - 0.5) * h;
        return -k(r) * r * r / h;
    }
    
    private double cCoefficient(int i) {
        double r = r0 + i * h;
        double rMinus = r0 + (i - 0.5) * h;
        double rPlus = r0 + (i + 0.5) * h;
        return rPlus * rPlus * k(rPlus) / h
                + rMinus * rMinus * k(rMinus) / h
                + h * r * r * q(r);
    }
    
    private double bCoefficient(int i) {
        double r = r0 + (i + 0.5) * h;
        return -k(r) * r * r / h;
    }
    
    private double gCoefficient(int i) {
        double r = r0 + i * h;
        return h * r * r * f(r);
    }
    
    private double calculateMaxEpsilon() {
        double[] exactSolution = getExactSolution();
        double maxEpsilon = 0.0;
        for (int i = 0; i < N + 1; i++) {
            double epsilon = Math.abs(exactSolution[i] - approximateSolution[i]);
            maxEpsilon = Math.max(maxEpsilon, epsilon);
        }
        return maxEpsilon;
    }
    
    public double getMaxEpsilon() {
        return maxEpsilon;
    }
    
    public int getN() {
        return N;
    }
    
    public double[] getExactSolution() {
        if (exactSolution != null) {
            return exactSolution;
        }
        return DoubleStream
                .iterate(r0, d -> d + h)
                .limit(getN() + 1)
                .map(this::u)
                .toArray();
    }
    
    protected abstract double k(double x);
    
    protected abstract double f(double x);
    
    protected abstract double q(double x);
    
    protected abstract double u(double x);
}
