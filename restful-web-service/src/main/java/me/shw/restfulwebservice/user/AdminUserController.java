package me.shw.restfulwebservice.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

	private UserDaoService service;

	//생성자를 통한 의존성 주입
	public AdminUserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public MappingJacksonValue AllUsers(){
		List<User> users = service.findAll();
		
		//가져온 값에 대해서 필터적용 선언한 값들만
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id","name","joinDate","password");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(users);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	
	//URI & 파라미터 변경 방법은 일반 브라우져에서 사용가능
//	@GetMapping("/v1/users/{id}")
//	@GetMapping(value="/users/{id}/", params = "version=1")
	

	//헤더정보 변경 & 미디어 타입 변경은 일반 브라우저에서 사용 불가
//	@GetMapping(value="/users/{id}",headers="X-API-VERSION=1")
	@GetMapping(value="/users/{id}" , produces ="application/vnd.company.appv1+json")
	public MappingJacksonValue OneUserV1(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id","name","joinDate","ssn");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);
		
		return mapping;  
	}
	
//	@GetMapping("/v2/users/{id}")
//	@GetMapping(value="/users/{id}/", params = "version=2")
//	@GetMapping(value="/users/{id}",headers="X-API-VERSION=2")
	@GetMapping(value="/users/{id}" , produces ="application/vnd.company.appv2+json")
	public MappingJacksonValue OneUserV2(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		//user -> user2
		
		UserV2 userV2 = new UserV2();
		
		BeanUtils.copyProperties(user, userV2);
		
		userV2.setGrade("VIP");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id","name","joinDate","grade");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(userV2);
		mapping.setFilters(filters);
		
		return mapping;  
	}
	
}
