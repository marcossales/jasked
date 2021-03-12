package br.dev.amvs.jasked.jpa.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_role_faq database table.
 * 
 */
@Entity
@Table(name="user_role_faq",schema = "jasked")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PermissionPK id;
	
	
	@Transient
	private User user;
	@Transient
	private FaqSite faqSite;
	@Transient
	private Role role;
	

	

	public Permission() {
	}

	public PermissionPK getId() {
		return this.id;
	}

	public void setId(PermissionPK id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	//getter and setter form Transient fields
	public User getTransientUser() {
		return user;
	}

	public void setTransientUser(User user) {
		this.user = user;
	}

	public FaqSite getTransientFaqSite() {
		return faqSite;
	}

	public void setTransientFaqSite(FaqSite faSite) {
		this.faqSite = faSite;
	}

	public Role getTransientRole() {
		return role;
	}

	public void setTransientRole(Role role) {
		this.role = role;
	}
	
	

}