package QuestaoTres;

import java.util.*;

class Puzzle {
    private static final int[][] GOAL_STATE = {{1, 2}, {'x', 3}};  // Estado objetivo

    // Representa o estado do quebra-cabeça
    static class State {
        int[][] board;
        int gCost; // Custo do caminho até o estado
        int hCost; // Heurística estimada (distância de Manhattan)
        State parent; // Estado anterior no caminho

        public State(int[][] board, int gCost, int hCost, State parent) {
            this.board = board;
            this.gCost = gCost;
            this.hCost = hCost;
            this.parent = parent;
        }

        // Função que retorna o valor de f(n) = g(n) + h(n)
        public int fCost() {
            return gCost + hCost;
        }

        // Compara estados para a fila de prioridade
        public static Comparator<State> stateComparator = (s1, s2) -> {
            if (s1.fCost() < s2.fCost()) return -1;
            if (s1.fCost() > s2.fCost()) return 1;
            return 0;
        };

        // Função para verificar se o estado atual é o objetivo
        public boolean isGoal() {
            return Arrays.deepEquals(board, GOAL_STATE);
        }
    }

    // Função que encontra a posição da peça 'x'
    private static int[] findBlank(int[][] state) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (state[i][j] == 'x') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Função que calcula a distância de Manhattan entre um estado e o objetivo
    private static int manhattanDistance(int[][] state) {
        int distance = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int value = state[i][j];
                if (value != 'x') {
                    int[] goalPos = findGoalPosition(value);
                    distance += Math.abs(i - goalPos[0]) + Math.abs(j - goalPos[1]);
                }
            }
        }
        return distance;
    }

    // Função para encontrar a posição de uma peça no estado objetivo
    private static int[] findGoalPosition(int value) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (GOAL_STATE[i][j] == value) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Função que gera os vizinhos de um estado
    private static List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        int[] blankPos = findBlank(state.board);
        int blankX = blankPos[0];
        int blankY = blankPos[1];

        // Definimos os possíveis movimentos: direita, esquerda, baixo, cima
        int[][] moves = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] move : moves) {
            int newX = blankX + move[0];
            int newY = blankY + move[1];

            if (newX >= 0 && newX < 2 && newY >= 0 && newY < 2) {
                int[][] newBoard = copyBoard(state.board);
                newBoard[blankX][blankY] = newBoard[newX][newY];
                newBoard[newX][newY] = 'x';

                int hCost = manhattanDistance(newBoard);
                neighbors.add(new State(newBoard, state.gCost + 1, hCost, state));
            }
        }
        return neighbors;
    }

    // Função auxiliar para copiar um tabuleiro
    private static int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[2][2];
        for (int i = 0; i < 2; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, 2);
        }
        return newBoard;
    }

    // Função que imprime o estado do tabuleiro
    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((char) cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Função que executa o algoritmo A* para encontrar a solução
    public static void aStar(int[][] startBoard) {
        PriorityQueue<State> frontier = new PriorityQueue<>(State.stateComparator);
        Set<String> explored = new HashSet<>();

        // Estado inicial
        int hCost = manhattanDistance(startBoard);
        State startState = new State(startBoard, 0, hCost, null);
        frontier.add(startState);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();

            // Verifica se encontramos o objetivo
            if (current.isGoal()) {
                System.out.println("Solução encontrada!");
                printSolutionPath(current);
                return;
            }

            // Serializa o estado atual como string para armazenar no conjunto explorado
            explored.add(Arrays.deepToString(current.board));

            // Gera os vizinhos e os insere na fronteira se ainda não foram explorados
            for (State neighbor : getNeighbors(current)) {
                if (!explored.contains(Arrays.deepToString(neighbor.board))) {
                    frontier.add(neighbor);
                }
            }
        }

        System.out.println("Nenhuma solução encontrada.");
    }

    // Função que imprime o caminho da solução
    private static void printSolutionPath(State state) {
        List<State> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        for (State s : path) {
            printBoard(s.board);
        }
    }

    public static void main(String[] args) {
        // Estado inicial
        int[][] start = {{'x', 1}, {3, 2}};

        // Executa o algoritmo A*
        aStar(start);
    }
}
