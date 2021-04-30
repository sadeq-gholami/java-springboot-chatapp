package com.kth.chatapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kth.chatapp.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
	@Query("select m from Message m where m.roomid = ?1")
	public List<Message> findMessagesById(Long id);
}