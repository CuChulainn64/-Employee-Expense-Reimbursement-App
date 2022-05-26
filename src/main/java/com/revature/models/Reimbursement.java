package com.revature.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="reimbursement")
public class Reimbursement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reimbursement_id")
	private int id;
	
	@Column(name="reimbursement_amount")
	private double amount;
	@Temporal(TemporalType.DATE)
	@Column(name="reimbursement_submitted")
	private Date submitted;
	@Column(name="reimbursement_resolved")
	@Temporal(TemporalType.DATE)
	private Date resolved;
	@Column(name="reimbursement_description")
	private String description;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="author", referencedColumnName = "user_id")
	private User author;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name= "user_id")
	private User resolver;
	
	@ManyToOne(targetEntity=ReimbursementStatus.class)
	@JoinColumn(name="reimbursement_status", referencedColumnName = "status_id")
	private ReimbursementStatus statusId;
	
	
	@ManyToOne(targetEntity=ReimbursementType.class)
	@JoinColumn(name="reimbursement_type", referencedColumnName = "type_id")
	private ReimbursementType typeId;
	
	
	public Reimbursement() {
		super();
	}
	public Reimbursement(double amount, Date submitted, Date resolved, String description, User author,
			User resolver, ReimbursementStatus statusId, ReimbursementType typeId) {
		super();
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
	}
	public Reimbursement(int id, double amount, Date submitted, Date resolved, String description, User author,
			User resolver, ReimbursementStatus statusId, ReimbursementType typeId) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", author=" + author + ", resolver=" + resolver + ", statusId="
				+ statusId + ", typeId=" + typeId + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public Date getResolved() {
		return resolved;
	}
	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getResolver() {
		return resolver;
	}
	public void setResolver(User resolver) {
		this.resolver = resolver;
	}
	public ReimbursementStatus getStatusId() {
		return statusId;
	}
	public void setStatusId(ReimbursementStatus statusId) {
		this.statusId = statusId;
	}
	public ReimbursementType getTypeId() {
		return typeId;
	}
	public void setTypeId(ReimbursementType typeId) {
		this.typeId = typeId;
	}
	
	
}
