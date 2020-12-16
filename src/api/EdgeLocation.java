package api;

public class EdgeLocation implements edge_location{
    edge_data edge;
    /**
     * Returns the edge on which the location is.
     * @return
     */
    @Override
    public edge_data getEdge() {
        return edge;
    }

    /**
    *Returns the relative ration [0,1] of the location between src and dest.
     */
    @Override
    public double getRatio() {
        return 0;
    }
}
