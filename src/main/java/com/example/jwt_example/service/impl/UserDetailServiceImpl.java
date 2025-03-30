package com.example.jwt_example.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt_example.entities.Role;
import com.example.jwt_example.entities.User;
import com.example.jwt_example.exception.StudentNotFoundException;
import com.example.jwt_example.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.findByUsername(username).orElseThrow(()->new StudentNotFoundException(username));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapTo(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapTo(List<Role> roles){
		return roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
