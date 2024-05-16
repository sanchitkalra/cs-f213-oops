public interface Abs {
    double func(double[] x) throws EmptyArrayException;
}

class EmptyArrayException extends Exception {
    EmptyArrayException() {
        super("Supplied array is empty");
    }
}

class LambdasExcep {
    public static void main(String[] args) throws EmptyArrayException {
        double[] vals = { 1.0, 2.0 };

        Abs abs = (x) -> {
            double sum = 0;

            if (x.length == 0)
                throw new EmptyArrayException();

            for (int k = 0; k < x.length; k++)
                sum += x[k];

            return sum / x.length;
        };

        System.out.println(abs.func(vals));
        System.out.println(abs.func(new double[0]));
    }
}
