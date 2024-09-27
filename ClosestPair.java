package closest_pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

class Point{
	public double x;
	public double y;
	
	Point(double d, double e)
	{
		this.x = d;
		this.y = e;
	}
	
	public static double distance(Point P1, Point P2) {
		return (double) Math.sqrt(((P1.x - P2.x)*(P1.x - P2.x)) + ((P1.y - P2.y)*(P1.y - P2.y)));
	}
	public static double calculateMinimumDistance(Point [] P, int n) {
		
		double minDistance = Double.MAX_VALUE;
		double currentMin = 0;
		for(int i=0;i<n;i++)
		{
			for(int j=i+1;j<n;j++)
			{
				currentMin = distance(P[i],P[j]);
				if (currentMin < minDistance)
				{
					minDistance = currentMin;
				}
			}
			
		}
		return minDistance;
		
	}
	
	public static Point[] sortXPoints(Point[] points, int n) {
	    Arrays.sort(points, 0, n, new PointXComparator());
	    return points;
	}
	public static Point[] sortYPoints(Point[] points, int n) {
	    Arrays.sort(points, 0, n, new PointYComparator());
	    return points;
	}
	
	
	public static double closestPair(Point[] P, int n)
	{
		Point[] Px = Arrays.copyOf(P, n);
		Px = sortXPoints(Px, n);
		Point[] Py = Arrays.copyOf(P, n);
		Py = sortYPoints(Py, n);
		return calculateClosestPairDistance(Px, Py, n);
		
	}
	
	public static double calculateClosestPairDistance(Point[] Px, Point[] Py, int n) {
		
		if(n<=3)
		{
			calculateMinimumDistance(Px, n);
		}
		
		int mid = n/2;
		Point midPoint = Px[mid];
		
		Point[] Pyl = Arrays.copyOfRange(Py, 0, mid);
		Point[] Pyr = Arrays.copyOfRange(Py, mid, n);
		
		Point[] Pxr = Arrays.copyOfRange(Px, mid, n);
		double dl = calculateClosestPairDistance(Px, Pyl, mid);
		double dr =  calculateClosestPairDistance(Pxr, Pyr, n - mid);
		
		double d = Math.min(dl, dr);
		
		List<Point> strip = new ArrayList<Point>();
		
		for(Point p: Py)
		{
			if (Math.abs(p.x - midPoint.x) < d)
			{
				strip.add(p);
			}
		}
		return findClosestInStrip(strip.toArray(new Point[strip.size()]), strip.size(), d);
	}
	
	public static double findClosestInStrip(Point[] stripPoints, int n, double d)
	
	{
		double min = d;
		for(int i=0;i<n;i++)
		{
			for(int j = i+1; j< n && (stripPoints[j].y - stripPoints[i].y) < min;j++)
			{
				double distance = distance(stripPoints[i], stripPoints[j]);
				if (distance < min)
						min = distance;
			}
		}
		return min;
	}
}
	class PointXComparator implements Comparator<Point>{
		@Override
		public int compare (Point pointa, Point pointb)
		{
			return Double.compare(pointa.x, pointb.x);
		}
	}
	class PointYComparator implements Comparator<Point>{
		@Override
		public int compare(Point pointa, Point pointb)
		{
			return Double.compare(pointa.y, pointb.y);
		}
	}
	
public class ClosestPair {
	 public static void main(String[] args) {
		    int [] sizes = {100,250,500,750,2500,5000};
		    DecimalFormat df = new DecimalFormat("#.######");
		    Random rand = new Random();
		    long  totalDuration =0;
		    for (int n : sizes) {
		        List<Point> pointList = new ArrayList<>();
		        while (pointList.size() < n) {
		            double x = rand.nextDouble() * 100; 
		            double y = rand.nextDouble() * 100; 
		            Point newPoint = new Point(x, y);
		            
		            // Check for duplicates before adding
		            if (!pointList.contains(newPoint)) {
		                pointList.add(newPoint);
		            }
		        }
		        Point[] P = pointList.toArray(new Point[0]);
		        long startTime = System.nanoTime();
	            double minDistance = Point.calculateMinimumDistance(P, n);
	            long endTime = System.nanoTime();
	            long duration = endTime - startTime; 
	            totalDuration += duration;
	            System.out.println("The smallest distance for n = " + n + " is " + 
	                    df.format(minDistance) + " | Time taken: " + (duration) + " ns");
		    }
		    double averageDuration = (double) totalDuration / sizes.length; 
	        System.out.println("Average execution time: " + averageDuration + " ns");
	    
}
	 }
