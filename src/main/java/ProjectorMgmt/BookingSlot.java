package ProjectorMgmt;

import java.time.LocalDateTime;

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

}