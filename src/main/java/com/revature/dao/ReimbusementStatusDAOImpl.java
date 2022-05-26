package com.revature.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.util.HibernateUtil;

public class ReimbusementStatusDAOImpl implements ReimbursementStatusDAO {

	private static Logger log = Logger.getLogger(ReimbusementStatusDAOImpl.class);
	@Override
	public int insert(ReimbursementStatus status) {
		log.info("adding status to database. status info: " + status);

		Session ses = HibernateUtil.getSession();

		Transaction tx = ses.beginTransaction();
		int statusId = 0;
		try {
			statusId = insertType(status, ses, tx);
		} catch (ConstraintViolationException e) {
			log.info("This type already exist in database");
			tx.rollback();
			statusId = getTypeByName(status.getStatus());
		}

		return statusId; // return the auto-generated PK
	}

	private int getTypeByName(String status) {
		System.out.println("searching status by name: " + status);
		Session ses = HibernateUtil.getSession();
		ReimbursementStatus statusDes = (ReimbursementStatus) ses
				.createNativeQuery("SELECT * FROM ReimbursementStatus WHERE status_description = '" + status + "'",
						ReimbursementStatus.class)
				.getSingleResult();

		System.out.println("Search complete! Found: " + statusDes);

		return statusDes.getId();
	}

	private int insertType(ReimbursementStatus status, Session ses, Transaction tx) {
		log.info("adding status to database. Reimbursement status: " + status);

		int typeId = (int) ses.save(status);

		tx.commit();

		log.info("type has been saved as: " + typeId);
		return typeId;
	}

	@Override
	public ReimbursementStatus selectById(int id) {
		System.out.println("searching status by id: " + id);
		Session ses = HibernateUtil.getSession();
		ReimbursementStatus status = (ReimbursementStatus) ses
				.createNativeQuery("SELECT * FROM ReimbursementStatus WHERE status_id = " + id + "",
						ReimbursementStatus.class)
				.getSingleResult();

		System.out.println("Search complete! Found: " + status);

		return status;
	}


	@Override
	public boolean update(ReimbursementStatus status) {
		System.out.println("Updating status. New status info: " + status);
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.clear();

		// ses.update(user);
		String sql = String.format("update ReimbursementStatus set status_id='%s', status_description='%s' where status_id=%d",
				status.getId(), status.getStatus(), status.getId());
		ses.createNativeQuery(sql, ReimbursementStatus.class).executeUpdate();
		tx.commit();

		System.out.println("Update complete!");

		return true;
	}

	@Override
	public boolean delete(ReimbursementStatus status) {
		// TODO Auto-generated method stub
				System.out.println("Deleting status. Removed status info: " + status);
				Session ses = HibernateUtil.getSession();
				Transaction tx = ses.beginTransaction();
				ses.clear();
				ses.delete(status);

				tx.commit();

				System.out.println("Deletion complete!");

				return true;
	}

}
