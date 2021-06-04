package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.amvs.jasked.dbfacade.UserFacade;
import br.dev.amvs.jasked.exception.DatabaseException;
import br.dev.amvs.jasked.exception.UnexpectedBehaivorException;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.security.util.SecurityUtil;

@Named("loginController")
@RequestScoped
public class LoginController implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	 @Inject
	 private br.dev.amvs.jasked.dbfacade.UserFacade ejbFacade;
	 
	 @Inject
	 private SessionInfoController sessionInfoController;

    public LoginController() {
    }

    
    
	public String login() {
		User foundUser;
		try {
			foundUser = searchUser(this.userName,this.password);
			if (foundUser!=null) {
				
				sessionInfoController.putUserInSession(foundUser);
				return "/admin/index?faces-redirect=true";
			} else {
				JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Messages").getString("InvalidUsernameOrPassoword"));
			}
			
		} catch (UnexpectedBehaivorException e) {
			JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Messages").getString("UnexpectedBehaivor"));
			e.printStackTrace();
			
		}
		return null;
		
	}
    
    private User searchUser(String username, String passwd) throws UnexpectedBehaivorException{
    	
    		User found;
			try {
				found = getFacade().findByUserNameAndPassword(username, SecurityUtil.passwordHash(passwd));
				return found;
			} catch (DatabaseException e) {
				e.printStackTrace();
				throw new UnexpectedBehaivorException(e);
				
			}
		
		
		
	}



	


	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	};
    
   
   private UserFacade getFacade() {
	   return this.ejbFacade;
   }
    
   
   public  String getPageTitle() {
	   return "Login";
   }
    

   
   
   public String getLoginButtonLabel() {
	   return ResourceBundle.getBundle("/Messages").getString("LoginPage_loginButtonLabel");
   }
   public String getPasswordFieldLabel() {
	   return ResourceBundle.getBundle("/Messages").getString("LoginPage_passwordFieldLabel");
   }
   public String getUserNameFieldLabel() {
	   return ResourceBundle.getBundle("/Messages").getString("LoginPage_userNameFieldLabel");
   }
   
  
}
