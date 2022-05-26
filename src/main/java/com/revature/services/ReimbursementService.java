package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementService {

	public List<Reimbursement> findAllResolvedReimbursements();
	
	public List<Reimbursement> findAllPendingReimbursements();	
	
	public List<Reimbursement> findAllPendingReimbursements(int id);
	
	public List<Reimbursement> findAllResolvedReimbursements(int id);
	
	public List<Reimbursement> findAllReimbursementsByEmpID(int id);

	boolean approveReimbursement(int rID, int id);

	boolean denyReimbursement(int rID, int id);


	boolean submitReimbursement(Reimbursement reimbursement);
	
	
}
