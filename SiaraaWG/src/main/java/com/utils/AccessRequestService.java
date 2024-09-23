/*
 * package com.service; import java.net.URLEncoder; import java.util.ArrayList;
 * import java.util.Arrays; import java.util.Collections; import
 * java.util.HashMap; import java.util.List; import java.util.Map; import
 * java.util.Properties;
 * 
 * import org.apache.logging.log4j.LogManager; import
 * org.apache.logging.log4j.Logger; import org.springframework.http.HttpEntity;
 * import org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.http.HttpStatusCode; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Service; import
 * org.springframework.util.LinkedMultiValueMap; import
 * org.springframework.util.MultiValueMap; import
 * org.springframework.web.client.HttpClientErrorException; import
 * org.springframework.web.client.HttpStatusCodeException; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.fasterxml.jackson.core.JsonProcessingException; import
 * com.fasterxml.jackson.databind.JsonMappingException; import
 * com.fasterxml.jackson.databind.ObjectMapper; import com.google.gson.Gson;
 * import com.utils.Constants; import com.utils.Utils;
 * 
 * 
 * @Service public class AccessRequestService {
 * 
 * Utils utils = new Utils(); private static final Logger logger =
 * LogManager.getLogger(AccessRequestService.class.getName());
 * 
 *//**
	 * This method is used to get access request status for specific user using
	 * filter limit,offset,count,regarding-identity
	 * 
	 * @param requested_for
	 * @param id            - Unique identifier for the specified Identity
	 * @return - List of map containing details of the access request status
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
/*
 * @SuppressWarnings("unchecked") public List<Map<String,Object>>
 * getAccessRequestStatus(String request_id, String requested_for) {
 * logger.info("getAccessRequestStatus method start"); RestTemplate restTemplate
 * = new RestTemplate(); String tenantUrl= null; String accessRequestStatusURL =
 * null; HttpHeaders headers = new HttpHeaders(); ResponseEntity<String>
 * response = null; String access_token = null; Properties properties = null;
 * HttpEntity<String> request = null; ObjectMapper mapper = new ObjectMapper();
 * List<Map<String,Object>> responseList = null; Map<String, Object> map= null;
 * Gson gson = new Gson(); Map<String,Object> errorMap = new HashMap<>();
 * List<Map<String,Object>> messageList = new ArrayList<>(); Map<String, Object>
 * user = null; String accountActivityId = null; String accountActivityItemId =
 * null; try { properties = utils.loadPropertyFromFile(); tenantUrl =
 * Constants.HTTPS+properties.getProperty(Constants.TENANT)+Constants.API+"."+
 * properties.getProperty(Constants.DOMAIN)+Constants.COM; access_token =
 * getAccessToken(properties,tenantUrl); accountActivityId =
 * getAccountActivityId(properties,tenantUrl,access_token,request_id,
 * requested_for); accountActivityItemId =
 * getAccountActivityItemId(properties,tenantUrl,access_token,accountActivityId)
 * ;
 * 
 * headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token);
 * accessRequestStatusURL =
 * tenantUrl+properties.getProperty(Constants.GET_ACCESS_REQUEST_STATUS)+
 * Constants.GET_ACCESS_REQUEST_STATUS_FILTER+URLEncoder.encode(Constants.
 * GET_ACCESS_REQUEST_STATUS_FILTER_VALUE,
 * "UTF-8")+'"'+accountActivityItemId+'"'+Constants.
 * GET_ACCESS_REQUEST_STATUS_FILTER_MORE+properties.getProperty(Constants.
 * GET_ACCESS_REQUEST_STATUS_FILTER_LIMIT);
 * 
 * request = new HttpEntity<String>(headers);
 * 
 * response = restTemplate.exchange(accessRequestStatusURL, HttpMethod.GET,
 * request, String.class);
 * logger.info("status code :"+response.getStatusCode());
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { responseList = new ArrayList<>();
 * List<Map<String,Object>> accessRequestList =
 * mapper.readValue(response.getBody(),ArrayList.class); for (Map<String,
 * Object> accessRequest : accessRequestList) { map = new HashMap<>();
 * map.put(Constants.NAME,accessRequest.get(Constants.NAME));
 * map.put(Constants.TYPE,accessRequest.get(Constants.TYPE));
 * map.put(Constants.STATE,accessRequest.get(Constants.STATE));
 * map.put(Constants.REQUEST_TYPE,accessRequest.get(Constants.REQUEST_TYPE));
 * map.put(Constants.ACCESS_REQUEST_ID,accessRequest.get(Constants.
 * ACCESS_REQUEST_ID)); if(null != accessRequest.get(Constants.CLIENT_METADATA))
 * { Map<String,Object> clientMetadata = (Map<String, Object>)
 * accessRequest.get(Constants.CLIENT_METADATA);
 * map.put(Constants.REQUESTED_APP_NAME,clientMetadata.get(Constants.
 * REQUESTED_APP_NAME)); } if(null !=
 * accessRequest.get(Constants.APPROVALDETAILS) ) { List<Map<String,Object>>
 * approvalDetails =
 * mapper.readValue(gson.toJson(accessRequest.get(Constants.APPROVALDETAILS)),
 * List.class); map.put(Constants.APPROVALDETAILS,approvalDetails); } if(null !=
 * accessRequest.get(Constants.ACCESS_REQUEST_PHASES)) {
 * List<Map<String,Object>> accessRequestPhasesList = (List<Map<String,
 * Object>>) accessRequest.get(Constants.ACCESS_REQUEST_PHASES);
 * Map<String,Object> accessRequestPhasesMap = null; List<Map<String,Object>>
 * phasesList = new ArrayList<>(); for (Map<String, Object> accessRequestPhases
 * : accessRequestPhasesList) { accessRequestPhasesMap = new HashMap<>();
 * accessRequestPhasesMap.put(Constants.NAME,
 * accessRequestPhases.get(Constants.NAME));
 * accessRequestPhasesMap.put(Constants.STATE,
 * accessRequestPhases.get(Constants.STATE));
 * phasesList.add(accessRequestPhasesMap); }
 * map.put(Constants.ACCESS_REQUEST_PHASES,phasesList); } responseList.add(map);
 * } logger.info("Returning response :"+response.getBody()); }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ errorMap =
 * gson.fromJson(response.getBody(),Map.class); messageList = (List<Map<String,
 * Object>>) errorMap.get(Constants.MESSAGE);
 * logger.error("Error message is:"+messageList); return messageList; }else {
 * errorMap = gson.fromJson(response.getBody(),Map.class); messageList = new
 * ArrayList<>(); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return messageList; }
 * logger.info("Returning response :"+response.getBody());
 * 
 * } catch (HttpClientErrorException exception) { errorMap =
 * gson.fromJson(response.getBody(),Map.class); //errorMap.put(Constants.ERROR,
 * .toString()); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return messageList; }catch
 * (HttpStatusCodeException exception) { errorMap =
 * gson.fromJson(response.getBody(),Map.class); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return messageList; }catch
 * (Exception e) { errorMap = gson.fromJson(response.getBody(),Map.class);
 * messageList.add(errorMap); logger.error("Error message is:"+messageList);
 * return messageList; } logger.info("getAccessRequestStatus method end");
 * return responseList; }
 * 
 * 
 * @SuppressWarnings("unchecked") private String
 * getAccountActivityItemId(Properties properties, String tenantUrl, String
 * access_token, String accountActivityId) {
 * logger.info("getAccountActivityItemId method start"); RestTemplate
 * restTemplate = new RestTemplate(); String getAcountActivityURL = null;
 * HttpHeaders headers = new HttpHeaders(); ResponseEntity<String> response =
 * null; HttpEntity<String> request = null; ObjectMapper mapper = new
 * ObjectMapper(); Map<String,Object> resultMap = new HashMap<>(); String
 * accountActivityItemId = null; try { headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token);
 * getAcountActivityURL =
 * tenantUrl+properties.getProperty(Constants.GET_ACCOUNT_ACTIVITY_URL)+
 * accountActivityId;
 * 
 * request = new HttpEntity<String>(headers);
 * 
 * response = restTemplate.exchange(getAcountActivityURL, HttpMethod.GET,
 * request, String.class);
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { Map<String,Object> map =
 * mapper.readValue(response.getBody(),Map.class);
 * 
 * List<Map<String,Object>> itemMap = (List<Map<String, Object>>)
 * map.get("items"); for (Map<String, Object> item : itemMap) {
 * accountActivityItemId = (String) item.get(Constants.ID); }
 * 
 * 
 * }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ Map<String,Object> errorMap =
 * mapper.readValue(response.getBody(),Map.class); List<Map<String,Object>>
 * messageList = (List<Map<String, Object>>) errorMap.get(Constants.MESSAGE);
 * for (Map<String, Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }else {
 * Map<String,Object> errorMap = mapper.readValue(response.getBody(),Map.class);
 * List<Map<String,Object>> messageList = new ArrayList<>();
 * messageList.add(errorMap); for (Map<String, Object> message : messageList) {
 * resultMap.put("error", message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }
 * 
 * 
 * }catch (HttpClientErrorException exception) {
 * logger.error("Error while getting AccountActivityItemId :"+exception.
 * getResponseBodyAsString()); }catch (HttpStatusCodeException exception) {
 * logger.error("Error while getting AccountActivityItemId :"+exception.
 * getResponseBodyAsString()); }catch (Exception e) {
 * logger.error("Error while getting AccountActivityItemId :"+e.getMessage()); }
 * logger.info("getAccountActivityItemId method end"); return
 * accountActivityItemId; }
 * 
 * 
 * private String getAccountActivityId(Properties properties, String tenantUrl,
 * String access_token, String request_id, String requested_for) {
 * logger.info("getAccountActivityId method start"); RestTemplate restTemplate =
 * new RestTemplate(); String searchQueryUrl = null; HttpEntity<Object> request
 * = null; HttpHeaders headers = new HttpHeaders(); ResponseEntity<String>
 * response = null; ObjectMapper mapper = new ObjectMapper(); Map<String,Object>
 * resultMap = new HashMap<>(); Map<String,Object> errorMap = new HashMap<>();
 * List<Map<String,Object>> messageList = new ArrayList<>(); Gson gson = new
 * Gson(); Map<String,Object> requestMap = null; String dslQuery = null;
 * List<String> indices = null; Map<String,Object> queryDslMap = null;
 * Map<String,Object> query_stringMap = null; Map<String,Object>
 * queryResultFilter = null; List<String> includes = null; List<String> sorted =
 * null; String trackingNumber = null;; try { headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token); searchQueryUrl
 * = tenantUrl+properties.getProperty(Constants.SEARCH_QUERY_URL); dslQuery =
 * properties.getProperty(Constants.DSL_QUERY);
 * 
 * indices = new ArrayList<>(); includes = new ArrayList<>(); sorted = new
 * ArrayList<>(); queryDslMap = new HashMap<>(); query_stringMap = new
 * HashMap<>(); requestMap = new HashMap<>(); queryResultFilter = new
 * HashMap<>();
 * 
 * indices.add(Constants.indices_type);
 * includes.add(Constants.queryResultFilter_includes);
 * sorted.add(Constants.sort_filter);
 * 
 * dslQuery = dslQuery.replace("{{requested_by}}",
 * properties.getProperty(Constants.ACCESS_REQUESTED_BY)); dslQuery =
 * dslQuery.replace("{{requested_for}}", requested_for); dslQuery =
 * dslQuery.replace("{{tracking_number}}", request_id);
 * 
 * query_stringMap.put(Constants.query, dslQuery);
 * queryDslMap.put(Constants.query_string, query_stringMap);
 * queryResultFilter.put(Constants.queryResultFilter, includes);
 * requestMap.put(Constants.indices, indices);
 * requestMap.put(Constants.queryType, Constants.DSL);
 * requestMap.put(Constants.queryDsl, queryDslMap);
 * requestMap.put(Constants.includes, includes); requestMap.put(Constants.sort,
 * sorted);
 * 
 * 
 * String payload = gson.toJson(requestMap);
 * logger.info("payload for user request:"+payload); request = new
 * HttpEntity<Object>(payload,headers); response =
 * restTemplate.exchange(searchQueryUrl, HttpMethod.POST, request,
 * String.class); logger.info("status code :"+response.getStatusCode());
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { List<Map<String,Object>> list =
 * mapper.readValue(response.getBody(),List.class); if(list.isEmpty())
 * resultMap.put("error", "No data found.");
 * logger.info("Returning response :"+response.getBody()); for (Map<String,
 * Object> map : list) { trackingNumber = map.get("trackingNumber").toString();
 * } }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ errorMap =
 * mapper.readValue(response.getBody(),Map.class); messageList =
 * (List<Map<String, Object>>) errorMap.get(Constants.MESSAGE); for (Map<String,
 * Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }else { errorMap =
 * mapper.readValue(response.getBody(),Map.class); messageList.add(errorMap);
 * for (Map<String, Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }
 * 
 * 
 * 
 * }catch (HttpClientErrorException exception) {
 * logger.error("Error while getting accountActvityId :"+exception.
 * getResponseBodyAsString()); }catch (HttpStatusCodeException exception) {
 * logger.error("Error while getting accountActvityId :"+exception.
 * getResponseBodyAsString()); }catch (Exception e) {
 * logger.error("Error while getting accountActvityId :"+e.getMessage()); }
 * logger.info("getAccountActivityId method end"); return trackingNumber; }
 * 
 * 
 *//**
	 * This method is used to get access token which needs to use in http request
	 * 
	 * @param properties
	 * @param tenantUrl
	 * @return
	 */
