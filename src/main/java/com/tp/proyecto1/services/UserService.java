package com.tp.proyecto1.services;

import com.tp.proyecto1.model.User;
import com.tp.proyecto1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Transactional
	public void save(User user){
		userRepository.save(user);
	}

	@Transactional
	public List<User> findAll(){
		return this.userRepository.findAll();
	}

	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}
}
