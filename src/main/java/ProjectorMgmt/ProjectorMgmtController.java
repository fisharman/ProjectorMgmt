package ProjectorMgmt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class ProjectorMgmtController {
    private final int EQUIPMENT_COUNT = 10;
    private ArrayList<Projector> projectors;

    public ProjectorMgmtController() {
        projectors = new ArrayList<>(EQUIPMENT_COUNT);
        for (int i = 0; i < EQUIPMENT_COUNT; i++){
            this.projectors.add(new Projector(i));
        }
    }

    @RequestMapping(value = "/projectors", method = RequestMethod.GET)
    public ArrayList<Projector> listProjectors() {
        return this.projectors;
    }

    /*
    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public ArrayList<Projector> listProjectors() {
        return this.projectors;
    }

    @RequestMapping(value = "/reservation/:id", method = RequestMethod.GET)
    public ArrayList<Projector> listProjectors() {
        return this.projectors;
    }

    @RequestMapping(value = "/reservation/:id", method = RequestMethod.DELETE)
    public ArrayList<Projector> listProjectors() {
        return this.projectors;
    }
    */
}
