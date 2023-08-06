package com.hechaodong.bookmanager.model;

// 用户类
public class User {
	private Integer userId;  // 用户id
	private String userName; // 用户名
	private String password; // 密码
	private Integer role;	 //用户角色  1普通  2管理员
	private String sex;		// 用户性别
	private String phone;   // 用户手机号

	/**
	 * 获取
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 设置
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取
	 * @return role
	 */
	public Integer getRole() {
		return role;
	}

	/**
	 * 设置
	 * @param role
	 */
	public void setRole(Integer role) {
		this.role = role;
	}

	/**
	 * 获取
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String toString() {
		return "User{userId = " + userId +
				", userName = " + userName +
				", password = " + password +
				", role = " + role +
				", sex = " + sex +
				", phone = " + phone +
				"}";
	}
}
