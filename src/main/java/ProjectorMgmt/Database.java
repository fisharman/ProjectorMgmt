package ProjectorMgmt;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class Database {
    // simulate equipment database

    private final int EQUIPMENT_COUNT = 10;
    private ArrayList<Projector> projectors;

    public Database () {
        projectors = new ArrayList<>(EQUIPMENT_COUNT);
        for (int i = 1; i <= EQUIPMENT_COUNT; i++){
            this.projectors.add(new Projector(i));
        }
    }

    public ArrayList<Projector> getEquipmentList() {
        return this.projectors;
    }
/*
    public ObjectNode addReservation (int date, int startTime, int endTime) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        // convert date and time to datetime objects


        return objectNode;

    }
    // takes in data and start time, end time.
    // looks at the projector map and puts it in the first project with available timeslot

*/
}
