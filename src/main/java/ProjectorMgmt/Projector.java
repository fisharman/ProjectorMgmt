package ProjectorMgmt;

import com.fasterxml.jackson.annotation.JsonIgnore;

class Attributes {
    private String name;
 
    public Attributes(String name){
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

}

public class Projector {
    private final int id;
    private final Attributes attributes;
    private final String type;

    public Projector (int id) {
        this.type = new String("projector");
        this.id = id;
        this.attributes = new Attributes(new String("P" + Integer.valueOf(id)));
    }
    
    public Attributes getAttributes() {
        return this.attributes;
    }
    
    @JsonIgnore
    public String getName() {
        return this.attributes.getName();
    }

    public String getType() {
        return this.type;
    }
    
    public int getId() {
        return this.id;
    }

}