import java.io.InputStream;
import java.util.*;


public class DijkstrasAlgorithm {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("What file do you want to read? ");
        String filename = scan.nextLine();
        processFile(filename);
    }

    public static void dijkstra(Graph g, String start, String finish)
    {
        Map<String, Integer> dist = new HashMap<String, Integer>();
        Map<String, String> prev = new HashMap<String, String>();
        PriQueue<String, Integer> pq = new PriQueue<String, Integer>(true);

        // Your Dijkstra's algorithm code here.
        String[] vertices = g.getVertices().toArray(new String[0]);

        for (String vertex : vertices) {
            dist.put(vertex, 999999);
            prev.put(vertex, null);
        }

        dist.put(start, 0);
        pq.add(start, 0);
        while (!pq.isEmpty()) {
            String u = pq.remove();
            System.out.println("Visting vertex " + u);
            if (u.equals(finish)){
                break;
            }
            Edge[] neighbors = g.getEdgesFrom(u).toArray(new Edge[0]);
            for (Edge e : neighbors) {
                int alt = dist.get(u) + e.getWeight();
                String v = e.getVertex2();
                if (alt < dist.get(v)) {
                    System.out.println("    Updating dist[" + v + "] from " + dist.get(v) + " to " + alt);
                    dist.put(v, alt);
                    prev.put(v, u);
                    if (pq.contains(v)){
                        pq.remove(v);
                    }
                    pq.add(v, alt);
                }
            }
            System.out.println();
        }
        System.out.println();
        int finalDist = dist.get(finish);
        String temp = finish;
        String finalPath = finish;
        while (prev.get(temp) != null) {
            temp = prev.get(temp);
            finalPath = finalPath.concat(temp);
        }
        System.out.print("Shortest path is: ");
        char[] chars = finalPath.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            System.out.print(chars[i] + " ");
        }
        System.out.println();
        System.out.println("Distance is: " + finalDist);
        System.out.println();
        System.out.println("Final dist map is:");
        for (String v : vertices) {
            System.out.println(v + ": " + dist.get(v));
        }
        System.out.println();
        System.out.println("Final prev map is:");
        for (String v : vertices) {
            System.out.println(v + ": " + prev.get(v));
        }

    }

    /**
     * Read the file specified to add proper items to the word frequencies.
     */
    private static void processFile(String filename)
    {
        InputStream is = DijkstrasAlgorithm.class.getResourceAsStream(filename);
        if (is == null) {
            System.err.println("Bad filename: " + filename);
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        // Make a blank graph.
        Graph g = new Graph();
        String dir = scan.nextLine();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split(" ");

            if(words[0].equals("vertex")){
                g.addVertex(words[1]);
            }
            else if(words[0].equals("edge")){
                if(dir.equals("directed")){
                    g.addEdge(words[1], words[2], Integer.parseInt(words[3]));
                }
                else if(dir.equals("undirected")){
                    g.addEdge(words[1], words[2], Integer.parseInt(words[3]));
                    g.addEdge(words[2], words[1], Integer.parseInt(words[3]));
                }
            }
            else if(words[0].equals("dijkstra")){
                dijkstra(g, words[1], words[2]);
            }

        }
        //System.out.println(g);
        scan.close();
    }
}
