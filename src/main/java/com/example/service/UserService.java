package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveData(User user)
	{
		userRepository.save(user);
	}
	
	public List<User> getAllData() {
		return userRepository.findAll();
	}
	
	public Optional<User> getDataById(int id) {
		return userRepository.findById(id);
	}

	

}
