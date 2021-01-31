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
@Table(name = "question_category", schema="jasked")
@NamedQueries({
    @NamedQuery(name = "QuestionCategory.findAll", query = "SELECT q FROM QuestionCategory q"),
    @NamedQuery(name = "QuestionCategory.findById", query = "SELECT q FROM QuestionCategory q WHERE q.id = :id"),
    @NamedQuery(name = "QuestionCategory.findByName", query = "SELECT q FROM QuestionCategory q WHERE q.name = :name"),
    @NamedQuery(name = "QuestionCategory.findByDescription", query = "SELECT q FROM QuestionCategory q WHERE q.description = :description")})
public class QuestionCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "questionCategory")
    private Collection<Question> questionCollection;
    
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FaqSite faqSite;

    public QuestionCategory() {
    }

    public QuestionCategory(Integer id) {
        this.id = id;
    }

    public QuestionCategory(Integer id, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    public FaqSite getFaqSite() {
        return faqSite;
    }

    public void setFaqSite(FaqSite faqSite) {
        this.faqSite = faqSite;
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
        if (!(object instanceof QuestionCategory)) {
            return false;
        }
        QuestionCategory other = (QuestionCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.dev.amvs.jasked.jpa.domain.QuestionCategory[ id=" + id + " ]";
    }
    
}