/*
 * @SuppressWarnings("unchecked") private String getAccessToken(Properties
 * properties, String tenantUrl) { logger.info("getAccessToken method start");
 * RestTemplate restTemplate = new RestTemplate(); //String
 * tenantUrl=Constants.HTTPS+properties.getProperty(Constants.TENANT)+Constants.
 * API+"."+properties.getProperty(Constants.DOMAIN)+".com"; String
 * tokenUrl=tenantUrl+properties.getProperty(Constants.TOKEN_URL);
 * MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String,
 * String>(); HttpHeaders headers = new HttpHeaders();
 * HttpEntity<MultiValueMap<String, String>> formEntity = null;
 * ResponseEntity<String> response = null; String access_token = null;
 * ObjectMapper mapper = new ObjectMapper();
 * 
 * try { headers.add(Constants.CONTENT_TYPE,
 * MediaType.APPLICATION_FORM_URLENCODED.toString());
 * headers.add(Constants.ACCEPT, MediaType.APPLICATION_JSON.toString());
 * 
 * 
 * requestBody.add(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);
 * requestBody.add(Constants.CLIENT_ID_HEADER,
 * properties.getProperty(Constants.CLIENT_ID));
 * requestBody.add(Constants.CLIENT_SECRET_HEADER,
 * properties.getProperty(Constants.CLIENT_SECRET));
 * 
 * 
 * formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody,
 * headers);
 * 
 * response = restTemplate.exchange(tokenUrl, HttpMethod.POST, formEntity,
 * String.class);
 * 
 * logger.info("status code :"+response.getStatusCode()); Map<String,Object> map
 * = mapper.readValue(response.getBody(), Map.class);
 * 
 * access_token = (String) map.get(Constants.ACCESS_TOKEN); }catch
 * (HttpClientErrorException exception) {
 * logger.error("Error while getting access token:"+exception.
 * getResponseBodyAsString()); }catch (HttpStatusCodeException exception) {
 * logger.error("Error while getting access token:"+exception.
 * getResponseBodyAsString()); }catch (Exception e) {
 * logger.error("Error while getting access token :"+e.getStackTrace()); }
 * logger.info("getAccessToken method end"); return access_token; }
 * 
 * 
 *//**
	 * This method is used to request entitlement or revoke entitlement for a user
	 * 
	 * @param requestMap
	 * @return If entitlement request is successful then return resultMap else if
	 *         error occurs returns errorMap
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 * @return returning successful status message if request raised successfully
	 *         with userID for whom request is raised
	 */
