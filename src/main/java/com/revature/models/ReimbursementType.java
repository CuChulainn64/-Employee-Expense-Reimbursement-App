package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reimbursement_type")
public class ReimbursementType {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="type_id")
	private int id;
	@Column(name="type_description", unique = true, nullable = false)
	private String type;
	public ReimbursementType() {
		super();
	}
	public ReimbursementType(String type) {
		super();
		this.type = type;
	}
	//LODGING, TRAVEL, FOOD, or OTHER.
	public ReimbursementType(int id) {
		super();
		switch(id) {
		case 1:
			this.type = "lodging";
			this.id = id;
			break;
		case 2:
			this.type = "travel";
			this.id = id;
			break;
		case 3:
			this.type = "food";
			this.id = id;
			break;
		case 4:
			this.type = "other";
			break;
			default:
				this.id = 0;
				this.type = "";
		}
		
	}
	public ReimbursementType(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ReimbursementType [id=" + id + ", type=" + type + "]";
	}
	
	
}
