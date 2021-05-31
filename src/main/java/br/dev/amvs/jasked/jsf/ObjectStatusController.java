package br.dev.amvs.jasked.jsf;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
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

import br.dev.amvs.jasked.jpa.domain.ObjectStatus;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.jsf.util.PaginationHelper;
import br.dev.amvs.jasked.sessionbeans.ObjectStatusFacade;
import br.dev.amvs.jasked.sessionbeans.RoleFacade;

@Named("objectStatusController")
@SessionScoped
public class ObjectStatusController extends BasicCrudPermissionVerifier<ObjectStatus> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObjectStatus current;
    @SuppressWarnings("rawtypes")
	private DataModel items = null;
    @EJB
    private br.dev.amvs.jasked.sessionbeans.ObjectStatusFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    @Inject
    private SessionInfoController sessionInfoController;
    
    @EJB
    private RoleFacade roleFacade; 

    public ObjectStatusController() {
    }
    
    private User user;

    @Override
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    @PostConstruct
    public void init() {
    	this.user = sessionInfoController.getUserInSessionWithPermissions();
    }

    public ObjectStatus getSelected() {
        if (current == null) {
            current = new ObjectStatus();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ObjectStatusFacade getFacade() {
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (ObjectStatus) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ObjectStatus();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("ObjectStatusCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ObjectStatus) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("ObjectStatusUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ObjectStatus) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("ObjectStatusDeleted"));
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

    public ObjectStatus getObjectStatus(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = ObjectStatus.class)
    public static class ObjectStatusControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ObjectStatusController controller = (ObjectStatusController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "objectStatusController");
            return controller.getObjectStatus(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ObjectStatus) {
                ObjectStatus o = (ObjectStatus) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ObjectStatus.class.getName());
            }
        }

    }

	@Override
	protected String getCreateRoleName() {
		return "CREATE_OBJECT_STATUS";
	}

	@Override
	protected String getReadRoleName() {
		return "READ_OBJECT_STATUS";
	}

	@Override
	protected String getUpdateRoleName() {
		return "UPDATE_OBJECT_STATUS";
	}

	@Override
	protected String getDeleteRoleName() {
		return "DELETE_OBJECT_STATUS";
	}

	@Override
	public RoleFacade getRoleFacade() {
		return this.roleFacade;
	}

	@Override
	protected String getAccessDeniedPageOutcome() {
		return "/admin/denied";
	}

	@Override
	public boolean canRead(ObjectStatus selected) {
		return isCanRead();
	}

	@Override
	public boolean canUpdate(ObjectStatus selected) {
		return isCanUpdate();
	}

	@Override
	public boolean canDelete(ObjectStatus selected) {
		return isCanDelete();
	}
    
   

}
