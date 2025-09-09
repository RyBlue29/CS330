import java.lang.reflect.Array;
import java.util.*;

public class LeastRed {
    //public LeastRed() {}

    public ArrayList<Boolean> leastRed(int n, int[][] edges) {
        List<List<Integer>> adjList = buildAdjList(n, edges);
        ArrayList<List<List<Integer>>> bipGraphs = bfs(adjList, n);
        if (bipGraphs.isEmpty()) {
            return null;
        }
        ArrayList<Boolean> ret = new ArrayList<>(Collections.nCopies(n, Boolean.FALSE));

        for (int i = 0; i < bipGraphs.size(); i++) {
            List<Integer> first = bipGraphs.get(i).get(0);
            List<Integer> second = bipGraphs.get(i).get(1);

            List<Integer> redSet = (first.size() <= second.size()) ? first : second;

            for (Integer v : redSet) {
                ret.set(v, Boolean.TRUE);
            }
        }

        //System.out.println(ret);
        return ret;
    }

    public List<List<Integer>> buildAdjList(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }
        return adjList;
    }

    public ArrayList<List<List<Integer>>> bfs(List<List<Integer>> adjList, int n) {
        Queue<Integer> queue = new ArrayDeque<>();
        HashSet<Integer> notSeen = new HashSet<>();
        int[] colorArr = new int[n];
        Arrays.fill(colorArr, -1);

        for (int i = 0; i < n; i++) notSeen.add(i);

        ArrayList<List<List<Integer>>> bipGraphs = new ArrayList<>();

        while (!notSeen.isEmpty()) {
            int start = notSeen.iterator().next();
            queue.add(start);
            colorArr[start] = 0;

            ArrayList<Integer> color0 = new ArrayList<>();
            ArrayList<Integer> color1 = new ArrayList<>();
            color0.add(start);

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                notSeen.remove(cur);

                for (int neighbor : adjList.get(cur)) {
                    if (colorArr[neighbor] == -1) {
                        colorArr[neighbor] = 1 - colorArr[cur];
                        queue.add(neighbor);

                        if (colorArr[neighbor] == 0) color0.add(neighbor);
                        else color1.add(neighbor);

                    } else if (colorArr[neighbor] == colorArr[cur]) {
                        return new ArrayList<>();
                    }
                }
            }

            List<List<Integer>> component = new ArrayList<>();
            component.add(color0);
            component.add(color1);
            bipGraphs.add(component);
        }

        return bipGraphs;
    }


    public static void main(String[] args) {
        int n = 11;
        int[][] edges = {
                {0, 6},
                {0, 8},
                {1, 7},
                {2, 7},
                {2, 8},
                {3, 9},
                {4, 9},
                {5, 9},
                {5, 10}
        };

        LeastRed solver = new LeastRed();
        ArrayList<Boolean> coloring = solver.leastRed(n, edges);

        if (coloring == null) {
            //System.out.println("No valid 2-coloring exists.");
        } else {
            for (int i = 0; i < coloring.size(); i++) {
                //System.out.println("Vertex " + i + ": " + (coloring.get(i) ? "RED" : "BLUE"));
            }
        }
    }
     

}
