package uj.pwj2020.introduction;

public class QuadraticEquation {

    public double[] findRoots(double a, double b, double c) {
        double delta = Math.pow(b,2) - 4 * a * c;

        if (delta > 0) {
            double firstRoot = (-b - Math.sqrt(delta))/(2 * a);
            double secondRoot = (-b + Math.sqrt(delta))/(2 * a);

            return new double[] {firstRoot, secondRoot};
        } else if (delta == 0) {
            double onlyRoot = (-b)/(2*a);
            return new double[] {onlyRoot};
        } else {
            return new double[]{};
        }
    }
}