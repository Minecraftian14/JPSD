package in.mcxiv.jpsd.data.path;

public class PathRecord {

    protected Selector selector;

    public PathRecord(Selector selector) {
        this.selector = selector;
    }

    @Override
    public String toString() {
        return "PathRecord{" +
                "selector=" + selector +
                '}';
    }
}
