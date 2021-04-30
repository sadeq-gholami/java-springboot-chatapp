package com.kth.chatapp.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.kth.chatapp.model.ChatRoom;
import com.kth.chatapp.model.CustomUserDetails;
import com.kth.chatapp.model.User;
import com.kth.chatapp.repos.ChatRoomRepo;
import com.kth.chatapp.repos.UserRepository;

@Controller
public class ChatRoomListController {
	@Autowired
    private UserRepository userRepo;
	@Autowired
	private ChatRoomRepo chatRoomRepo;
	
	
	@GetMapping("/chat_rooms")
    public String listUsers(@AuthenticationPrincipal CustomUserDetails user,Model model) {
	 	String email = user.getUsername();
        List<ChatRoom> roomList = chatRoomRepo.getRooms(email);
	 	//List<User> roomList = userRepo.findAll();
        model.addAttribute("roomList", roomList);  
        return "chat_room_list";
    }
	
	@GetMapping("/create_room")
	public String createRoom(Model model) {
		model.addAttribute("room",new ChatRoom());
		return "create_room";
	}
	@PostMapping("/handle_room")
    public String handleRoom(ChatRoom room, @AuthenticationPrincipal CustomUserDetails userDetail) {
		String email = userDetail.getUsername();
		User user = userRepo.findByEmail(email);
		user.getRooms().add(room);
		room.getMembers().add(user);
        chatRoomRepo.save(room);
         
        return "redirect:/chat_rooms";
	    }
   
}
