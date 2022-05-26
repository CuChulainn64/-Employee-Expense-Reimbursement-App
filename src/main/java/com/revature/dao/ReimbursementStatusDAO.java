package com.revature.dao;

import com.revature.models.ReimbursementStatus;

public interface ReimbursementStatusDAO {

	// Basic CRUD operations
	public int insert(ReimbursementStatus status);

	public ReimbursementStatus selectById(int id);


	public boolean update(ReimbursementStatus status);

	public boolean delete(ReimbursementStatus status);
}
