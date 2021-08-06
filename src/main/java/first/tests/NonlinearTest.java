package first.tests;

public class NonlinearTest extends Test {
    
    public NonlinearTest(int N, double r0, double rN) {
        super(N, r0, rN);
    }
    
    @Override
    protected double k(double x) {
        return x * x + 7;
    }
    
    @Override
    protected double f(double x) {
        return x * x * x * x
                - 15 * x * x * x
                - 84 * x;
    }
    
    @Override
    protected double q(double x) {
        return x + 3;
    }
    
    @Override
    protected double u(double x) {
        return x * x * x;
    }
}
