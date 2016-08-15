package com.assoft.ssomonitor.web.rest.dto;

/**
 * A DTO representing a user, with his authorities.
 */
public class PartyDTO {

    private Long id;

    private String parentId;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PartyDTO{" +
            "id=" + id +
            ", parentId='" + parentId + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
