package me.shw.restfulwebservice.user;

import java.util.Date;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"})
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 정보를 위한 도메인 객체")
public class User {
	
	private Integer id;
	
	@Size(min=2 , message ="name은 2글자 이상 입력해 주세요")
	@ApiModelProperty(notes = "사용자의 이름을 입력해 주세요.")
	private String name;
	
	@PastOrPresent
	@ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요.")
	private Date joinDate;
	
	//데이터 반환시 생략하고 제공
	//@JsonIgnore
	@ApiModelProperty(notes = "사용자의 비밀번호 입력해 주세요.")
	private String password;
	@ApiModelProperty(notes = "사용자의 주민번호 입력해 주세요.")
	private String ssn;

}
