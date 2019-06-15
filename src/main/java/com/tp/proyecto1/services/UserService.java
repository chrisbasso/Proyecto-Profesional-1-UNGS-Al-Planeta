package com.tp.proyecto1.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.users.Privilege;
import com.tp.proyecto1.model.users.Role;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.repository.users.PrivilegeRepository;
import com.tp.proyecto1.repository.users.RoleRepository;
import com.tp.proyecto1.repository.users.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;


	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Transactional
	public void createUserIfNotExist(User user){
		User u = userRepository.findByUser(user.getUser());
		if (u == null) {
			userRepository.save(user);
		}
	}

	@Transactional
	public List<User> findAll(){
		return this.userRepository.findAll();
	}

	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Transactional
	public Privilege createPrivilegeIfNotFound(String name) {

		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	public Role getRolByName(String name) {
		Role role = roleRepository.findByName(name);
		return role;
	}

	@Transactional
	public void save(User user){

		userRepository.save(user);
	}


	@Transactional
	public Role createRoleIfNotFound(
			String name, Collection<Privilege> privileges) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}

	@Transactional
	public List<Privilege> getPrivileges(){
		return privilegeRepository.findAll();
	}

	@Transactional
	public List<Role> getRoles(){
		return roleRepository.findAll();
	}

	@Transactional
	public boolean valideUser(User user){
		return userRepository.exists(Example.of(user));
	}

	@Transactional
	public User getUserByName(String userName){
		return userRepository.findByUser(userName);
	}

	@Transactional
	public User getUserById(Long userId){
		Optional <User> usuario = userRepository.findById(userId);
		if(usuario.isPresent()) {
			return usuario.get();
		}else {
			return null;
		}
	}
	
}
