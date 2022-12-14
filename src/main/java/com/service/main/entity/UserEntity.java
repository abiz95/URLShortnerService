package com.service.main.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="UserDetails")
@Data
//@XmlRootElement
//@NamedQueries({
//	@NamedQuery(name = "CustomerDetailsEntity.countByCustomerId", query = "SELECT COUNT(c) FROM CustomerDetailsEntity c WHERE c.customerId=:customerId")
//})
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	@Column(name = "userIdRec", nullable = false, updatable = false, insertable = false)
	private long userIdRec;
	@Column(name = "userId", nullable = false, updatable = true, insertable = true)
	private String userId;
	@Column(name = "firstName", nullable = false, updatable = true, insertable = true)
	private String firstName;
	@Column(name = "lastName", nullable = false, updatable = true, insertable = true)
	private String lastName;
	@Column(name = "country", nullable = false, updatable = true, insertable = true)
	private String country;
	@Column(name = "email", nullable = false, updatable = true, insertable = true)
	private String email;
	@Column(name = "phoneNumber", nullable = false, updatable = true, insertable = true)
	private String phoneNumber;
	@Column(name = "password", nullable = false, updatable = true, insertable = true)
	private String password;
	@Column(name = "userStatus", nullable = false, updatable = true, insertable = true)
	private Integer userStatus;
	@Column(name = "plan", nullable = true, updatable = true, insertable = true)
	private String plan;
	@Column(name = "planStatus", nullable = true, updatable = true, insertable = true)
	private Integer planStatus;
	@Column(name = "cre_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date cre_rec_ts;
	@Column(name = "upd_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date upd_rec_ts;
	
}
