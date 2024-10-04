package closest_pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class MyPoint {
    public double x;
    public double y;

    MyPoint(double d, double e) {
        this.x = d;
        this.y = e;
    }

    public static double distance(MyPoint P1, MyPoint P2) {
        return Math.sqrt(((P1.x - P2.x) * (P1.x - P2.x)) + ((P1.y - P2.y) * (P1.y - P2.y)));
    }

    public static double calculateMinimumDistance(MyPoint[] P, int n) {
        double minDistance = Double.MAX_VALUE;
        double currentMin;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                currentMin = distance(P[i], P[j]);
                if (currentMin < minDistance) {
                    minDistance = currentMin;
                }
            }
        }
        return minDistance;
    }

    public static MyPoint[] sortXPoints(MyPoint[] points, int n) {
        Arrays.sort(points, 0, n, (a, b) -> Double.compare(a.x, b.x));
        return points;
    }

    public static MyPoint[] sortYPoints(MyPoint[] points, int n) {
        Arrays.sort(points, 0, n, (a, b) -> Double.compare(a.y, b.y));
        return points;
    }

    public static double closestPair(MyPoint[] P, int n) {
        MyPoint[] Px = Arrays.copyOf(P, n);
        Px = sortXPoints(Px, n);
        MyPoint[] Py = Arrays.copyOf(P, n);
        Py = sortYPoints(Py, n);
        return calculateClosestPairDistance(Px, Py, n);
    }

    public static double calculateClosestPairDistance(MyPoint[] Px, MyPoint[] Py, int n) {
        if (n <= 3) {
            return calculateMinimumDistance(Px, n);
        }

        int mid = n / 2;
        MyPoint midPoint = Px[mid];

        MyPoint[] Pyl = Arrays.copyOfRange(Py, 0, mid);
        MyPoint[] Pyr = Arrays.copyOfRange(Py, mid, n);

        MyPoint[] Pxr = Arrays.copyOfRange(Px, mid, n);
        double dl = calculateClosestPairDistance(Px, Pyl, mid);
        double dr = calculateClosestPairDistance(Pxr, Pyr, n - mid);

        double d = Math.min(dl, dr);

        List<MyPoint> strip = new ArrayList<>();

        for (MyPoint p : Py) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip.add(p);
            }
        }
        return findClosestInStrip(strip.toArray(new MyPoint[0]), strip.size(), d);
    }

    public static double findClosestInStrip(MyPoint[] stripPoints, int n, double d) {
        double min = d;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && (stripPoints[j].y - stripPoints[i].y) < min; j++) {
                double distance = distance(stripPoints[i], stripPoints[j]);
                if (distance < min) {
                    min = distance;
                }
            }
        }
        return min;
    }
}

public class ClosestPairPoints {
    public static void main(String[] args) {
        int[] sizes = {10, 50, 100, 500, 1000, 5000,10000, 25000, 50000 };
        DecimalFormat df = new DecimalFormat("#.######");
        Random rand = new Random();
        long totalDuration = 0;
        
        for (int n : sizes) {
            List<MyPoint> pointList = new ArrayList<>();
            while (pointList.size() < n) {
                double x = rand.nextDouble() * 10; // Random points in a larger area
                double y = rand.nextDouble() * 10; // Random points in a larger area
                MyPoint newPoint = new MyPoint(x, y);

                // Check for duplicates before adding
                if (!pointList.contains(newPoint)) {
                    pointList.add(newPoint);
                }
            }
            MyPoint[] P = pointList.toArray(new MyPoint[0]);
            long startTime = System.nanoTime();
            double minDistance = MyPoint.calculateMinimumDistance(P, n);
            long endTime = System.nanoTime();
            long duration = endTime - startTime; // duration in nanoseconds

            totalDuration += duration; 
            System.out.println("The smallest distance for n = " + n + " is " +
                    df.format(minDistance) + " | Time taken: " + duration + " ns");
        }
       
    }
}
