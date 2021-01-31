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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "faq_site", schema = "jasked")
@NamedQueries({
    @NamedQuery(name = "FaqSite.findAll", query = "SELECT f FROM FaqSite f"),
    @NamedQuery(name = "FaqSite.findById", query = "SELECT f FROM FaqSite f WHERE f.id = :id"),
    @NamedQuery(name = "FaqSite.findByName", query = "SELECT f FROM FaqSite f WHERE f.name = :name"),
    @NamedQuery(name = "FaqSite.findByPath", query = "SELECT f FROM FaqSite f WHERE f.path = :path")})
public class FaqSite implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "path")
    private String path;
    
    @OneToMany(mappedBy = "faqSite")
    private Collection<QuestionCategory> questionCategoryCollection;
    
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ObjectStatus objectStatus;

    public FaqSite() {
    }

    public FaqSite(Integer id) {
        this.id = id;
    }

    public FaqSite(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Collection<QuestionCategory> getQuestionCategoryCollection() {
        return questionCategoryCollection;
    }

    public void setQuestionCategoryCollection(Collection<QuestionCategory> questionCategoryCollection) {
        this.questionCategoryCollection = questionCategoryCollection;
    }

    public ObjectStatus getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(ObjectStatus objectStatus) {
        this.objectStatus = objectStatus;
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
        if (!(object instanceof FaqSite)) {
            return false;
        }
        FaqSite other = (FaqSite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.dev.amvs.jasked.jpa.domain.FaqSite[ id=" + id + " ]";
    }
    
}
