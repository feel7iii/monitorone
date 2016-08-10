package com.assoft.ssomonitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.assoft.ssomonitor.domain.enumeration.Msqlctype;

/**
 * A Mmetadate.
 */
@Entity
@Table(name = "mmetadate")
@Document(indexName = "mmetadate")
public class Mmetadate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mcname")
    private String mcname;

    @Column(name = "mcdescription")
    private String mcdescription;

    @Column(name = "mcsidordatabasename")
    private String mcsidordatabasename;

    @Column(name = "mcusername")
    private String mcusername;

    @Column(name = "mcpassword")
    private String mcpassword;

    @Column(name = "mcport")
    private String mcport;

    @Column(name = "msconnurl")
    private String msconnurl;

    @Enumerated(EnumType.STRING)
    @Column(name = "mcsqltype")
    private Msqlctype mcsqltype;

    @ManyToOne
    private Cserver cserver;

    @ManyToMany(mappedBy = "mmetadates")
    @JsonIgnore
    private Set<Application> applications = new HashSet<>();

    @ManyToOne
    private Middleware middleware;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMcname() {
        return mcname;
    }

    public void setMcname(String mcname) {
        this.mcname = mcname;
    }

    public String getMcdescription() {
        return mcdescription;
    }

    public void setMcdescription(String mcdescription) {
        this.mcdescription = mcdescription;
    }

    public String getMcsidordatabasename() {
        return mcsidordatabasename;
    }

    public void setMcsidordatabasename(String mcsidordatabasename) {
        this.mcsidordatabasename = mcsidordatabasename;
    }

    public String getMcusername() {
        return mcusername;
    }

    public void setMcusername(String mcusername) {
        this.mcusername = mcusername;
    }

    public String getMcpassword() {
        return mcpassword;
    }

    public void setMcpassword(String mcpassword) {
        this.mcpassword = mcpassword;
    }

    public String getMcport() {
        return mcport;
    }

    public void setMcport(String mcport) {
        this.mcport = mcport;
    }

    public String getMsconnurl() {
        return msconnurl;
    }

    public void setMsconnurl(String msconnurl) {
        this.msconnurl = msconnurl;
    }

    public Msqlctype getMcsqltype() {
        return mcsqltype;
    }

    public void setMcsqltype(Msqlctype mcsqltype) {
        this.mcsqltype = mcsqltype;
    }

    public Cserver getCserver() {
        return cserver;
    }

    public void setCserver(Cserver cserver) {
        this.cserver = cserver;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Middleware getMiddleware() {
        return middleware;
    }

    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mmetadate mmetadate = (Mmetadate) o;
        if(mmetadate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mmetadate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mmetadate{" +
            "id=" + id +
            ", mcname='" + mcname + "'" +
            ", mcdescription='" + mcdescription + "'" +
            ", mcsidordatabasename='" + mcsidordatabasename + "'" +
            ", mcusername='" + mcusername + "'" +
            ", mcpassword='" + mcpassword + "'" +
            ", mcport='" + mcport + "'" +
            ", msconnurl='" + msconnurl + "'" +
            ", mcsqltype='" + mcsqltype + "'" +
            '}';
    }
}
