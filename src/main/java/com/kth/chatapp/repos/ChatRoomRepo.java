package com.kth.chatapp.repos;

import java.util.List;
//CRUD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kth.chatapp.model.ChatRoom;

public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
	@Query("SELECT r FROM ChatRoom r JOIN r.members rm WHERE rm.email = ?1")
	public List<ChatRoom> getRooms(String email);
	
	@Query("select r from ChatRoom r where r.id = ?1")
	public ChatRoom findRoomById(Long id);
}
