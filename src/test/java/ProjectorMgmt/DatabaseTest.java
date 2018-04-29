package ProjectorMgmt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseTest {
    private final int EQUIPMENT_COUNT = 10;
    private ArrayList<Projector> projectors;
    private Map<String, BookingSlot> bookings;
    private Map<String, ArrayList<BookingSlot>> projectorStatus;

    @Before
    public void setUp() throws Exception {
        bookings = new HashMap<>();
        projectorStatus = new HashMap<>();
        projectors = new ArrayList<>(EQUIPMENT_COUNT);

        for (int i = 1; i <= EQUIPMENT_COUNT; i++){
            Projector projector = new Projector(i);
            projectorStatus.putIfAbsent(projector.getName(), new ArrayList<>() );
            this.projectors.add(projector);
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getEquipmentList() {
    }

    @Test
    public void setReservation() {
    }

    @Test
    public void checkSchedule() {
        String date = new String("2018-04-28");
        String startTime = new String ("12:00");
        String endTime = new String("12:30");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate parsedD = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime parsedSDT = LocalDateTime.of(parsedD, LocalTime.parse(startTime, formatter));
        LocalDateTime parsedEDT = LocalDateTime.of(parsedD, LocalTime.parse(endTime, formatter));


        for (int i = 0; i < 10; i++){
        //    assertTrue(Database.checkSchedule(new BookingSlot(), projectorStatus, parsedSDT.plusMinutes(1), parsedEDT));
        }

        //assertFalse(Database.checkSchedule(objectNode, projectorStatus, parsedSDT.plusMinutes(1), parsedEDT));

    }
}