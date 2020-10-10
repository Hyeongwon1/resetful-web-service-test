
package me.shw.restfulwebservice.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

@RestController
public class UserController {

	private UserDaoService service;

	//생성자를 통한 의존성 주입
	public UserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> AllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> OneUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		
		//hateoas
		Resource<User> resource = new Resource<>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).AllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		User savedUser = service.save(user);
		
		//
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
			
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user= service.deleteById(id);
		
		if (user == null) {
			
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
			
		}
	}
	
	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		
		User updateUser = service.update(user);
		
		if (updateUser == null) {
			
			throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
			
		}
		
		//
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(updateUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
			
	}
	
}
