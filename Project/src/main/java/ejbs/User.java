package ejbs;

import javax.ejb.Stateless;

@Stateless
public class User {
String name;
int id;
public String getMessage() {
	return "Hello from User";
}
}
