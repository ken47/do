import java.io.*;
import java.util.*;
import deps.*;

public class GraphColoring {
	int vertCount;
	int edgeCount;
	Graph0 graph;
	Integer adj[];
	Integer colorings[];

	int degrees[];
	int sortedDegrees[];

	// an array of the original indices of sortedDegrees
	Integer sortedIndices[];

	// key: degree, value: ordering
	HashMap<Integer, Integer> ordering = new HashMap<Integer, Integer>();

	public static void main(String[] args) {
		try {
			GraphColoring gc = new GraphColoring(args[0]);
//			GraphColoring gc = new GraphColoring("data/gc_4_1");
			int chromaticNum = gc.findChromatic();
			System.out.println(chromaticNum + " " + 1);
			for(int i = 0; i < gc.colorings.length; i++ ) {
				System.out.print(gc.colorings[i] + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GraphColoring(String[] args) throws IOException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null)
			return;

		// read the lines out of the file
		construct(fileName);
	}

	public GraphColoring(String filename) throws IOException {
		construct(filename);
	}

	public void construct(String filename) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader input = new BufferedReader(new FileReader(filename));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}
		
		String settings = lines.get(0);
		String[] parts = settings.split("\\s+");
		vertCount = Integer.parseInt(parts[0]);
		edgeCount = Integer.parseInt(parts[1]);
		graph = new Graph0(vertCount);
		colorings = new Integer[vertCount];

		// StdOut.println("vertCount " + vertCount);
		for (int i = 1; i < vertCount; i++) {
			String edge = lines.get(i);
			String[] vertices = edge.split("\\s+");
			int from = Integer.parseInt(vertices[0]);
			int to = Integer.parseInt(vertices[1]);
			// StdOut.println("addEdge " + from + ", " + to);
			graph.addEdge(from, to);
		}

		preprocess();
	}

	/**
	 * processes vertices s.t. one can easily iterate from the vtx w the largest
	 * to the smallest degree
	 */
	public void preprocess() {
		degrees = new int[vertCount];
		sortedDegrees = new int[vertCount];
		sortedIndices = new Integer[vertCount];
		for (int i = 0; i < vertCount; i++) {
			LinkedList<Integer> list = graph.adj(i);
			sortedDegrees[i] = degrees[i] = list.size();
		}
		Arrays.sort(sortedDegrees);
		for (int i = 0; i < vertCount; i++) {
			int degree = sortedDegrees[i];
			for (int j = 0; j < vertCount; j++) {
				if (degrees[j] == degree
						&& Arrays.asList(sortedIndices).indexOf(j) < 0) {
					sortedIndices[i] = j;
					break;
				}
			}
		}
		// StdOut.println("sorted indices");
		// StdOut.println(Arrays.toString(degrees));
		// StdOut.println(Arrays.toString(sortedDegrees));
		// StdOut.println(Arrays.toString(sortedIndices));
	}

	public int findChromatic() {
		int colorIndex = 0;
		int count = vertCount;
		int coloringsAssigned = 0;
		// holds onto the adjacent vertices
		// of the vertex in question
		adj = new Integer[vertCount];
		while (coloringsAssigned < vertCount && count > 0) {
			Arrays.fill(adj, -1);
			Integer vtx = sortedIndices[--count];
			// StdOut.println("vtx " + vtx);
			if (colorings[vtx] == null) {
				coloringsAssigned++;
				colorings[vtx] = colorIndex;
				// StdOut.println("assigning " + colorings[vtx] + " to " + vtx);

			}
			Iterator<Integer> iter = graph.adj(vtx).iterator();

			int tmp = 0;
			while (iter.hasNext()) {
				adj[tmp++] = iter.next();
			}

			// StdOut.println("adj " + Arrays.toString(adj));

			for (int i = 0; i < vertCount; i++) {
				// if i isn't adjacent to vtx, and it hasn't been assigned a
				// color,
				// then give it the same color
				// StdOut.println("");
				// StdOut.println("considering " + i);
				// StdOut.println(colorings[i] == null);
				// StdOut.println(Arrays.asList(adj).indexOf(i));
				if (colorings[i] == null && Arrays.asList(adj).indexOf(i) < 0) {
					coloringsAssigned++;
					colorings[i] = colorIndex;
					// StdOut.println("assigning " + colorings[i] + " to " + i);
					// StdOut.println("colorings " +
					// Arrays.toString(colorings));
				}
			}

			colorIndex++;
			// if there are any undefined colors, keep running loop
		}
		// StdOut.println("chromaticNum " + colorIndex);
		// StdOut.println("colorings " + Arrays.toString(colorings));
		return colorIndex;
	}
}
