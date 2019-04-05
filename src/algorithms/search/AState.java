package algorithms.search;

public abstract class AState implements Comparable {

    private AState parent;
    private double cost;

    /**
     * Constructor
     * @param parent - AState - the previous state (the state which led to this state)
     * @param cost - double - the cost of the state
     */
    public AState(AState parent, double cost) {
        this.parent = parent;
        this.cost = cost;
    }

    /**
     * Receives an AState and sets it as the parent of this state
     * @param parent - AState - the state which led to this state
     */
    public void setParent(AState parent) {
        this.parent = parent;
    }

    /**
     * Receives a double value and sets it as this state cost
     * @param cost - double
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Returns the parent of this state
     * @return - AState
     */
    public AState getParent() {
        return parent;
    }

    /**
     * Returns the cost of this state
     * @return - double
     */
    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof AState){
            return (int)(this.getCost()-((AState)o).getCost());
        }
        return 0;
    }
}
