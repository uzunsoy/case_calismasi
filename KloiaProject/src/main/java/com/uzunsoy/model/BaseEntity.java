package com.uzunsoy.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate = Calendar.getInstance();

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedDate;

	@Enumerated(EnumType.STRING)
	private STATUS statu = STATUS.ACTIVE;

	@Version
	private Long version;
 
}
