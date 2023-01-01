package com.ashokitproject.binding;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="CITIZEN_PLAN_INFO")
public class CitizenPlan {

	@Id
	private Integer cid;
	
	private String cname;
	
	private String email;
	
	private String phoneNo;
	
	private String gender;
	
	private String ssn;
	
	private String planName;
	
	private String planStatus;
}
