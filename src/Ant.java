import java.util.*;

public class Ant {
    public List<Node> visited;
    public double travelCost = 0;
    private AntOpt opt;

    public Ant(AntOpt opt) {
        this.opt = opt;
        this.visited = new ArrayList<>();
    }

    public void reset(Node start){
        // clear visited List and leave only the beginning node
        if (this.visited.size() > 0){
            this.visited.clear();
        }
        this.visited.add(start);

        // reset travel cost
        this.travelCost = 0;
    }

    public void move(Node endNode, boolean lastIter, int endIndex){
        Node last = this.visited.get(this.visited.size() - 1);
        List<Edge> edges = last.getEdges();
        List<Double> probabilities = new ArrayList<>();
        double probDen = 0.0;

        // calculate transition probability to each node
        for (int i = 0; i < edges.size(); i++) {
            Edge neighbour = edges.get(i);

            // choose from yet unvisited and non-end-nodes
            if(!this.visited.contains(neighbour.destinationNode()) && neighbour.destinationNode() != endNode) {
                double probNum = Math.pow(neighbour.getPheromone(), this.opt.beta) * Math.pow(1.0 / neighbour.getCost(), this.opt.beta);
                probDen += probNum;
                probabilities.add(probNum);
            }
            else {
                probabilities.add(0.0);
            }
        }

        // divide numerator/accumulated denominator
        for (int i = 0; i < probabilities.size(); i++) {
            double probNum = probabilities.get(i);
            if (probNum > 0.0)
                probabilities.set(i, probNum / probDen);
            else
                probabilities.set(i, 1.0E-10);          // close but not equal to zero
        }

        if (!lastIter) {
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
            edges.get(nextNodeIndex).sumDeltaTau += deltaTau;
            this.travelCost += edges.get(nextNodeIndex).getCost();      // increase iteration travel cost
        }
        else {
            // if we reached the end-node
            this.visited.add(endNode);
            double deltaTau = this.opt.Q / edges.get(endIndex).getCost();               // todo: get index of endNode within edges List
            edges.get(endIndex).sumDeltaTau += deltaTau;
            this.travelCost += edges.get(endIndex).getCost();
        }
    }
}