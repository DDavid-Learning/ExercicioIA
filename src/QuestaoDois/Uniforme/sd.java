package QuestaoDois.Uniforme;

import java.util.*;

class Node {
    String name;
    int cost;

    Node(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
}

class UniformCostSearch {
    private final Map<String, List<Node>> graph = new HashMap<>();

    public void addNode(String name) {
        graph.putIfAbsent(name, new ArrayList<>());
    }

    public void addEdge(String from, String to, int cost) {
        graph.get(from).add(new Node(to, cost));
    }

    public List<String> uniformCostSearch(String start, String goal) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        Map<String, Integer> visited = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        List<String> path = new ArrayList<>();

        priorityQueue.add(new Node(start, 0));
        visited.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            if (currentNode.name.equals(goal)) {
                reconstructPath(parent, path, goal);
                return path;
            }

            for (Node neighbor : graph.get(currentNode.name)) {
                int newCost = visited.get(currentNode.name) + neighbor.cost;

                if (!visited.containsKey(neighbor.name) || newCost < visited.get(neighbor.name)) {
                    visited.put(neighbor.name, newCost);
                    parent.put(neighbor.name, currentNode.name);
                    priorityQueue.add(new Node(neighbor.name, newCost));
                }
            }
        }
        return path; // Retorna uma lista vazia se o caminho não for encontrado
    }

    private void reconstructPath(Map<String, String> parent, List<String> path, String goal) {
        String step = goal;
        while (step != null) {
            path.add(0, step);
            step = parent.get(step);
        }
    }

    public static void main(String[] args) {
        UniformCostSearch ucs = new UniformCostSearch();
        ucs.addNode("A");
        ucs.addNode("B");
        ucs.addNode("C");
        ucs.addNode("D");
        ucs.addNode("G");

        ucs.addEdge("A", "B", 1);
        ucs.addEdge("A", "D", 3);
        ucs.addEdge("B", "A", 2);
        ucs.addEdge("B", "D", 4);
        ucs.addEdge("B", "C", 5);
        ucs.addEdge("B", "G", 6);
        ucs.addEdge("C", "G", 1);
        ucs.addEdge("D", "C", 1);
        ucs.addEdge("D", "G", 3);

        List<String> path = ucs.uniformCostSearch("A", "G");
        System.out.println("Caminho mais curto: " + path);
    }
}
