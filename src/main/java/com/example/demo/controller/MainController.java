package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.FavouriteRepository;
import com.example.demo.model.Favourite;
import com.example.demo.model.User;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {
	@Autowired
	private FavouriteRepository favouriteRepository;
	
	
	//Using PoJo Classes
	@GetMapping("/teams")
	public ModelAndView getTeams(HttpSession session) {
		//session.setAttribute("userID", userID);
		String userID = session.getAttribute("userID").toString();
		
		if(userID == null) {
			ModelAndView showTeams = new ModelAndView("redirect:/login");
			return 	showTeams;
		}
		
		ModelAndView showTeams = new ModelAndView("showTeams");
		//showTeams.addObject("name", "Human"); 
		
		ArrayList<HashMap<String, String>> teamDetails = new ArrayList<HashMap<String, String>>();
		
		//Endpoint to call
		String url ="https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		//Encode Username and Password
        String encoding = Base64.getEncoder().encodeToString("1340ca72-19aa-4287-8500-9b53bb:9elninoacmillan9".getBytes());
        // TOKEN:PASS
        //Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
			System.out.println(str);
			//JsonNode jsonNode1 = actualObj.get("lastUpdatedOn");
	       // System.out.println(root.get("teamgamelogs").get("lastUpdatedOn").asText());
	        //System.out.println(root.get("teamgamelogs").get("gamelogs").getNodeType());
	        JsonNode teamstandings = root.get("overallteamstandings").get("teamstandingsentry");
	        
	        if(teamstandings.isArray()) {
	        	
	        	teamstandings.forEach(teamstanding -> {
	        		JsonNode game = teamstanding.get("team");
	        		HashMap<String,String> teamDetail = new HashMap<String, String>();
	        		teamDetail.put("ID", game.get("ID").asText());
	        		teamDetail.put("City", game.get("City").asText());
	        		teamDetail.put("Name", game.get("Name").asText());
	        		teamDetail.put("Abbreviation", game.get("Abbreviation").asText());
	        		teamDetail.put("rank", teamstanding.get("rank").asText());
	        		teamDetails.add(teamDetail);
	        		
	        	});
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		showTeams.addObject("teamDetails", teamDetails);
		
		return showTeams;
	}
	
	//Using objectMapper
	@GetMapping("/team")
	public ModelAndView getTeamInfo(
			@RequestParam("id") String teamID 
			) {
		//System.out.println(teamID );
		ModelAndView teamInfo = new ModelAndView("teamInfo");
		ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/team_gamelogs.json?team=" + teamID;
		String encoding = Base64.getEncoder().encodeToString("1340ca72-19aa-4287-8500-9b53bb:9elninoacmillan9".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
			
	        JsonNode gamelogs = root.get("teamgamelogs").get("gamelogs");
	     
	        
	        if(gamelogs.isArray()) {
	        	
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("game");
	        		JsonNode stats = gamelog.get("stats");
	        		
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("Name", game.get("homeTeam").get("Name").asText());
	        		gameDetail.put("id", game.get("id").asText());
	        		gameDetail.put("date", game.get("date").asText());
	        		gameDetail.put("time", game.get("time").asText());
	        		gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
	        		gameDetail.put("Wins", stats.get("Wins").get("#text").asText());
	        		gameDetail.put("Losses", stats.get("Losses").get("#text").asText());
	        		gameDetails.add(gameDetail);
	        		
	        		
	        		
	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        teamInfo.addObject("gameDetails", gameDetails);
		
		

		ArrayList<HashMap<String, String>> fixtureDetails = new ArrayList<HashMap<String, String>>();
		String url1 = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/full_game_schedule.json";
		String encoding1 = Base64.getEncoder().encodeToString("1340ca72-19aa-4287-8500-9b53bb:9elninoacmillan9".getBytes());
        
		HttpHeaders headers1 = new HttpHeaders();
		headers1.setContentType(MediaType.APPLICATION_JSON);
		headers1.set("Authorization", "Basic "+encoding1);
		HttpEntity<String> request1 = new HttpEntity<String>(headers1);

		
		
		RestTemplate restTemplate1 = new RestTemplate();
		ResponseEntity<String> response1 = restTemplate1.exchange(url1, HttpMethod.GET, request1, String.class);
		String str1 = response1.getBody(); 
		ObjectMapper mapper1 = new ObjectMapper();
		try {
			JsonNode root = mapper1.readTree(str1);
			
	        JsonNode gamelogs = root.get("fullgameschedule").get("gameentry");
	       
	        	if(gamelogs.isArray()) {      		        
	        		
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("awayTeam");
	        		 if(gamelog.get("homeTeam").get("ID").asText().equals(teamID)) {
	        		HashMap<String,String> fixtureDetail = new HashMap<String, String>();
	        		fixtureDetail.put("time", gamelog.get("time").asText());
	        		fixtureDetail.put("date", gamelog.get("date").asText());		
	        		fixtureDetail.put("Abbreviation",gamelog.get("awayTeam").get("Abbreviation").asText());
	        		
	        		/*gameDetail.put("time", game.get("time").asText());
	        		gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
	        		gameDetail.put("Wins", stats.get("Wins").get("#text").asText());
	        		gameDetail.put("Losses", stats.get("Losses").get("#text").asText());*/
	        		
	        		fixtureDetails.add(fixtureDetail);	
	        		 }
	        
	        		
	        	});
	        }
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 
		teamInfo.addObject("fixtureDetails", fixtureDetails);
		
        
		return teamInfo;
	}
	
	@GetMapping("/index")
	public ModelAndView getScores(HttpSession session) {
		String teamName = session.getAttribute("teamName").toString();
		ModelAndView index = new ModelAndView("index");
		
		ArrayList<HashMap<String, String>> scoreDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/scoreboard.json?fordate=20181020";
		String encoding = Base64.getEncoder().encodeToString("1340ca72-19aa-4287-8500-9b53bb:9elninoacmillan9".getBytes());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
            JsonNode gamelogs = root.get("scoreboard").get("gameScore");
            
	        
	        if(gamelogs.isArray()) {
	        	
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("game");
	        		HashMap<String,String> scoreDetail = new HashMap<String, String>();
	        		scoreDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
	        		scoreDetail.put("homeTeam", game.get("homeTeam").get("Abbreviation").asText());
	        		scoreDetail.put("awayScore", gamelog.get("awayScore").asText());
	        		scoreDetail.put("homeScore", gamelog.get("homeScore").asText());
	        		scoreDetails.add(scoreDetail);
	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 List<Favourite> user = new ArrayList<Favourite>();
	      for(Favourite user1 : favouriteRepository.findAll()) {
	    	  
	    	  /*if(user1.equals(teamID)) {
		    	  System.out.println("asd");
		    	  favouriteRepository.deleteTeamById(user1);
		      }*/
	    	  
	    	  if(user1.equals(teamName)) {
		    		System.out.println("already selected");
		    	}
	    	  else {
	        user.add(user1);
	        }
		      System.out.println("eee");
	      }
	      
	    
	     // favouriteRepository.save(user);
		index.addObject("scoreDetails", scoreDetails);
		index.addObject("user", user);	
			
		return index;
		
		
}
	
	@GetMapping("/ranks")
	public ModelAndView getRanks(HttpSession session) {
		//session.setAttribute("userID", userID);
		String userID = session.getAttribute("userID").toString();
		
		if(userID == null) {
			ModelAndView showTeams = new ModelAndView("redirect:/login");
			return 	showTeams;
		}
		
		ModelAndView showRanks = new ModelAndView("showRanks");
		//showTeams.addObject("name", "Human"); 
		
		ArrayList<HashMap<String, String>> rankDetails = new ArrayList<HashMap<String, String>>();
		
		//Endpoint to call
		String url ="https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		//Encode Username and Password
        String encoding = Base64.getEncoder().encodeToString("1340ca72-19aa-4287-8500-9b53bb:9elninoacmillan9".getBytes());
        // TOKEN:PASS
        //Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
			System.out.println(str);
			//JsonNode jsonNode1 = actualObj.get("lastUpdatedOn");
	       // System.out.println(root.get("teamgamelogs").get("lastUpdatedOn").asText());
	        //System.out.println(root.get("teamgamelogs").get("gamelogs").getNodeType());
	        JsonNode teamstandings = root.get("overallteamstandings").get("teamstandingsentry");
	        
	        if(teamstandings.isArray()) {
	        	
	        	teamstandings.forEach(teamstanding -> {
	        		JsonNode game = teamstanding.get("team");
	        		HashMap<String,String> rankDetail = new HashMap<String, String>();
	        		//teamDetail.put("ID", game.get("ID").asText());
	        		//teamDetail.put("City", game.get("City").asText());
	        		rankDetail.put("Name", game.get("Name").asText());
	        		rankDetail.put("Abbreviation", game.get("Abbreviation").asText());
	        		rankDetail.put("rank", teamstanding.get("rank").asText());
	        		rankDetails.add(rankDetail);
	        		
	        	});
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		showRanks.addObject("rankDetails", rankDetails);
		
		return showRanks;
	}
/*	
	//Using objectMapper
		@GetMapping("/fav")
		public ModelAndView getProfileInfo(
				@RequestParam("id") String teamID, HttpSession session
				) {
			ModelAndView teamInfo = new ModelAndView("teamInfo");
			ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
			String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/team_gamelogs.json?team=" + teamID;
			String encoding = Base64.getEncoder().encodeToString("1340ca72-19aa-4287-8500-9b53bb:9elninoacmillan9".getBytes());
	        
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Basic "+encoding);
			HttpEntity<String> request = new HttpEntity<String>(headers);

			
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			String str = response.getBody(); 
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode root = mapper.readTree(str);
				
		        JsonNode gamelogs = root.get("teamgamelogs").get("gamelogs");
		        
		        if(gamelogs.isArray()) {
		        	
		        	gamelogs.forEach(gamelog -> {
		        		JsonNode game = gamelog.get("game");
		        		//JsonNode stats = gamelog.get("stats");
		        		
		        		HashMap<String,String> gameDetail = new HashMap<String, String>();
		        		gameDetail.put("Id", game.get("homeTeam").get("Id").asText());
		        		gameDetail.put("Name", game.get("homeTeam").get("Name").asText());
		        		//gameDetail.put("date", game.get("date").asText());
		        		//gameDetail.put("time", game.get("time").asText());
		        		//gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
		        	    //gameDetail.put("Wins", stats.get("Wins").get("#text").asText());
		        		//gameDetail.put("Losses", stats.get("Losses").get("#text").asText());
		        		gameDetails.add(gameDetail);
		        			
		        	});
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	         teamInfo.addObject("gameDetails", gameDetails);
			
	         return teamInfo;
	
		}*/
}