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
@Table(name="UserPicture")
@Data
//@XmlRootElement
//@NamedQueries({
//	@NamedQuery(name = "CustomerDetailsEntity.countByCustomerId", query = "SELECT COUNT(c) FROM CustomerDetailsEntity c WHERE c.customerId=:customerId")
//})
public class UserPictureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	@Column(name = "userPicIdRec", nullable = false, updatable = false, insertable = false)
	private long userPicIdRec;
	@Column(name = "userId", nullable = false, updatable = true, insertable = true)
	private String userId;
	@Column(name = "imagePath", nullable = false, updatable = true, insertable = true)
	private String imagePath;
	@Column(name = "imgStatus", nullable = true, updatable = true, insertable = true)
	private Integer imgStatus;
	@Column(name = "imgInd", nullable = true, updatable = true, insertable = true)
	private Integer imgInd;
	@Column(name = "cre_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date cre_rec_ts;
	@Column(name = "upd_rec_ts", nullable = true, updatable = true, insertable = true)
	private Date upd_rec_ts;
	
}
