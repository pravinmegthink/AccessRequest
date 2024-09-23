package com.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.service.AccessRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
public class AccessRequestController {


	@Autowired
	AccessRequestService accessRequestService;

	/**
	 *
	 * @param requestid - Unique identifier for the access request
	Filter the results by the specified request id
	 * @return Access Request Status Response
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@Operation(summary = " Get Request Status by RequestID  - Unique identifier for the specified Access Request"+" \n Filter the results by the specified identity who is either the requester or target of the requests."+" \n *me* indicates the current user. ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of requested item statuses.",content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content),
			@ApiResponse(responseCode = "400", description = "Client Error",content = @Content),
			@ApiResponse(responseCode = "404", description = "Forbidden",  content = @Content) })
	@GetMapping("/GetRequestStatus/{requestId}")
	public ResponseEntity<List<Map<String,Object>>> getAccessRequestStatus(@PathVariable String requestId)  {
		return new ResponseEntity<List<Map<String,Object>>>(accessRequestService.getAccessRequestStatus(requestId), HttpStatus.OK);
	}

	/**
	 *
	 * @param requestMap - Access request input parameters.(application,group,username,operation,notficationEmailDL)
	 * @return access requested id
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */

	@Operation(summary = " The wrapper API uses a single endpoint (e.g., /access-requests) for both GRANT_ACCESS and REVOKE_ACCESS.\n\n"
			+ "The request body (payload) structure determines the type of access request (GRANT or REVOKE).\n\n"
			+ "Consideration: \n\n"
			+ "•	Implements authorization checks to ensure users can only request access for themselves or others based on their permissions (e.g., self-request, manager access).\n\n"
			+ "\n\n"
			+ "•	Roles, access profiles and entitlements can be requested.\n\n"
			+ "•	While requesting entitlements, maximum of 25 entitlements and 10 recipients are allowed in a request by ISC API \n\n"
			+ "•	Revoke Access API allows revoking access for a single identity per request.\n\n"
			+ "•	Implement authorization checks to ensure only managers can revoke access for their direct reports or users with ORG_ADMIN authority can revoke access for anyone.\n\n"
			+ " ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of requested item statuses.",content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content),
			@ApiResponse(responseCode = "400", description = "Client Error",content = @Content),
			@ApiResponse(responseCode = "404", description = "Forbidden",  content = @Content) })
	@PostMapping("/UserRequest")
	public ResponseEntity<List<Map<String,Object>>> userRequest(@RequestBody Map<String,Object> requestMap) {
		return new ResponseEntity<List<Map<String,Object>>>(accessRequestService.userRequest(requestMap), HttpStatus.OK);
	}

}