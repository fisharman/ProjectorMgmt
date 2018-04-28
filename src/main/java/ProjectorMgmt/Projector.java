package ProjectorMgmt;

public class Projector {
    private final String name;

    public Projector (int id) {
        this.name = new String("P" + Integer.valueOf(id));
    }

    public String getName() {
        return this.name;
    }

}