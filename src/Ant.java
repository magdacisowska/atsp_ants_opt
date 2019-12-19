import java.util.*;

public class Ant {
    public List<Node> visited;
    public double travelCost = 0;
    private AntOpt opt;
    List<Double> taus = new ArrayList<>();

    public Ant(AntOpt opt) {
        this.opt = opt;
        this.visited = new ArrayList<>();
    }

    public void reset(Node start){
        // clear visited List and leave only the beginning node
        if (this.visited.size() > 0){
            this.visited.clear();
        }
        Random random = new Random();
        Node startNode = this.opt.graph.get(random.nextInt(this.opt.graph.size()));
        this.visited.add(startNode);

        // reset travel cost
        this.travelCost = 0;
        this.taus.clear();
    }

    public void move(Node endNode, boolean lastIter){
        Node last = this.visited.get(this.visited.size() - 1);
        List<Edge> edges = last.getEdges();
        List<Double> probabilities = new ArrayList<>();
        double denominator = 0.0;

        // calculate transition probability to each node
        for (int i = 0; i < edges.size(); i++) {
            Edge neighbour = edges.get(i);

            // choose from yet unvisited and non-end-nodes or allow it to be end-node if lastIter is set
            if((!this.visited.contains(neighbour.destinationNode()) /*&& neighbour.destinationNode() != endNode) || (lastIter && neighbour.destinationNode() == endNode*/)) {
                double numerator = Math.pow(neighbour.getPheromone(), this.opt.beta) * Math.pow(1.0 / neighbour.getCost(), this.opt.beta);
                denominator += numerator;
                probabilities.add(numerator);
            }
            else {
                probabilities.add(0.0);
            }
        }

        // divide numerator/accumulated denominator
        for (int i = 0; i < probabilities.size(); i++) {
            double numerator = probabilities.get(i);
            if (numerator > 0.0)
                probabilities.set(i, numerator / denominator);
            else
                probabilities.set(i, 1.0E-10);          // close but not equal to zero - useful for later mapping
        }

        // map probability densities
        TreeMap<Double, Integer> map = new TreeMap<>();
        double density = 0.0;
        for (int i = 0; i < probabilities.size(); i++) {
            density += probabilities.get(i);
            map.put(density, i);
        }

        // choose next node randomly with distributions
        double random = (new Random()).nextDouble();
        int nextNodeIndex = map.ceilingEntry(random).getValue();
        this.visited.add(edges.get(nextNodeIndex).destinationNode());

        // leave certain value of pheromone on the chosen edge
        double deltaTau = this.opt.Q / edges.get(nextNodeIndex).getCost();
        edges.get(nextNodeIndex).sumDeltaTau += deltaTau;           // add it to iteration-accumulating pheromone on the edge (AS)
        this.taus.add(deltaTau);                                    // track pheromone history to further use pheromone updates of the best ant only (MMAS)
        this.travelCost += edges.get(nextNodeIndex).getCost();      // increase iteration travel cost
    }
}
