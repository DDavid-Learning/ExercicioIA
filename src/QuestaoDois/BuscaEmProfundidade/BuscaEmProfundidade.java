package QuestaoDois.BuscaEmProfundidade;
import java.util.*;

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
}

public class BuscaEmProfundidade {

    // Método de DFS
    public static boolean dfs(Node node, Set<Node> visited, String target) {
        if (visited.contains(node)) {
            return false; // Já visitado
        }

        // Marcar o nó como visitado
        visited.add(node);
        System.out.println("Visitando nó: " + node.value);

        // Verificar se encontrou o alvo
        if (node.value.equals(target)) {
            return true; // Encontrou o nó "G"
        }

        // Ordenar os filhos em ordem alfabética
        node.children.sort(Comparator.comparing(n -> n.value));

        // Recursivamente visitar cada filho
        for (Node child : node.children) {
            if (dfs(child, visited, target)) {
                return true; // Parar se encontrou o nó alvo
            }
        }

        return false; // Continuar se não encontrou o alvo
    }

    public static void main(String[] args) {
        // Criação dos nós
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node G = new Node("G");

        // Construção da árvore
        A.addChild(B);
        A.addChild(D);
        B.addChild(A); // A já está visitado, mas adicionado para refletir o comportamento pedido
        B.addChild(D);
        B.addChild(G);
        B.addChild(C);
        C.addChild(G);
        D.addChild(G);
        D.addChild(C);

        // Inicialização do DFS
        Set<Node> visited = new HashSet<>();
        dfs(A, visited, "G"); // Define "G" como alvo
    }
}
