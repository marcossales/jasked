/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.amvs.jasked.jsf;

import br.dev.amvs.jasked.jpa.domain.FaqSite;
import br.dev.amvs.jasked.jpa.domain.Question;
import br.dev.amvs.jasked.jpa.domain.QuestionCategory;
import br.dev.amvs.jasked.jsf.util.JsfUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
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
    @EJB
    private br.dev.amvs.jasked.sessionbeans.QuestionCategoryFacade ejbQuestionCategoryFacade;
    @EJB
    private br.dev.amvs.jasked.sessionbeans.QuestionFacade ejbQuestionFacade;

	private List<FaqSite> publishedFaqs;
	
	@Inject
	private SessionInfoController sessionInfoController;
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
    public String getExpiredPageTitle(){
        return ResourceBundle.getBundle("/Messages").getString("PublicExpiredPage_title");
    }
    public String getExpiredPageMessage(){
        return ResourceBundle.getBundle("/Messages").getString("PublicExpiredPage_message");
    }
    
    public String getIndexPageAllPublishedFaqMessage(){
        return ResourceBundle.getBundle("/Messages").getString("PublicIndexPage_allPublishedFaq");
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
    
    public List<QuestionCategory> getQuestionCategories(){
    	
    	List<QuestionCategory> categories = ejbQuestionCategoryFacade.findByFaqPath(this.currentFaqPath,true);
    	return categories;
    }
    
    public List<QuestionCategory> getQuestionCategoriesWithQuestions(){
    	
    	List<QuestionCategory> categories = ejbQuestionCategoryFacade.findByFaqPath(this.currentFaqPath,true);
    	for(QuestionCategory qc: categories) {
    		List<Question> questions = ejbQuestionFacade.findByCategory(qc,true);
    		qc.setQuestionCollection(questions);
    	}
    	
    
    	return categories;
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
    
   
   
    
    
    public String getStyle() {
    	if(!isCurrentFaqPathEmpty()) {
    		FaqSite f = ejbFaqSiteFacade.findByFaqPath(this.currentFaqPath, true);
    		if(f.getStyle()!=null && !f.getStyle().isEmpty()) {
    			StringBuilder sb = new StringBuilder();
    			sb.append("<style>");
    			sb.append(f.getStyle());
    			sb.append("</style>");
    			return sb.toString();
    		}
    	}
    	
    	
    	return getContentFromDefautStyle();
    	
    }


	private String getContentFromDefautStyle() {
		InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("resources/css/default-public-theme.css");
   	 StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        
        String line;
        try {
        	sb.append("<style>");
			while ((line = br.readLine()) != null) {
			     sb.append(line + System.lineSeparator());
			 }
			sb.append("</style>");
			return sb.toString();
		} catch (IOException e) {
			
			e.printStackTrace();
			return "";
		}
	}

   public boolean isCanShowTaskBar() {
	  String userNameInSession =  sessionInfoController.getUsernameInSession();
	  if(userNameInSession!=null && !userNameInSession.isEmpty() && !this.isCurrentFaqPathEmpty() ) {
		  return true;
	  }
	  return false;
   }
	
    
}