/*
 * @SuppressWarnings("unchecked") public List<Map<String,Object>>
 * userRequest(Map<String,Object> requestMap){
 * logger.info("getAccessRequestStatus method start"); String tenantUrl= null;
 * String access_token = null; Properties properties = null; Gson gson = new
 * Gson(); String entitlementStr = null; String[] entitlementArr = null; String
 * seperator = ","; List<String> entitlementList = null; Map<String,Object>
 * resultMap = new HashMap<String, Object>(); Map<String,Object> userMap = null;
 * Map<String,Object> entitlementMap = null, accessProfileMap = null, roleMap =
 * null; Map<String,Object> errorMap = new HashMap<>(); String comment = null;
 * Map<String,String> requestItem = null; Map<String,Object> accessRequestMap =
 * null; List<Map<String,Object>> resultList = null; try { properties =
 * utils.loadPropertyFromFile(); tenantUrl =
 * Constants.HTTPS+properties.getProperty(Constants.TENANT)+Constants.API+"."+
 * properties.getProperty(Constants.DOMAIN)+Constants.COM; access_token =
 * getAccessToken(properties,tenantUrl); userMap =
 * getUserIDUsingUserName(requestMap.get(Constants.USERNAME).toString(),
 * access_token,properties,tenantUrl); resultList = new ArrayList<>(); if(null
 * != userMap && userMap.containsKey(Constants.ID)) { accessRequestMap = new
 * HashMap<>(); requestItem = new HashMap<>();
 * 
 * if(requestMap.get(Constants.TYPE) != "" &&
 * requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.
 * ACCESS_PROFILE)) { accessProfileMap =
 * getAccessProfileID(requestMap.get("profile").toString(),access_token,
 * properties,tenantUrl); requestItem.put(Constants.ID,
 * accessProfileMap.get(Constants.ID).toString());
 * if(requestMap.get(Constants.OPERATION).toString().equalsIgnoreCase(Constants.
 * ADD)) { accessRequestMap.put(Constants.REQUEST_TYPE, Constants.GRANT_ACCESS);
 * requestItem.put("comment",
 * "Access requested for user : "+requestMap.get("username")
 * +" for access profile : "+requestMap.get("profile")); }else {
 * accessRequestMap.put(Constants.REQUEST_TYPE, Constants.REVOKE_ACCESS);
 * requestItem.put(Constants.COMMENT,"Revoking request for user : "+requestMap.
 * get(Constants.USERNAME)+" for access profile : "+requestMap.get("profile"));
 * } resultMap =
 * submitAccessRequest(accessProfileMap,comment,properties,access_token,
 * tenantUrl,userMap,requestMap,requestItem,accessRequestMap,null);
 * resultList.add(resultMap); }else if(requestMap.get(Constants.TYPE) != "" &&
 * requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.ROLE)) {
 * roleMap =
 * getRoleID(requestMap.get("role").toString(),access_token,properties,tenantUrl
 * ); requestItem.put(Constants.ID, roleMap.get(Constants.ID).toString());
 * if(requestMap.get(Constants.OPERATION).toString().equalsIgnoreCase(Constants.
 * ADD)) { accessRequestMap.put(Constants.REQUEST_TYPE, Constants.GRANT_ACCESS);
 * //requestItem.put("comment",
 * "Access requested for user : "+requestMap.get("username")
 * +" for entitlement : "+requestMap.get("group")); }else {
 * accessRequestMap.put(Constants.REQUEST_TYPE, Constants.REVOKE_ACCESS);
 * requestItem.put(Constants.COMMENT,
 * "Revoking request for user : "+requestMap.get(Constants.USERNAME)
 * +" for role : "+requestMap.get("role")); } resultMap =
 * submitAccessRequest(accessProfileMap,comment,properties,access_token,
 * tenantUrl,userMap,requestMap,requestItem,accessRequestMap,null);
 * resultList.add(resultMap); }else if(requestMap.get(Constants.TYPE) != "" &&
 * requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.
 * ENTITLEMENT)) { entitlementStr = requestMap.get(Constants.GROUP).toString();
 * entitlementArr = entitlementStr.split(seperator); entitlementList =
 * Arrays.asList(entitlementArr); for(String entitlement: entitlementList) {
 * entitlementMap =
 * getEntitlementID(entitlement,access_token,properties,tenantUrl); if(null !=
 * entitlementMap && entitlementMap.containsKey(Constants.ID)) {
 * if(requestMap.get(Constants.OPERATION).toString().equalsIgnoreCase(Constants.
 * ADD)) { accessRequestMap.put(Constants.REQUEST_TYPE, Constants.GRANT_ACCESS);
 * //requestItem.put("comment",
 * "Access requested for user : "+requestMap.get("username")
 * +" for entitlement : "+requestMap.get("group")); }else {
 * accessRequestMap.put(Constants.REQUEST_TYPE, Constants.REVOKE_ACCESS);
 * requestItem.put(Constants.COMMENT,
 * "Revoking request for user : "+requestMap.get(Constants.USERNAME)
 * +" for entitlement : "+entitlement); } requestItem.put(Constants.ID,
 * entitlementMap.get(Constants.ID).toString()); resultMap =
 * submitAccessRequest(accessProfileMap,comment,properties,access_token,
 * tenantUrl,userMap,requestMap,requestItem,accessRequestMap,entitlement); }else
 * { resultMap.put(entitlement+":Error - ", entitlementMap.get("error")); }
 * resultList.add(resultMap); } }else { resultMap.put("Error",
 * "Please select access request type"); } }else {
 * resultMap.put("Error - "+requestMap.get("username").toString(),
 * userMap.get("error")); resultList.add(resultMap); } }catch (Exception e) {
 * errorMap = gson.fromJson(e.getMessage(),Map.class);
 * if(requestMap.get("operation").toString().equalsIgnoreCase(Constants.ADD))
 * logger.error("Error in access request :"+e.getMessage()); else
 * logger.error("Error in revoke request :"+e.getMessage());
 * resultList.add(errorMap); return resultList; }
 * logger.info("getAccessRequestStatus method end"); return resultList; }
 * 
 * @SuppressWarnings("unused") private Map<String,Object>
 * submitAccessRequest(Map<String, Object> accessProfileMap, String comment,
 * Properties properties, String access_token, String tenantUrl, Map<String,
 * Object> userMap, Map<String, Object> requestMap, Map<String, String>
 * requestItem, Map<String, Object> accessRequestMap, String entitlement){
 * Map<String,Object> resultMap = new HashMap<String, Object>(); StringBuffer
 * message = null; Map<String,Object> errorMap = new HashMap<>();
 * HttpEntity<Object> request = null;
 * 
 * List<String> requestedFor = null; List<Map<String,String>> requestedItems =
 * null;
 * 
 * String submitAccessRequestURL = null; RestTemplate restTemplate = new
 * RestTemplate(); HttpHeaders headers = new HttpHeaders();
 * ResponseEntity<String> response = null; Gson gson = new Gson(); String msg=
 * null; try { requestedFor = new ArrayList<>(); requestedItems = new
 * ArrayList<>(); requestedFor.add(userMap.get(Constants.ID).toString());
 * accessRequestMap.put(Constants.REQUESTED_FOR, requestedFor);
 * requestItem.put(Constants.TYPE, requestMap.get(Constants.TYPE).toString());
 * requestedItems.add(requestItem); submitAccessRequestURL =
 * tenantUrl+properties.getProperty(Constants.SUBMIT_ACCESS_REQUEST);
 * accessRequestMap.put(Constants.REQUESTED_ITEMS, requestedItems); headers =
 * new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token); String payload
 * = gson.toJson(accessRequestMap);
 * logger.info("payload for user request:"+payload); request = new
 * HttpEntity<Object>(payload,headers); try { response =
 * restTemplate.exchange(submitAccessRequestURL, HttpMethod.POST, request,
 * String.class);
 * 
 * logger.info("status code :"+response.getStatusCode());
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { msg =
 * "Access requested for user "+requestMap.get(Constants.USERNAME)+" for the "
 * +requestMap.get(Constants.TYPE); if(null != message) { message.append("\n");
 * message.append(msg);
 * if(requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.
 * ACCESS_PROFILE)) message.append(" "+requestMap.get("profile")); else
 * if(requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.ROLE)
 * ) message.append(" "+requestMap.get("role")); else
 * message.append(" "+entitlement);
 * 
 * }else { message = new StringBuffer(msg);
 * if(requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.
 * ACCESS_PROFILE)) message.append(" "+requestMap.get("profile")); else
 * if(requestMap.get(Constants.TYPE).toString().equalsIgnoreCase(Constants.ROLE)
 * ) message.append(" "+requestMap.get("role")); else
 * message.append(" "+entitlement); }
 * Thread.sleep(Integer.valueOf(properties.getProperty(Constants.SLEEP_INTERVAL)
 * )); String accessRequestId =
 * getAccessRequestID(userMap.get(Constants.ID).toString(),access_token,
 * properties,tenantUrl); resultMap.put("Success - ", message);
 * resultMap.put("Access Request Id: - ", accessRequestId); } }catch
 * (HttpClientErrorException exception) { resultMap =
 * exception.getResponseBodyAs(Map.class);
 * if(requestMap.get("operation").toString().equalsIgnoreCase(Constants.ADD))
 * logger.error("Error in access request :"+exception.getResponseBodyAsString())
 * ; else
 * logger.error("Error in revoke request :"+exception.getResponseBodyAsString())
 * ; }catch (HttpStatusCodeException exception) { resultMap =
 * exception.getResponseBodyAs(Map.class);
 * if(requestMap.get("operation").toString().equalsIgnoreCase(Constants.ADD))
 * logger.error("Error in access request :"+exception.getResponseBodyAsString())
 * ; else
 * logger.error("Error in revoke request :"+exception.getResponseBodyAsString())
 * ; }
 * 
 * }catch (Exception e) { errorMap = gson.fromJson(e.getMessage(),Map.class);
 * if(requestMap.get("operation").toString().equalsIgnoreCase(Constants.ADD))
 * logger.error("Error in access request :"+e.getMessage()); else
 * logger.error("Error in revoke request :"+e.getMessage()); return errorMap; }
 * return resultMap;
 * 
 * }
 * 
 * private String getAccessRequestID(String requestedForID, String access_token,
 * Properties properties, String tenantUrl) {
 * logger.info("getAccessRequestID method start"); RestTemplate restTemplate =
 * new RestTemplate(); String accessRequestStatusURL = null; HttpHeaders headers
 * = new HttpHeaders(); ResponseEntity<String> response = null;
 * HttpEntity<String> request = null; ObjectMapper mapper = new ObjectMapper();
 * List<Map<String,Object>> responseList = null; Map<String, Object> map= null;
 * Gson gson = new Gson(); Map<String,Object> errorMap = new HashMap<>();
 * List<Map<String,Object>> messageList = new ArrayList<>(); Map<String, Object>
 * user = null; String accessRequestId = null; try { user =
 * getUserIDUsingUserName(properties.getProperty(Constants.ACCESS_REQUESTED_BY),
 * access_token, properties, tenantUrl); if(null != user &&
 * user.containsKey(Constants.ID)) { headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token); String url =
 * properties.getProperty(Constants.GET_ACCESS_REQUEST_ID_WITH_FILTER).toString(
 * ).replace("{{requestedForID}}", requestedForID); url =
 * url.replace("{{requestedByID}}", user.get(Constants.ID).toString());
 * accessRequestStatusURL = tenantUrl+url;
 * 
 * request = new HttpEntity<String>(headers);
 * 
 * response = restTemplate.exchange(accessRequestStatusURL, HttpMethod.GET,
 * request, String.class);
 * logger.info("status code :"+response.getStatusCode());
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { responseList = new ArrayList<>();
 * List<Map<String,Object>> accessRequestList =
 * mapper.readValue(response.getBody(),ArrayList.class); for (Map<String,
 * Object> accessRequest : accessRequestList) { accessRequestId =
 * accessRequest.get("accessRequestId").toString(); return accessRequestId; }
 * logger.info("Returning response :"+response.getBody()); }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ errorMap =
 * gson.fromJson(response.getBody(),Map.class); messageList = (List<Map<String,
 * Object>>) errorMap.get(Constants.MESSAGE);
 * logger.error("Error message is:"+messageList); return response.getBody();
 * }else { errorMap = gson.fromJson(response.getBody(),Map.class); messageList =
 * new ArrayList<>(); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return response.getBody(); }
 * logger.info("Returning response :"+response.getBody()); } } catch
 * (HttpClientErrorException exception) { errorMap =
 * gson.fromJson(response.getBody(),Map.class); //errorMap.put(Constants.ERROR,
 * .toString()); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return exception.getMessage();
 * }catch (HttpStatusCodeException exception) { errorMap =
 * gson.fromJson(response.getBody(),Map.class); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return exception.getMessage();
 * }catch (Exception e) { errorMap =
 * gson.fromJson(response.getBody(),Map.class); messageList.add(errorMap);
 * logger.error("Error message is:"+messageList); return e.getMessage(); }
 * return accessRequestId; }
 * 
 * 
 * private Map<String, Object> getRoleID(String role, String access_token,
 * Properties properties, String tenantUrl) {
 * logger.info("getRoleID method start"); RestTemplate restTemplate = new
 * RestTemplate(); String getEntitlementIDURL = null; HttpHeaders headers = new
 * HttpHeaders(); ResponseEntity<String> response = null; HttpEntity<String>
 * request = null; ObjectMapper mapper = new ObjectMapper(); String filter =
 * null; Map<String,Object> resultMap = new HashMap<>(); Map<String,Object>
 * errorMap = new HashMap<>(); List<Map<String,Object>> messageList = new
 * ArrayList<>(); try { filter =
 * URLEncoder.encode(properties.getProperty(Constants.GET_ROLE_ID_FILTER),
 * "UTF-8"); headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token);
 * getEntitlementIDURL =
 * tenantUrl+properties.getProperty(Constants.GET_ROLE_ID_USING_FILTER)+filter+'
 * "'+role+'"';
 * 
 * request = new HttpEntity<String>(headers);
 * 
 * response = restTemplate.exchange(getEntitlementIDURL, HttpMethod.GET,
 * request, String.class);
 * logger.info("status code :"+response.getStatusCode());
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { List<Map<String,Object>> roleList =
 * mapper.readValue(response.getBody(),List.class); if(roleList.isEmpty())
 * resultMap.put("error", "Role not found.");
 * logger.info("Returning response :"+response.getBody()); for (Map<String,
 * Object> roleMap : roleList) { String roleName =
 * roleMap.get("name").toString(); if(roleName.equalsIgnoreCase(role)) {
 * resultMap.put(Constants.ID, roleMap.get(Constants.ID).toString()); }
 * 
 * } }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ errorMap =
 * mapper.readValue(response.getBody(),Map.class); messageList =
 * (List<Map<String, Object>>) errorMap.get(Constants.MESSAGE); for (Map<String,
 * Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }else { errorMap =
 * mapper.readValue(response.getBody(),Map.class); messageList.add(errorMap);
 * for (Map<String, Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }
 * 
 * 
 * 
 * }catch (HttpClientErrorException exception) {
 * logger.error("Error while getting entitlementId :"+exception.
 * getResponseBodyAsString()); }catch (HttpStatusCodeException exception) {
 * logger.error("Error while getting entitlementId :"+exception.
 * getResponseBodyAsString()); }catch (Exception e) {
 * logger.error("Error while getting entitlementId :"+e.getMessage()); }
 * logger.info("getRoleID method end"); return resultMap; }
 * 
 * 
 * private Map<String, Object> getAccessProfileID(String profile, String
 * access_token, Properties properties, String tenantUrl) {
 * logger.info("getAccessProfileID method start"); RestTemplate restTemplate =
 * new RestTemplate(); String getEntitlementIDURL = null; HttpHeaders headers =
 * new HttpHeaders(); ResponseEntity<String> response = null; HttpEntity<String>
 * request = null; ObjectMapper mapper = new ObjectMapper(); String filter =
 * null; Map<String,Object> resultMap = new HashMap<>(); Map<String,Object>
 * errorMap = new HashMap<>(); List<Map<String,Object>> messageList = new
 * ArrayList<>(); try { filter =
 * URLEncoder.encode(properties.getProperty(Constants.
 * GET_ACCESS_PROFILE_ID_FILTER), "UTF-8"); headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token);
 * getEntitlementIDURL =
 * tenantUrl+properties.getProperty(Constants.GET_ACCESS_PROFILE_ID_USING_FILTER
 * )+filter+'"'+profile+'"';
 * 
 * request = new HttpEntity<String>(headers);
 * 
 * response = restTemplate.exchange(getEntitlementIDURL, HttpMethod.GET,
 * request, String.class);
 * logger.info("status code :"+response.getStatusCode());
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { List<Map<String,Object>> accessProfileList =
 * mapper.readValue(response.getBody(),List.class);
 * if(accessProfileList.isEmpty()) resultMap.put("error",
 * "Access profile not found.");
 * logger.info("Returning response :"+response.getBody()); for (Map<String,
 * Object> accessProfileMap : accessProfileList) { String accessProfileName =
 * accessProfileMap.get("name").toString();
 * if(accessProfileName.equalsIgnoreCase(profile)) { resultMap.put(Constants.ID,
 * accessProfileMap.get(Constants.ID).toString()); }
 * 
 * } }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ errorMap =
 * mapper.readValue(response.getBody(),Map.class); messageList =
 * (List<Map<String, Object>>) errorMap.get(Constants.MESSAGE); for (Map<String,
 * Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }else { errorMap =
 * mapper.readValue(response.getBody(),Map.class); messageList.add(errorMap);
 * for (Map<String, Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }
 * 
 * 
 * 
 * }catch (HttpClientErrorException exception) {
 * logger.error("Error while getting accessProfileID :"+exception.
 * getResponseBodyAsString()); }catch (HttpStatusCodeException exception) {
 * logger.error("Error while getting accessProfileID :"+exception.
 * getResponseBodyAsString()); }catch (Exception e) {
 * logger.error("Error while getting accessProfileID :"+e.getMessage()); }
 * logger.info("getAccessProfileID method end"); return resultMap; }
 * 
 * 
 *//**
	 * This method is used to get system generated ID of user using filter criteria
	 * 
	 * @param username
	 * @param access_token
	 * @param properties
	 * @param tenantUrl
	 * @return If user found then return userid else return error message
	 */
