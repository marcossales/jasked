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

import br.dev.amvs.jasked.jpa.domain.QuestionCategory;
import br.dev.amvs.jasked.jpa.domain.User;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
import br.dev.amvs.jasked.jsf.util.PaginationHelper;
import br.dev.amvs.jasked.sessionbeans.QuestionCategoryFacade;
import br.dev.amvs.jasked.sessionbeans.RoleFacade;

@Named("questionCategoryController")
@SessionScoped
public class QuestionCategoryController extends BelongingToFaqSiteCrudPermissionVerifier<QuestionCategory> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionCategory current;
    @SuppressWarnings("rawtypes")
	private DataModel items = null;
    @EJB
    private br.dev.amvs.jasked.sessionbeans.QuestionCategoryFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    @Inject
    private SessionInfoController sessionInfoController;

    
    @EJB
    private RoleFacade roleFacade; 
    
    
    public QuestionCategoryController() {
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
    public QuestionCategory getSelected() {
        if (current == null) {
            current = new QuestionCategory();
            selectedItemIndex = -1;
        }
        return current;
    }

    private QuestionCategoryFacade getFacade() {
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},sessionInfoController.getUserInSession()));
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
        current = (QuestionCategory) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new QuestionCategory();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("QuestionCategoryCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (QuestionCategory) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("QuestionCategoryUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Messages").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (QuestionCategory) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Messages").getString("QuestionCategoryDeleted"));
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

    public QuestionCategory getQuestionCategory(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = QuestionCategory.class)
    public static class QuestionCategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionCategoryController controller = (QuestionCategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionCategoryController");
            return controller.getQuestionCategory(getKey(value));
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
            if (object instanceof QuestionCategory) {
                QuestionCategory o = (QuestionCategory) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + QuestionCategory.class.getName());
            }
        }

    }

    
	@Override
	protected String getCreateRoleName() {
		return "CREATE_QUESTION_CATEGORY";
	}

	@Override
	protected String getReadRoleName() {
		return "READ_QUESTION_CATEGORY";
	}

	@Override
	protected String getUpdateRoleName() {
		return "UPDATE_QUESTION_CATEGORY";
	}

	@Override
	protected String getDeleteRoleName() {
		return "DELETE_QUESTION_CATEGORY";
	}

	@Override
	public RoleFacade getRoleFacade() {
		return this.roleFacade;
	}

	@Override
	protected String getAccessDeniedPageOutcome() {
		return "/admin/denied";
	}
}
