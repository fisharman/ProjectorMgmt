package ProjectorMgmt;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

class DataResponse {

    private ObjectNode data;

    public DataResponse (ObjectNode data){
        this.data = data;
    }

    public ObjectNode getData () {
        return this.data;
    }

}


@RestController
public class ProjectorMgmtController {

    @Autowired
    Database db;

    @RequestMapping(value = "/projectors", method = RequestMethod.GET)
    public ArrayDataResponse listProjectors() {
        return (new ArrayDataResponse(db.getEquipmentList()));
    }

/*
    @RequestMapping(value = "/reservation", method = RequestMethod.GET)
    public DataResponse addReservation() {
        return (new DataResponse(db.addReservation()));

    }

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.GET)
    public DataResponse getReservation (@PathVariable("id") int id) {

    }

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.DELETE)
    public DataResponse deleteReservation(@PathVariable("id") int id) {

    }
*/
}
