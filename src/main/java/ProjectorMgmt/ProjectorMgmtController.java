package ProjectorMgmt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class ArrayDataResponse {

    private ArrayList<Projector> data;

    public ArrayDataResponse (ArrayList<Projector> data){
        this.data = data;
    }

    public ArrayList<Projector> getData () {
        return this.data;
    }

}

@JsonInclude(JsonInclude.Include.NON_NULL)
class DataResponse {
    
    private ObjectNode data;
    private String error;

    public DataResponse (ObjectNode data, String error){
        this.data = data;
        this.error = error;
    }

    public ObjectNode getData () {
        return this.data;
    }
    
    public String getError () {
        return this.error;
    }

    
}

class ConvertDateTime {
    public static LocalDate convertD (String date) throws Exception {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public static LocalDateTime convertDT (String date, String time) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalDateTime.of(convertD(date), LocalTime.parse(time, formatter));
    }
}

@RestController
public class ProjectorMgmtController {

    @Autowired
    Database db;

    @RequestMapping(value = "/projectors", method = RequestMethod.GET)
    public ResponseEntity<ArrayDataResponse> listProjectors() {
        ArrayList<Projector> equipmentList = db.getEquipmentList();
        if (equipmentList != null) {
            return new ResponseEntity<>(new ArrayDataResponse(db.getEquipmentList()), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public ResponseEntity<DataResponse> addReservation(@RequestBody ObjectNode json) {
        String date, startT, endT;
        LocalDateTime parsedSDT, parsedEDT;
        
        try {
            JsonNode attributes = json.get("data").get("attributes");
            date = attributes.get("date").asText();
            startT = attributes.get("startTime").asText();
            endT = attributes.get("endTime").asText();
            
            parsedSDT = ConvertDateTime.convertDT(date, startT);
            parsedEDT = ConvertDateTime.convertDT(date, endT);
        }
        catch (Exception e){
            return new ResponseEntity<>(new DataResponse(null, "bad input"),HttpStatus.BAD_REQUEST);
        }
        
        ObjectNode reservation= db.setReservation(parsedSDT, parsedEDT);
        
        if (reservation != null)
            return new ResponseEntity<>(new DataResponse(reservation, null),HttpStatus.CREATED);
        
        return new ResponseEntity<>(new DataResponse(null, "reservation full"), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> retrieveReservation (@PathVariable("id") String id) {
        int parsedInt;
        try {
            parsedInt = Integer.valueOf(id);
        }
        catch (Exception e){
            return new ResponseEntity<>(new DataResponse(null, "bad input"),HttpStatus.BAD_REQUEST);
        }
        
        ObjectNode reservation = db.getReservation(parsedInt);
        if (reservation != null)
            return new ResponseEntity<>(new DataResponse(reservation, null),HttpStatus.OK);
        
        return new ResponseEntity<>(new DataResponse(null, "reservation not found"), HttpStatus.NOT_FOUND);
        
    }

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataResponse> deleteReservation(@PathVariable("id") String id) {
        int parsedInt;
        try {
            parsedInt = Integer.valueOf(id);
        }
        catch (Exception e){
            return new ResponseEntity<>(new DataResponse(null, "bad input"),HttpStatus.BAD_REQUEST); 
        }
        
        if (db.removeReservation(parsedInt))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
