package br.dev.amvs.jasked.jpa.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user", schema = "jasked")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Identifiable<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;

	@Size(min = 1)
	@NotNull
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Size(min = 3)
	@NotNull
	@Column(name="user_name")
	private String userName;
	
	@NotNull
	@Size(min = 8)
	@Column(name="password")
	private String password;

	@Transient
	private boolean editingPassword;
	

	@Column(name="super_user")
	private boolean superUser;
	
	@Transient
	List<Permission> permissions;
	
	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;

	}

	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

	public boolean isEditingPassword() {
		return editingPassword;
	}

	public void setEditingPassword(boolean editingPassword) {
		this.editingPassword = editingPassword;
	}

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
	
	


	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
		public String toString() {
		    StringBuilder sb = new StringBuilder();
		    sb.append(this.firstName);
		    if(this.lastName!=null) {
		    	sb.append(" ");
		    	sb.append(this.lastName);
		    }
		    sb.append(" ( ");
		    sb.append(this.userName);
		    sb.append(" ) ");
			return sb.toString();
		}
	

}