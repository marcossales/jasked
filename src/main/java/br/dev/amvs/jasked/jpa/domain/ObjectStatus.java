/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.jpa.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author marcossales
 */
@Entity
@Table(name = "object_status", schema = "jasked")
@NamedQueries({
    @NamedQuery(name = "ObjectStatus.findAll", query = "SELECT o FROM ObjectStatus o"),
    @NamedQuery(name = "ObjectStatus.findById", query = "SELECT o FROM ObjectStatus o WHERE o.id = :id"),
    @NamedQuery(name = "ObjectStatus.findByName", query = "SELECT o FROM ObjectStatus o WHERE o.name = :name")})
public class ObjectStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "objectStatus")
    private Collection<Question> questionCollection;
    
    @OneToMany(mappedBy = "objectStatus")
    private Collection<FaqSite> faqSiteCollection;

    public ObjectStatus() {
    }

    public ObjectStatus(Integer id) {
        this.id = id;
    }

    public ObjectStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    public Collection<FaqSite> getFaqSiteCollection() {
        return faqSiteCollection;
    }

    public void setFaqSiteCollection(Collection<FaqSite> faqSiteCollection) {
        this.faqSiteCollection = faqSiteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjectStatus)) {
            return false;
        }
        ObjectStatus other = (ObjectStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
}
