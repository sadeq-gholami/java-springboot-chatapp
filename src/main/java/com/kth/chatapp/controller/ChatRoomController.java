package com.kth.chatapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kth.chatapp.model.*;
import com.kth.chatapp.repos.*;

@Controller
@CrossOrigin
public class ChatRoomController {
	@Autowired
    private UserRepository userRepo;
	@Autowired
	private ChatRoomRepo chatRoomRepo;
	
	@Autowired
	private MessageRepo message1repo;
	private Long roomId;
	private String roomName;
	private String firstName;
	
	@MessageMapping("/chat/{roomname}")
	@SendTo("/topic/messages/{roomname}")
	public OutputMessage send(Message message) throws Exception {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		message.setTime(time);
		message.setRoomid(this.roomId);
		message1repo.save(message);
		
	    return new OutputMessage(message.getSender(), message.getContent(), time);
	}
	
	@GetMapping("/chat_room/{firstName}/{roomName}/{id}")
    public String createChatRoom(@PathVariable("firstName") String firstName, @PathVariable("roomName") String roomName,@PathVariable("id") String roomId, HttpSession session, Model model) {
		//System.out.println(roomId);
		
		this.roomName = roomName;
		this.roomId = Long.parseLong(roomId);
		session.setAttribute("roomName", roomName);
		this.firstName=firstName;
		session.setAttribute("firstName", firstName);
		
		List<User> members = userRepo.getRoomMembers(roomName);
		 model.addAttribute("members", members);
		 
		List<Message> messages = message1repo.findMessagesById(this.roomId);  
		 
		 model.addAttribute("messages", messages);
		
        return "chat_room";
    }
	
	
	@PostMapping("/add_user")
	public String addUser(@RequestParam("memberToAdd") String memberToAdd,RedirectAttributes redirAttrs) {
		try {
			User user = userRepo.findByEmail(memberToAdd);
			ChatRoom room = chatRoomRepo.findRoomById(this.roomId);
			user.getRooms().add(room);
			room.getMembers().add(user);
			chatRoomRepo.save(room);
			return "redirect:/chat_room/"+this.firstName+"/"+this.roomName +"/" +this.roomId;
		}catch (Exception e) {
			redirAttrs.addFlashAttribute("error", "email does not exist");
			return "redirect:/chat_room/"+this.firstName+"/"+this.roomName +"/" +this.roomId;
		}
	}
	
}
