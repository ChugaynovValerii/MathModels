package second.tests;

public class ConstantTest extends Test {
    public ConstantTest(int N, int M, double r0, double rN, double tM) {
        super(N, M, r0, rN, tM);
    }
    
    @Override
    protected double k(double t, double r) {
        return 1;
    }
    
    @Override
    protected double f(double t, double r) {
        return 1;
    }
    
    @Override
    protected double q(double t, double r) {
        return 1;
    }
    
    @Override
    protected double u(double t, double r) {
        return 1;
    }
}
