package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.amvs.jasked.jpa.domain.FaqSite;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.jsf.util.PaginationHelper;
import br.dev.amvs.jasked.sessionbeans.FaqSiteFacade;

@Named("accessDeniedController")
@SessionScoped
public class AccessDeniedController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    

    public AccessDeniedController() {
    }

  public String getPageTitle() {
	  return ResourceBundle.getBundle("/Messages").getString("AccessDeniedPage_pageTitle");
}
  
  public String getAccessDeniedMessage() {
	  return ResourceBundle.getBundle("/Messages").getString("AccessDeniedPage_accessDeniedMessage");
  }
  public String getReturnToAdminHomePageMessage() {
	  return ResourceBundle.getBundle("/Messages").getString("AccessDeniedPage_returnToAdminHomePageMessage");
  }

}
