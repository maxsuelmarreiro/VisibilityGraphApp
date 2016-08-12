package menorcaminho;

import grafoapp.Vertice;
import java.awt.List;
import java.util.ArrayList;

public class WeightedGraph {

    private double[][] edges;  // adjacency matrix
    private String[] labels;

    public WeightedGraph(int n) {
        edges = new double[n][n];
        labels = new String[n];
    }

    public WeightedGraph() {

    }

    public int size() {
        return labels.length;
    }

    public void setLabel(int vertex, String label) {
        labels[vertex] = label;
    }

    public Object getLabel(int vertex) {
        return labels[vertex];
    }

    public void addEdge(int source, int target, double w) {
        edges[source][target] = w;
    }

    public boolean isEdge(int source, int target) {
        return edges[source][target] > 0;
    }

    public void removeEdge(int source, int target) {
        edges[source][target] = 0;
    }

    public double getWeight(int source, int target) {
        return edges[source][target];
    }

    public int[] neighbors(int vertex) {
        int count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) {
                count++;
            }
        }
        final int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) {
                answer[count++] = i;
            }
        }
        return answer;
    }

    public void print() {
        for (int j = 0; j < edges.length; j++) {
            System.out.print(labels[j] + ": ");
            for (int i = 0; i < edges[j].length; i++) {
                if (edges[j][i] > 0) {
                    System.out.print(labels[i] + ":" + edges[j][i] + " ");
                }
            }
            System.out.println();
        }
    }

    public String menorCaminho(grafoapp.Grafo grafo) {
        ArrayList<grafoapp.Vertice> listaVertices = grafo.getVertices();

        ArrayList<grafoapp.Aresta> listaArestas = grafo.getArestas();
        ArrayList<grafoapp.Aresta> listaArestasTemp = grafo.getArestasTemp();

        ArrayList<grafoapp.Aresta> listaArestasCompleto = new ArrayList<>();
        listaArestasCompleto.addAll(listaArestas);
        listaArestasCompleto.addAll(listaArestasTemp);

        final WeightedGraph t = new WeightedGraph(listaVertices.size());

        for (grafoapp.Vertice vertice : listaVertices) {
            t.setLabel(vertice.getId(), String.valueOf(vertice.getId()));
        }

        for (grafoapp.Aresta aresta : listaArestasCompleto) {
            System.out.println(aresta.getV1().getId() + " | " + aresta.getV2().getId() + " | " + aresta.getPeso());
            t.addEdge(aresta.getV1().getId(), aresta.getV2().getId(), aresta.getPeso());
            t.addEdge(aresta.getV2().getId(), aresta.getV1().getId(), aresta.getPeso());
        }

        final int[] pred = Dijkstra.dijkstra(t, 0);

        for (int n = 0; n < listaVertices.size(); n++) {
            Dijkstra.printPath(t, pred, 0, n);
        }

        return Dijkstra.printPath(t, pred, 0, 1).replaceAll("[\\[\\],]", "");
    }

//    public static void main(String args[]) {
//        final WeightedGraph t = new WeightedGraph(8);
//
//        A > B(1) > E(3) > F(13) | A --> F
//        int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6;
//
//        t.setLabel(0, "A");
//        t.setLabel(1, "B");
//        t.setLabel(2, "C");
//        t.setLabel(3, "D");
//        t.setLabel(4, "E");
//        t.setLabel(5, "F");
//        t.setLabel(6, "G");
//        t.setLabel(7, "G");
//        t.setLabel(8, "G");
//        t.setLabel(9, "G");
//        t.setLabel(10, "G");
//
//        t.addEdge(a, b, 1);
//        t.addEdge(b, d, 1);
//        t.addEdge(b, c, 2);
//        t.addEdge(b, e, 2);
//        t.addEdge(c, e, 14);
//        t.addEdge(d, e, 12);
//        t.addEdge(c, g, 1);
//        t.addEdge(g, f, 1);
//        t.addEdge(e, f, 10);
//        t.addEdge(e, a, 20);
//        t.print();
//
//        final int[] pred = Dijkstra.dijkstra(t, 0);
//
//        Dijkstra.printPath(t, pred, 0, 5);
//
//        for (int n = 0; n < 7; n++) {
//            Dijkstra.printPath(t, pred, 0, n);
//        }
//    }

}
