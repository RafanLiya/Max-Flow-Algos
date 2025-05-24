/**
 * Student Full Name: Liyakkathkhan Rafan
 * Student UoW ID: w2082100
 * Student IIT ID: 20220656
 * Module Code: 5SENG003C.2
 * Module Name: Algorithms: Theory, Design and Implementation
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class representing an edge in the flow network.
 */
class Edge {
    int to;
    int capacity;
    int flow;
    Edge reverse; // Reverse edge needed for residual graph

    public Edge(int to, int capacity) {
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int residualCapacity() {
        return capacity - flow;
    }

    public void addFlow(int bottleneck) {
        flow += bottleneck;
        reverse.flow -= bottleneck;
    }
}

/**
 * Main class for maximum flow computation.
 */
public class Main {
    private static List<Edge>[] graph;

    public static void main(String[] args) {
        final String fileName = "src/benchmarks/bridge_1.txt";
        //filename format: src/benchmarks/<filename>

        long startTime = System.currentTimeMillis();

        try {
            readGraphFromFile(fileName);

            long endReadingTime = System.currentTimeMillis();
            System.out.println("Graph reading from text file done.");
            System.out.println("Time taken to read graph: " + (endReadingTime - startTime) + " ms");

            final int source = 0;
            final int sink = graph.length - 1;

            long startSolvingTime = System.currentTimeMillis();
            int maxFlow = pushRelabel(source, sink);
            long endSolvingTime = System.currentTimeMillis();

            System.out.println("Max flow is " + maxFlow + ".");
            System.out.println("Time taken to find max flow: " + (endSolvingTime - startSolvingTime) + " ms");

        } catch (IOException e) {
            System.err.println("Error reading file '" + fileName + "': " + e.getMessage());
        }
    }

    /**
     * Reads a graph from an input file.
     */
    private static void readGraphFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int numNodes = Integer.parseInt(reader.readLine().trim());
            graph = new ArrayList[numNodes];

            for (int i = 0; i < numNodes; i++) {
                graph[i] = new ArrayList<>();
            }

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);
                    addEdge(from, to, capacity);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed line (not numbers): " + line);
                }
            }
        }
    }

    /**
     * Adds a directed edge and its reverse to the graph.
     */
    private static void addEdge(int from, int to, int capacity) {
        Edge forward = new Edge(to, capacity);
        Edge backward = new Edge(from, 0); // Reverse edge with 0 capacity initially

        forward.reverse = backward;
        backward.reverse = forward;

        graph[from].add(forward);
        graph[to].add(backward);
    }

    /**
     * Removes an edge from the graph (optional extra operation for abstraction compliance).
     */
    private static void removeEdge(int from, int to) {
        graph[from].removeIf(e -> e.to == to);
    }

    /**
     * Finds an edge from 'from' to 'to' (optional search operation).
     */
    private static Edge findEdge(int from, int to) {
        for (Edge edge : graph[from]) {
            if (edge.to == to) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Push-Relabel algorithm to compute maximum flow.
     */
    private static int pushRelabel(int source, int sink) {
        int n = graph.length;
        int[] height = new int[n];
        int[] excess = new int[n];
        Queue<Integer> queue = new ArrayDeque<>();

        // Pre-flow: Saturate all edges from source
        height[source] = n;
        for (Edge e : graph[source]) {
            e.flow = e.capacity;
            e.reverse.flow = -e.capacity;
            excess[e.to] = e.capacity;
            if (e.to != sink) {
                queue.add(e.to);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            boolean pushed = false;

            for (Edge e : graph[u]) {
                if (e.residualCapacity() > 0 && height[u] == height[e.to] + 1) {
                    int delta = Math.min(excess[u], e.residualCapacity());
                    e.addFlow(delta);
                    excess[u] -= delta;
                    excess[e.to] += delta;

                    System.out.println("Pushed " + delta + " units from " + u + " to " + e.to);

                    if (e.to != source && e.to != sink && excess[e.to] == delta) {
                        queue.add(e.to); // Newly active node
                    }

                    if (excess[u] == 0) {
                        pushed = true;
                        break;
                    }
                }
            }

            if (!pushed && excess[u] > 0) {
                // Relabel
                int minHeight = Integer.MAX_VALUE;
                for (Edge e : graph[u]) {
                    if (e.residualCapacity() > 0) {
                        minHeight = Math.min(minHeight, height[e.to]);
                    }
                }
                if (minHeight < Integer.MAX_VALUE) {
                    height[u] = minHeight + 1;
                    queue.add(u); // Try again after relabel
                    System.out.println("Relabeled node " + u + " to height " + height[u]);
                }
            }
        }

        int maxFlow = 0;
        for (Edge e : graph[source]) {
            maxFlow += e.flow;
        }

        return maxFlow;
    }
}
