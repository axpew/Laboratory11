package domain;

import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void test() {
        try {
            AdjacencyListGraph graph = new AdjacencyListGraph(50);

            // Agregar 30 nuevos vértices con números al azar entre 10 y 50
            System.out.println("=== AGREGANDO 30 VÉRTICES CON NÚMEROS ALEATORIOS (10-50) ===");
            List<Integer> addedVertices = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                int randomNumber = util.Utility.random(41) + 10;
                while (addedVertices.contains(randomNumber)) {
                    randomNumber = util.Utility.random(41) + 10;
                }
                addedVertices.add(randomNumber);
                graph.addVertex(randomNumber);
                System.out.println("Vértice agregado: " + randomNumber);
            }

            System.out.println("\nTotal de vértices agregados: " + addedVertices.size());
            System.out.println("Vértices: " + addedVertices);


            System.out.println("\n=== CONECTANDO VÉRTICES PARES ENTRE SÍ E IMPARES ENTRE SÍ ===");
            graph.connectEvenAndOddVertices();
            System.out.println("Conexiones realizadas según paridad de números.");

            // Mostrar información sobre pares e impares
            List<Integer> evenVertices = new ArrayList<>();
            List<Integer> oddVertices = new ArrayList<>();

            for (Integer vertex : addedVertices) {
                if (vertex % 2 == 0) {
                    evenVertices.add(vertex);
                } else {
                    oddVertices.add(vertex);
                }
            }

            System.out.println("Vértices PARES conectados entre sí: " + evenVertices);
            System.out.println("Vértices IMPARES conectados entre sí: " + oddVertices);
            System.out.println("Número de conexiones entre pares: " + (evenVertices.size() * (evenVertices.size() - 1)) / 2);
            System.out.println("Número de conexiones entre impares: " + (oddVertices.size() * (oddVertices.size() - 1)) / 2);

            // Mostrar el contenido del grafo por consola
            System.out.println("\n=== CONTENIDO COMPLETO DEL GRAFO ===");
            System.out.println(graph.toString());

            // Conectar en forma aleatoria 10 vértices pares existentes en el grafo con 10 vértices impares
            System.out.println("\n=== CONECTANDO 10 PARES CON 10 IMPARES ALEATORIAMENTE ===");

            int connectionsToMake = Math.min(10, Math.min(evenVertices.size(), oddVertices.size()));
            System.out.println("Realizando " + connectionsToMake + " conexiones aleatorias entre pares e impares...");

            for (int i = 0; i < connectionsToMake; i++) {
                // Seleccionar vértice par aleatorio
                int randomEvenIndex = util.Utility.random(evenVertices.size());
                Integer evenVertex = evenVertices.get(randomEvenIndex);

                // Seleccionar vértice impar aleatorio
                int randomOddIndex = util.Utility.random(oddVertices.size());
                Integer oddVertex = oddVertices.get(randomOddIndex);

                // Conectar con peso aleatorio entre 1 y 40
                int weight = util.Utility.random(40) + 1;

                if (!graph.containsEdge(evenVertex, oddVertex)) {
                    graph.addEdgeWeight(evenVertex, oddVertex, weight);
                    System.out.println("Conectado: " + evenVertex + " ↔ " + oddVertex + " (peso: " + weight + ")");
                } else {
                    System.out.println("Ya existe conexión entre: " + evenVertex + " ↔ " + oddVertex);
                }
            }

            // Probar los recorridos dfs(), bfs()
            System.out.println("\n=== RECORRIDOS DEL GRAFO ===");
            System.out.println("DFS Transversal Tour: " + graph.dfs());
            System.out.println("BFS Transversal Tour: " + graph.bfs());

            // Suprimir 5 vértices al azar
            System.out.println("\n=== SUPRIMIENDO 5 VÉRTICES AL AZAR ===");

            List<Integer> verticesToRemove = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                int randomIndex = util.Utility.random(addedVertices.size());
                Integer vertexToRemove = addedVertices.get(randomIndex);

                while (verticesToRemove.contains(vertexToRemove)) {
                    randomIndex = util.Utility.random(addedVertices.size());
                    vertexToRemove = addedVertices.get(randomIndex);
                }

                verticesToRemove.add(vertexToRemove);
                addedVertices.remove(vertexToRemove); // Remover de la lista local también

                System.out.println("Suprimiendo vértice: " + vertexToRemove);
                graph.removeVertex(vertexToRemove);
                System.out.println("Vértice " + vertexToRemove + " eliminado (junto con sus aristas y pesos)");
            }

            System.out.println("Vértices eliminados: " + verticesToRemove);

            // Mostrar el contenido del grafo por consola
            System.out.println("\n=== CONTENIDO FINAL DEL GRAFO ===");
            System.out.println("(después de eliminar 5 vértices)");
            System.out.println(graph.toString());

            System.out.println("\n=== RECORRIDOS FINALES DEL GRAFO MODIFICADO ===");
            System.out.println("DFS Transversal Tour: " + graph.dfs());
            System.out.println("BFS Transversal Tour: " + graph.bfs());

            // Información adicional sobre el estado final
            System.out.println("\n=== RESUMEN FINAL ===");
            System.out.println("Número total de vértices restantes: " + graph.size());
            System.out.println("¿El grafo está vacío? " + graph.isEmpty());
            System.out.println("Vértices restantes: " + addedVertices);

            // Verificaciones adicionales
            System.out.println("\n=== VERIFICACIONES ADICIONALES ===");
            if (!addedVertices.isEmpty()) {
                Integer firstVertex = addedVertices.get(0);
                System.out.println("¿Contiene el primer vértice restante (" + firstVertex + ")? " + graph.containsVertex(firstVertex));

                // Verificar si alguno de los vértices eliminados sigue en el grafo
                for (Integer removedVertex : verticesToRemove) {
                    System.out.println("¿Contiene vértice eliminado (" + removedVertex + ")? " + graph.containsVertex(removedVertex));
                }
            }

        } catch (GraphException | ListException | StackException | QueueException e) {
            System.err.println("Error durante la ejecución del test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}