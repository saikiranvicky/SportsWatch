package com.example.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.example.demo.model.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

	//void deleteById(String userID);
	 /*@Modifying
	 @Transactional
	 public void delete(userID);
*/
	void save(List<User> user);

	//Object deleteById(String userID);

//	void save(List<User> user);
	
	
	@Modifying
    @Transactional
    @Query("delete from User u where userID = ?1")
	void deleteUserById(String userID);

}
