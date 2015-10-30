package demo.entity;

// Generated 2015-5-27 16:42:53 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Userclass generated by hbm2java
 */
public class Userclass implements java.io.Serializable {

	private Integer id;
	private String userclass;
	private Set mydemos = new HashSet(0);

	public Userclass() {
	}

	public Userclass(String userclass, Set mydemos) {
		this.userclass = userclass;
		this.mydemos = mydemos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserclass() {
		return this.userclass;
	}

	public void setUserclass(String userclass) {
		this.userclass = userclass;
	}

	public Set getMydemos() {
		return this.mydemos;
	}

	public void setMydemos(Set mydemos) {
		this.mydemos = mydemos;
	}

}