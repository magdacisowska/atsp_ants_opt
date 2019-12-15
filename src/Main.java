import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import java.util.*;

public class Main {
    public static void showSolution(List<Node> graph){
        Collections.swap(graph, 0, 3);
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
        System.out.println(solution + " cost:" + globalCost);
    }

    public static void plot(List<Integer> hist){
        List<Integer> history = hist;
        int[][] l = new int[history.size()][1];
        for (int i = 0; i < history.size(); i++) {
            l[i][0] = history.get(i);
        }
        JavaPlot p = new JavaPlot();
        p.addPlot(l);
        PlotStyle s = ((AbstractPlot) p.getPlots().get(0)).getPlotStyle();
        s.setStyle(Style.LINES);
        p.plot();
    }

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
        int cost = 0;
        for (Node n : graph){
            for (int i = 0; i < nodesN; i++) {
                if (graph.get(i) != n) {
                    cost = random.nextInt(13) + 7;
                    n.addEdge(graph.get(i), cost, 1.0 / (nodesN * cost));
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        List<Node> graph = init2(15);
        AntOpt opt = new AntOpt(graph,10, 0.1, 0.1, 2.0, 1, 100);
        opt.run(graph.get(3), graph.get(0), 0);
        showSolution(opt.graph);

        plot(opt.history);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Node> init(){
        List<Node> graph = new ArrayList<>();

        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');
        Node e = new Node('E');
        Node f = new Node('F');
        Node g = new Node('G');
        Node h = new Node('H');
        Node i = new Node('I');
        Node j = new Node('J');
        Node k = new Node('K');
        Node l = new Node('L');
        Node m = new Node('M');
        Node n = new Node('N');
        Node o = new Node('O');
        Node p = new Node('P');
        Node r = new Node('R');

        a.addEdge(k, 10, 0.5);
        a.addEdge(g, 10, 0.5);
        a.addEdge(b, 10, 0.5);

        b.addEdge(l, 10, 0.5);
        b.addEdge(d, 10, 0.5);
        b.addEdge(c, 12, 0.5);
        b.addEdge(g, 13, 0.5);
        b.addEdge(a, 10, 0.5);

        c.addEdge(e,12, 0.5);
        c.addEdge(b,12, 0.5);
        c.addEdge(g,10, 0.5);
        c.addEdge(h, 11, 0.5);
        c.addEdge(d, 10, 0.5);//

        d.addEdge(b, 10, 0.5);
        d.addEdge(e, 11, 0.5);
        d.addEdge(c, 11, 0.5);//
        d.addEdge(f, 11, 0.5);//

        e.addEdge(d, 10, 0.5);
        e.addEdge(c, 11, 0.5);
        e.addEdge(f, 9, 0.5);

        f.addEdge(e,10, 0.5);
        f.addEdge(l,10,0.5);
        f.addEdge(n,9,0.5);
        f.addEdge(d,13, 0.5);//

        g.addEdge(c,10, 0.5);
        g.addEdge(b,11, 0.5);
        g.addEdge(a,12,0.5);
        g.addEdge(k,10,0.5);
        g.addEdge(j,13,0.5);
        g.addEdge(h,12,0.5);

        h.addEdge(p,10,0.5);
        h.addEdge(i,10,0.5);
        h.addEdge(g,13,0.5);
        h.addEdge(c,12,0.5);

        i.addEdge(p,11,0.5);
        i.addEdge(o,10,0.5);
        i.addEdge(h,9,0.5);
        i.addEdge(j,10,0.5);
        i.addEdge(m,12,0.5);

        j.addEdge(i,9,0.5);
        j.addEdge(g,11,0.5);
        j.addEdge(k,12,0.5);
        j.addEdge(m,10,0.5);

        k.addEdge(j,12,0.5);
        k.addEdge(g,11,0.5);
        k.addEdge(a,9,0.5);
        k.addEdge(l,11,0.5);
        k.addEdge(n,9,0.5);

        l.addEdge(b,10,0.5);
        l.addEdge(f,11,0.5);
        l.addEdge(k,12,0.5);

        m.addEdge(r,10,0.5);
        m.addEdge(i,11,0.5);
        m.addEdge(j,10,0.5);
        m.addEdge(n,10,0.5);

        n.addEdge(r,11,0.5);
        n.addEdge(m,10,0.5);
        n.addEdge(k,12,0.5);
        n.addEdge(f,9,0.5);

        o.addEdge(p,9,0.5);
        o.addEdge(i,10,0.5);

        p.addEdge(h,11,0.5);
        p.addEdge(o,10,0.5);
        p.addEdge(i,11,0.5);
        p.addEdge(r,12,0.5);

        r.addEdge(m,9,0.5);
        r.addEdge(n,11,0.5);
        r.addEdge(p,10,0.5);

        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(e);
        graph.add(f);
        graph.add(g);
        graph.add(h);
        graph.add(i);
        graph.add(j);
        graph.add(k);
        graph.add(l);
        graph.add(m);
        graph.add(n);
        graph.add(o);
        graph.add(p);
        graph.add(r);

        return graph;
    }
    //-----------------------------------------------------------------------------------------------------------------

    public static List<Node> init3(){
        List<Node> graph = new ArrayList<>();

        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');
        Node e = new Node('E');
        Node f = new Node('F');

        a.addEdge(b, 10, 1.0/(60));
        a.addEdge(c, 11, 1.0/(66));
        a.addEdge(d, 12, 1.0/(72));
        a.addEdge(e, 9, 1.0/(54));
        a.addEdge(f, 9, 1.0/(54));

        b.addEdge(a, 12, 1.0/(72));
        b.addEdge(c, 9, 1.0/(54));
        b.addEdge(d, 10, 1.0/(60));
        b.addEdge(e, 12, 1.0/(72));
        b.addEdge(f, 9, 1.0/(54));

        c.addEdge(a, 9, 1.0/(54));
        c.addEdge(b, 11, 1.0/(66));
        c.addEdge(d, 9, 1.0/(54));
        c.addEdge(e, 11, 1.0/(66));
        c.addEdge(f, 10, 1.0/(60));

        d.addEdge(a, 12, 1.0/(72));
        d.addEdge(b, 12, 1.0/(72));
        d.addEdge(c, 10, 1.0/(60));
        d.addEdge(e, 9, 1.0/(54));
        d.addEdge(f, 11, 1.0/(66));

        e.addEdge(a, 10, 1.0/(60));
        e.addEdge(b, 10, 1.0/(60));
        e.addEdge(c, 9, 1.0/(54));
        e.addEdge(d, 12, 1.0/(72));
        e.addEdge(f, 10, 1.0/(60));

        f.addEdge(a, 11, 1.0/(66));
        f.addEdge(b, 12, 1.0/(72));
        f.addEdge(c, 11, 1.0/(66));
        f.addEdge(d, 9, 1.0/(54));
        f.addEdge(e, 10, 1.0/(60));

        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(e);
        graph.add(f);

        return graph;
    }

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
