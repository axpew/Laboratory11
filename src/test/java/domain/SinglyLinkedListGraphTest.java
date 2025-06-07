package domain;

import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListGraphTest {

    @Test
    void test() {
        try {
            SinglyLinkedListGraph graph = new SinglyLinkedListGraph();

            // Array de nombres de personas para los pesos
            String[] personNames = {"Alice", "Bob", "Carol", "David", "Emma", "Frank",
                    "Grace", "Henry", "Isabel", "Jack", "Kate", "Luis",
                    "Maria", "Nick", "Olivia", "Pedro", "Quinn", "Rosa",
                    "Sam", "Tina", "Ulises", "Vera", "Will", "Ximena", "Yuri", "Zoe"};


            System.out.println("=== CREANDO GRAFO SEGÚN EL MODELO DEL DIAGRAMA ===");

            char[] vertices = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

            for (char vertex : vertices) {
                graph.addVertex(vertex);
                System.out.println("Vértice agregado: " + vertex);
            }

            // Agregar como pesos nombres de personas
            System.out.println("\n=== AGREGANDO ARISTAS CON PESOS (NOMBRES DE PERSONAS) ===");

            // Conexiones según el diagrama exacto
            // A conecta con B, C, D
            graph.addEdgeWeight('A', 'B', personNames[util.Utility.random(personNames.length)]);
            graph.addEdgeWeight('A', 'C', personNames[util.Utility.random(personNames.length)]);
            graph.addEdgeWeight('A', 'D', personNames[util.Utility.random(personNames.length)]);

            // B conecta con F
            graph.addEdgeWeight('B', 'F', personNames[util.Utility.random(personNames.length)]);

            // C conecta con G
            graph.addEdgeWeight('C', 'G', personNames[util.Utility.random(personNames.length)]);

            // D conecta con H
            graph.addEdgeWeight('D', 'H', personNames[util.Utility.random(personNames.length)]);

            // E conecta con F
            graph.addEdgeWeight('E', 'F', personNames[util.Utility.random(personNames.length)]);

            // F conecta con J
            graph.addEdgeWeight('F', 'J', personNames[util.Utility.random(personNames.length)]);

            // G conecta con J
            graph.addEdgeWeight('G', 'J', personNames[util.Utility.random(personNames.length)]);

            // H conecta con I y J
            graph.addEdgeWeight('H', 'I', personNames[util.Utility.random(personNames.length)]);
            graph.addEdgeWeight('H', 'J', personNames[util.Utility.random(personNames.length)]);


            System.out.println("Todas las aristas con pesos (nombres de personas) han sido agregadas.");

            // Mostrar el contenido del grafo por consola
            System.out.println("\n=== CONTENIDO COMPLETO DEL GRAFO ===");
            System.out.println(graph.toString());

            // Probar los recorridos dfs(), bfs()
            System.out.println("\n=== RECORRIDOS DEL GRAFO ===");
            System.out.println("DFS Transversal Tour: " + graph.dfs());
            System.out.println("BFS Transversal Tour: " + graph.bfs());

            // Suprima los vértices F, H, J
            System.out.println("\n=== SUPRIMIENDO VÉRTICES F, H, J ===");

            System.out.println("\nSuprimiendo vértice: F");
            graph.removeVertex('F');
            System.out.println("Vértice F eliminado (junto con sus aristas y pesos)");

            System.out.println("\nSuprimiendo vértice: H");
            graph.removeVertex('H');
            System.out.println("Vértice H eliminado (junto con sus aristas y pesos)");

            System.out.println("\nSuprimiendo vértice: J");
            graph.removeVertex('J');
            System.out.println("Vértice J eliminado (junto con sus aristas y pesos)");

            // Probar nuevamente los recorridos dfs(), bfs()
            System.out.println("\n=== CONTENIDO FINAL DEL GRAFO ===");
            System.out.println("(después de eliminar vértices F, H, J)");
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
            System.out.println("¿Contiene vértice A? " + graph.containsVertex('A'));
            System.out.println("¿Contiene vértice B? " + graph.containsVertex('B'));
            System.out.println("¿Contiene vértice C? " + graph.containsVertex('C'));
            System.out.println("¿Contiene vértice F? " + graph.containsVertex('F')); // Debe ser false
            System.out.println("¿Contiene vértice H? " + graph.containsVertex('H')); // Debe ser false
            System.out.println("¿Contiene vértice J? " + graph.containsVertex('J')); // Debe ser false

            // Verificar aristas restantes
            System.out.println("¿Existe arista entre A y B? " + graph.containsEdge('A', 'B'));
            System.out.println("¿Existe arista entre A y C? " + graph.containsEdge('A', 'C'));
            System.out.println("¿Existe arista entre A y D? " + graph.containsEdge('A', 'D'));

            // Análisis del impacto de las eliminaciones
            System.out.println("\n=== ANÁLISIS DEL IMPACTO DE LAS ELIMINACIONES ===");
            System.out.println("Vértices eliminados: F, H, J");
            System.out.println("Impacto:");
            System.out.println("- E quedó desconectado (perdió su única conexión con F)");
            System.out.println("- I quedó desconectado (perdió su única conexión con H)");
            System.out.println("- B perdió su conexión secundaria (con F)");
            System.out.println("- C perdió su conexión secundaria (con G a través de J)");
            System.out.println("- D perdió su conexión secundaria (con H)");
            System.out.println("- G quedó con solo la conexión a C");

            // Estado final esperado del grafo
            System.out.println("\n=== ESTADO FINAL ESPERADO ===");
            System.out.println("Componentes conectados:");
            System.out.println("1. A-B-C-D (componente principal conectado)");
            System.out.println("2. E (vértice aislado)");
            System.out.println("3. G (conectado solo con C)");
            System.out.println("4. I (vértice aislado)");

        } catch (GraphException | ListException | StackException | QueueException e) {
            System.err.println("Error durante la ejecución del test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}