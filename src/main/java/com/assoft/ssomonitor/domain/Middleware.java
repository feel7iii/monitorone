package com.assoft.ssomonitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.assoft.ssomonitor.domain.enumeration.Midtype;

/**
 * A Middleware.
 */
@Entity
@Table(name = "middleware")
@Document(indexName = "middleware")
public class Middleware implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "midname", nullable = false)
    private String midname;

    @NotNull
    @Column(name = "midport", nullable = false)
    private String midport;

    @NotNull
    @Column(name = "midpath", nullable = false)
    private String midpath;

    @Column(name = "midmoreinfo")
    private String midmoreinfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "midtype")
    private Midtype midtype;

    @OneToMany(mappedBy = "middleware")
    @JsonIgnore
    private Set<Mmetadate> mmetadates = new HashSet<>();

    @OneToMany(mappedBy = "middleware")
    @JsonIgnore
    private Set<Application> applications = new HashSet<>();

    @ManyToOne
    private Computer computer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getMidport() {
        return midport;
    }

    public void setMidport(String midport) {
        this.midport = midport;
    }

    public String getMidpath() {
        return midpath;
    }

    public void setMidpath(String midpath) {
        this.midpath = midpath;
    }

    public String getMidmoreinfo() {
        return midmoreinfo;
    }

    public void setMidmoreinfo(String midmoreinfo) {
        this.midmoreinfo = midmoreinfo;
    }

    public Midtype getMidtype() {
        return midtype;
    }

    public void setMidtype(Midtype midtype) {
        this.midtype = midtype;
    }

    public Set<Mmetadate> getMmetadates() {
        return mmetadates;
    }

    public void setMmetadates(Set<Mmetadate> mmetadates) {
        this.mmetadates = mmetadates;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Middleware middleware = (Middleware) o;
        if(middleware.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, middleware.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Middleware{" +
            "id=" + id +
            ", midname='" + midname + "'" +
            ", midport='" + midport + "'" +
            ", midpath='" + midpath + "'" +
            ", midmoreinfo='" + midmoreinfo + "'" +
            ", midtype='" + midtype + "'" +
            '}';
    }
}
