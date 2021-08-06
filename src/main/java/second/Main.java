package second;

import second.tests.ConstantTest;
import second.tests.DependsOnRAndT;
import second.tests.DependsOnRTest;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Constant test\n  M\t\t   N\tImplicit\tExplicit");
        IntStream.iterate(100, M -> M * 10)
                .limit(3)
                .forEach(M -> IntStream.iterate(4, N -> N * 2)
                        .limit(4)
                        .mapToObj(N -> new ConstantTest(N, M, 1.0, 2, 1))
                        .forEach(test -> System.out.printf("%4d\t%4d\t%.2e\t%.2e\n",
                                M, test.getN(),
                                test.getMaxEpsilon(false), test.getMaxEpsilon(true))));
        System.out.println("\nDepends on r test\n  M\t\t   N\tImplicit\tExplicit");
        IntStream.iterate(100, M -> M * 10)
                .limit(3)
                .forEach(M -> IntStream.iterate(4, N -> N * 2)
                        .limit(4)
                        .mapToObj(N -> new DependsOnRTest(N, M, 1.0, 2, 1))
                        .forEach(test -> System.out.printf("%4d\t%4d\t%.2e\t%.2e\n",
                                M, test.getN(),
                                test.getMaxEpsilon(false), test.getMaxEpsilon(true))));
        
        System.out.println("\nDepends on r and t test\n  M\t\t   N\tImplicit\tExplicit");
        IntStream.iterate(100, M -> M * 10)
                .limit(3)
                .forEach(M -> IntStream.iterate(4, N -> N * 2)
                        .limit(4)
                        .mapToObj(N -> new DependsOnRAndT(N, M, 1.0, 2, 1))
                        .forEach(test -> System.out.printf("%4d\t%4d\t%.2e\t%.2e\n",
                                M, test.getN(),
                                test.getMaxEpsilon(false), test.getMaxEpsilon(true))));
    }
}
