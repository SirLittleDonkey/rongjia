package com.wyait.manage.entity;

/**
 * @项目名称：wyait-manage
 * @包名：com.wyait.manage.entity
 * @类描述：
 * @创建人：wyait
 * @创建时间：2018-01-02 17:10
 * @version：V1.0
 */
public class UserSearchDTO {

	private String uname;

	private String insertTimeStart;

	private String insertTimeEnd;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getInsertTimeStart() {
		return insertTimeStart;
	}

	public void setInsertTimeStart(String insertTimeStart) {
		this.insertTimeStart = insertTimeStart;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}

}
