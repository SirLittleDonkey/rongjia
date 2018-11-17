package com.wyait.manage.entity;

import com.wyait.manage.pojo.UserRoleKey;

import java.util.List;

public class UserRolesVO {
	private Integer id;

	private String username;

	private String password;

	private Integer insertUid;

	private String insertTime;

	private String updateTime;

	private boolean isDel;

	private boolean isJob;

	private List<UserRoleKey> userRoles;

	private Integer version;

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
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getInsertUid() {
		return insertUid;
	}

	public void setInsertUid(Integer insertUid) {
		this.insertUid = insertUid;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean getDel() {
		return isDel;
	}

	public void setDel(boolean del) {
		isDel = del;
	}

	public boolean getJob() {
		return isJob;
	}

	public void setJob(boolean job) {
		isJob = job;
	}

	public List<UserRoleKey> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoleKey> userRoles) {
		this.userRoles = userRoles;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


}