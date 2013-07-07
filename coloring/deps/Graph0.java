package deps;

import java.util.*;

/*************************************************************************
 *  Compilation:  javac Graph.java        
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges 
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges 
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15 
 *  1: 220 203 200 194 189 164 150 130 107 72 
 *  2: 141 110 108 86 79 51 42 18 14 
 *  ...
 *  
 *************************************************************************/

/**
 * The <tt>Graph</tt> class represents an undirected graph of vertices named 0
 * through V-1. It supports the following operations: add an edge to the graph,
 * iterate over all of the neighbors adjacent to a vertex. Parallel edges and
 * self-loops are permitted.
 * <p>
 * For additional documentation, see <a
 * href="http://algs4.cs.princeton.edu/51undirected">Section 5.1</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Graph0 {
	private final int V;
	private int E;
	private LinkedList<Integer>[] adj;

	/**
	 * Create an empty graph with V vertices.
	 * 
	 * @throws java.lang.IllegalArgumentException
	 *             if V < 0
	 */
	public Graph0(int V) {
		if (V < 0)
			throw new IllegalArgumentException(
					"Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = new LinkedList[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new LinkedList<Integer>();
		}
	}

	/**
	 * Return the number of vertices in the graph.
	 */
	public int V() {
		return V;
	}

	/**
	 * Return the number of edges in the graph.
	 */
	public int E() {
		return E;
	}

	/**
	 * Add the undirected edge v-w to graph.
	 * 
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless both 0 <= v < V and 0 <= w < V
	 */
	public void addEdge(int v, int w) {
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException();
		if (w < 0 || w >= V)
			throw new IndexOutOfBoundsException();
		E++;
		adj[v].add(w);
		adj[w].add(v);
	}

	/**
	 * Return the list of neighbors of vertex v as in Iterable.
	 * 
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless 0 <= v < V
	 */
	public LinkedList<Integer> adj(int v) {
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException();
		return adj[v];
	}

	/**
	 * Return a string representation of the graph.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (int w : adj[v]) {
				s.append(w + " ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	/**
	 * Test client.
	 */
	public static void main(String[] args) {
	}

}
