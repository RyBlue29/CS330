import java.util.ArrayList;
import java.util.Arrays;

public class Bellman {

    public ArrayList<Integer> bellman(int n, int t, int[][] edges, double[] weights) {
        double[] distance = new double[n];
        int[] pred = new int[n];

        Arrays.fill(distance, Double.POSITIVE_INFINITY); //init infinity
        Arrays.fill(pred, -1); // all nodes to -1 pred to start (0 will alwys be -1)
        distance[0] = 0; // start node = 0 distance obv

        for (int i = 0; i < n - 1; i++) { //relax n-1
            for (int j = 0; j < edges.length; j++) {
                int u = edges[j][0];
                int v = edges[j][1];
                double weight = weights[j];

                if (distance[u] != Double.POSITIVE_INFINITY && distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    pred[v] = u;
                }
            }
        }

        //relax one more and check to see if there are any changes (indicates negative cycle)
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            double weight = weights[i];
            if (distance[u] != Double.POSITIVE_INFINITY && distance[u] + weight < distance[v]) {
                return null;
            }
        }

        //build path back to 0/s
        ArrayList<Integer> path = new ArrayList<>();
        int curr = t;
        while (curr != -1) {
            path.add(0, curr);
            curr = pred[curr];
        }
        return path;
    }

    /*
    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {
                {0, 1}, {0, 2}, {1, 2}, {1, 3}, {1, 4}, {3, 2}, {3, 1}, {4, 3}
        };
        double[] weights = {-1, 4, 3, 2, 2, 5, 1, -3};

        Bellman solver = new Bellman();
        ArrayList<Integer> path = solver.bellman(n, 4, edges, weights);

        if (path == null) {
            System.out.println("Negative cycle detected.");
        } else {
            System.out.println("Shortest path to target: " + path);
        }
       }
     */

}
