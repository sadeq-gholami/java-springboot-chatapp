package com.kth.chatapp.repos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kth.chatapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u From User u Where u.email =?1")
	public User findByEmail(String email);
	
	
	@Query("SELECT u FROM User u JOIN u.rooms ur WHERE ur.roomName = ?1")
	public List<User> getRoomMembers(String name);
}
