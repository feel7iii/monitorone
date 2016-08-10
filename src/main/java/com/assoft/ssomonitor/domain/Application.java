package com.assoft.ssomonitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.assoft.ssomonitor.domain.enumeration.Connway;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Document(indexName = "application")
//@JsonIgnoreProperties(value = {"computer"})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "appname", nullable = false)
    private String appname;

    @Column(name = "appdescription")
    private String appdescription;

    @Column(name = "appaccess")
    private String appaccess;

    @Column(name = "appusingport")
    private Integer appusingport;

    @Column(name = "appvolumepath")
    private String appvolumepath;

    @Column(name = "appconnpath")
    private String appconnpath;

    @Column(name = "appsysadmin")
    private String appsysadmin;

    @Column(name = "appsyspass")
    private String appsyspass;

    @Column(name = "appstatus")
    private String appstatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "appconnway")
    private Connway appconnway;

    @ManyToMany
    @JoinTable(name = "application_mmetadate",
               joinColumns = @JoinColumn(name="applications_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="mmetadates_id", referencedColumnName="ID"))
    private Set<Mmetadate> mmetadates = new HashSet<>();

    @ManyToOne()
    private Computer computer;

    @ManyToOne
    private Middleware middleware;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppdescription() {
        return appdescription;
    }

    public void setAppdescription(String appdescription) {
        this.appdescription = appdescription;
    }

    public String getAppaccess() {
        return appaccess;
    }

    public void setAppaccess(String appaccess) {
        this.appaccess = appaccess;
    }

    public Integer getAppusingport() {
        return appusingport;
    }

    public void setAppusingport(Integer appusingport) {
        this.appusingport = appusingport;
    }

    public String getAppvolumepath() {
        return appvolumepath;
    }

    public void setAppvolumepath(String appvolumepath) {
        this.appvolumepath = appvolumepath;
    }

    public String getAppconnpath() {
        return appconnpath;
    }

    public void setAppconnpath(String appconnpath) {
        this.appconnpath = appconnpath;
    }

    public String getAppsysadmin() {
        return appsysadmin;
    }

    public void setAppsysadmin(String appsysadmin) {
        this.appsysadmin = appsysadmin;
    }

    public String getAppsyspass() {
        return appsyspass;
    }

    public void setAppsyspass(String appsyspass) {
        this.appsyspass = appsyspass;
    }

    public String getAppstatus() {
        return appstatus;
    }

    public void setAppstatus(String appstatus) {
        this.appstatus = appstatus;
    }

    public Connway getAppconnway() {
        return appconnway;
    }

    public void setAppconnway(Connway appconnway) {
        this.appconnway = appconnway;
    }

    public Set<Mmetadate> getMmetadates() {
        return mmetadates;
    }

    public void setMmetadates(Set<Mmetadate> mmetadates) {
        this.mmetadates = mmetadates;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
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
        Application application = (Application) o;
        if(application.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, application.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + id +
            ", appname='" + appname + "'" +
            ", appdescription='" + appdescription + "'" +
            ", appaccess='" + appaccess + "'" +
            ", appusingport='" + appusingport + "'" +
            ", appvolumepath='" + appvolumepath + "'" +
            ", appconnpath='" + appconnpath + "'" +
            ", appsysadmin='" + appsysadmin + "'" +
            ", appsyspass='" + appsyspass + "'" +
            ", appstatus='" + appstatus + "'" +
            ", appconnway='" + appconnway + "'" +
            '}';
    }
}
