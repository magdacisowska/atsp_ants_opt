import java.util.ArrayList;
import java.util.List;

public class Node {
    private char name;
    private List<Edge> edges = new ArrayList<>();

    public Node(char nodeName){
        this.name = nodeName;
    }

    public void addEdge(Node to, int cost, double pheromone){
        edges.add(new Edge(to, cost, pheromone));
    }

    public List<Edge> getEdges(){
        return edges;
    }

    public Edge connects(Node node){
        for (Edge edge : this.getEdges()){
            if (edge.destinationNode() == node)
                return edge;
        }
        return null;
    }

    @Override
    public String toString() {
        return Character.toString(this.name);
    }
}
