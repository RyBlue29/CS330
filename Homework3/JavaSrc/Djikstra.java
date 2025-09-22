import java.util.*;

public class Djikstra {

    public static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    public static int[] dijkstra(Map<Integer, List<Edge>> graph, int source) {
        int[] ret = new int[graph.size()];
        Arrays.fill(ret, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.add(new int[]{source, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            if (ret[current[0]] < current[1]) {
                continue;
            }
            int currentWeight = current[1];
            ret[current[0]] = currentWeight;
            for (Edge neighbour : graph.get(current[0])) {
                int newDist = currentWeight + neighbour.weight;
                if (newDist < ret[neighbour.target]) {
                    ret[neighbour.target] = newDist;
                    pq.add(new int[]{neighbour.target, newDist});
                }
            }
        }
        return ret;
    }


    public static void main(String[] args) {
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            graph.put(i, new ArrayList<>());
        }

        // add edges
        graph.get(0).add(new Edge(1, 4));
        graph.get(0).add(new Edge(2, 1));
        graph.get(2).add(new Edge(1, 2));
        graph.get(1).add(new Edge(3, 1));
        graph.get(2).add(new Edge(3, 5));

        // run dijkstra from source = 0
        int source = 0;
        int[] dist = dijkstra(graph, source);

        // print results
        System.out.println("Shortest distances from source " + source + ":");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("To " + i + " = " + dist[i]);
        }
    }

}
