package first.tests;

public class LinearTest extends Test {
    
    public LinearTest(int N, double r0, double rN) {
        super(N, r0, rN);
    }
    
    /*@Override
    protected double k(double x) {
        return x;
    }
    
    @Override
    protected double f(double x) {
        return 5 * x * x
                + 20 * x
                - 15;
    }
    
    @Override
    protected double q(double x) {
        return x + 4;
    }
    
    @Override
    protected double u(double x) {
        return 5 * x;
    }*/
    
    @Override
    protected double k(double x) {
        return 19 * x + 9;
    }
    
    @Override
    protected double f(double x) {
        return 32 * x + 8;
    }
    
    @Override
    protected double q(double x) {
        return 4 * x + 1;
    }
    
    @Override
    protected double u(double x) {
        return 8;
    }
}
