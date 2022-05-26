package com.revature.dao;

import com.revature.models.ReimbursementType;

public interface ReimbursementTypeDAO {

	// Basic CRUD operations
	public int insert(ReimbursementType type);

	public ReimbursementType selectById(int id);


	public boolean update(ReimbursementType type);

	public boolean delete(ReimbursementType type);
}
