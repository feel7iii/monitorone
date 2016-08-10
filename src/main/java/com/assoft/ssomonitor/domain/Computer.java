package com.assoft.ssomonitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.hibernate.annotations.Columns;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

import com.assoft.ssomonitor.domain.enumeration.Powersupply;

import com.assoft.ssomonitor.domain.enumeration.Secretornot;

/**
 * A Computer.
 */
@Entity
@Table(name = "computer")
@Document(indexName = "computer")
public class Computer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "cpname", nullable = false)
    private String cpname;

    @Column(name = "cpmoreinfo")
    private String cpmoreinfo;

    @Column(name = "cposx")
    private String cposx;

    @Column(name = "cpedition")
    private String cpedition;

    @Column(name = "cploginname")
    private String cploginname;

    @Column(name = "cploginpassword")
    private String cploginpassword;

    @Column(name = "cpip")
    private String cpip;

    @Column(name = "cplocation")
    private String cplocation;

    @Column(name = "cpnetwordarea")
    private String cpnetwordarea;

    @Column(name = "cpstatus")
    private String cpstatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "cppowersupply")
    private Powersupply cppowersupply;

    @Enumerated(EnumType.STRING)
    @Column(name = "cpsecretornot")
    private Secretornot cpsecretornot;

    @OneToMany(mappedBy = "computer")
    private Set<Middleware> middlewares = new HashSet<>();

    @OneToMany(mappedBy = "computer")
    private Set<Cserver> cservers = new HashSet<>();

    @OneToMany(mappedBy = "computer")
    private Set<Application> applications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getCpmoreinfo() {
        return cpmoreinfo;
    }

    public void setCpmoreinfo(String cpmoreinfo) {
        this.cpmoreinfo = cpmoreinfo;
    }

    public String getCposx() {
        return cposx;
    }

    public void setCposx(String cposx) {
        this.cposx = cposx;
    }

    public String getCpedition() {
        return cpedition;
    }

    public void setCpedition(String cpedition) {
        this.cpedition = cpedition;
    }

    public String getCploginname() {
        return cploginname;
    }

    public void setCploginname(String cploginname) {
        this.cploginname = cploginname;
    }

    public String getCploginpassword() {
        return cploginpassword;
    }

    public void setCploginpassword(String cploginpassword) {
        this.cploginpassword = cploginpassword;
    }

    public String getCpip() {
        return cpip;
    }

    public void setCpip(String cpip) {
        this.cpip = cpip;
    }

    public String getCplocation() {
        return cplocation;
    }

    public void setCplocation(String cplocation) {
        this.cplocation = cplocation;
    }

    public String getCpnetwordarea() {
        return cpnetwordarea;
    }

    public void setCpnetwordarea(String cpnetwordarea) {
        this.cpnetwordarea = cpnetwordarea;
    }

    public String getCpstatus() {
        return cpstatus;
    }

    public void setCpstatus(String cpstatus) {
        this.cpstatus = cpstatus;
    }

    public Powersupply getCppowersupply() {
        return cppowersupply;
    }

    public void setCppowersupply(Powersupply cppowersupply) {
        this.cppowersupply = cppowersupply;
    }

    public Secretornot getCpsecretornot() {
        return cpsecretornot;
    }

    public void setCpsecretornot(Secretornot cpsecretornot) {
        this.cpsecretornot = cpsecretornot;
    }

    public Set<Middleware> getMiddlewares() {
        return middlewares;
    }

    public void setMiddlewares(Set<Middleware> middlewares) {
        this.middlewares = middlewares;
    }

    public Set<Cserver> getCservers() {
        return cservers;
    }

    public void setCservers(Set<Cserver> cservers) {
        this.cservers = cservers;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Computer computer = (Computer) o;
        if (computer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, computer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Computer{" +
            "id=" + id +
            ", cpname='" + cpname + "'" +
            ", cpmoreinfo='" + cpmoreinfo + "'" +
            ", cposx='" + cposx + "'" +
            ", cpedition='" + cpedition + "'" +
            ", cploginname='" + cploginname + "'" +
            ", cploginpassword='" + cploginpassword + "'" +
            ", cpip='" + cpip + "'" +
            ", cplocation='" + cplocation + "'" +
            ", cpnetwordarea='" + cpnetwordarea + "'" +
            ", cpstatus='" + cpstatus + "'" +
            ", cppowersupply='" + cppowersupply + "'" +
            ", cpsecretornot='" + cpsecretornot + "'" +
            '}';
    }
}
