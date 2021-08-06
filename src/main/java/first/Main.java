package first;

import algorithms.RunThroughTest;
import first.tests.ConstantTest;
import first.tests.LinearTest;
import first.tests.NonlinearTest;

import java.util.stream.IntStream;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("Constant test");
        IntStream
                .iterate(4, d -> d * 2)
                .limit(10)
                .mapToDouble(d -> new ConstantTest(d, 1.0, 4.0).getMaxEpsilon())
                .forEach(epsilon -> System.out.printf("%.1e%n", epsilon));
        
        System.out.println("\nLinear test");
        IntStream
                .iterate(4, d -> d * 2)
                .limit(10)
                .mapToDouble(d -> new LinearTest(d, 1.0, 4.0).getMaxEpsilon())
                .forEach(epsilon -> System.out.printf("%.1e%n", epsilon));
        
        
        System.out.println("\nNonlinear test");
        double[] nonLinearEpsilon = IntStream
                .iterate(4, d -> d * 2)
                .limit(10)
                .mapToDouble(d -> new NonlinearTest(d, 1.0, 4.0).getMaxEpsilon())
                .toArray();
        
        System.out.println("MaxEps\t\tmaxEps[i-1]/maxEps[i]");
        System.out.printf("%.1e%n", nonLinearEpsilon[0]);
        for (int i = 1; i < nonLinearEpsilon.length; i++) {
            System.out.println(String.format("%.1e", nonLinearEpsilon[i]) + "\t\t" + nonLinearEpsilon[i - 1] / nonLinearEpsilon[i]);
        }
        System.out.println();
        
        RunThroughTest.printResult(4, System.out);
    }
}