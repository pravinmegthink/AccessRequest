/**
 * 
 */
package com.utils;

/**
 * @author pallavi.karle_identi
 *
 */
public interface Constants {
	
	String CLIENT_ID="CLIEN_ID";
	String CLIENT_SECRET="CLIENT_SECRET";
	String PROP_FILE_NAME="application.properties";
	String ENC_PROP_FILE_NAME="enc.properties";
	String HTTPS="https://";
	String DOMAIN="DOMAIN";
	String TENANT="TENANT";
	String API=".api";
	String COM=".com";
	String TOKEN_URL="TOKEN_URL";
	String GET_ACCESS_REQUEST_STATUS="GET_ACCESS_REQUEST_STATUS";
	String GET_ACCESS_REQUEST_STATUS_FILTER="?filters=accountActivityItemId";
	String GET_ACCESS_REQUEST_STATUS_FILTER_VALUE=" eq ";
	String GET_ACCESS_REQUEST_STATUS_FILTER_MORE="&count=true&offset=0&sorters=-created&limit=";
	String GET_ACCESS_REQUEST_STATUS_FILTER_LIMIT="GET_ACCESS_REQUEST_STATUS_FILTER_LIMIT";
	String SUBMIT_ACCESS_REQUEST="SUBMIT_ACCESS_REQUEST";
	String GET_ENTITLEMENT_ID_USING_FILTER="GET_ENTITLEMENT_ID_USING_FILTER";
	String GET_ENTITLEMENT_ID_FILTER="GET_ENTITLEMENT_ID_FILTER";
	String GET_IDENTITYID_USING_FILTER="GET_IDENTITYID_USING_FILTER";
	String GET_IDENTITYID_FILER="GET_IDENTITYID_FILER";
	String AUTHORIZATION="Authorization";
	String STATUS_200="200";
	String STATUS_201="201";
	String STATUS_202="202";
	String STATUS_400="400";
	String STATUS_403="403";
	String STATUS_500="500";
	String CONTENT_TYPE="Content-Type";
	String ACCEPT="Accept";
	String GRANT_TYPE="grant_type";
	String CLIENT_CREDENTIALS="client_credentials";
	String CLIENT_ID_HEADER="client_id";
	String CLIENT_SECRET_HEADER="client_secret";
	String ACCESS_TOKEN="access_token";
	String GRANT_ACCESS="GRANT_ACCESS";
	String REVOKE_ACCESS="REVOKE_ACCESS";
	String ENTITLEMENT="ENTITLEMENT";
	String SUCCESS="success";
	String ERROR="Error";
	String OPERATION="operation";
	String MESSAGE="message";
	String NAME="name";
	String TYPE="type";
	String STATE="state";
	String REQUEST_TYPE="requestType";
	String ACCESS_REQUEST_ID="accessRequestId";
	String CLIENT_METADATA="clientMetadata";
	String REQUESTED_APP_NAME="requestedAppName";
	String ACCESS_REQUEST_PHASES="accessRequestPhases";
	String USERNAME="username";
	String GROUP="group";
	String REQUESTED_FOR="requestedFor";
	String ADD="add";
	String COMMENT="comment";
	String ID="id";
	String REQUESTED_ITEMS="requestedItems";
	String TEXT="text";
	String ATTRIBUTES="attributes";
	String APPROVALDETAILS="approvalDetails";
	String APPROVALID="approvalId";
	String ORIGINALOWNER="originalOwner";
	String CURRENTOWNER="currentOwner";
	String SCHEME="scheme";
	String ROLE="role";
	String ACCESS_PROFILE="ACCESS_PROFILE";
	String GET_ROLE_ID_FILTER="GET_ROLE_ID_FILTER";
	String GET_ROLE_ID_USING_FILTER="GET_ROLE_ID_USING_FILTER";
	String GET_ACCESS_PROFILE_ID_USING_FILTER="GET_ACCESS_PROFILE_ID_USING_FILTER";
	String GET_ACCESS_PROFILE_ID_FILTER="GET_ACCESS_PROFILE_ID_FILTER";
	String GET_ACCESS_REQUEST_ID_WITH_FILTER="GET_ACCESS_REQUEST_ID_WITH_FILTER";
	String ACCESS_REQUESTED_BY="ACCESS_REQUESTED_BY";
	String SLEEP_INTERVAL="SLEEP_INTERVAL";
	String SEARCH_QUERY_URL="SEARCH_QUERY_URL";
	String indices ="indices";
	String queryType="queryType";
	String DSL = "DSL";
	String queryDsl = "queryDsl";
	String query_string="query_string";
	String DSL_QUERY = "DSL_QUERY";
	String query = "query";
	String queryResultFilter="queryResultFilter";
	String includes="includes";
	String sort="sort";
	String sort_filter = "-created";
	String indices_type = "events";
	String queryResultFilter_includes ="*";
	String GET_ACCOUNT_ACTIVITY_URL="GET_ACCOUNT_ACTIVITY_URL";
}