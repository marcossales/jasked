/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.jsf;

import br.dev.amvs.jasked.jpa.domain.FaqSite;
import br.dev.amvs.jasked.jsf.util.JsfUtil;

import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author marcossales
 */
@Named(value = "publicViewingController")
@RequestScoped
public class PublicViewingController {

    private String currentFaqPath;
   
    @EJB
    private br.dev.amvs.jasked.sessionbeans.FaqSiteFacade ejbFaqSiteFacade;

	private List<FaqSite> publishedFaqs;
    /**
     * Creates a new instance of PublicViewingController
     */
    public PublicViewingController() {
    }
    
    
    public void preRenderView(ComponentSystemEvent event) {
    	this.publishedFaqs = ejbFaqSiteFacade.findAllPublished();
    }
    
    public String getIndexPageTitle(){
        return ResourceBundle.getBundle("/Messages").getString("PublicIndexPage_title");
    }
    
    public String getIndexPageHeaderTitle(){
        return ResourceBundle.getBundle("/Messages").getString("PublicIndexPage_headerTitle");
    }
    public String getSelectFaqMessage(){
        return ResourceBundle.getBundle("/Messages").getString("PublicIndexPage_selectFaqMessage");
    }
    
    public SelectItem[] getPublishedFaqSitesSelectOne() {
        return JsfUtil.getSelectItems(this.publishedFaqs, false);
    }
    public List<FaqSite> getPublishedFaqSites() {
    	return this.publishedFaqs; 
    }
     
    public String getNoPublishedItemsMessage() {
    	return ResourceBundle.getBundle("/Messages").getString("PublicIndexPage_noPublishedItems");
    }
      
      
      
    public String getCurrentFaqPath() {
        return currentFaqPath;
    }

    public void setCurrentFaqPath(String currentFaqPath) {
        this.currentFaqPath = currentFaqPath;
    }
    
    public boolean isCurrentFaqPathEmpty(){
        if(this.currentFaqPath==null || "".equals(this.currentFaqPath)){
            return true;
        }
        return false;
    }
    public boolean isPublishedFaqSitesEmpty() {
    	if(this.publishedFaqs.isEmpty()){
            return true;
        }
        return false;
    }
}
