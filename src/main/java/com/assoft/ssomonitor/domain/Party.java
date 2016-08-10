package com.assoft.ssomonitor.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Document(indexName = "party")
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "parent_id", nullable = false)
    private String parentId;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "unique_name", nullable = false)
    private String uniqueName;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "security_level")
    private String securityLevel;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "modify_time")
    private ZonedDateTime modifyTime;

    @Column(name = "manage_by")
    private String manageBy;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(ZonedDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getManageBy() {
        return manageBy;
    }

    public void setManageBy(String manageBy) {
        this.manageBy = manageBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Party party = (Party) o;
        if(party.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, party.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Party{" +
            "id=" + id +
            ", parentId='" + parentId + "'" +
            ", path='" + path + "'" +
            ", name='" + name + "'" +
            ", uniqueName='" + uniqueName + "'" +
            ", description='" + description + "'" +
            ", position='" + position + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", securityLevel='" + securityLevel + "'" +
            ", createTime='" + createTime + "'" +
            ", modifyTime='" + modifyTime + "'" +
            ", manageBy='" + manageBy + "'" +
            '}';
    }
}
