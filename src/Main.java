import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import java.util.*;

public class Main {
    public static void showSolution(List<Node> graph, int start){
        Collections.swap(graph, 0, start);
        List<Node> solution = new ArrayList<>();
        solution.add(graph.get(0));
        int cost = 0;
        int globalCost = 0;
        Node node = graph.get(0);

        for (int i = 0; i < graph.size() - 1; i++){
            Node bestNode = null;
            double bestPheromone = 0.0;
            for (Edge edge : node.getEdges()){
                if (edge.getPheromone() > bestPheromone && !solution.contains(edge.destinationNode())){
                    bestPheromone = edge.getPheromone();
                    bestNode = edge.destinationNode();
                    node = edge.destinationNode();
                    cost = edge.getCost();
                }
            }
            solution.add(bestNode);
            globalCost += cost;
        }
        System.out.println(solution + " cost:" + globalCost);                   // todo: sometimes doesnt show last as last
    }

    public static void plot(List<Integer> hist){
        List<Integer> history = hist;

        int[][] data = new int[history.size()][1];
        for (int i = 0; i < history.size(); i++) {
            data[i][0] = history.get(i);
        }

        JavaPlot p = new JavaPlot();
        p.addPlot(data);
        p.setTitle("Mean score per iteration", "Arial", 12);
        p.set("xlabel", "'iteration'");
        p.set("ylabel", "'mean score'");
        p.set("key", "off");

        PlotStyle s = ((AbstractPlot) p.getPlots().get(0)).getPlotStyle();
        s.setStyle(Style.LINES);

        p.plot();
    }

    public static void buildGraph(List<Node> graph){
        for (Node n : graph){
            for (Edge e : n.getEdges()){
                System.out.println(n.toString() + " -> " + e.destinationNode().toString() + " [ label = \"" + e.getCost() + "\" ];");
            }
        }
    }

    public static List<Node> init(int nodesN, int max, int min){
        List<Node> graph = new ArrayList<>();
        Random random = new Random();
        Node node;

        // create nodes
        char letter = 'A';
        for (int i = 0; i < nodesN; i++) {
            node = new Node(letter++);
            graph.add(node);
        }

        // fill them with edges; cost and initial pheromone are random
        int cost = 0;
        for (Node n : graph){
            for (int i = 0; i < nodesN; i++) {
                if (graph.get(i) != n) {
                    cost = random.nextInt(max + min - 1) + min;
                    n.addEdge(graph.get(i), cost, 1.0 / (nodesN * cost));
                }
            }
        }
        return graph;
    }

    // ---------------------------------------- main ------------------------------------------------------------------
    public static void main(String[] args){
        List<Node> graph = init(45, 13, 7);
//        AntOpt opt = new AntOpt(graph,20, 0.8, 1.0, 0.5, 1.0, 20, "AS");
        AntOpt opt = new AntOpt(graph,20, 0.1, 1.0, 0.5, 0.1, 300, "MMAS");

        opt.run(graph.get(3), graph.get(0));
        showSolution(opt.graph, 3);

        // print graph data in format suitable for http://www.webgraphviz.com/
//        buildGraph(opt.graph);

        plot(opt.history);
    }
}
