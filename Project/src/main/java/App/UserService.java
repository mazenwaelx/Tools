package App;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ejbs.User;
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserService {
	@EJB
	User user;
	
	ArrayList<User> users = new ArrayList<User>();
	
	@GET
	@Path("hello")
	public String sayHello() {
		return user.getMessage();
	}
	@GET
	@Path("helloGuy/{id}")
	public String sayHello(@PathParam("id") int id) {
		return user.getMessage() + " " + id;
		
	}
	
	@POST
	@Path("adduser")
	public String adduser(User u)
	{
		users.add(u);
		return "User Added Successfully";
	}
	

}
