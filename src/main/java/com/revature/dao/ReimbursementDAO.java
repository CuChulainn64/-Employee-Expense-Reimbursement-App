package com.revature.dao;

import java.util.List;

import com.revature.models.Reimbursement;


public interface ReimbursementDAO {

	// Basic CRUD operations
	public boolean insert(Reimbursement reimbursement);


	public List<Reimbursement> selectAll();

	public List<Reimbursement> selectAllPending();
	
	public List<Reimbursement> selectAllResolved();
	
	public List<Reimbursement> selectAllPendingById(int id);
	
	public List<Reimbursement> selectAllResolvedById(int id);

	public boolean update(Reimbursement reimbursement);

	public boolean delete(Reimbursement reimbursement);

}
