package com.wyait.manage.entity;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class UserDTO {

	private Integer id;
	@NotNull(message = "用户名不能为空，请您先填写用户名")
	private String username;

	@NotNull(message = "密码不能为空")
	@MatchPattern(pattern = "^[0-9_a-zA-Z]{6,20}$", message = "用户名或密码有误，请您重新填写")
	private String password;

	private static final Pattern CODE = Pattern.compile("[0-9]{6}$");



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}