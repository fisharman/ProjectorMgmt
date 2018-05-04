package ProjectorMgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


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
    
    public ObjectNode setReservation (LocalDateTime startDT, LocalDateTime endDT) {
        ObjectNode objectNode = mapper.createObjectNode();
        
        BookingSlot bookingSlot = new BookingSlot(startDT, endDT);

        if (checkSchedule(bookingSlot, projectorStatus, startDT, endDT)){
            int id = counter.incrementAndGet();
            bookings.put(id, bookingSlot);

            objectNode.put("type", "reservation_success" );
            objectNode.put("id", Long.toString(id) );
            objectNode.put("name", bookingSlot.getName());
            objectNode.put("date", bookingSlot.getStart().toLocalDate().toString());
            objectNode.put("startTime", bookingSlot.getStart().toLocalTime().toString());
            objectNode.put("endTime", bookingSlot.getEnd().toLocalTime().toString());

            return objectNode;
        }

        return null;

    }

    public ObjectNode getReservation (int id) {
        BookingSlot bookingSlot = bookings.get(id);

        if (bookingSlot == null)
            return null;

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", bookingSlot.getName());
        objectNode.put("date", bookingSlot.getStart().toLocalDate().toString());
        objectNode.put("startTime", bookingSlot.getStart().toLocalTime().toString());
        objectNode.put("endTime", bookingSlot.getEnd().toLocalTime().toString());
            
        return objectNode;
        
    }

    public boolean removeReservation (int id) {
        
        BookingSlot bookingSlot = bookings.get(id);
        if (bookingSlot == null)
            return false;
            
        ArrayList<BookingSlot> bookingArray = projectorStatus.get(bookingSlot.getName());
        boolean removed = bookingArray.remove(bookingSlot);
        bookings.remove(id);
        
        return true;
    }
}
