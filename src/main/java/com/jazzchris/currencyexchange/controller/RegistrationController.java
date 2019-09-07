package com.jazzchris.currencyexchange.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jazzchris.currencyexchange.entity.UserRegistration;
import com.jazzchris.currencyexchange.service.UserService;

@Controller
@RequestMapping
public class RegistrationController {

	@Autowired private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/register/form")
	public String showForm(Model model) {
		model.addAttribute("userRegistration", new UserRegistration());
		return "registration-form";
	}
	
	@PostMapping("/register/form")
	public String processForm(@Valid @ModelAttribute("userRegistration") UserRegistration userRegistration,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "registration-form";
		}
		if(userService.findByUsername(userRegistration.getUsername()).isPresent()) {
			model.addAttribute("userRegistration", new UserRegistration());
			model.addAttribute("registrationError", "Username already exists");
			return "registration-form";
		}
		userService.save(userRegistration);
		return "registration-confirm";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
