package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TwineJson {

    ArrayList<Passage> passages;
    String name;
    String startnode;
    String creator;

    @JsonProperty("creator-version")
    String creatorVesrion;
    String ifid;

    public ArrayList<Passage> getPassages() {
        return passages;
    }

    public void setPassages(ArrayList<Passage> passages) {
        this.passages = passages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartnode() {
        return startnode;
    }

    public void setStartnode(String startnode) {
        this.startnode = startnode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorVesrion() {
        return creatorVesrion;
    }

    public void setCreatorVesrion(String creatorVesrion) {
        this.creatorVesrion = creatorVesrion;
    }

    public String getIfid() {
        return ifid;
    }

    public void setIfid(String ifid) {
        this.ifid = ifid;
    }
}
