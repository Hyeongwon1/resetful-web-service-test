package me.shw.restfulwebservice.user;

import java.util.Date;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"})
@JsonFilter("UserInfoV2")
public class UserV2 extends User{

	private String grade;

}
