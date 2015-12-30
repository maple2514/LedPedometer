/**
 *---------------------------------------------------
 * 项目名称： LedPedometer
 * Copyright: © 2015 Sun Quan,Computer Science,NJU University 
 * @author Sun Quan
 * @version 1.0
 * @date 2015年12月30日 下午7:14:01
 * @since JDK 1.7.0_67
 * User.java 
 *  <p>Description:用户信息类 包含姓名，年龄，体重，身高
 *----------------------------------------------------
*/   
package com.nju.run.model;

public class User {
	private User(){
		
	}
	private static User user;
	//单例
	public static User makeInstance(){
		if(user == null){
			synchronized (User.class) {
				if(user == null){
					user = new User();
					return user;
				}
			}
		}
		return user;
	}
	private String name;
	private int age;
	private int weight;
	private int height;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @param name
	 * @param age
	 * @param weight
	 * @param height
	 */
	public User(String name, int age, int weight, int height) {
		super();
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.height = height;
	}

	
}
