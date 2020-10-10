package me.shw.restfulwebservice.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	
	private static int usersCount =3;
	
	static {
		users.add(new User(1,"kttt",new Date(),"pass1","900409-1192342"));
		users.add(new User(2,"Attt",new Date(),"pass2","900409-1023456"));
		users.add(new User(3,"Ettt",new Date(),"pass3","900402-1023455"));
	}
	
	public List<User> findAll(){
		return users;
	}

	public User save(User user) {
		if (user.getId()==null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		
		for (User user : users) {
			if (user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {
		
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			User user = iterator.next();
			
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
			
		}
		
		return null;
		
	}
	
	public User update(User updateuser) {

		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			User user = iterator.next();
			
			if (user.getId() == updateuser.getId()) {
				users.get(updateuser.getId()).setName("asdas");
				users.get(updateuser.getId()).setJoinDate(new Date());
				return user;
			}
			
		}
		return null;
	}
	
}
