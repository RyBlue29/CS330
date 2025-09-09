import java.util.*;


public class StronglyConnected {

    static class Graph {
        int V; // number of vertices
        List<List<Integer>> adj;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
        }

        List<List<Integer>> reverseAdj(List<List<Integer>> adj, int V) {
            List<List<Integer>> res = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                res.add(new ArrayList<>());
            }
            for (int i = 0; i < adj.size(); i++) {
                for (int j = 0; j < adj.get(i).size(); j++) {
                    res.get(adj.get(i).get(j)).add(i);
                }
            }
            return res;
        };

        boolean isStronglyConnected() {
            return isStronglyConnectedHelper(this.adj) && isStronglyConnectedHelper(reverseAdj(this.adj, V));
        }

        boolean isStronglyConnectedHelper(List<List<Integer>> adjList) {
            Queue<Integer> q = new ArrayDeque<>();
            if (adj.isEmpty()) {
                return false;
            }
            else if (adjList.get(0).isEmpty()) {
                return true;
            }
            int chosen = adj.get(0).get(0); //add random
            q.add(chosen);
            HashSet<Integer> seen = new HashSet<>();
            while (!q.isEmpty()) {
                int u = q.poll();
                if (!seen.contains(u)) {
                    seen.add(u);
                    q.addAll(adj.get(u));
                }
            }
            return seen.size() == adj.size();
        }

        boolean isDag() {
            Queue<Integer> q = new ArrayDeque<>();
            if (adj.isEmpty()) {
                return true;
            }
            List<List<Integer>> rev = reverseAdj(adj, V);

            // indegree array
            int[] inDegree = new int[V];
            for (int i = 0; i < V; i++) {
                inDegree[i] = rev.get(i).size();
                if (inDegree[i] == 0) {
                    q.add(i);
                }
            }

            int visitedCount = 0;
            while (!q.isEmpty()) {
                int u = q.poll();
                visitedCount++;
                for (int v : adj.get(u)) {
                    inDegree[v]--;
                    if (inDegree[v] == 0) {
                        q.add(v);
                    }
                }
            }

            return visitedCount == V;
        }

    }

    public static void main(String[] args) {
        // Test 1: Strongly connected graph (a cycle 0->1->2->3->0)
        Graph g1 = new Graph(4);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        g1.addEdge(3, 0);
        System.out.println("Graph g1 strongly connected? " + g1.isStronglyConnected());
        // Expected: true

        // Test 2: Not strongly connected (chain 0->1->2 only)
        Graph g2 = new Graph(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        System.out.println("Graph g2 strongly connected? " + g2.isStronglyConnected());
        // Expected: false

        // Test 3: Strongly connected complete graph (each node connects to all others)
        Graph g3 = new Graph(3);
        g3.addEdge(0, 1);
        g3.addEdge(0, 2);
        g3.addEdge(1, 0);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(2, 1);
        System.out.println("Graph g3 strongly connected? " + g3.isStronglyConnected());
        // Expected: true
        // === Edge Case Tests ===

        // 1. Single Vertex
        Graph g4 = new Graph(1);
        System.out.println("Single node graph: " + g4.isStronglyConnected());
        // Expected: true

        // 2. Two Vertices, One Direction
        Graph g5 = new Graph(2);
        g5.addEdge(0, 1);
        System.out.println("One edge 0→1: " + g5.isStronglyConnected());
        // Expected: false

        // 3. Two Vertices, Both Directions
        Graph g6 = new Graph(2);
        g6.addEdge(0, 1);
        g6.addEdge(1, 0);
        System.out.println("Edges 0→1 and 1→0: " + g6.isStronglyConnected());
        // Expected: true

        // 4. Disconnected Vertex
        Graph g7 = new Graph(3);
        g7.addEdge(0, 1);
        g7.addEdge(1, 0);
        // Node 2 isolated
        System.out.println("Disconnected node: " + g7.isStronglyConnected());
        // Expected: false

        // 5. Cycle of 3 Nodes
        Graph g8 = new Graph(3);
        g8.addEdge(0, 1);
        g8.addEdge(1, 2);
        g8.addEdge(2, 0);
        System.out.println("Cycle of 3: " + g8.isStronglyConnected());
        // Expected: true

        // 6. Chain of 3 Nodes
        Graph g9 = new Graph(3);
        g9.addEdge(0, 1);
        g9.addEdge(1, 2);
        System.out.println("Chain 0→1→2: " + g9.isStronglyConnected());
        // Expected: false

        // 7. Complete Graph of 4 Nodes
        Graph g10 = new Graph(4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j) g10.addEdge(i, j);
            }
        }
        System.out.println("Complete digraph: " + g10.isStronglyConnected());
        // Expected: true

        // === DAG Test Cases ===

        // 1. Empty graph (no vertices)
        Graph dag1 = new Graph(0);
        System.out.println("Empty graph is DAG? " + dag1.isDag());
        // Expected: true (trivially no cycles)

        // 2. Single vertex
        Graph dag2 = new Graph(1);
        System.out.println("Single node graph is DAG? " + dag2.isDag());
        // Expected: true

        // 3. Simple chain 0→1→2 (no cycles)
        Graph dag3 = new Graph(3);
        dag3.addEdge(0, 1);
        dag3.addEdge(1, 2);
        System.out.println("Chain 0→1→2 is DAG? " + dag3.isDag());
        // Expected: true

        // 4. Simple cycle 0→1→0
        Graph dag4 = new Graph(2);
        dag4.addEdge(0, 1);
        dag4.addEdge(1, 0);
        System.out.println("Cycle 0↔1 is DAG? " + dag4.isDag());
        // Expected: false

        // 5. Longer cycle 0→1→2→0
        Graph dag5 = new Graph(3);
        dag5.addEdge(0, 1);
        dag5.addEdge(1, 2);
        dag5.addEdge(2, 0);
        System.out.println("Cycle of 3 is DAG? " + dag5.isDag());
        // Expected: false

        // 6. Branching DAG 0→1, 0→2, 1→3, 2→3
        Graph dag6 = new Graph(4);
        dag6.addEdge(0, 1);
        dag6.addEdge(0, 2);
        dag6.addEdge(1, 3);
        dag6.addEdge(2, 3);
        System.out.println("Branching DAG is DAG? " + dag6.isDag());
        // Expected: true

        // 7. Complete digraph with cycle (4 nodes, edges both ways)
        Graph dag7 = new Graph(4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j) dag7.addEdge(i, j);
            }
        }
        System.out.println("Complete digraph (4 nodes) is DAG? " + dag7.isDag());
        // Expected: false

    }
}
