package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

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
