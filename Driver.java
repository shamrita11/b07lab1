import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Testing default constructor and evaluate method
        Polynomial p = new Polynomial();
        System.out.println("Evaluate default polynomial (0) at x = 3: " + p.evaluate(3)); // Expect 0

        // Testing parameterized constructor and evaluate method
        double[] c1 = {3, 2};
        int[] e1 = {2, 2};
        Polynomial p1 = new Polynomial(c1, e1);
        System.out.println("Evaluate p1 (6 + 5x^3) at x = 2: " + p1.evaluate(2)); // Expect 6 + 5(8) = 46

        // Testing add method
        double[] c2 = {4, 6};
        int[] e2 = {2, 2};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial sum = p1.add(p2);
        System.out.println("Sum of p1 and p2 evaluated at x = 1: " + sum.evaluate(1)); // Expect 6 + (-2) + 5 + (-9) = 0
        System.out.println("Sum of p1 and p2 evaluated at x = 2: " + sum.evaluate(2)); // Test sum at a different value
        
        // for (int i = 0; i < sum.coefficients.length; i++){
        //     System.out.print(sum.coefficients[i]);
        //     System.out.println(sum.exponents[i]);
        // } test case to see the actual arrays

        // Testing hasRoot method
        if (sum.hasRoot(1)) {
            System.out.println("1 is a root of the sum polynomial.");
        } else {
            System.out.println("1 is not a root of the sum polynomial.");
        }

        // Testing multiply method
        Polynomial product = p1.multiply(p2);
        System.out.println("Product of p1 and p2 evaluated at x = 2: " + product.evaluate(2)); // Should print the product evaluated at x = 2

        // for (int i = 0; i < product.coefficients.length; i++){
        //     System.out.print(product.coefficients[i]);
        //     System.out.println(product.exponents[i]);
        // } test case to see the actual arrays

        // Testing file constructor and saveToFile method
        try {
            // Create a Polynomial from a file
            File file = new File("polynomial.txt");
            Polynomial fromFile = new Polynomial(file);
            System.out.println("Evaluate polynomial from file at x = 1: " + fromFile.evaluate(1));

            // Save a polynomial to a file
            fromFile.saveToFile("saved_polynomial.txt");
            System.out.println("Polynomial successfully saved to file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error while saving polynomial to file: " + e.getMessage());
        }
    }

}
