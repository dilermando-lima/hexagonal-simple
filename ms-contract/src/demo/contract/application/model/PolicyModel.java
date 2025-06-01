package demo.contract.application.model;

import java.util.List;

public class PolicyModel {

    private String id;
    private String name;
    private String description;
    private List<CoverageModel> coverages;

    public PolicyModel() {
    }

    public PolicyModel(String id, String name, String description, List<CoverageModel> coverages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverages = coverages;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<CoverageModel> getCoverages() {
        return coverages;
    }
    public void setCoverages(List<CoverageModel> coverages) {
        this.coverages = coverages;
    }


    


    
}
