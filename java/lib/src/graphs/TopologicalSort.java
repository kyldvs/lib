package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TopologicalSort {

	/**
	 * The dependency graph must be a directed acyclic graph or this algorithm
	 * will infinitely loop
	 * 
	 * @param deps
	 *            a map of nodes, to a list of their dependencies
	 * @return a sequential ordering of the nodes that satisfies all
	 *         dependencies
	 */
	public static List<Integer> run(Map<Integer, Set<Integer>> deps) {
		Map<Integer, Set<Integer>> reverse = new HashMap<>();

		// deps.keySet should have ALL of the vertices in the problem
		for (int i : deps.keySet()) {
			reverse.put(i, new HashSet<Integer>());
		}

		for (int i : deps.keySet()) {
			for (int j : deps.get(i)) {
				reverse.get(j).add(i);
			}
		}

		// find sources
		List<Integer> sources = new ArrayList<Integer>();
		for (int i : reverse.keySet()) {
			if (reverse.get(i).isEmpty()) {
				sources.add(i);
			}
		}

		// dfs from all sources
		Set<Integer> v = new HashSet<>();
		List<Integer> close = new ArrayList<>();
		LinkedList<Integer> stack = new LinkedList<>();

		for (int s : sources) {
			stack.add(s);
		}

		while (!stack.isEmpty()) {
			int top = stack.getLast();
			boolean child = false;
			for (int a : reverse.get(top)) {
				if (v.contains(a))
					continue;
				child = true;
				stack.add(a);
			}

			if (!child) {
				v.add(top);
				close.add(top);
				stack.removeLast();
			}
		}

		return close;
	}

}
