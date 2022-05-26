package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 8339100247721381693L;

	private static Logger log = Logger.getLogger(FrontController.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/Project1/", "");
		log.info("URI: " + URI);

		switch (URI) {
		case "employee-home":
			log.info("viewing employee homepage");
			RequestHelper.processEmpHome(req, resp);
			break;
		case "manager-home":
			log.info("viewing manager homepage");
			RequestHelper.processManHome(req, resp);
			break;
		case "my-requests":
			log.info("updating user information...");
			RequestHelper.processMyReqs(req, resp);
			break;
		case "my-pending":
			log.info("updating user information...");
			RequestHelper.processMyPendingReqs(req, resp);
			break;
		case "my-resolved":
			log.info("updating user information...");
			RequestHelper.processMyResolvedReqs(req, resp);
			break;
		case "my-info":
			log.info("updating user information...");
			RequestHelper.processMyInfo(req, resp);
			break;
		case "all-pending":
			log.info("updating user information...");
			RequestHelper.processAllPendingReqs(req, resp);
			break;
		case "all-resolved":
			log.info("updating user information...");
			RequestHelper.processAllResolvedReqs(req, resp);
			break;
		case "employees":
			log.info("updating user information...");
			RequestHelper.processAllEmployees(req, resp);
			break;
		case "employee-requests":
			log.info("updating user information...");
			RequestHelper.processRequests(req, resp);
			break;
		case "logout":
			log.info("updating user information...");
			RequestHelper.processLogout(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/Project1/", "");
		log.info("URI: " + URI);

		switch (URI) {
		case "login":
			log.info("Logging in...");
			RequestHelper.processLogin(req, resp);
			break;
		case "submit":
			log.info("submitting reimbursement request...");
			RequestHelper.processSubmit(req, resp);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/Project1/", "");
		log.info("URI: " + URI);

		switch (URI) {
		case "update":
			log.info("updating user information...");
			RequestHelper.processUpdate(req, resp);
			break;
		case "approve":
			log.info("approving reimbursement request...");
			RequestHelper.processApprove(req, resp);
			break;
		case "deny":
			log.info("denying reimbursement request...");
			RequestHelper.processDeny(req, resp);
			break;
		default:
			break;
		}
	}
	
}
