package me.shw.restfulwebservice.user;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PostRepository PostRepository;
	
	
	@GetMapping("/users")
	public List<User> AllUsers(){
	
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> OneUser(@PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		Resource<User> resource = new Resource<>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).AllUsers());
		resource.add(linkTo.withRel("all-user"));
		
		return resource;
		
	}
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> createUsser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		
		
		//
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	@GetMapping("/users/{id}/posts")
	public List<Post> AllPostsByUser(@PathVariable int id){
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		
		return user.get().getPosts();
		
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		post.setUser(user.get());
		
		Post savedPost = PostRepository.save(post);
		
		//
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
			
	}

}
