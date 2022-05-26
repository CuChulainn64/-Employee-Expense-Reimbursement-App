package com.revature.services;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;

public class ReimbursementServiceImpl implements ReimbursementService {

	private static Logger log = Logger.getLogger(ReimbursementServiceImpl.class);
	private ReimbursementDAO rdao;

	public ReimbursementServiceImpl(ReimbursementDAO rdao) {
		super();
		this.rdao = rdao;
	}

	@Override
	public boolean submitReimbursement(Reimbursement reimbursement) {
		// TODO Auto-generated method stub
		//someone submitted for reimbursement
		log.info("in service layer. Employee #" + reimbursement.getAuthor() + " Submitting reimbursement " + reimbursement);
		reimbursement.setResolved(new Date(0));
		reimbursement.setResolver(new User(0));
		reimbursement.setSubmitted(new Date());
		reimbursement.setStatusId(new ReimbursementStatus(1));
		return rdao.insert(reimbursement);
	}

	@Override
	public boolean approveReimbursement(int rID, int id) {
		// TODO Auto-generated method stub
		//manager approved reimbursement
		log.info("in service layer. manager #" + id + " approved reimbursement " + rID);
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setResolved(new Date());
		reimbursement.setResolver(new UserServiceImpl(new UserDAOImpl()).findUserById(id));
		reimbursement.setStatusId(new ReimbursementStatus(2));
		reimbursement.setId(rID);
		return rdao.update(reimbursement);
	}

	@Override
	public List<Reimbursement> findAllPendingReimbursements() {
		// TODO Auto-generated method stub
		//manager request all pending reimbursement
		return rdao.selectAllPending();
	}


	@Override
	public List<Reimbursement> findAllReimbursementsByEmpID(int id) {
		// TODO Auto-generated method stub
		//manager request all employee's reimbursement
		log.info("in service layer. manager sercing for all reimbursements from employee " + id);
		List<Reimbursement> result = new ArrayList<>();
		result.addAll(rdao.selectAllPendingById(id));
		result.addAll(rdao.selectAllResolvedById(id));
		return result;
	}

	@Override
	public boolean denyReimbursement(int rID, int id) {
		// TODO Auto-generated method stub
		//manager denied reimbursement
		log.info("in service layer. manager #" + id + " denied reimbursement " + rID);
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setResolved(new Date());
		reimbursement.setResolver(new UserServiceImpl(new UserDAOImpl()).findUserById(id));
		reimbursement.setStatusId(new ReimbursementStatus(3));
		reimbursement.setId(rID);
		return rdao.update(reimbursement);
	}

	@Override
	public List<Reimbursement> findAllResolvedReimbursements() {
		// TODO Auto-generated method stub
		//manager request all resolved reimbursements
		log.info("in service layer. manager serching for all resolved reimbursements");
		return rdao.selectAllResolved();
	}

	@Override
	public List<Reimbursement> findAllPendingReimbursements(int id) {
		// TODO Auto-generated method stub
		//user request all their pending requests
		log.info("in service layer. user " + id + "  serching for all their pending reimbursements");
		List<Reimbursement> result = rdao.selectAllPendingById(id);
		return result;
	}

	@Override
	public List<Reimbursement> findAllResolvedReimbursements(int id) {
		// TODO Auto-generated method stub
		//user request all resolved reimbursements
		log.info("in service layer. user " + id + "  serching for all their resolved reimbursements");
		List<Reimbursement> result = rdao.selectAllResolvedById(id);
		return result;
	}

	

	
}
