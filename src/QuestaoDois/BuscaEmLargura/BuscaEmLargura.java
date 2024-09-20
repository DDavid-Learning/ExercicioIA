package QuestaoDois.BuscaEmLargura;
import java.util.*;

// Classe representando um nó na árvore
class Node {
    String value;
    List<Node> children;

    public Node(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }
}

// Implementação do algoritmo de Busca em Largura com um ponto de chegada
public class BuscaEmLargura {

    // Método de busca em largura (BFS) até encontrar o ponto de chegada
    public static void bfs(Node root, String target) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        // Adiciona o nó raiz à fila
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.value + " ");

            // Verifica se encontrou o nó de destino
            if (current.value.equals(target)) {
                System.out.println("\nDestino '" + target + "' encontrado.");
                return; // Interrompe a busca ao encontrar o destino
            }

            // Se ainda não foi visitado, marca como visitado
            if (!visited.contains(current)) {
                visited.add(current);

                // Ordena os filhos em ordem alfabética
                List<Node> children = new ArrayList<>(current.getChildren());
                children.sort(Comparator.comparing(node -> node.value));

                queue.addAll(children); // Adiciona todos os filhos à fila
            }
        }

        System.out.println("\nDestino '" + target + "' não encontrado.");
    }

    public static void main(String[] args) {
        // Criação dos nós
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node G = new Node("G");

        // Construção da árvore com base nas relações
        A.addChild(B);
        A.addChild(D);

        B.addChild(C);
        B.addChild(D);
        B.addChild(G);

        D.addChild(C);
        D.addChild(G);

        C.addChild(G);

        // Execução do algoritmo de busca em largura com o destino 'G'
        bfs(A, "G");
    }
}

