package com.openspringlab.ecommerce.dto.categorydto;

public class UpdateCategoryRequest {

    private String name;

    private String description;

    private Boolean active;

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Boolean active() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UpdateCategoryRequest()
    {}
}
