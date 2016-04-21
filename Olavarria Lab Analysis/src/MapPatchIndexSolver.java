// By Baihan Lin, May 2014

import java.util.*;

public class MapPatchIndexSolver {

	private TreeMap<Integer, Double> grayValue;

	// Pre: throws an IllegalArgumentException if the map is null or empty
	// Post: construct a patch index solver with the data given as a X-Y correlation
	public MapPatchIndexSolver(Map<Integer, Double> data) {
		checkNull(data);
		grayValue = new TreeMap<Integer, Double>(data);
	}

	// Post: return SM
	private double getSM() {
		return grayValue.lastKey();
	}

	// Post: return SI
	private double getSI() {
		double sum = 0;
		int lastX = 0;
		double lastY = grayValue.get(lastX);
		for (int x : grayValue.keySet()) {
			int xDiff = x - lastX;
			double yDiff = grayValue.get(x) - lastY;
			sum += Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			lastX = x;
			lastY = grayValue.get(x);
		}				
		return sum;
	}

	// Post: return Patch Index = 1 - (SM/SI)
	public double doPICalculation() {
		double sm = getSM();
		double si = getSI();
		double pi = 1 - (sm / si);
		System.out.println("SM: " + sm);
		System.out.println("SI: " + si);
		System.out.println("PI: " + pi);		
		return 	pi;
	}

	// Method chosen for smoothing: Moving Average (MA)
	// given n per mov. avg. (here called smooth index), smooth the curve to avoid noise
	public void smooth(int n){
		List<Double> temp = new ArrayList<Double>();
		for (int index = 0; index < grayValue.lastKey(); index++) {
			if (grayValue.containsKey(index + n)) {
				double sum = 0;
				for (int i = index; i < index + n; i++) {
					sum += grayValue.get(index);
				}
				temp.add(index, sum / n);
			} else {
				grayValue.remove(index);
			}
		}
	}

	// Pre: given an object as an input
	// Post: if it is null or empty, throw an IllegalArgumentException
	private void checkNull(Object o) {
		if (o == null) {  
			throw new IllegalArgumentException();
		}
	}

}
