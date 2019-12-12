import java.util.*;

public class Main {
    public static List<Node> init2(int nodesN){
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
        for (Node n : graph){
            for (int i = 0; i < nodesN; i++) {
                if (graph.get(i) != n)
                    n.addEdge(graph.get(i), random.nextInt(14) + 7, random.nextDouble() + 0.5);
            }
        }
        return graph;
    }

    public static void main(String[] args){
        List<Node> graph = init2(15);
        AntOpt opt = new AntOpt(graph,3, 0.3, 0.5, 0.5, 1, 1200);
        opt.run(graph.get(1), graph.get(0), 0);
    }

    //-----------------------------------------------------------------------------------------------------------------

    public static List<Node> initSymmetric(int nodesN){
        List<Node> graph = new ArrayList<>();
        Random random = new Random();
        Node node;
        int counter = 0;

        // create nodes
        char letter = 'A';
        for (int i = 0; i < nodesN; i++) {
            node = new Node(letter++);
            graph.add(node);
        }

        // fill them with edges, cost and initial pheromone are random
        for (Node n : graph){
            for (int i = 0; i < nodesN; i++) {
                if (graph.get(i) != n) {
                    if (i <= counter) {
                        Edge e = graph.get(i).getEdges().get(i);
                        n.addEdge(graph.get(i), e.getCost(), e.getPheromone());
                    }
                    else
                        n.addEdge(graph.get(i), random.nextInt(14) + 7, random.nextDouble());
                }
            }
            counter++;
        }
        return graph;
    }
}
