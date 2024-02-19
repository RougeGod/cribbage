import java.util.Arrays;

public class Statistics {

    public static double meandianTolerance = 0.0001;

    public static double getArithmeticMean(double[] arr) {
        double total = 0;
        for (int count = 0; count < arr.length; count++) {
            total += arr[count];
            }
        return total / (double)arr.length;
        }

    public static double getGeometricMean(double[] arr) throws IllegalArgumentException{
        double total = 1;
        for (int count = 0; count < arr.length; count++) {
            if (arr[count] < 0) {
                throw new IllegalArgumentException("Cannot calculate geometric mean of negative numbers.");
            }
            total *= arr[count];
            }
        return Math.pow(total, (1.0/arr.length));
        }
    public static double getMedian(double[] arr) {
        Arrays.sort(arr);
        if (arr.length % 2 == 0) {
            return (arr[arr.length / 2] + arr[(arr.length / 2) - 1]) / 2.0;
            }
        else {
            return (arr[arr.length / 2]); //integer division to only get the middle element
            }
        }

    //
    public static double getMeandian(double[] arr) {
        double[] components = new double[3];
        components[0] = getArithmeticMean(arr);
        components[1] = getMedian(arr);
        components[2] = getGeometricMean(arr);
        Arrays.sort(components);
        if (components[2] - components[0] < meandianTolerance) {
            return components[1];
            }
        return getMeandian(components);
        }

    public static double getArithmeticMean(int[] arr) {
        double[] doubleArray = new double[arr.length];
        for (int count = 0; count < arr.length; count++) {
            doubleArray[count] = (double)arr[count];
            }
        return getArithmeticMean(doubleArray);
        }

    public static int getMedian(int[] arr) {
        Arrays.sort(arr);
        return (arr[arr.length / 2]);
        //yes, median is different when array is even length but for
        //any purposes used here, that doesn't matter when asking for int medians
        }
    public static double getMeandian(int[] arr) {
        double[] doubleArray = new double[arr.length];
        for (int count = 0; count < arr.length; count++) {
            doubleArray[count] = (double)arr[count];
            }
        return getMeandian(doubleArray);
        }
    public static int getMax(int[] arr) {
        Arrays.sort(arr);
        return arr[arr.length - 1];
        }
}
