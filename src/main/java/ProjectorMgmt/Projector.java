package ProjectorMgmt;

public class Projector {
    private final int id;
    private final String name;

    public Projector (int id) {
        this.id = id;
        this.name = new String("P" + Integer.valueOf(id));
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}