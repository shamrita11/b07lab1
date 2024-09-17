public class Polynomial {
    double[] coefficients; 

    //constructor
    public Polynomial() {
		coefficients = new double[]{0};
	}

    //constructor
    public Polynomial(double[] array) {
		
        coefficients = new double[array.length];
       
        for (int i = 0; i < array.length; i++){
            coefficients[i] = array[i];
        }
	}

    public Polynomial add(Polynomial p) {
        //one case to look at is if both polynomials have different lengths, so we see which one is larger and set result to that length
        int length_result = Math.max(coefficients.length, p.coefficients.length);

        double [] result = new double[length_result]; 

        for (int i = 0; i < coefficients.length; i++){
            result[i] += coefficients[i];
        }

        for (int i = 0; i < p.coefficients.length; i++){
            result[i] += p.coefficients[i];
        }

		return new Polynomial(result);
	}

    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i]*(Math.pow(x,i));
        }

        return result;
    }

    public boolean hasRoot(double x){
        if (evaluate(x) == 0){
            return true;
        }
        else{
            return false;
        }
    }
}