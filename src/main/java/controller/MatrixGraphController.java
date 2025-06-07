package controller;

import domain.AdjacencyMatrixGraph;
import domain.Vertex;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import util.AristaVisual;

import java.util.*;

public class MatrixGraphController {

    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private Label infoLabel;
    @javafx.fxml.FXML
    private Pane buttonPane11;
    @javafx.fxml.FXML
    private TextArea TA_Info;
    @javafx.fxml.FXML
    private Pane graphPane;



    // --------------------------------- TODO LO RELACIONADO A CREAR CIRCULOS: --------------------------------------------
    private int cantidad = 0; // Contador de círculos generados
    private final double radio = 100; // Radio en el que se colocan los círculos
    private final double centroX = 200; // Coordenada X del centro del círculo
    private final double centroY = 200; // Coordenada Y del centro del círculo
    // Aumentar el radio para que los círculos estén más separados y quepan más
    double angulo = Math.toRadians(360.0 / 30 * cantidad); // Usamos 30 posiciones por vuelta
    double x = centroX + radio * Math.cos(angulo);
    double y = centroY + radio * Math.sin(angulo);
    private final double radioNodo = 16; // Aumentado de 20 a 30

    private int contador = 1;;
    private final List<StackPane> nodos = new ArrayList<>();
    private final Random random = new Random();
    private List<AristaVisual> aristas = new ArrayList<>();

    private final Set<Integer> usedNumbers = new HashSet<>();
    private final Set<String> aristasDibujadas = new HashSet<>(); // Para evitar duplicadas


    public void initialize() {

        //Crear grafo con capacidad para 10 nodos
       AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(10);

       //Se crea el contenido del grafo
        for (int i = 0; i < 8; i++) {
            int randomNum;
            do {randomNum = util.Utility.random(0, 99);
            } while (usedNumbers.contains(randomNum));

            usedNumbers.add(randomNum);
            graph.vertexList[i] = new Vertex(randomNum);
            graph.counter++;
        }

        //Ahora cargar el grafo visualmente
        loadGraph(graph);
        updatePos(); // Asegura que estén bien posicionados antes de calcular coordenadas


        infoLabel.setText("Clickee en una arista para saber su peso");
        drawEdges(nodos, 6, graphPane); // Por ejemplo, 6 aristas aleatorias
    }

    public void generateCircleButton() {
        if (nodos.size() >= 15) {
            util.FXUtility.showAlert("Error", "Se alcanzó el número máximo de vértices");
            return;
        }

        StackPane nuevoNodo = crearNodo("N" + contador++);
        nodos.add(nuevoNodo);
        updatePos();
        graphPane.getChildren().add(nuevoNodo);
    }


    private StackPane crearNodo(String texto) {
        Circle c = new Circle(radioNodo);
        c.setFill(Color.LIGHTBLUE);
        c.setStroke(Color.DARKBLUE);

        int numeroAleatorio;
        do {
            numeroAleatorio = util.Utility.random(0, 99);
        } while (usedNumbers.contains(numeroAleatorio));
        usedNumbers.add(numeroAleatorio);

        Label label = new Label(String.valueOf(numeroAleatorio));
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        StackPane stack = new StackPane();
        stack.getChildren().addAll(c, label);
        return stack;
    }


    private void updatePos() {
        int n = nodos.size();
        for (int i = 0; i < n; i++) {
            double angulo = 2 * Math.PI * i / n;
            double x = centroX + radio * Math.cos(angulo);
            double y = centroY + radio * Math.sin(angulo);

            StackPane nodo = nodos.get(i);
            nodo.setLayoutX(x - radioNodo);
            nodo.setLayoutY(y - radioNodo);
        }
    }


    public void eliminarNodoAlAzar() {
        if (nodos.isEmpty()) {
            System.out.println("No hay nodos para eliminar.");
            return;
        }

        // Elegir índice aleatorio
        int indice = random.nextInt(nodos.size());

        // Remover nodo del pane y de la lista
        StackPane nodoEliminado = nodos.remove(indice);
        graphPane.getChildren().remove(nodoEliminado);

        // Reajustar posiciones
        updatePos();


    }

    private void drawEdges(List<StackPane> nodos, int cantidadAristas, Pane graphPane) {
        aristas.clear(); // Limpiamos la lista global de aristas
        Set<String> aristasDibujadas = new HashSet<>();
        Random rand = new Random();

        int intentos = 0;
        while (aristasDibujadas.size() < cantidadAristas && intentos < 100) {
            int i = rand.nextInt(nodos.size());
            int j = rand.nextInt(nodos.size());

            if (i == j) {
                intentos++;
                continue;
            }

            String clave = i < j ? i + "-" + j : j + "-" + i;
            if (aristasDibujadas.contains(clave)) {
                intentos++;
                continue;
            }

            StackPane nodoA = nodos.get(i);
            StackPane nodoB = nodos.get(j);

            // Asegurarse que están posicionados
            double ax = nodoA.getLayoutX() + nodoA.getWidth() / 2;
            double ay = nodoA.getLayoutY() + nodoA.getHeight() / 2;
            double bx = nodoB.getLayoutX() + nodoB.getWidth() / 2;
            double by = nodoB.getLayoutY() + nodoB.getHeight() / 2;

            Line aristaLinea = new Line(ax, ay, bx, by);
            aristaLinea.setStroke(Color.GRAY);
            aristaLinea.setStrokeWidth(2);

            //Crear peso aleatorio entre 1 y 50
            int edgeWeight = util.Utility.random(1,50);

            //Crear objeto AristaVisual que contiene la línea y el peso
            AristaVisual arista = new AristaVisual(aristaLinea, edgeWeight, nodoA, nodoB);

            //Al clickear muestra actualiza el label
            aristaLinea.setOnMouseClicked(e -> {
                infoLabel.setText("Peso de la arista: " + arista.getPeso());
                e.consume();
            });

            // Guardar en la lista global para gestión futura
            aristas.add(arista);

            // Añadir la línea al pane (antes que los nodos para que nodos estén arriba)
            graphPane.getChildren().add(0, aristaLinea);
            aristasDibujadas.add(clave);

        }
    }



    public void loadGraph(AdjacencyMatrixGraph graph) {
        nodos.clear();
        graphPane.getChildren().clear();

        for (int i = 0; i < graph.counter; i++) {
            Vertex v = graph.vertexList[i];
            if (v != null) {
                StackPane nodo = crearNodo(v.toString());  // usa v.toString() que devuelve data como texto
                nodos.add(nodo);
            }
        }

        updatePos();
        graphPane.getChildren().addAll(nodos);
    }


        @javafx.fxml.FXML
    public void toStringOnAction(ActionEvent actionEvent) {

        eliminarNodoAlAzar();
    }

    @javafx.fxml.FXML
    public void dfsTourOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void bfsTourOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void containsEdgeOnAction(ActionEvent actionEvent) {
        generateCircleButton();

    }

    @javafx.fxml.FXML
    public void containsVertexOnAction(ActionEvent actionEvent) {
    }


    @javafx.fxml.FXML
    public void pressedRandom(Event event) {
    }
}
