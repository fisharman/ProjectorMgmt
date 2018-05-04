package ProjectorMgmt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {
    private final int EQUIPMENT_COUNT = 10;
    private ArrayList<Projector> projectors;
    
    @Before
    public void setUp() throws Exception {
        projectors = new ArrayList<>(EQUIPMENT_COUNT);

        for (int i = 1; i <= EQUIPMENT_COUNT; i++){
            Projector projector = new Projector(i);
            this.projectors.add(projector);
        }
    }

    @After
    public void tearDown() throws Exception {
        projectors = null;
    }

    @Test
    public void getEquipmentList() {
        int i = 1;
        for (Projector projector : projectors){
            assertEquals(projector.getId(), i);
            assertEquals(projector.getName(), new String("P" + Integer.toString(i++)));
        }
    }

    @Test
    public void addBooking() throws Exception {
        String date = new String("2018-04-28");
        String startT = new String ("12:00");
        String endT = new String("13:00");

        LocalDateTime parsedSDT = ConvertDateTime.convertDT(date, startT);
        LocalDateTime parsedEDT = ConvertDateTime.convertDT(date, endT);

        BookingSlot bookingSlot = new BookingSlot(parsedSDT, parsedEDT);
        Map<String, ArrayList<BookingSlot>> mockProjectorStatus = new HashMap<>();
        mockProjectorStatus.put(projectors.get(0).getName(), new ArrayList<BookingSlot>());
        
        assertTrue(Database.checkSchedule(bookingSlot, mockProjectorStatus, parsedSDT, parsedEDT));
    }
    
    @Test
    public void checkBlockingSchedule() throws Exception {
        String date = new String("2018-04-28");
        String startT = new String ("12:00");
        String endT = new String("13:00");
        
        LocalDateTime parsedSDT = ConvertDateTime.convertDT(date, startT);
        LocalDateTime parsedEDT = ConvertDateTime.convertDT(date, endT);
        
        BookingSlot bookingSlot = new BookingSlot(parsedSDT, parsedEDT);
        Map<String, ArrayList<BookingSlot>> mockProjectorStatus = generateSameBooking(parsedSDT, parsedEDT);

        assertFalse(Database.checkSchedule(bookingSlot, mockProjectorStatus, parsedSDT, parsedEDT));
    }
    
    private Map<String, ArrayList<BookingSlot>> generateSameBooking (LocalDateTime startTime, LocalDateTime endTime) {
        Map<String, ArrayList<BookingSlot>> mockProjectorStatus = new HashMap<>();
        
        for (int i = 0; i < EQUIPMENT_COUNT; i++){
            ArrayList<BookingSlot> arrayList = new ArrayList<>();
            BookingSlot bookingSlot = new BookingSlot(startTime, endTime);
            arrayList.add(bookingSlot);
            mockProjectorStatus.putIfAbsent(projectors.get(i).getName(), arrayList);    
        }
        return mockProjectorStatus;
    }
}