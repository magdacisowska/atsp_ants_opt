class Edge {
    private Node t;                         // node on the other side of the edge
    private int c;                          // cost of the edge
    private double p;                       // pheromone value
    public double sumDeltaTau = 0;          // is reset after each iteration

    public Edge(Node to, int cost, double pheromone){
        this.t = to;
        this.c = cost;
        this.p = pheromone;
    }

    public int getCost(){
        return this.c;
    }

    public double getPheromone(){
        return this.p;
    }

    public void setPheromone(double pheromone){
        this.p += pheromone;
    }

    public Node destinationNode() {
        return this.t;
    }

    @Override
    public String toString() {
        return "to:"+this.t+" c="+this.c+", p="+this.p;
//        return this.t.toString();
    }
}