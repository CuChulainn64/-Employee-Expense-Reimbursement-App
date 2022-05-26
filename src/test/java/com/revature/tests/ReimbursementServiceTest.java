package com.revature.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.models.Reimbursement;

public class ReimbursementServiceTest {

	/*@Override
	public boolean submitReimbursement(Reimbursement reimbursement) {
		// TODO Auto-generated method stub
		//someone submitted for reimbursement
		log.info("in service layer. Employee #" + reimbursement.getAuthor() + " Submitting reimbursement " + reimbursement);
		
		reimbursement.setSubmitted(new Date(Instant.now().getEpochSecond()));
		reimbursement.setStatusId(1);
		return rdao.insert(reimbursement);
	}*/
	
	
	@Test
	public void testSubmitReimbursement() {
		Reimbursement testReim = new Reimbursement();
		
		fail("Not yet implemented");
	}

	@Test
	public void testApproveReimbursement() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllPendingReimbursements() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllReimbursementsByEmpID() {
		fail("Not yet implemented");
	}

	@Test
	public void testDenyReimbursement() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllResolvedReimbursements() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllPendingReimbursementsInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllResolvedReimbursementsInt() {
		fail("Not yet implemented");
	}

}
