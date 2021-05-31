package br.dev.amvs.jasked.jpa.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name = "permission", schema = "jasked")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Identifiable<Integer>  {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "faq_site_id", referencedColumnName = "id")
	@ManyToOne(optional = true)
	private FaqSite faqSite;
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	@ManyToOne(optional = true)
	private Role role;
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private User user;

	public Permission() {
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public FaqSite getFaqSite() {
		return faqSite;
	}


	public void setFaqSite(FaqSite faqSite) {
		this.faqSite = faqSite;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Permission [id=" + id + ", faqSite=" + faqSite + ", role=" + role + ", user=" + user + "]";
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
	

 

}