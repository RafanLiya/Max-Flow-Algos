# Algorithms for Max Flow Calculation

This repository contains implementations of several classical algorithms used to compute the **maximum flow** in a flow network. These algorithms are foundational in graph theory and have applications in areas like network routing, bipartite matching, project selection, and more.

## 📌 Project Scope

This project is focused on comparing and analyzing the following maximum flow algorithms:

- **Ford-Fulkerson Algorithm**
- **Edmonds-Karp Algorithm**
- **Dinic’s Algorithm**
- **Push-Relabel (Preflow-Push) Algorithm**

Each algorithm is implemented with clarity and efficiency in mind, and includes test cases to demonstrate correctness and performance.

---

## 🔧 Algorithms To Be Implemented

### 1. Ford-Fulkerson Algorithm
- A greedy approach to solving the max flow problem.
- Works by augmenting flow along paths from source to sink as long as possible.
- May not terminate for irrational capacities.
- Typically implemented with DFS for finding augmenting paths.

### 2. Edmonds-Karp Algorithm
- A specialization of Ford-Fulkerson that uses **Breadth-First Search (BFS)** to find the shortest augmenting path.
- Guarantees polynomial time: **O(VE²)**.
- More reliable and predictable than basic Ford-Fulkerson.

### 3. Dinic’s Algorithm
- Utilizes **level graphs** and **blocking flows** to achieve improved performance.
- Time complexity: **O(V²E)** in general, **O(E√V)** for unit capacity networks.
- Highly efficient for large and dense graphs.

### 4. Push-Relabel Algorithm (Preflow-Push) \[Completed\]
- Maintains a **preflow** and pushes excess flow locally.
- Uses height labeling to guide the flow.
- Time complexity: **O(V²E)** (improved versions can achieve **O(V²√E)**).
- Performs well in practice on dense graphs.

---

## 🚀 Coming Soon

- Visualization tools to demonstrate algorithm behavior step-by-step.
- GUI to interact with graph inputs and observe flow computation.
