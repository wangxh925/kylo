package com.thinkbiganalytics.datalake.ranger.domain;


import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Class for setting parameters for HDFS policy which returns JSON policy object
 * @author sv186029
 *
 */
public class HDFSPolicy {

    private static final Logger log = LoggerFactory.getLogger(HDFSPolicy.class); 
    
	private ArrayList<String> userList = new ArrayList<String>();
	private ArrayList<String> groupList = new ArrayList<String>();
	private static ArrayList<String> permList = new ArrayList<String>();
	private String policyName;
	private String resourceName;
	private String description;
	private String repositoryName;
	private String repositoryType;
	private String isEnabled;
	private String isRecursive;
	private String isAuditEnabled;

	/**
	 * getter and setter 
	 * @return
	 */
	public String getPolicyName()
	{
		return policyName;
	}

	public String getResourceName()
	{
		return resourceName;
	}

	public String getDescription()
	{
		return description;
	}

	public String getRepositoryName()
	{
		return repositoryName;
	}

	public String getRepositorytype()
	{
		return repositoryType;
	}

	public String getIsEnabled()
	{
		return isEnabled;
	}

	public String getIsRecursive()
	{
		return isRecursive;
	}

	public String getIsAuditEnabled()
	{
		return isAuditEnabled;
	}

	public ArrayList<String> getUsers()
	{
		return userList;
	}

	public ArrayList<String> getGroups( )
	{
		return groupList;
	}

	public static ArrayList<String> getPermissions()
	{
		return permList ;
	}

	public void setPolicyName(String policyName)
	{
		this.policyName=policyName;
	}

	public void setResourceName(String resourceName)
	{
		this.resourceName=resourceName;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public void setRepositoryName(String repositoryName)
	{
		this.repositoryName=repositoryName;
	}

	public void setRepositorytype(String repositoryType)
	{
		this.repositoryType=repositoryType;
	}

	public void setIsEnabled(String isEnabled)
	{
		this.isEnabled=isEnabled;
	}

	public void setIsRecursive(String isRecursive)
	{
		this.isRecursive=isRecursive;
	}

	public void setIsAuditEnabled(String isAuditEnabled)
	{
		this.isAuditEnabled=isAuditEnabled;
	}

	public void setUsers(ArrayList<String> userList )
	{
		this.userList=userList;
	}

	public void setGroups(ArrayList<String> groupList )
	{
		this.groupList=groupList;
	}

	public static void setPermissions(ArrayList<String> permList )
	{
		HDFSPolicy.permList=permList;
	}

	/***
	 * Method for forming JSON policy object to post to Ranger REST API
	 * @return
	 */
	public JSONObject policyJson()

	{

		JSONObject policy = new JSONObject();
		JSONArray permMapList = new JSONArray();
		JSONObject permList = new JSONObject();
		JSONArray userValue = new JSONArray();
		JSONArray permValue= new JSONArray();
		JSONArray groupValue = new JSONArray();

		//Add users to list
		if (getUsers().isEmpty())
		{
			System.out.println("empty");
			//Do not add anything to list
		}
		else
		{
			for (int userCnt = 0; userCnt < getUsers().size(); userCnt++) {
				userValue.add(getUsers().get(userCnt));
			}
			permList.put("userList",userValue);
		}

		//Add groups to list
		if(getGroups().isEmpty())
		{
			//Do not add anything to list
		}

		else
		{
			for (int groupCnt = 0; groupCnt < getGroups().size(); groupCnt++) {
				groupValue.add(getGroups().get(groupCnt));
			}	
			permList.put("groupList",groupValue);
		}

		//Add permissions to list
		if(getPermissions().isEmpty())
		{
			//Do not add anything to list
		}

		else
		{
			for (int permissions = 0; permissions < getPermissions().size(); permissions++) {
				permValue.add(getPermissions().get(permissions));
			}
			permList.put("permList",permValue);
		}

		if(getUsers().isEmpty() && getGroups().isEmpty() && getPermissions().isEmpty())
		{
			System.out.println("permMapList is empty");
			//Do not add anything to list
		}

		else
		{
			permMapList.add(permList);
			policy.put("permMapList",permMapList);
		}

		policy.put("policyName",getPolicyName());
		policy.put("resourceName",getResourceName());
		policy.put("description",getDescription());
		policy.put("repositoryName",getRepositoryName());
		policy.put("repositoryType",getRepositorytype());
		policy.put("isEnabled",getIsEnabled());
		policy.put("isRecursive", getIsRecursive());
		policy.put("isAuditEnabled",getIsAuditEnabled());

		return policy;

	}

}
