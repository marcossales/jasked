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
import javax.inject.Named;

import br.dev.amvs.jasked.jpa.domain.Permission;
import br.dev.amvs.jasked.jpa.domain.PermissionPK;
import br.dev.amvs.jasked.jpa.domain.Role;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.jsf.util.PaginationHelper;
import br.dev.amvs.jasked.sessionbeans.PermissionFacade;

@Named("permissionController")
@SessionScoped
public class PermissionController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Permission current;
    @SuppressWarnings("rawtypes")
	private DataModel items = null;
    @EJB
    private br.dev.amvs.jasked.sessionbeans.PermissionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    
    @EJB
    private br.dev.amvs.jasked.sessionbeans.UserFacade ejbUserFacade;
    @EJB
    private br.dev.amvs.jasked.sessionbeans.FaqSiteFacade ejbFaqSiteFacade;
    @EJB
    private br.dev.amvs.jasked.sessionbeans.RoleFacade ejbRoleFacade;
    

    public PermissionController() {
    }

    public Permission getSelected() {
        if (current == null) {
            current = new Permission();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PermissionFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
                public DataModel createPageDataModel() {
                	List<Permission> permissions = getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()});
                	populatePermissionsTransientFields(permissions);
                    return new ListDataModel(permissions);
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Permission) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Permission();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try { 
        	current.setId(new PermissionPK(current.getTransientUser().getId(),current.getTransientFaqSite().getId(),current.getTransientRole().getId()));
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("PermissionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Permission) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("PermissionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Permission) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("PermissionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
            populatePermissionTransientFields(current);
        }
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
   

   
    public Permission getPermission(PermissionPK id) {
        return ejbFacade.find(id);
    }

    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = Permission.class)
    public static class PermissionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PermissionController controller = (PermissionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "permissionController");
            return controller.getPermission(getKey(value));
        }

        /**
         * 
         * @param value A String in the format "userId_faqSiteId_roleId"
         * @return
         */
        PermissionPK getKey(String value) {
        	PermissionPK key = null;
        	String ids[] = value.split("_");
        	key = new PermissionPK();
        	key.setUserId(Integer.valueOf(ids[0]));
        	key.setFaqSiteId(Integer.valueOf(ids[1]));
        	key.setRoleId(Integer.valueOf(ids[2]));
            
            return key;
        }

        String getStringKey(PermissionPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserId());
            sb.append("_");
            sb.append(value.getFaqSiteId());
            sb.append("_");
            sb.append(value.getRoleId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Permission) {
            	Permission o = (Permission) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Permission.class.getName());
            }
        }

    }
    
    
    
    public br.dev.amvs.jasked.sessionbeans.UserFacade getUserFacade() {
		return ejbUserFacade;
	}

	public br.dev.amvs.jasked.sessionbeans.FaqSiteFacade getFaqSiteFacade() {
		return ejbFaqSiteFacade;
	}

	public br.dev.amvs.jasked.sessionbeans.RoleFacade getRoleFacade() {
		return ejbRoleFacade;
	}

	private void populatePermissionsTransientFields(List<Permission> permissions) {
		for(Permission p:permissions) {
			p.setTransientUser(getUserFacade().find(p.getId().getUserId()));
			p.setTransientFaqSite(getFaqSiteFacade().find(p.getId().getFaqSiteId()));
			p.setTransientRole(getRoleFacade().find(p.getId().getRoleId()));
		}
		
	}
	private void populatePermissionTransientFields(Permission p) {
			p.setTransientUser(getUserFacade().find(p.getId().getUserId()));
			p.setTransientFaqSite(getFaqSiteFacade().find(p.getId().getFaqSiteId()));
			p.setTransientRole(getRoleFacade().find(p.getId().getRoleId()));
		
	}
	
	//Since the Role entity does not have a controller bean (since there is no CRUD for it), 
	//some code are right here:
	public Role getRole(Integer id) {
        return ejbRoleFacade.find(id);
    }
	 public SelectItem[] getRoleItemsAvailableSelectOne() {
	        return JsfUtil.getSelectItems(ejbRoleFacade.findAll(), true);
	 }
	 @SuppressWarnings("rawtypes")
		@FacesConverter(forClass = Role.class)
	    public static class RoleControllerConverter implements Converter {

	        @Override
	        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	            if (value == null || value.length() == 0) {
	                return null;
	            }
	            
	            PermissionController controller = (PermissionController) facesContext.getApplication().getELResolver().
	                    getValue(facesContext.getELContext(), null, "permissionController");
	            return controller.getRole(getKey(value));
	        }

	        /**
	         * 
	         * @param value A String in the format "userId_faqSiteId_roleId"
	         * @return
	         */
	        Integer getKey(String value) {
	        	  java.lang.Integer key;
	              key = Integer.valueOf(value);
	              return key;
	        }

	        String getStringKey(Integer value) {
	        	 StringBuilder sb = new StringBuilder();
	             sb.append(value);
	             return sb.toString();
	        }

	        @Override
	        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
	            if (object == null) {
	                return null;
	            }
	            if (object instanceof Role) {
	            	Role o = (Role) object;
	                return getStringKey(o.getId());
	            } else {
	                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Role.class.getName());
	            }
	        }

	    }
	 //end code for Role entity
	
	
    
}
