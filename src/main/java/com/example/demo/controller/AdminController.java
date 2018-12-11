package com.example.demo.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
//import com.example.demo.service.UserService;

@Controller
public class AdminController {

	//@Autowired
//	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	/*  @GetMapping("/adminlogin")
		public String renderadminlogin() {
			return "adminlogin";
		}
	  */
	/*  public Iterable<User> findAll(){
	        return userRepository.findAll();
	  }
	
		*/  
	  @GetMapping("/admin")
		  public ModelAndView admin() {
		     
		  ModelAndView aaa = new ModelAndView("admin");
		  List<User> user = new ArrayList<User>();
		      for(User user1 : userRepository.findAll()) {
		        user.add(user1);
		      //  userRepository.save(user);
			      
		      }
		      System.out.println("hello");
					aaa.addObject("user", user);
	   return aaa;
	  }
	  
  @GetMapping("/block")
	  public ModelAndView ModelAndView(@RequestParam("userID") String userID) {
	  ModelAndView aaa = new ModelAndView("admin");
	 /* User se = new User();
	  ArrayList<Long> ur = new ArrayList<Long>();	  
       System.out.println("world");	  
      userRepository.delete(userName);
        return new ModelAndView("redirect:/admin");
	*/
	  //User user = new User();
	  
	  //user.setUserID(userID);
	 // List<User> user = new ArrayList<User>();
	//  User user = new User();
	  System.out.println("bbbb");
	//  for(User user1:userRepository.findAll()) {
		  userRepository.deleteUserById(userID);
		  
		  
		  System.out.println("1223");
		  
		  List<User> user = new ArrayList<User>();
	      for(User user1 : userRepository.findAll()) {
	        user.add(user1);
	      //  userRepository.save(user);
		      
	      }
	      aaa.addObject("user", user);
//	  }
	 // userRepository.save(user);
	System.out.println("yo");
	 // aaa.addObject("user", user);
	   
	return aaa;
			/*new ModelAndView("redirect:/admin");*/
	  
	  }
	  
	
}




	

