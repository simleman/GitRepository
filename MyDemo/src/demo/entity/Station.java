package demo.entity;

// Generated 2015-5-27 16:42:53 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Station generated by hbm2java
 */
public class Station implements java.io.Serializable {

	private Integer id;
	private String name;
	private Set papers = new HashSet(0);
	private Set testquestionses = new HashSet(0);

	public Station() {
	}

	public Station(String name, Set papers, Set testquestionses) {
		this.name = name;
		this.papers = papers;
		this.testquestionses = testquestionses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getPapers() {
		return this.papers;
	}

	public void setPapers(Set papers) {
		this.papers = papers;
	}

	public Set getTestquestionses() {
		return this.testquestionses;
	}

	public void setTestquestionses(Set testquestionses) {
		this.testquestionses = testquestionses;
	}

}
