package first.tests;

public class ConstantTest extends Test {
    
    public ConstantTest(int N, double r0, double rN) {
        super(N, r0, rN);
    }
    
    @Override
    protected double k(double x) {
        return 1;
    }
    
    @Override
    protected double f(double x) {
        return 1;
    }
    
    @Override
    protected double q(double x) {
        return 1;
    }
    
    @Override
    protected double u(double x) {
        return 1;
    }
}
