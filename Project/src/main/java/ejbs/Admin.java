package ejbs;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Stateless
@Entity
public class Admin {
	//remove it later
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int AdminId;
	public String AdminName;
	public String AdminPassword;
	public String role;
	
	public void setRole(String role)
	{
		this.role = role;
		
	}
	
	
	
	public String getRole()
	{
		return role;
	}
	public String getAdminName()
	{
		return AdminName;
	}
	
	public void setAdminName(String AdminName)
	{
		this.AdminName = AdminName;
	}
	
	public int getAdminId()
	{
		return AdminId;
	}
	
	public void setAdminId(int AdminId)
	{
		this.AdminId = AdminId;
	}
	
	public String AdminPassword()
	{
		return AdminPassword;
	}
	
	public void setAdminPassword(String AdminPassword)
	{
		this.AdminPassword = AdminPassword;
	}
	
	
	

}
