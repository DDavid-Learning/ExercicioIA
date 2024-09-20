package HeuristicasAdmissiveis;

import java.util.*;

public class HeuristicasAdmissiveis {

    // Definindo o grafo com seus custos
    static Map<String, List<Node>> graph = new HashMap<>();
    static Map<String, Integer> heuristic = new HashMap<>();

    public static void main(String[] args) {
        // Definir os vizinhos e pesos
        graph.put("S", Arrays.asList(new Node("A", 1, 0, null), new Node("G", 12, 0, null)));
        graph.put("A", Arrays.asList(new Node("B", 3, 0, null), new Node("C", 1, 0, null)));
        graph.put("B", Arrays.asList(new Node("D", 3, 0, null)));
        graph.put("C", Arrays.asList(new Node("D", 1, 0, null), new Node("G", 2, 0, null)));
        graph.put("D", Arrays.asList(new Node("G", 3, 0, null)));

        // Definir as heurísticas (neste caso, escolhemos h2)
        heuristic.put("S", 5);
        heuristic.put("A", 3);
        heuristic.put("B", 6);
        heuristic.put("C", 2);
        heuristic.put("D", 3);
        heuristic.put("G", 0);

        // Executar o algoritmo A*
        aStarSearch("S", "G");
    }

    static void aStarSearch(String start, String goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        Set<String> closedList = new HashSet<>();

        // Começar do nó inicial
        Node startNode = new Node(start, 0, heuristic.get(start), null);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            // Se atingimos o objetivo, reconstruir o caminho
            if (current.name.equals(goal)) {
                printPath(current);
                return;
            }

            closedList.add(current.name);

            // Expandir os vizinhos
            for (Node neighbor : graph.getOrDefault(current.name, new ArrayList<>())) {
                if (closedList.contains(neighbor.name)) continue;

                int gScore = current.g + neighbor.g;  // custo g acumulado
                Node neighborNode = new Node(neighbor.name, gScore, heuristic.get(neighbor.name), current);

                // Verificar se o nó já está na lista aberta com um custo menor
                Optional<Node> openNeighbor = openList.stream().filter(n -> n.name.equals(neighbor.name)).findFirst();
                if (!openNeighbor.isPresent() || gScore < openNeighbor.get().g) {
                    openList.add(neighborNode);
                }
            }
        }

        System.out.println("Caminho não encontrado.");
    }

    // Imprimir o caminho a partir do nó objetivo
    static void printPath(Node node) {
        List<String> path = new ArrayList<>();
        Node current = node;
        while (current != null) {
            path.add(current.name);
            current = current.parent;
        }
        Collections.reverse(path);
        System.out.println("Caminho encontrado: " + String.join(" -> ", path));
    }
}