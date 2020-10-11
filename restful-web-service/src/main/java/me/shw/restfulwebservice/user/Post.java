package me.shw.restfulwebservice.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	// User : Post -> 1: (0~N), Main : sub -> Parent : Child
	//post 입장에서는 user가 1개가 와야하기때문에 매니투원
	@ManyToOne(fetch = FetchType.LAZY) //post데이터가 로딩되는 시점에 가져온
	@JsonIgnore
	private User user;
	

}
