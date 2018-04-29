package ProjectorMgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class BookingSlot {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private String name;

    public static boolean isOverlapping(BookingSlot slot, LocalDateTime start2, LocalDateTime end2){
        return slot.getStart().isBefore(end2) && start2.isBefore(slot.getEnd());
    }

    public BookingSlot (LocalDateTime start, LocalDateTime end){
        this.start = start;
        this.end = end;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getStart () {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

/*
    @Override
    public int compareTo(BookingSlot b){
        // todo: check that end time is after start time
        if (this.getEnd().isBefore(b.getStart()))
            return -1;
        if (this.getStart().isAfter(b.getEnd()))
            return 1;
        return 0;
    }
*/
}

@SpringBootApplication
public class Database {
    // simulate equipment database

    private final int EQUIPMENT_COUNT = 10;
    private ArrayList<Projector> projectors;
    private Map<Integer, BookingSlot> bookings;
    private Map<String, ArrayList<BookingSlot>> projectorStatus;
    private final AtomicInteger counter = new AtomicInteger();
    private ObjectMapper mapper = new ObjectMapper();

    public Database () {
        bookings = new HashMap<>();
        projectorStatus = new HashMap<>();
        projectors = new ArrayList<>(EQUIPMENT_COUNT);

        for (int i = 1; i <= EQUIPMENT_COUNT; i++){
            Projector projector = new Projector(i);
            projectorStatus.putIfAbsent(projector.getName(), new ArrayList<>() );
            this.projectors.add(projector);
        }
    }

    public static boolean checkSchedule(BookingSlot bookingSlot, Map<String, ArrayList<BookingSlot>> projectorStatus,
                                        LocalDateTime parsedSDT, LocalDateTime parsedEDT)
    {
        for (Map.Entry<String, ArrayList<BookingSlot>> entry : projectorStatus.entrySet()){
            boolean overlap = false;

            ArrayList<BookingSlot> bookingArray = entry.getValue();
            for (BookingSlot slot : bookingArray){
                if(BookingSlot.isOverlapping(slot, parsedSDT, parsedEDT)){
                    overlap = true;
                    break;
                }
            }
            if (!overlap){
                bookingSlot.setName(entry.getKey());
                bookingArray.add(bookingSlot);
                return true;
            }
        }

        return false;
    }

    public ArrayList<Projector> getEquipmentList() {
        return this.projectors;
    }



    public ObjectNode setReservation (String date, String startTime, String endTime) {
        ObjectNode objectNode = mapper.createObjectNode();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate parsedD = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime parsedSDT = LocalDateTime.of(parsedD, LocalTime.parse(startTime, formatter));
        LocalDateTime parsedEDT = LocalDateTime.of(parsedD, LocalTime.parse(endTime, formatter));

        BookingSlot bookingSlot = new BookingSlot(parsedSDT, parsedEDT);

        if (checkSchedule(bookingSlot, projectorStatus, parsedSDT, parsedEDT)){
            // add reservation to reservation list
            int id = counter.incrementAndGet();
            bookings.put(id, bookingSlot);

            objectNode.put("reservation_id", Long.toString(id) );
            objectNode.put("name", bookingSlot.getName());
            objectNode.put("date", bookingSlot.getStart().toLocalDate().toString());
            objectNode.put("startTime", bookingSlot.getStart().toLocalTime().toString());
            objectNode.put("endTime", bookingSlot.getEnd().toLocalTime().toString());

            return objectNode;

        }
        objectNode.put("not available", "true");

        return objectNode;

    }

    public ObjectNode getReservation (int id) {
        // todo: return error if id is invalid
        ObjectNode objectNode = mapper.createObjectNode();
        BookingSlot bookingSlot = bookings.get(id);

        objectNode.put("name", bookingSlot.getName());
        objectNode.put("date", bookingSlot.getStart().toLocalDate().toString());
        objectNode.put("startTime", bookingSlot.getStart().toLocalTime().toString());
        objectNode.put("endTime", bookingSlot.getEnd().toLocalTime().toString());

        return objectNode;
    }

    public ObjectNode removeReservation (int id) {
        // todo: return error if id is invalid
        ObjectNode objectNode = mapper.createObjectNode();
        BookingSlot bookingSlot = bookings.get(id);

        ArrayList<BookingSlot> bookingArray = projectorStatus.get(bookingSlot.getName());
        boolean removed = bookingArray.remove(bookingSlot);
        bookings.remove(id);

        objectNode.put("removed", removed ? "yes":"no");

        return objectNode;
    }
}
