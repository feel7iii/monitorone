package com.assoft.ssomonitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.assoft.ssomonitor.domain.enumeration.Cstatus;

/**
 * 数据库服务                                                                       
 * 
 */
@ApiModel(description = ""
    + "数据库服务                                                                  "
    + "")
@Entity
@Table(name = "cserver")
@Document(indexName = "cserver")
public class Cserver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "csname", nullable = false)
    private String csname;

    @NotNull
    @Column(name = "csdescription", nullable = false)
    private String csdescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "cstartstatus")
    private Cstatus cstartstatus;

    @NotNull
    @Column(name = "cport", nullable = false)
    private Integer cport;

    @Column(name = "cstatusliveordie")
    private String cstatusliveordie;

    @OneToMany(mappedBy = "cserver")
    @JsonIgnore
    private Set<Mmetadate> mmetadates = new HashSet<>();

    @ManyToOne
    private Computer computer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCsname() {
        return csname;
    }

    public void setCsname(String csname) {
        this.csname = csname;
    }

    public String getCsdescription() {
        return csdescription;
    }

    public void setCsdescription(String csdescription) {
        this.csdescription = csdescription;
    }

    public Cstatus getCstartstatus() {
        return cstartstatus;
    }

    public void setCstartstatus(Cstatus cstartstatus) {
        this.cstartstatus = cstartstatus;
    }

    public Integer getCport() {
        return cport;
    }

    public void setCport(Integer cport) {
        this.cport = cport;
    }

    public String getCstatusliveordie() {
        return cstatusliveordie;
    }

    public void setCstatusliveordie(String cstatusliveordie) {
        this.cstatusliveordie = cstatusliveordie;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cserver cserver = (Cserver) o;
        if(cserver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cserver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cserver{" +
            "id=" + id +
            ", csname='" + csname + "'" +
            ", csdescription='" + csdescription + "'" +
            ", cstartstatus='" + cstartstatus + "'" +
            ", cport='" + cport + "'" +
            ", cstatusliveordie='" + cstatusliveordie + "'" +
            '}';
    }
}
