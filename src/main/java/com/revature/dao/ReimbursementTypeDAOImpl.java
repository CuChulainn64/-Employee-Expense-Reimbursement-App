package com.revature.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.revature.models.ReimbursementType;
import com.revature.util.HibernateUtil;

public class ReimbursementTypeDAOImpl implements ReimbursementTypeDAO {

	private static Logger log = Logger.getLogger(ReimbursementTypeDAOImpl.class);

	@Override
	public int insert(ReimbursementType type) {

		log.info("adding type to database. type info: " + type);

		Session ses = HibernateUtil.getSession();

		Transaction tx = ses.beginTransaction();
		int typeId = 0;
		try {
			typeId = insertType(type, ses, tx);
		} catch (ConstraintViolationException e) {
			log.info("This type already exist in database");
			tx.rollback();
			typeId = getTypeByName(type.getType());
		}

		return typeId; // return the auto-generated PK
	}

	private int getTypeByName(String type) {
		System.out.println("searching type by name: " + type);
		Session ses = HibernateUtil.getSession();
		ReimbursementType typeDes = (ReimbursementType) ses
				.createNativeQuery("SELECT * FROM ReimbursementType WHERE type_description = '" + type + "'",
						ReimbursementType.class)
				.getSingleResult();

		System.out.println("Search complete! Found: " + typeDes);

		return typeDes.getId();
	}

	private int insertType(ReimbursementType type, Session ses, Transaction tx) {
		log.info("adding type to database. Reimbursement type: " + type);

		int typeId = (int) ses.save(type);

		tx.commit();

		log.info("type has been saved as: " + typeId);
		return typeId;
	}

	@Override
	public ReimbursementType selectById(int id) {
		// TODO Auto-generated method stub
		System.out.println("searching role by id: " + id);
		Session ses = HibernateUtil.getSession();
		ReimbursementType type = (ReimbursementType) ses
				.createNativeQuery("SELECT * FROM ReimbursementType WHERE type_id = " + id + "",
						ReimbursementType.class)
				.getSingleResult();

		System.out.println("Search complete! Found: " + type);

		return type;
	}

	

	@Override
	public boolean update(ReimbursementType type) {
		System.out.println("Updating type. New type info: " + type);
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.clear();

		// ses.update(user);
		String sql = String.format("update ReimbursementType set type_id='%s', type_description='%s' where type_id=%d",
				type.getId(), type.getType(), type.getId());
		ses.createNativeQuery(sql, ReimbursementType.class).executeUpdate();
		tx.commit();

		System.out.println("Update complete!");

		return true;
	}

	@Override
	public boolean delete(ReimbursementType type) {
		// TODO Auto-generated method stub
		System.out.println("Deleting type. Removed type info: " + type);
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.clear();
		ses.delete(type);

		tx.commit();

		System.out.println("Deletion complete!");

		return true;

	}

}
