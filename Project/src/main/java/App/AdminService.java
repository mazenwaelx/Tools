package App;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ejbs.Admin;

@Stateless
@Path("admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AdminService {
	@PersistenceContext(unitName="hello")
	private EntityManager entityManager;
	
	@POST
	@Path("persistadmin")
	public String persistAdmin(Admin a)
	{
		entityManager.persist(a);
		return "Person Persisted";
	}
	
	@GET
	@Path("getAdmin/{id}")
	public String getAdminbyId(@PathParam("id") int id)
	{
		entityManager.find(Admin.class, id);
		return "success";
	}

}
