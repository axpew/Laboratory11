package domain;

import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {

    @Test
    void test() {
        try {
            AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(50);

            // Array de colores en inglés para los pesos
            String[] colors = {"red", "blue", "green", "yellow", "orange", "purple",
                    "pink", "brown", "black", "white", "gray", "cyan", "magenta"};

            // Agregado de vértices, aristas y pesos
            char[] vertices = {'P', 'T', 'K', 'D', 'S', 'M', 'H', 'A', 'E', 'Q', 'G', 'R', 'B', 'J'};

            System.out.println("=== CREANDO GRAFO SEGÚN EL MODELO ===");
            for (char vertex : vertices) {
                graph.addVertex(vertex);
                System.out.println("Vértice agregado: " + vertex);
            }

            // Agregado de aristas con pesos (colores aleatorios)
            System.out.println("\n=== AGREGANDO ARISTAS CON PESOS (COLORES) ===");

            // Conexiones desde P
            graph.addEdgeWeight('P', 'T', colors[util.Utility.random(colors.length)]);
            graph.addEdgeWeight('P', 'K', colors[util.Utility.random(colors.length)]);
            graph.addEdgeWeight('P', 'D', colors[util.Utility.random(colors.length)]);

            // Conexiones desde T
            graph.addEdgeWeight('T', 'S', colors[util.Utility.random(colors.length)]);

            // Conexiones desde K
            graph.addEdgeWeight('K', 'M', colors[util.Utility.random(colors.length)]);

            // Conexiones desde D
            graph.addEdgeWeight('D', 'H', colors[util.Utility.random(colors.length)]);

            // Conexiones desde S
            graph.addEdgeWeight('S', 'A', colors[util.Utility.random(colors.length)]);

            // Conexiones desde M
            graph.addEdgeWeight('M', 'E', colors[util.Utility.random(colors.length)]);

            // Conexiones desde H
            graph.addEdgeWeight('H', 'Q', colors[util.Utility.random(colors.length)]);

            // Conexiones desde A
            graph.addEdgeWeight('A', 'G', colors[util.Utility.random(colors.length)]);

            // Conexiones desde Q
            graph.addEdgeWeight('Q', 'R', colors[util.Utility.random(colors.length)]);

            // Conexiones desde G
            graph.addEdgeWeight('G', 'B', colors[util.Utility.random(colors.length)]);

            // Conexiones desde R
            graph.addEdgeWeight('R', 'J', colors[util.Utility.random(colors.length)]);

            System.out.println("Todas las aristas con pesos (colores) han sido agregadas.");

            // c. Redefinición del método toString()
            System.out.println("\n=== INFORMACIÓN COMPLETA DEL GRAFO ===");
            System.out.println(graph.toString());

            // Prueba de los recorridos dfs(), bfs()
            System.out.println("\n=== RECORRIDOS DEL GRAFO ===");
            System.out.println("DFS Transversal Tour: " + graph.dfs());
            System.out.println("BFS Transversal Tour: " + graph.bfs());

            // Suprimir los vértices T, K, H
            System.out.println("\n=== SUPRIMIENDO VÉRTICES T, K, H ===");

            System.out.println("\nSuprimiendo vértice: T");
            graph.removeVertex('T');
            System.out.println("Vértice T eliminado (junto con sus aristas y pesos)");

            System.out.println("\nSuprimiendo vértice: K");
            graph.removeVertex('K');
            System.out.println("Vértice K eliminado (junto con sus aristas y pesos)");

            System.out.println("\nSuprimiendo vértice: H");
            graph.removeVertex('H');
            System.out.println("Vértice H eliminado (junto con sus aristas y pesos)");

            // Mostrar el contenido del grafo por consola
            System.out.println("\n=== CONTENIDO FINAL DEL GRAFO ===");
            System.out.println("(después de eliminar vértices T, K, H)");
            System.out.println(graph.toString());

            System.out.println("\n=== RECORRIDOS FINALES DEL GRAFO MODIFICADO ===");
            System.out.println("DFS Transversal Tour: " + graph.dfs());
            System.out.println("BFS Transversal Tour: " + graph.bfs());

            // Información adicional sobre el estado final
            System.out.println("\n=== RESUMEN FINAL ===");
            System.out.println("Número total de vértices restantes: " + graph.size());
            System.out.println("¿El grafo está vacío? " + graph.isEmpty());

            // Verificaciones adicionales
            System.out.println("\n=== VERIFICACIONES ADICIONALES ===");
            System.out.println("¿Contiene vértice P? " + graph.containsVertex('P'));
            System.out.println("¿Contiene vértice T? " + graph.containsVertex('T')); // Debe ser false
            System.out.println("¿Contiene vértice K? " + graph.containsVertex('K')); // Debe ser false
            System.out.println("¿Contiene vértice H? " + graph.containsVertex('H')); // Debe ser false
            System.out.println("¿Existe arista entre P y D? " + graph.containsEdge('P', 'D'));

        } catch (GraphException | ListException | StackException | QueueException e) {
            System.err.println("Error durante la ejecución del test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}