package HeuristicasAdmissiveis;

class Node {
    String name;
    int g;  // custo acumulado
    int h;  // heurística
    Node parent;  // para reconstruir o caminho

    Node(String name, int g, int h, Node parent) {
        this.name = name;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    int f() {
        return g + h;  // f(n) = g(n) + h(n)
    }
}