/*
 * @SuppressWarnings("unchecked") private Map<String,Object>
 * getUserIDUsingUserName(String username, String access_token, Properties
 * properties, String tenantUrl) {
 * logger.info("getUserIDUsingUserName method start"); RestTemplate restTemplate
 * = new RestTemplate(); String getIdentityIDURL = null; HttpHeaders headers =
 * new HttpHeaders(); ResponseEntity<String> response = null; HttpEntity<String>
 * request = null; ObjectMapper mapper = new ObjectMapper(); String filter =
 * null; Map<String,Object> resultMap = new HashMap<>(); try { filter =
 * URLEncoder.encode(properties.getProperty(Constants.GET_IDENTITYID_FILER),
 * "UTF-8"); headers = new HttpHeaders();
 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token);
 * getIdentityIDURL =
 * tenantUrl+properties.getProperty(Constants.GET_IDENTITYID_USING_FILTER)+
 * filter+'"'+username+'"';
 * 
 * request = new HttpEntity<String>(headers);
 * 
 * response = restTemplate.exchange(getIdentityIDURL, HttpMethod.GET, request,
 * String.class);
 * 
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_200))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_201))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_202)))) { List<Map<String,Object>> identityList =
 * mapper.readValue(response.getBody(),List.class); if(identityList.isEmpty())
 * resultMap.put("success", "Identity no found."); for (Map<String, Object>
 * identity : identityList) {
 * if(username.equalsIgnoreCase(identity.get("name").toString())) {
 * resultMap.put(Constants.ID, identity.get(Constants.ID).toString()); } } }else
 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
 * valueOf(Constants.STATUS_400))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_403))) ||
 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
 * Constants.STATUS_500)))){ Map<String,Object> errorMap =
 * mapper.readValue(response.getBody(),Map.class); List<Map<String,Object>>
 * messageList = (List<Map<String, Object>>) errorMap.get(Constants.MESSAGE);
 * for (Map<String, Object> message : messageList) { resultMap.put("error",
 * message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }else {
 * Map<String,Object> errorMap = mapper.readValue(response.getBody(),Map.class);
 * List<Map<String,Object>> messageList = new ArrayList<>();
 * messageList.add(errorMap); for (Map<String, Object> message : messageList) {
 * resultMap.put("error", message.get(Constants.TEXT)); }
 * logger.error("Error message is:"+resultMap.get("error")); }
 * 
 * 
 * }catch (HttpClientErrorException exception) {
 * logger.error("Error while getting userId :"+exception.getResponseBodyAsString
 * ()); }catch (HttpStatusCodeException exception) {
 * logger.error("Error while getting userId :"+exception.getResponseBodyAsString
 * ()); }catch (Exception e) {
 * logger.error("Error while getting userId :"+e.getMessage()); }
 * logger.info("getUserIDUsingUserName method end"); return resultMap;
 * 
 * }
 * 
 *//**
	 * This method is used to get system generated ID of entitlement using
	 * entitlement name
	 * 
	 * @param username
	 * @param access_token
	 * @param properties
	 * @param tenantUrl
	 * @return If entitlement found then return entitlementId else return error
	 *         message
	 *//*
		 * @SuppressWarnings("unchecked") private Map<String,Object>
		 * getEntitlementID(String name, String access_token, Properties properties,
		 * String tenantUrl) { logger.info("getEntitlementID method start");
		 * RestTemplate restTemplate = new RestTemplate(); String getEntitlementIDURL =
		 * null; HttpHeaders headers = new HttpHeaders(); ResponseEntity<String>
		 * response = null; HttpEntity<String> request = null; ObjectMapper mapper = new
		 * ObjectMapper(); String filter = null; Map<String,Object> resultMap = new
		 * HashMap<>(); Map<String,Object> errorMap = new HashMap<>();
		 * List<Map<String,Object>> messageList = new ArrayList<>(); try { filter =
		 * URLEncoder.encode(properties.getProperty(Constants.GET_ENTITLEMENT_ID_FILTER)
		 * , "UTF-8"); headers = new HttpHeaders();
		 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 * headers.add(Constants.AUTHORIZATION, "Bearer "+access_token);
		 * getEntitlementIDURL =
		 * tenantUrl+properties.getProperty(Constants.GET_ENTITLEMENT_ID_USING_FILTER)+
		 * filter+'"'+name+'"';
		 * 
		 * request = new HttpEntity<String>(headers);
		 * 
		 * response = restTemplate.exchange(getEntitlementIDURL, HttpMethod.GET,
		 * request, String.class);
		 * logger.info("status code :"+response.getStatusCode());
		 * 
		 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
		 * valueOf(Constants.STATUS_200))) ||
		 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
		 * Constants.STATUS_201))) ||
		 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
		 * Constants.STATUS_202)))) { List<Map<String,Object>> entitlementList =
		 * mapper.readValue(response.getBody(),List.class);
		 * if(entitlementList.isEmpty()) resultMap.put("error", "Group not found.");
		 * logger.info("Returning response :"+response.getBody()); for (Map<String,
		 * Object> entitlement : entitlementList) { String entitlementName =
		 * entitlement.get("name").toString();
		 * if(entitlementName.equalsIgnoreCase(name)) { resultMap.put(Constants.ID,
		 * entitlement.get(Constants.ID).toString()); }
		 * 
		 * } }else
		 * if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.
		 * valueOf(Constants.STATUS_400))) ||
		 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
		 * Constants.STATUS_403))) ||
		 * response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(Integer.valueOf(
		 * Constants.STATUS_500)))){ errorMap =
		 * mapper.readValue(response.getBody(),Map.class); messageList =
		 * (List<Map<String, Object>>) errorMap.get(Constants.MESSAGE); for (Map<String,
		 * Object> message : messageList) { resultMap.put("error",
		 * message.get(Constants.TEXT)); }
		 * logger.error("Error message is:"+resultMap.get("error")); }else { errorMap =
		 * mapper.readValue(response.getBody(),Map.class); messageList.add(errorMap);
		 * for (Map<String, Object> message : messageList) { resultMap.put("error",
		 * message.get(Constants.TEXT)); }
		 * logger.error("Error message is:"+resultMap.get("error")); }
		 * 
		 * 
		 * 
		 * }catch (HttpClientErrorException exception) {
		 * logger.error("Error while getting entitlementId :"+exception.
		 * getResponseBodyAsString()); }catch (HttpStatusCodeException exception) {
		 * logger.error("Error while getting entitlementId :"+exception.
		 * getResponseBodyAsString()); }catch (Exception e) {
		 * logger.error("Error while getting entitlementId :"+e.getMessage()); }
		 * logger.info("getEntitlementID method end"); return resultMap; }
		 * 
		 * }
		 */