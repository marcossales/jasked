package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.Role;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.jsf.util.PaginationHelper;


@Named("sessionDataController")
@RequestScoped
public class SessionDataController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionData current;
    @SuppressWarnings("rawtypes")
	private DataModel items = null;
    
    private PaginationHelper pagination;
    @SuppressWarnings("unused")
	private int selectedItemIndex;
    
    
    private User user;
    @Inject
	private br.dev.amvs.jasked.dbfacade.RoleFacade roleFacade;
    
    @Inject
    private SessionInfoController sessionInfoController;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	 @PostConstruct
	    public void init() {
	    	this.user = sessionInfoController.getUserInSessionWithPermissions();
	    	reloadOpenSessions();
	    }
    
    
    @Inject
    private AppInfoController appInfoController;
    
    private List<SessionData> openSessions;

    public SessionDataController() {
    }

    public SessionData getSelected() {
        if (current == null) {
            current = new SessionData();
            selectedItemIndex = -1;
        }
        return current;
    }

 
    
   

    public String refresh() {
    	reloadOpenSessions();
    	recreatePagination();
    	return prepareList();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void reloadOpenSessions() {
    	Map<String,SessionData> sessions = appInfoController.getOpenSessions();
    	this.openSessions = new ArrayList(sessions.values());
		
	}
    

	public PaginationHelper getPagination() {
		
        if (pagination == null) {
        
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return openSessions.size();
                }

                @SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
                public DataModel createPageDataModel() {
                	int endIndex =  getPageFirstItem() + getPageSize() ;
                	if(endIndex >  openSessions.size()) {
                		endIndex = openSessions.size();
                	}
                    return new ListDataModel(openSessions.subList(getPageFirstItem(), endIndex));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

   



    @SuppressWarnings("rawtypes")
	public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

  

    public SessionData getSessionData(String id) {
    	for(SessionData s: openSessions) {
    		if(id.equals(s.getSessionId())) {
    			return s;
    		}
    	}
        return null;
    }

    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = SessionData.class)
    public static class SessionDataControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SessionDataController controller = (SessionDataController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sessionDataController");
            return controller.getSessionData(value);
        }

        

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SessionData) {
            	SessionData o = (SessionData) object;
                return o.getSessionId();
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SessionData.class.getName());
            }
        }

    }
    
    
    @SuppressWarnings("rawtypes")
    @FacesConverter(forClass = SessionData.class)
    public static  class SessionDataConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SessionDataController controller = (SessionDataController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sessionDataController");
            return controller.getSessionData(value);
        }


        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SessionData) {
            	SessionData o = (SessionData) object;
                return o.getSessionId();
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SessionData.class.getName());
            }
        }

    }
    
	public boolean isCanRead() {
		User u = getUser();
    	if(u.isSuperUser()) {
    		return true;
    	}
		Role role = roleFacade.findByExactName("READ_SESSION_DATA"); 
		for(Permission p: getUser().getPermissions()) {
			 if( role.equals(p.getRole())   ) {
				 return true;
			 }
		 }
		return false;
	}
	
    public void checkPreRenderList(ComponentSystemEvent event) {
    	if(!isCanRead() ) {
    		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
    		.handleNavigation(FacesContext.getCurrentInstance(), null, getAccessDeniedPageOutcome());
    	}
    }

	private String getAccessDeniedPageOutcome() {
		return "/admin/denied";
	}

}
