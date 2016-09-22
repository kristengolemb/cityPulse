package com.jkmsteam.citypulse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jkmsteam.citypulse.*;
import com.jkmsteam.model.dao.UsersDAO;
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
	public String saveUser(@ModelAttribute("userForm") User user,
			BindingResult result, Model model) {
		User tempUser = UsersDAO.getUserById(user.getFbId());
		logger.info("user's fbId: "+ user.getFbId());
		logger.info("tempUser's fbId: "+ tempUser.getFbId());
		if (tempUser.getFbId() == user.getFbId())
			logger.info(user.getFirstName() + " already exist, can't add");
		else {
			logger.info(user.toString());
			UsersDAO.addUser(user);
			logger.info(user.getFirstName() + " saved");
		}
		return "login";
	}
	
	
}
