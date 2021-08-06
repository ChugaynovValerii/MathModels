package second.tests;

import static java.lang.Math.pow;
import static java.lang.Math.exp;

public class DependsOnRAndT extends Test {
    public DependsOnRAndT(int N, int M, double r0, double rN, double tM) {
        super(N, M, r0, rN, tM);
    }
    
    @Override
    protected double k(double t, double r) {
        return r * exp(-2 * t);
    }
    
    @Override
    protected double f(double t, double r) {
        return -3 * exp(-3 * t) + pow(r, 3) * exp(-5 * t) - r * exp(-t);
    }
    
    @Override
    protected double q(double t, double r) {
        return r * r * exp(-4 * t);
    }
    
    @Override
    protected double u(double t, double r) {
        return r * exp(-t);
    }
}
