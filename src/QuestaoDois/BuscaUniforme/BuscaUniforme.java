package QuestaoDois.BuscaUniforme;
import java.util.*;

class Node implements Comparable<Node> {
    String name;
    int cost;

    public Node(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class BuscaUniforme {
    // Representação do grafo como mapa de adjacências
    private static Map<String, List<Node>> graph = new HashMap<>();

    // Função para inicializar o grafo
    private static void initializeGraph() {
        graph.put("A", Arrays.asList(new Node("B", 1), new Node("D", 3)));
        graph.put("B", Arrays.asList(new Node("C", 5), new Node("D", 4), new Node("G", 6)));
        graph.put("D", Arrays.asList(new Node("C", 1), new Node("G", 3)));
        graph.put("C", Arrays.asList(new Node("G", 1)));
        graph.put("G", new ArrayList<>()); // G não possui filhos
    }

    // Função para executar o Uniform Cost Search
    private static void uniformCostSearch(String start, String goal) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Map<String, Integer> visited = new HashMap<>(); // Guarda o menor custo para cada nó

        // Inicia com o nó de partida
        priorityQueue.add(new Node(start, 0));
        visited.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            String currentNode = current.name;
            int currentCost = current.cost;

            System.out.println("Visitando nó: " + currentNode + " com custo: " + currentCost);

            // Verifica se chegamos ao nó objetivo
            if (currentNode.equals(goal)) {
                System.out.println("Objetivo '" + goal + "' alcançado com custo: " + currentCost);
                return;
            }

            // Percorre os vizinhos do nó atual
            for (Node neighbor : graph.getOrDefault(currentNode, new ArrayList<>())) {
                int newCost = currentCost + neighbor.cost;

                // Se o vizinho ainda não foi visitado ou se encontramos um caminho mais barato
                if (!visited.containsKey(neighbor.name) || newCost < visited.get(neighbor.name)) {
                    visited.put(neighbor.name, newCost);
                    priorityQueue.add(new Node(neighbor.name, newCost));
                }
            }
        }
        System.out.println("Objetivo '" + goal + "' não encontrado.");
    }

    public static void main(String[] args) {
        initializeGraph();
        String startNode = "A";
        String goalNode = "G";
        uniformCostSearch(startNode, goalNode);
    }
}
