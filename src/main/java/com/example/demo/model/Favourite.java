package com.example.demo.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
//@Table(name="FavTeams")
public class Favourite {
	
	 @javax.persistence.Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String teamID;

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	
	private String teamName;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private User user;
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Favourite() {
	
	}

	@Override
	public String toString() {
		return "Favourite [id=" + id + ", teamID=" + teamID + ", teamName=" + teamName + ", user=" + user + "]";
	}
    
}
