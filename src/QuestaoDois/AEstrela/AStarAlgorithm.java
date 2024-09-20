package QuestaoDois.AEstrela;
import java.util.*;

public class AStarAlgorithm {
    static class Node {
        String name;
        double heuristic; // Heurística para o nó
        List<Edge> edges; // Arestas conectadas a este nó

        Node(String name, double heuristic) {
            this.name = name;
            this.heuristic = heuristic;
            this.edges = new ArrayList<>();
        }
    }

    static class Edge {
        Node target;
        double weight; // Peso da aresta

        Edge(Node target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    private List<Node> nodes;

    public AStarAlgorithm() {
        nodes = new ArrayList<>();
    }

    public void addNode(String name, double heuristic) {
        nodes.add(new Node(name, heuristic));
    }

    public void addEdge(String from, String to, double weight) {
        Node fromNode = findNode(from);
        Node toNode = findNode(to);
        if (fromNode != null && toNode != null) {
            fromNode.edges.add(new Edge(toNode, weight));
        }
    }

    private Node findNode(String name) {
        for (Node node : nodes) {
            if (node.name.equals(name)) {
                return node;
            }
        }
        return null;
    }

    public List<String> aStar(String startName, String goalName) {
        Node startNode = findNode(startName);
        Node goalNode = findNode(goalName);

        if (startNode == null || goalNode == null) {
            return Collections.emptyList();
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.heuristic));
        Map<Node, Double> gScores = new HashMap<>();
        Map<Node, Node> cameFrom = new HashMap<>();

        gScores.put(startNode, 0.0);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goalNode)) {
                return reconstructPath(cameFrom, current);
            }

            for (Edge edge : current.edges) {
                double tentativeGScore = gScores.getOrDefault(current, Double.POSITIVE_INFINITY) + edge.weight;

                if (tentativeGScore < gScores.getOrDefault(edge.target, Double.POSITIVE_INFINITY)) {
                    cameFrom.put(edge.target, current);
                    gScores.put(edge.target, tentativeGScore);
                    double fScore = tentativeGScore + edge.target.heuristic; // f(n) = g(n) + h(n)
                    edge.target.heuristic = fScore;
                    openSet.add(edge.target);
                }
            }
        }

        return Collections.emptyList(); // Caminho não encontrado
    }

    private List<String> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<String> totalPath = new ArrayList<>();
        while (current != null) {
            totalPath.add(current.name);
            current = cameFrom.get(current);
        }
        Collections.reverse(totalPath);
        return totalPath;
    }

    public static void main(String[] args) {
        AStarAlgorithm aStar = new AStarAlgorithm();
        aStar.addNode("A", 3);
        aStar.addNode("B", 6);
        aStar.addNode("C", 4);
        aStar.addNode("D", 3);
        aStar.addNode("G", 0);

//        aStar.addNode("A", 3);
//        aStar.addNode("B", 3);
//        aStar.addNode("C", 0);
//        aStar.addNode("D", 1);
//        aStar.addNode("G", 0);

        aStar.addEdge("A", "B", 1);
        aStar.addEdge("A", "D", 3);
        aStar.addEdge("B", "A", 2);
        aStar.addEdge("B", "D", 4);
        aStar.addEdge("B", "C", 5);
        aStar.addEdge("B", "G", 6);
        aStar.addEdge("C", "G", 1);
        aStar.addEdge("D", "C", 1);
        aStar.addEdge("D", "G", 3);

        List<String> path = aStar.aStar("A", "G");
        System.out.println("Caminho encontrado: " + path); // Deve imprimir: A → D → G
    }
}
