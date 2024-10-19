import java.util.*;
import java.math.*;

public class BaseValue {
    
    public static BigInteger decodeValue(int base, String value) {
        try {
            return new BigInteger(value, base); // BigInteger handles large values
        } catch (NumberFormatException e) {
            System.err.println("Error decoding value: " + value + " in base " + base);
            throw e;
        }
    }

    // Lagrange interpolation to calculate the constant term (P(0))
    public static BigInteger lagrangeInterpolation(List<int[]> points) {
        int n = points.size();
        BigInteger constantTerm = BigInteger.ZERO;

        for (int i = 0; i < n; i++) {
            int xi = points.get(i)[0];
            BigInteger yi = BigInteger.valueOf(points.get(i)[1]);
            BigDecimal term = new BigDecimal(yi);

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int xj = points.get(j)[0];
                    BigDecimal factor = BigDecimal.valueOf(0 - xj)
                            .divide(BigDecimal.valueOf(xi - xj), 15, BigDecimal.ROUND_HALF_UP);
                    term = term.multiply(factor);
                }
            }

            constantTerm = constantTerm.add(term.toBigInteger());
        }

        return constantTerm;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        // Data structure to store the points (x, base, value)
        Map<Integer, int[]> data = new HashMap<>();


        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int base = sc.nextInt();
            String value = sc.next();

            // Decode the y value based on the provided base
            BigInteger decodedY = decodeValue(base, value);
            System.out.println(decodedY);

            // Store the point
            data.put(x, new int[]{x, decodedY.intValue()});
        }

        // Collect the points into a list
        List<int[]> points = new ArrayList<>();
        for (Map.Entry<Integer, int[]> entry : data.entrySet()) {
            points.add(entry.getValue());
        }

        // Perform Lagrange interpolation to find the constant term
        BigInteger secret = lagrangeInterpolation(points.subList(0, k));

        System.out.println(secret);

    }
}
