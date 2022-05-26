package com.revature.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class ReimbursementDAOImpl implements ReimbursementDAO {

	@Override
	public boolean insert(Reimbursement reimbursement) {
		System.out.println("inserting " + reimbursement + " into reimbursements");
		Session ses = HibernateUtil.getSession();
		Transaction tx = null;
        Integer reimbursementId = null;
        reimbursement.setResolved(new Date(1));
        reimbursement.setResolver(new User(0));
        try
        {
            tx = ses.beginTransaction();            
            reimbursementId = (Integer) ses.save(reimbursement);
            System.out.println("about to commit");
            tx.commit();
        } 
        catch (HibernateException ex)
        {
        	System.out.println("error");
        	System.out.println(ex.getMessage());
            if(tx != null)
                tx.rollback();
        }
        if (reimbursementId == null) {
        	return false;
        }
        return true;
	}


	@Override
	public List<Reimbursement> selectAll() {
		System.out.println("getting all Reimbursements...");
		Session ses = HibernateUtil.getSession();
		
		List<Reimbursement> reimbursementList = ses.createQuery("FROM Reimbursement ORDER BY id", Reimbursement.class).list();
		
		System.out.println("Reimbursement list retrieval complete! Size: " + reimbursementList.size());
		
		return reimbursementList;
	}

	@Override
	public boolean update(Reimbursement reimbursement) {
		System.out.println("Updating reimbursement. New reimbursement info: " + reimbursement);
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.clear();
		
		//ses.update(user);
		String sql = String.format("update Reimbursement set reimbursement_amount='%f', reimbursement_submitted='%t', reimbursement_resolved='%t', reimbursement_description='%s', author='%d',reimbursement_resolver='%d',reimbursement_status='%d',reimbursement_type='%d', where reimbursement_id=%d", 
				reimbursement.getAmount(), reimbursement.getSubmitted(), reimbursement.getResolved(), reimbursement.getDescription(), reimbursement.getAuthor(),reimbursement.getResolver(), reimbursement.getStatusId(),reimbursement.getTypeId(), reimbursement.getId());
		ses.createNativeQuery(sql, Reimbursement.class).executeUpdate();
		tx.commit();
		
		System.out.println("Update complete!");
		
		return true;
	}

	@Override
	public boolean delete(Reimbursement reimbursement) {
		System.out.println("Deleting reimbursement. Removed reimbursement info: " + reimbursement);
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		ses.clear();
		ses.delete(reimbursement);

		tx.commit();

		System.out.println("Deletion complete!");

		return true;
	}

	@Override
	public List<Reimbursement> selectAllPending() {
		System.out.println("getting all Reimbursements...");
		Session ses = HibernateUtil.getSession();
		
		List<Reimbursement> reimbursementList = ses.createQuery("FROM Reimbursement WHERE  reimbursement_status = 1 ORDER BY reimbursement_id", Reimbursement.class).list();
		
		System.out.println("Reimbursement list retrieval complete! Size: " + reimbursementList.size());
		
		return reimbursementList;
	}

	@Override
	public List<Reimbursement> selectAllResolved() {
		System.out.println("getting all Reimbursements...");
		Session ses = HibernateUtil.getSession();
		
		List<Reimbursement> reimbursementList = ses.createQuery("FROM Reimbursement WHERE reimbursement_status = 2 ORDER BY reimbursement_id", Reimbursement.class).list();
		
		System.out.println("Reimbursement list retrieval complete! Size: " + reimbursementList.size());
		
		return reimbursementList;
	}

	@Override
	public List<Reimbursement> selectAllPendingById(int id) {
		System.out.println("getting all Reimbursements...");
		Session ses = HibernateUtil.getSession();
		//filter results
		List<Reimbursement> reimbursementList = ses.createQuery("FROM Reimbursement WHERE reimbursement_status = 2 AND author = " + id + " ORDER BY id", Reimbursement.class).list();
		
		System.out.println("Reimbursement list retrieval complete! Size: " + reimbursementList.size());
		
		return reimbursementList;
	}

	@Override
	public List<Reimbursement> selectAllResolvedById(int id) {
		System.out.println("getting all Reimbursements...");
		Session ses = HibernateUtil.getSession();
		//filter results
		List<Reimbursement> reimbursementList = ses.createQuery("FROM Reimbursement WHERE reimbursement_status = 2 AND author = " + id + " ORDER BY id", Reimbursement.class).list();
		
		System.out.println("Reimbursement list retrieval complete! Size: " + reimbursementList.size());
		
		return reimbursementList;
	}

}
