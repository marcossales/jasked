package br.dev.amvs.jasked.jpa.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_role_faq database table.
 * 
 */
@Embeddable
public class PermissionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="role_id")
	private Integer roleId;

	@Column(name="faq_site_id")
	private Integer faqSiteId;

	public PermissionPK() {
	}
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return this.roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getFaqSiteId() {
		return this.faqSiteId;
	}
	public void setFaqSiteId(Integer faqSiteId) {
		this.faqSiteId = faqSiteId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PermissionPK)) {
			return false;
		}
		PermissionPK castOther = (PermissionPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.roleId.equals(castOther.roleId)
			&& this.faqSiteId.equals(castOther.faqSiteId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.roleId.hashCode();
		hash = hash * prime + this.faqSiteId.hashCode();
		
		return hash;
	}
}