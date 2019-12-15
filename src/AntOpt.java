import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AntOpt {
    public List<Node> graph;
    private List<Ant> ants;
    private final int m, iters;
    private final double evaporate;
    public final double Q, alpha, beta;
    public List<Integer> history = new ArrayList<>();

    public AntOpt(List<Node> graph, int antsN, double evaporate, double alpha, double beta, double Q, int iters){
        this.graph = graph;
        this.ants = new ArrayList<>();
        this.m = antsN;
        this.iters = iters;
        this.evaporate = evaporate;
        this.Q = Q;
        this.alpha = alpha;
        this.beta = beta;
    }

    public void createAnts(){
        for (int i = 0; i < this.m; i++) {
            this.ants.add(new Ant(this));
        }
    }

    public void modifyPheromone(){
        for (Node node : this.graph){
            for (Edge edge : node.getEdges()){
                double evaporated = edge.getPheromone() * (1 - this.evaporate);
                edge.setPheromone(evaporated + edge.sumDeltaTau);
                edge.sumDeltaTau = 0.0;
            }
        }
    }

    public void run(Node start, Node end, int endIndex){
        double meanCost = 0;

        //create ants population
        createAnts();

        //begin iterations
        for (int iter = 0; iter < this.iters; iter++) {
            // reset ants
            for (int i = 0; i < this.m; i++) {
                this.ants.get(i).reset(start);
            }

            // move ants through the graph within one iteration
            for (int i = 0; i < this.m; i++) {
                int graphIter = 0;

                // let one go through the whole graph
                while (graphIter < graph.size() - 2){
                    this.ants.get(i).move(end, false);
                    graphIter++;
                }
                // make final move to the end node
                this.ants.get(i).move(end, true);

                System.out.println("iteration " + (iter + 1) + ", ant " + (i + 1) + ", nodes visited:" + this.ants.get(i).visited + ", travel cost:" + this.ants.get(i).travelCost);
                meanCost += this.ants.get(i).travelCost;
            }
            // after iteration passes
            modifyPheromone();
            meanCost /= this.m;
            this.history.add((int)meanCost);
            System.out.println("---------- mean cost:" + meanCost + " ------------");
            meanCost = 0;
        }
    }
}
