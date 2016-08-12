package com.assoft.ssomonitor.web.rest.dto;

/**
 * A DTO representing a user, with his authorities.
 */
public class PartyDTO {

    private String key;

    private String parentId;

    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
            "key='" + key + '\'' +
            ", parentId='" + parentId + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
