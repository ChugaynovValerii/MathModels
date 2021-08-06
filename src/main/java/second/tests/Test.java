package second.tests;

import algorithms.RunThrough;

public abstract class Test {
    
    private final int N;
    private final int M;
    private final double r0;
    private final double rN;
    private final double t0 = 0;
    private final double tM;
    
    private final double dR;
    private final double dT;
    
    private final double[][] exactSolution;
    private final double[][] approximateExplicitSolution;
    private final double[][] approximateImplicitSolution;
    
    private final double maxExplicitEpsilon;
    private final double maxImplicitEpsilon;
    
    public Test(int N, int M, double r0, double rN, double tM) {
        this.N = N;
        this.M = M;
        this.r0 = r0;
        this.rN = rN;
        this.tM = tM;
        dR = (rN - r0) / N;
        dT = (tM - t0) / M;
        exactSolution = getExactSolution();
        approximateExplicitSolution = getApproximateExplicitSolution();
        approximateImplicitSolution = getApproximateImplicitSolution();
        maxImplicitEpsilon = calculateMaxEpsilon(false);
        maxExplicitEpsilon = calculateMaxEpsilon(true);
    }
    
    public double[][] getApproximateExplicitSolution() {
        if (approximateExplicitSolution != null) {
            return approximateExplicitSolution;
        }
        double[][] approximateSolution = new double[M + 1][N + 1];
        approximateSolution[0] = u(t0);
        for (int k = 1; k <= M; k++) {
            approximateSolution[k][0] = u(t(k - 1), r0);
            approximateSolution[k][N] = u(t(k - 1), rN);
            for (int i = 1; i < N; i++) {
                double rightSideExpr = aCoefficient(k - 1, i) * approximateSolution[k - 1][i - 1]
                        - cCoefficient(k - 1, i) * approximateSolution[k - 1][i]
                        + bCoefficient(k - 1, i) * approximateSolution[k - 1][i + 1]
                        + gCoefficient(k - 1, i);
                approximateSolution[k][i] = approximateSolution[k - 1][i] + dT * rightSideExpr / (dR * r(i) * r(i));
            }
        }
        return approximateSolution;
    }
    
    public double[][] getApproximateImplicitSolution() {
        if (approximateImplicitSolution != null) {
            return approximateImplicitSolution;
        }
        double[][] approximateSolution = new double[M + 1][N + 1];
        approximateSolution[0] = u(t0);
        for (int k = 0; k < M; k++) {
            double[] aArray = new double[N];
            aArray[N - 1] = 0.0;
            double[] cArray = new double[N + 1];
            cArray[0] = 1.0;
            cArray[N] = 1.0;
            double[] bArray = new double[N];
            bArray[0] = 0.0;
            double[] gArray = new double[N + 1];
            gArray[0] = u(t(k + 1), r0);
            gArray[N] = u(t(k + 1), rN);
            for (int i = 1; i < N; i++) {
                double r = r(i);
                aArray[i - 1] = -dT * aCoefficient(k + 1, i) / (dR * r * r);
                cArray[i] = 1 + dT * cCoefficient(k + 1, i) / (dR * r * r);
                bArray[i] = -dT * bCoefficient(k + 1, i) / (dR * r * r);
                gArray[i] = approximateSolution[k][i] + dT * gCoefficient(k + 1, i) / (dR * r * r);
            }
            approximateSolution[k + 1] = RunThrough.solve(N, aArray, cArray, bArray, gArray);
        }
        return approximateSolution;
    }
    
    public int getN() {
        return N;
    }
    
    private double r(double i) {
        if (i > N || i < 0) {
            throw new IllegalArgumentException("i must be less than N");
        }
        return r0 + i * dR;
    }
    
    private double t(double k) {
        if (k > M || k < 0) {
            throw new IllegalArgumentException("i must be less than M");
        }
        return t0 + k * dT;
    }
    
    private double aCoefficient(int tK, int rI) {
        double rMinusHalf = r(rI - 0.5);
        return rMinusHalf * rMinusHalf * k(t(tK), rMinusHalf) / dR;
    }
    
    private double cCoefficient(int tK, int rI) {
        double rMinusHalf = r(rI - 0.5);
        double r = r(rI);
        double rPlusHalf = r(rI + 0.5);
        return rPlusHalf * rPlusHalf * k(t(tK), rPlusHalf) / dR
                + rMinusHalf * rMinusHalf * k(t(tK), rMinusHalf) / dR
                + q(t(tK), r) * dR * r * r;
    }
    
    private double bCoefficient(int tK, int rI) {
        double rPlusHalf = r(rI + 0.5);
        return k(t(tK), rPlusHalf) * rPlusHalf * rPlusHalf / dR;
    }
    
    private double gCoefficient(int tK, int rI) {
        double r = r(rI);
        return f(t(tK), r) * r * r * dR;
    }
    
    private double calculateMaxEpsilon(boolean isExplicit) {
        double[][] exactSolution = getExactSolution();
        double[][] approximateSolution = isExplicit ? getApproximateExplicitSolution() : getApproximateImplicitSolution();
        double maxEpsilon = 0.0;
        for (int k = 0; k < M + 1; k++) {
            for (int i = 0; i < N + 1; i++) {
                double epsilon = Math.abs(exactSolution[k][i] - approximateSolution[k][i]);
                maxEpsilon = Math.max(maxEpsilon, epsilon);
            }
        }
        return maxEpsilon;
    }
    
    public double getMaxEpsilon(boolean isExplicit) {
        return isExplicit ? maxExplicitEpsilon : maxImplicitEpsilon;
    }
    
    public double[][] getExactSolution() {
        if (exactSolution != null) {
            return exactSolution;
        }
        double[][] exactSolution = new double[M + 1][N + 1];
        for (int k = 0; k <= M; k++) {
            exactSolution[k] = u(t(k));
        }
        return exactSolution;
    }
    
    protected abstract double k(double t, double r);
    
    protected abstract double f(double t, double r);
    
    protected abstract double q(double t, double r);
    
    protected abstract double u(double t, double r);
    
    private double[] u(double t) {
        double[] u = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            u[i] = u(t, r(i));
        }
        return u;
    }
    
}
