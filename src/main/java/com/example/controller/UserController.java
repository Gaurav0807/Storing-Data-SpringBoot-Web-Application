package com.example.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.Entity.*;
import com.example.repository.UserRepository;
import com.example.service.UserService;


@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository r;
	
	@GetMapping(value= {"/","/home"})
	public String DataUser() {
		return "index";
	}
	
	@PostMapping("/save_data")
	public String createProduct(@RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("description") String description, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) throws IOException {
		
			
			String[] names = name.split(",");
			String[] descriptions = description.split(",");
			String[] departments = department.split(",");
			
			byte[] imageData = file.getBytes();
			 BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
             stream.write(imageData);
             stream.close();
			User user = new User();
			user.setName(names[0]);
			user.setImage(imageData);
			user.setDepartment(departments[0]);
			user.setDescription(descriptions[0]);
			userService.saveData(user);
			return "index";
	}
	
	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") int id, HttpServletResponse response, Optional<User> op)
			throws ServletException, IOException {
		
		op = userService.getDataById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(op.get().getImage());
		response.getOutputStream().close();
	}
	
	@GetMapping("/show_data")
	String show(Model m) {
		List<User> images = userService.getAllData();
		m.addAttribute("images", images);
		return "AllData";
	}
	
	
	
}
