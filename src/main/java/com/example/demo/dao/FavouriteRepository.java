package com.example.demo.dao;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Favourite;
//import com.example.demo.model.User;

@Transactional
public interface FavouriteRepository extends CrudRepository<Favourite, Integer>{

	//void save(String teamID);
	
	void save(List<Favourite> user);
	

/*	@Modifying
    @Transactional
    @Query("delete from FavTeams f where user = ?1")
	void deleteTeamById(String user);

*/
	//void deleteTeamById(List<Favourite> user);


// deleteTeamById(Favourite user1);


//boolean findByName(String teamName);


//boolean findByName(String teamName);


}
