package com.example.demo.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.FavouriteRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Favourite;
import com.example.demo.model.User;
//import com.example.demo.service.UserService;





@Controller
class genericCtrl{
   @Autowired
   private UserRepository userRepository;
   
   @Autowired
   private FavouriteRepository favouriteRepository;
   
  // @Autowired
   //private UserService userService;
   
 /*  @GetMapping("/index")
	public String renderIndex() {
		return "index";
	}*/
	
	@GetMapping("/selectfavouriteteam")
	public String renderselectfavouriteteam() {
		return "selectfavouriteteam";
	}
	
	/*@PostMapping("/index")
	public ModelAndView saveStuff(@RequestParam String team,@RequestParam String abbreviation) {
		if(team == null) {
			team = " ";
		}
		System.out.println(team);
		ModelAndView aa = new ModelAndView("index");
		aa.addObject("team",team);
		//aa.addObject("abb",abbreviation);
		return aa;
	}*/
	@GetMapping("/login")
	public ModelAndView getTeams() {
		ModelAndView demo = new ModelAndView("login");
		return demo;
	}
  
	
	@PostMapping("/login")
	public ModelAndView handleLogin(
			@RequestParam("userID") String userID,
	        @RequestParam("userName") String userName,
	        HttpSession session
	        ) {		 
		User us = new User();
	
		session.setAttribute("userID", userID);
		us.setUserID(userID);
		us.setUserName(userName);
		userRepository.save(us);
		
		System.out.println(userID+ " : " +userName);
	    
		if(userID.equals("108720323497079")) {
			System.out.println("12");
			return new ModelAndView("redirect:/admin");		
		}
		
		else {
        return new ModelAndView("redirect:/index");	
		}
	}
	
	
	@GetMapping("/select")
	public ModelAndView selectFav(@RequestParam("name") String teamName, HttpSession session) {
		String userID = session.getAttribute("userID").toString();
	   //String userID = session.getAttribute("user").toString();
	  //  session.setAttribute("user", user);
		
		ModelAndView select = new ModelAndView("index");
	    
		Favourite team = new Favourite();
		
		
		session.setAttribute("teamName", teamName);
		System.out.println("yo");
		  team.setTeamID(teamName);
		  //team.setUser(user);
		  System.out.println("yo2");
			  
		//team.setTeamName(teamName);
		  favouriteRepository.save(team);
		//	System.out.println("yo1");

		  
		  System.out.println("yo11");
		
	/*	 for(Favourite fav1 : favouriteRepository.findAll() ) { 
			fav.add(fav1);
				}
		 select.addObject("fav", fav);
		 System.out.println("yo111");*/
		  
		//  List<Favourite> fav = new ArrayList<Favourite>();
			
		 // HashMap<String,String> list = new HashMap<String,String>();
			
		  
		/*  for(Favourite list1 : favouriteRepository.findAll()) {
			  
			  
			  list.put("ID", teamID);
			 // ((Object) list).add(list1);
			 // ((Collection<Favourite>) list).add(list1);
			 // fav.add(list1);
			//list.get(list1);
		  }*/
		  
		  List<Favourite> user = new ArrayList<Favourite>();
	      for(Favourite user1 : favouriteRepository.findAll()) {
	    	
	 
	        user.add(user1);
	    	
	         // System.out.println("eee");
	        // favouriteRepository.save(user); 
	      }
	      
	     
	      select.addObject("user", user);
          
	     
	
	      //favouriteRepository.save(user);
		  
      //  select.addObject("list", list);
		
		 return new ModelAndView("redirect:/index");
		
	}
	
	/*@PostMapping("/index")
	public ModelAndView saveStuff(@RequestParam String team,@RequestParam String abbreviation) {
		if(team == null) {
			team = " ";
		}
		System.out.println(team);
		ModelAndView aa = new ModelAndView("index");
		aa.addObject("team",team);
		//aa.addObject("abb",abbreviation);
		return aa;
	}*/
	
	
	

}
