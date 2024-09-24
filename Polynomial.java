import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients; 
    int[] exponents;

    //constructor
    public Polynomial() {
		coefficients = new double[]{0};
        exponents = new int[]{0};
	}

    //constructor
    public Polynomial(double[] c, int[] e) {
		//assumes coefficients and exponents will have the same length
        coefficients = Arrays.copyOf(c, c.length);
        exponents = Arrays.copyOf(e, e.length);
	}

    //constructor
    public Polynomial(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String polynomial = scan.nextLine().trim();
        scan.close();
        
        //this uses lookahead regex which basically splits all values if they have a + or - before but keeps the delimiters
        String[] terms = polynomial.split("(?=[+-])");
    
        double[] coeffs = new double[terms.length];
        int[] exps = new int[terms.length];
    
        for (int i = 0; i < terms.length; i++) {
            String term = terms[i].trim(); // Remove any whitespaces
    
            if (term.contains("x")) {
                // Split the term into coefficient and exponent parts at 'x'
                String[] parts = term.split("x", 2); 
    
                // Handle the coefficient part
                if (parts[0].equals("") || parts[0].equals("+")) {
                    coeffs[i] = 1;
                } else if (parts[0].equals("-")) {
                    coeffs[i] = -1; 
                } else {
                    coeffs[i] = Double.parseDouble(parts[0]);
                }
    
                // Handle the exponent part
                if (parts.length == 1 || parts[1].equals("")) {
                    exps[i] = 1; 
                } else {
                    exps[i] = Integer.parseInt(parts[1]);
                }
    
            } else {
                
                coeffs[i] = Double.parseDouble(term);
                exps[i] = 0; // Constant term implies exponent 0
            }
        }
    
        coefficients = coeffs;
        exponents = exps;
    }

    public Polynomial add(Polynomial p) {
        Map<Integer, Double> result = new HashMap<>();
       
        for (int i = 0; i < this.coefficients.length; i++){
            int exp1 = this.exponents[i];
            result.put(exp1, result.getOrDefault(exp1, 0.0) + this.coefficients[i]);
       }
       
        for (int i = 0; i < p.coefficients.length; i++) {
            int exp2 = p.exponents[i];
            result.put(exp2, result.getOrDefault(exp2, 0.0) + p.coefficients[i]);
            //getOrDefault method either gets the value that corresponds with the exponent or if the exponent isn't there it gets 0, allowing to do addition in one step
        }

        int size = result.size();
        double[] newCoeffs = new double[size];
        int[] newExps = new int[size];

        int index = 0;
        for (Map.Entry<Integer, Double> entry : result.entrySet()) {
            newExps[index] = entry.getKey();
            newCoeffs[index] = entry.getValue();
            index++;
        }

       return new Polynomial(newCoeffs, newExps);
	}

    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i]*(Math.pow(x,exponents[i]));
        }

        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial p){
        Map<Integer, Double> result = new HashMap<>();
    
        for (int i = 0; i < this.coefficients.length; i++){
            for (int j = 0; j < p.coefficients.length; j++){
                double c = this.coefficients[i] * p.coefficients[j];
                int e = this.exponents[i] + p.exponents[j];
    
                result.put(e, result.getOrDefault(e, 0.0) + c);
            }
        }
    
        int size = result.size();
        double[] newCoeffs = new double[size];
        int[] newExps = new int[size];
    
        int index = 0;
        for (Map.Entry<Integer, Double> entry : result.entrySet()) {
            newExps[index] = entry.getKey();
            newCoeffs[index] = entry.getValue();
            index++;
        }
    
        return new Polynomial(newCoeffs, newExps);
    }
    
    public void saveToFile (String filename) throws IOException{
        FileWriter writer = new FileWriter (filename);

        //using hashmap to combine terms
        Map<Integer, Double> combine = new HashMap<>();
        for (int i = 0; i < coefficients.length; i++) {
            int exp = exponents[i];
            combine.put(exp, combine.getOrDefault(exp, 0.0) + coefficients[i]);
        }

        StringBuilder polynomial = new StringBuilder();

        for (int i = 0; i < exponents.length; i++) {
            int exp = exponents[i];
            double coeff = combine.get(exp);

            // Skip terms with a zero coefficient
            if (coeff == 0) continue;

            // Handle the first term differently
            if (polynomial.length() > 0 && coeff > 0) polynomial.append("+");

            // add the coefficient if it's not 1 or -1, or if it's a constant
            if (coeff != 1 && coeff != -1 || exp == 0) {
                polynomial.append(coeff);
            } else if (coeff == -1 && exp != 0) {
                polynomial.append("-");
            }

            // add the variable x if exponent is not 0
            if (exp != 0) {
                polynomial.append("x");
                if (exp > 1) {
                    polynomial.append(exp);
                }
            }
        }
        writer.write(polynomial.toString());
        writer.close();

    }
}