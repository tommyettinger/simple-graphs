package space.earlygrey.simplegraphs;

import java.util.Collection;
import java.util.function.Supplier;

import space.earlygrey.simplegraphs.Connection.UndirectedConnection;

public class UndirectedGraph<V> extends Graph<V> {

    UndirectedGraphAlgorithms<V> algorithms;

    //================================================================================
    // Constructors
    //================================================================================

    public UndirectedGraph() {
        super();
        algorithms = new UndirectedGraphAlgorithms<>(this);
    }

    public UndirectedGraph(Collection<V> vertices) {
        super(vertices);
        algorithms = new UndirectedGraphAlgorithms<>(this);
    }


    //================================================================================
    // Graph building
    //================================================================================

    @Override
    protected Supplier<Connection<V>> getEdgeSupplier() {
        return () -> new UndirectedConnection<>();
    }

    @Override
    Connection<V> addConnection(Node<V> a, Node<V> b, float weight) {
        Connection<V> e = a.addEdge(b, weight);
        edgeMap.put(e, e);
        b.addEdge(a, weight);
        return e;
    }

    @Override
    boolean removeConnection(Node<V> a, Node<V> b) {
        Connection<V> e = a.removeEdge(b);
        if (e == null) return false;
        b.removeEdge(a);
        edgeMap.remove(e);
        return true;
    }

    @Override
    Connection<V> getEdge(Node<V> a, Node<V> b) {
        Connection<V> edge = a.getEdge(b);
        if (edge == null) return null;
        edge = edgeMap.get(edge);
        return edge;
    }


    //================================================================================
    // Superclass implementations
    //================================================================================

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    Graph<V> createNew() {
        return new UndirectedGraph<>();
    }

    @Override
    public UndirectedGraphAlgorithms<V> algorithms() {
        return algorithms;
    }
}
