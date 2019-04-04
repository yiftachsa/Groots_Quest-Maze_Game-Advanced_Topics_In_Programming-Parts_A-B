package algorithms.search;

public abstract class AState implements Comparable {

    private AState parent;
    private double cost;

    public AState(AState parent, double cost) {
        this.parent = parent;
        this.cost = cost;
    }

    public void setParent(AState parent) {
        this.parent = parent;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public AState getParent() {
        return parent;
    }

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
