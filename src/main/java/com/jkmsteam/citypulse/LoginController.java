package com.jkmsteam.citypulse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.jkmsteam.citypulse.*;
import com.jkmsteam.model.dao.RatingsDAO;
import com.jkmsteam.model.dao.UsersDAO;
import com.jkmsteam.model.dto.Rating;
import com.jkmsteam.model.dto.User;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String begin(Model model) {
		User user = new User();
		model.addAttribute("userForm", user);
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userForm") User user, Model model) {

		User tempUser = UsersDAO.getUserById(user.getId());
		logger.info("user's fbId: " + user.getId());
		logger.info("tempUser's fbId: " + tempUser.getId());
		if (tempUser.getId() == user.getId())
			logger.info(user.getFirstName() + " already exist, can't add");
		else {
			logger.info(user.toString());
			UsersDAO.addUser(user);
			logger.info(user.getFirstName() + " saved");
		}

		Map<String, Rating> ratingsMap = new HashMap<String, Rating>();
		List<Rating> counts = RatingsDAO.getAllRatings();
		for (Rating rating : counts) {
			ratingsMap.put(rating.getPlaceId(), rating);
			System.out.println(rating);
		}
		Gson gson = new Gson();
		String data = gson.toJson(ratingsMap);
		System.out.println(data);
		model.addAttribute("jsonData", data);
		return "map";
	}

}
