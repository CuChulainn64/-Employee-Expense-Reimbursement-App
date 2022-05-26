package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reimbursement_status")
public class ReimbursementStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="status_id")
	private int id;
	
	@Column(name="status_description", unique = true, nullable = false)
	private String status;
	public ReimbursementStatus() {
		super();
	}
	public ReimbursementStatus(String status) {
		super();
		this.status = status;
	}
	public ReimbursementStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public ReimbursementStatus(int i) {
		// TODO Auto-generated constructor stub
		switch(i) {
			case 1:
				this.status = "pending";
				this.id = i;
				break;
			case 2:
				this.status = "approved";
				this.id = i;
				break;
			case 3:
				this.status = "denied";
				this.id = i;
				break;
			default:
				this.id = 0;
				this.status = "";
				
		}
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ReimbursementStatus [id=" + id + ", status=" + status + "]";
	}
	
	
}
