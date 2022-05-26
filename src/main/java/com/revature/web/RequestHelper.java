package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.ReimbursementDAOImpl;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.models.*;
import com.revature.services.*;

public class RequestHelper {

	private static UserService userv = new UserServiceImpl(new UserDAOImpl());
	private static ReimbursementService rserve = new ReimbursementServiceImpl(new ReimbursementDAOImpl());
	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper();
	private static JwtService jwtService = new JwtService();

	public static void processLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		log.info("logging in with user info: " + body);

		String[] sepByAmp = body.split("&");
		List<String> values = new ArrayList<String>();
		for (String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}
		log.info("In RequestHelper. Attempting login...");

		resp.setContentType("application/json");

		for (int i = 0; i < values.size(); i++) {
			System.out.println(values.get(i));
		}

		String username = values.get(0);
		String password = values.get(1);

		User user = userv.login(username, password);

		System.out.println("++++++++++++++++++++ " + user);

		if (user != null) {
			String jwt = jwtService.createJwt(user);
			resp.addHeader("X-Auth-Token", "Bearer " + jwt);

			HttpSession session = req.getSession();
			session.setAttribute("user", user);

			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json)");

			out.println(om.writeValueAsString(user));

			System.out.println("The user, " + username + ", has logged in.");
		} else {
			resp.setStatus(204);
		}

		log.info("Leaving RequestHelper");
	}

	public static UserJwtDTO authenticateUser(HttpServletRequest request) {
		log.info("Request data:");
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> map = new HashMap<String, String>();

		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		log.info(map);

		String headerValue = request.getHeader("authorization");
		String jwt = headerValue.split(" ")[1]; // Bearer token

		UserJwtDTO dto = new UserJwtDTO();

		try {
			dto = jwtService.parseJwt(jwt);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static void processMyReqs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processMyReqs...");

		UserJwtDTO dto = authenticateUser(req);
		int userId = dto.getId();

		List<Reimbursement> myRequests = rserve.findAllReimbursementsByEmpID(userId);

		if (myRequests.size() != 0) {
			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(myRequests));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing all my requests");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");

	}

	public static void processMyPendingReqs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processMyPendingReqs...");

		UserJwtDTO dto = authenticateUser(req);
		int userId = dto.getId();

		List<Reimbursement> myPendingRequests = rserve.findAllPendingReimbursements(userId);

		if (myPendingRequests.size() != 0) {
			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(myPendingRequests));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing all my pending requests");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");
	}

	public static void processMyResolvedReqs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processMyResolvedReqs...");

		UserJwtDTO dto = authenticateUser(req);
		int userId = dto.getId();

		List<Reimbursement> myResolvedRequests = rserve.findAllResolvedReimbursements(userId);

		if (myResolvedRequests.size() != 0) {
			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(myResolvedRequests));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing all my resolved requests");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");
	}

	public static void processMyInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processMyInfo...");

		UserJwtDTO dto = authenticateUser(req);

		if (dto != null) {
			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(dto));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing my user information.");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");
	}

	public static void processAllPendingReqs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processAllPendingReqs...");

		UserJwtDTO dto = authenticateUser(req);
		
		if (dto != null && dto.getRole().getRoleName().equals("manager")) {
			
			List<Reimbursement> allPendingRequests = rserve.findAllPendingReimbursements();

			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(allPendingRequests));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing all pending requests");
		} else {
			log.info("User is unauthorized to perform this operation.");
			resp.setContentType("application/json");
			resp.setStatus(401);
		}
		log.info("leaving request helper now...");
	}

	public static void processAllResolvedReqs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processAllResolvedReqs...");

		UserJwtDTO dto = authenticateUser(req);
		
		if (dto != null && dto.getRole().getRoleName().equals("manager")) {
			
			List<Reimbursement> allResolvedRequests = rserve.findAllResolvedReimbursements();

			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(allResolvedRequests));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing all resolved requests");
		} else {
			log.info("User is unauthorized to perform this operation.");
			resp.setContentType("application/json");
			resp.setStatus(401);
		}
		log.info("leaving request helper now...");
	}

	public static void processAllEmployees(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processAllEmployees...");

		UserJwtDTO dto = authenticateUser(req);
		
		if (dto != null && dto.getRole().getRoleName().equals("manager")) {
			
			List<User> allEmployees = userv.findAllUsers();

			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(allEmployees));

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing all employees");
		} else {
			log.info("User is unauthorized to perform this operation.");
			resp.setContentType("application/json");
			resp.setStatus(401);
		}
		log.info("leaving request helper now...");
	}

	public static void processRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processRequests...");

		UserJwtDTO dto = authenticateUser(req);

		if (dto != null && dto.getRole().getRoleName().equals("manager")) {
			BufferedReader reader = req.getReader();
			StringBuilder s = new StringBuilder();

			String line = reader.readLine();
			while (line != null) {
				s.append(line);
				line = reader.readLine();
			}

			String body = s.toString();
			String[] sepByAmp = body.split("&");

			List<String> values = new ArrayList<String>();

			for (String pair : sepByAmp) {
				values.add(pair.substring(pair.indexOf("=") + 1));
			}
			int EmpId = Integer.parseInt(values.get(0));
			List<Reimbursement> requests = rserve.findAllReimbursementsByEmpID(EmpId);
			
			PrintWriter pw = resp.getWriter();
			pw.println(om.writeValueAsString(requests));
			
			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Viewing an employee's requests");
		} else {
			log.info("User is unauthorized to perform this operation.");
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");
	}

	public static void processUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processUpdate...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		String[] sepByAmp = body.split("&");

		List<String> values = new ArrayList<String>();

		for (String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}

		String username = values.get(0);
		String password = values.get(1);
		String firstName = values.get(2);
		String lastName = values.get(3);
		String email = values.get(4);
		UserJwtDTO dto = authenticateUser(req);
		int userId = dto.getId();

		User user = new User(userId, username, password, firstName, lastName, dto.getRole(), email);
		UserJwtDTO dtoNew = new UserJwtDTO(userId, username, firstName, lastName, email, dto.getRole());

		if (userv.editUser(user)) {
			PrintWriter pw = resp.getWriter();
			String json = om.writeValueAsString(dtoNew);
			pw.println(json);

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("User has been updated.");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");
	}

	public static void processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processSubmit...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		String[] sepByAmp = body.split("&");

		List<String> values = new ArrayList<String>();

		for (String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}

		double amount = Double.parseDouble(values.get(0));
		String description = values.get(1);
		String type = values.get(2);
		int typeId = 0;
		switch (type) {
		case "lodging":
			typeId = 1;
			break;
		case "travel":
			typeId = 2;
			break;
		case "food":
			typeId = 3;
			break;
		case "other":
			typeId = 4;
			break;
		}
		UserJwtDTO dto = authenticateUser(req);
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setAmount(amount);
		reimbursement.setDescription(description);
		reimbursement.setAuthor(dto.getUser());
		reimbursement.setTypeId(new ReimbursementType(typeId));
				
		if (rserve.submitReimbursement(reimbursement)) {
			PrintWriter pw = resp.getWriter();
			String json = om.writeValueAsString(reimbursement);
			pw.println(json);

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Reimbursement request has been submitted");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");

	}

	public static void processEmpHome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processManagerHome...");
		PrintWriter pw = resp.getWriter();
		pw.println("Welcome to the Employee Homepage.");
	}

	public static void processManHome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processManagerHome...");
		PrintWriter pw = resp.getWriter();
		pw.println("Welcome to the Manager Homepage.");

	}

	public static void processApprove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processSubmit...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		String[] sepByAmp = body.split("&");

		List<String> values = new ArrayList<String>();

		for (String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}

		int reimbursementId = Integer.parseInt(values.get(0));
		UserJwtDTO dto = authenticateUser(req);
		int resolverId = dto.getId();

		if (rserve.approveReimbursement(reimbursementId, resolverId)) {
			PrintWriter pw = resp.getWriter();
			pw.println("Reimbursement ID " + reimbursementId + " approved.");

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Reimbursement request has been approved");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");

	}

	public static void processDeny(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processSubmit...");
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();

		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = s.toString();
		String[] sepByAmp = body.split("&");

		List<String> values = new ArrayList<String>();

		for (String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}

		int reimbursementId = Integer.parseInt(values.get(0));
		UserJwtDTO dto = authenticateUser(req);
		int resolverId = dto.getId();

		if (rserve.denyReimbursement(reimbursementId, resolverId)) {
			PrintWriter pw = resp.getWriter();
			pw.println("Reimbursement ID " + reimbursementId + " denied.");

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Reimbursement request has been denied");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");

	}

	public static void processLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...processLogout...");
		UserJwtDTO dto = authenticateUser(req);
		if (dto != null) {
			PrintWriter pw = resp.getWriter();
			pw.println("Logged out.");

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("Logged out.");
		} else {
			resp.setContentType("application/json");
			resp.setStatus(204);
		}
		log.info("leaving request helper now...");
	}

}
