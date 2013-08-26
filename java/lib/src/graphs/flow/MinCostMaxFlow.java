package graphs.flow;

import java.util.Arrays;

import struct.misc.Pair;

public class MinCostMaxFlow {

	public static Pair<Integer, Double> run(int s, int t, int[][] cap, double[][] cost) {
		int n = cap.length;
		int totalFlow = 0;
		double totalCost = 0;

		boolean path = true;
		while(path) {
			path = false;
			
			double[] weight = new double[n];
			int[] from = new int[n];
			
			Arrays.fill(weight, Double.POSITIVE_INFINITY);
			Arrays.fill(from, -1);
			
			for (int i = 0; i < n; i++) {
				if (cap[s][i] > 0) {
					weight[i] = cost[s][i];
					from[i] = s;
				}
			}
			
			for (int ct = 0; ct < n; ct++) {
				for (int u = 0; u < n; u++) {
					for (int v = 0; v < n; v++) {
						if (cap[u][v] > 0 && weight[u] + cost[u][v] < weight[v]) {
							weight[v] = weight[u] + cost[u][v];
							from[v] = u;
						}
					}
				}
			}
			
			if (from[t] != -1) {
				path = true;
				
				int flowToPush = Integer.MAX_VALUE;
				int at = t;
				while(at != s) {
					flowToPush = Math.min(flowToPush, cap[from[at]][at]);
					at = from[at];
				}

				// update everything
				at = t;
				while(at != s) {
					cap[from[at]][at] -= flowToPush;
					cap[at][from[at]] += flowToPush;
					totalCost += cost[from[at]][at] * flowToPush;
					at = from[at];
				}
				
				totalFlow += flowToPush;
			}
		}
		
		return new Pair<>(totalFlow, totalCost);
	}
	
}
