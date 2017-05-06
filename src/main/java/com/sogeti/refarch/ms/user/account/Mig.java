package com.sogeti.refarch.ms.user.account;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Mig {
	private final Logger log = LoggerFactory.getLogger(Mig.class);

	@RequestMapping("/")
	public String msg()	{
		return "successfully build....";
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public Response authenticateUser(@RequestBody User user) {

		try {
			log.error("=========================>");
			// Authenticate the user using the credentials provided
			int userId = authenticate(user.email, user.password);
			log.error(user.email + " == " + user.password + " == " + userId);
			System.err.println(user.email + " == " + user.password + " == " + userId);
			// Issue a token for the user
			//String token = issueToken(username);

			// Return the token on the response
			return Response.ok(userId).build();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return Response.status(Response.Status.NO_CONTENT).build();
		}      
	}
	
	public static Integer authenticate(String email, String pass)	{
		
		ArrayList<User> allUser = getAllUser();
		
		for (User usr : allUser) {
			if(usr.getEmail().equalsIgnoreCase(email) & 
					usr.getPassword().equalsIgnoreCase(pass)) return usr.uid;
		}
		return 0;
	}

	public static ArrayList<User> getAllUser() {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<User> user = null;
		String json = "[\r\n" + 
				"	{\r\n" + 
				"        \"uid\": 146901,\r\n" + 
				"        \"email\": \"mig@capg.com\",\r\n" + 
				"        \"password\": \"pass\",\r\n" + 
				"        \"name\" : \"Mrigank Varma\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"uid\": 45678,\r\n" + 
				"        \"email\": \"chris@capg.com\",\r\n" + 
				"        \"password\": \"pass\",\r\n" + 
				"        \"name\" : \"Christian\"\r\n" + 
				"    }\r\n" + 
				"]";
		try {
			user = mapper.readValue(new File("src/main/java/user.json"), new TypeReference<ArrayList<User>>() {});			
			//user = mapper.readValue(json, new TypeReference<ArrayList<User>>() {});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static void main(String args[])	{
		System.out.println(authenticate("chris@capg.com", "pass"));
	}
}

class User{
	String email;
	String name;
	String password;
	Integer uid;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
