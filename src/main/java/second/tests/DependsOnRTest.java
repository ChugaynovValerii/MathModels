package second.tests;

public class DependsOnRTest extends Test {
    
    public DependsOnRTest(int N, int M, double r0, double rN, double tM) {
        super(N, M, r0, rN, tM);
    }
    
    @Override
    protected double k(double t, double r) {
        return r;
    }
    
    @Override
    protected double f(double t, double r) {
        return r * r - 3;
    }
    
    @Override
    protected double q(double t, double r) {
        return r;
    }
    
    @Override
    protected double u(double t, double r) {
        return r;
    }
}
