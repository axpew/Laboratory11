package domain;

import domain.list.ListException;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;

public class AdjacencyListGraph implements Graph {
    private Vertex[] vertexList; //arreglo de objetos tupo vértice
    private int n; //max de elementos
    private int counter; //contador de vertices

    //para los recorridos dfs, bfs
    private LinkedStack stack;
    private LinkedQueue queue;

    //Constructor
    public AdjacencyListGraph(int n) {
        if (n <= 0) System.exit(1); //sale con status==1 (error)
        this.n = n;
        this.counter = 0;
        this.vertexList = new Vertex[n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
    }

    @Override
    public int size() throws ListException {
        return counter;
    }

    @Override
    public void clear() {
        this.vertexList = new Vertex[n];
        this.counter = 0; //inicializo contador de vértices
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException, ListException {
        if(isEmpty())
            throw new GraphException("Adjacency List Graph is Empty");
        //opcion-1
       /* for (int i = 0; i < counter; i++) {
            if(util.Utility.compare(vertexList[i].data, element)==0)
                return true;
        }*/
        //opcion-2
        return indexOf(element)!=-1;
        //return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if(isEmpty())
            throw new GraphException("Adjacency List Graph is Empty");
        return !vertexList[indexOf(a)].edgesList.isEmpty()
                && vertexList[indexOf(a)].edgesList.contains(new EdgeWeight(b, null));
    }

    @Override
    public void addVertex(Object element) throws GraphException, ListException {
        if(counter>=vertexList.length)
            throw new GraphException("Adjacency List Graph is Full");
        vertexList[counter++] = new Vertex(element);
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b))
            throw new GraphException("Cannot add edge between vertexes ["+a+"] y ["+b+"]");
        vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, null));
        //grafo no dirigido
        vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, null));

    }

    private int indexOf(Object element){
        for (int i = 0; i < counter; i++) {
            if(util.Utility.compare(vertexList[i].data, element)==0)
                return i; //retorna la pos en el arreglo de objectos vertexList
        }
        return -1; //significa q la data de todos los vertices no coinciden con element
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsEdge(a, b))
            throw new GraphException("There is no edge between the vertexes["+a+"] y ["+b+"]");
        updateEdgesListEdgeWeight(a, b, weight);
        //GRAFO NO DIRIGIDO
        updateEdgesListEdgeWeight(b, a, weight);
    }

    private void updateEdgesListEdgeWeight(Object a, Object b, Object weight) throws ListException {
        EdgeWeight ew = (EdgeWeight) vertexList[indexOf(a)].edgesList
                .getNode(new EdgeWeight(b, null)).getData();
        //setteo el peso en el campo respectivo
        ew.setWeight(weight);
        //ahora actualizo la info en la lista de aristas correspondiente
        vertexList[indexOf(a)].edgesList.getNode(new EdgeWeight(b, null))
                .setData(ew);
    }

    @Override
    public void addEdgeWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b))
            throw new GraphException("Cannot add edge between vertexes ["+a+"] y ["+b+"]");
        if(!containsEdge(a, b)){ //si no existe la arista
            vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, weight));
            //grafo no dirigido
            vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, weight));
        }
    }

    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        if(isEmpty())
            throw new GraphException("Adjacency List Graph is Empty");
        if(containsVertex(element)){
            for (int i = 0; i < counter; i++) {
                if(util.Utility.compare(vertexList[i].data, element)==0) {
                    //ya lo encontro, ahora
                    //se debe suprimir el vertice a eliminar de todas las listas
                    //enlazadas de los otros vértices
                    for (int j = 0; j < counter; j++) {
                        if(containsEdge(vertexList[j].data, element))
                            removeEdge(vertexList[j].data, element);
                    }

                    //ahora, debemos suprimir el vértice
                    for (int j = i; j < counter-1; j++) {
                        vertexList[j] = vertexList[j+1];
                    }
                    counter--; //decrementamos el contador de vértices
                }
            }
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b))
            throw new GraphException("There's no some of the vertexes");
        if(!vertexList[indexOf(a)].edgesList.isEmpty()) {
            vertexList[indexOf(a)].edgesList.remove(new EdgeWeight(b, null));
        }
        //grafo no dirigido
        if(!vertexList[indexOf(b)].edgesList.isEmpty()){
            vertexList[indexOf(b)].edgesList.remove(new EdgeWeight(a, null));
        }
    }

    // Recorrido en profundidad
    @Override
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false);//marca todos los vertices como no vistados
        // inicia en el vertice 0
        String info = vertexList[0].data + ", ";
        vertexList[0].setVisited(true); // lo marca
        stack.clear();
        stack.push(0); //lo apila
        while (!stack.isEmpty()) {
            // obtiene un vertice adyacente no visitado,
            //el que esta en el tope de la pila
            int index = adjacentVertexNotVisited((int) stack.top());
            if (index == -1) // no lo encontro
                stack.pop();
            else {
                vertexList[index].setVisited(true); // lo marca
                info += vertexList[index].data + ", "; //lo muestra
                stack.push(index); //inserta la posicion
            }
        }
        return info;
    }

    //Recorrido en amplitud
    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
        // inicia en el vertice 0
        String info = vertexList[0].data + ", ";
        vertexList[0].setVisited(true); // lo marca
        queue.clear();
        queue.enQueue(0); // encola el elemento
        int v2;
        while (!queue.isEmpty()) {
            int v1 = (int) queue.deQueue(); // remueve el vertice de la cola
            // hasta que no tenga vecinos sin visitar
            while ((v2 = adjacentVertexNotVisited(v1)) != -1) {
                // obtiene uno
                vertexList[v2].setVisited(true); // lo marca
                info += vertexList[v2].data + ", "; //lo muestra
                queue.enQueue(v2); // lo encola
            }
        }
        return info;
    }

    //setteamos el atributo visitado del vertice respectivo
    private void setVisited(boolean value) {
        for (int i = 0; i < counter; i++) {
            vertexList[i].setVisited(value); //value==true o false
        }//for
    }

    private int adjacentVertexNotVisited(int index) throws ListException {
        Object vertexData = vertexList[index].data;
        for (int i = 0; i < counter; i++) {
            if(!vertexList[index].edgesList.isEmpty()
                    && vertexList[i].edgesList.contains(new EdgeWeight(vertexData, null))
                    && !vertexList[i].isVisited())
                return i;//retorna la posicion del vertice adyacente no visitado
        }//for i
        return -1;
    }

    /**
     * Conecta todos los vértices pares entre sí y todos los vértices impares entre sí
     * Para los pesos utiliza números aleatorios entre 1 y 40
     */
    public void connectEvenAndOddVertices() throws GraphException, ListException {
        // Conectar todos los vértices pares entre sí
        for (int i = 0; i < counter; i++) {
            Object dataI = vertexList[i].data;
            if (isEvenNumber(dataI)) {
                for (int j = i + 1; j < counter; j++) {
                    Object dataJ = vertexList[j].data;
                    if (isEvenNumber(dataJ)) {
                        // Conectar solo si no existe la arista
                        if (!containsEdge(dataI, dataJ)) {
                            int weight = util.Utility.random(40) + 1; // Entre 1 y 40
                            addEdgeWeight(dataI, dataJ, weight);
                        }
                    }
                }
            }
        }

        // Conectar todos los vértices impares entre sí
        for (int i = 0; i < counter; i++) {
            Object dataI = vertexList[i].data;
            if (isOddNumber(dataI)) {
                for (int j = i + 1; j < counter; j++) {
                    Object dataJ = vertexList[j].data;
                    if (isOddNumber(dataJ)) {
                        // Conectar solo si no existe la arista
                        if (!containsEdge(dataI, dataJ)) {
                            int weight = util.Utility.random(40) + 1; // Entre 1 y 40
                            addEdgeWeight(dataI, dataJ, weight);
                        }
                    }
                }
            }
        }
    }

    /**
     * Verifica si un objeto representa un número par
     */
    private boolean isEvenNumber(Object data) {
        try {
            int number = Integer.parseInt(data.toString());
            return number % 2 == 0;
        } catch (NumberFormatException e) {
            return false; // Si no es un número, no es par
        }
    }

    /**
     * Verifica si un objeto representa un número impar
     */
    private boolean isOddNumber(Object data) {
        try {
            int number = Integer.parseInt(data.toString());
            return number % 2 == 1;
        } catch (NumberFormatException e) {
            return false; // Si no es un número, no es impar
        }
    }

    @Override
    public String toString() {
        String result = "Adjacency List Graph Content...";
        //se muestran todos los vértices del grafo
        for (int i = 0; i < counter; i++) {
            result+="\nThe vextex in the position: "+i+" is: "+vertexList[i].data;
            if(!vertexList[i].edgesList.isEmpty())
                result+="\n......EDGES AND WEIGHTS: "+vertexList[i].edgesList.toString();
        }
        return result;
    }
}