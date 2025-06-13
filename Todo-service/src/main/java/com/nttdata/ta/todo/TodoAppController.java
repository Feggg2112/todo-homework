package com.nttdata.ta.todo;

import com.nttdata.ta.common.TodoItem;
import com.nttdata.ta.common.User;
import com.nttdata.ta.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TodoAppController {

	@Autowired
	private TodoItemRepository todoItemRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		List<TodoItem> todoList = todoItemRepository.findByUser_Id(user.getId());
		model.addAttribute("items", new TodoListViewModel(todoList));
		model.addAttribute("newitem", new TodoItem());
		return "index";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute TodoItem requestItem, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
// 如果用户是新的并且还没有被保存，则取消下面一行的注释并确保 userRepository 可用
// user = userRepository.save(user);
		TodoItem item = new TodoItem(requestItem.getCategory(), requestItem.getName(), user);
		todoItemRepository.save(item);
		return "redirect:/";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute TodoListViewModel requestItems, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		for (TodoItem requestItem : requestItems.getTodoList()) {
			TodoItem item = todoItemRepository.findById(requestItem.getId()).orElse(null);
			if (item != null && item.getUser().getId().equals(user.getId())) {
				item.setCategory(requestItem.getCategory());
				item.setName(requestItem.getName());
				item.setComplete(requestItem.isComplete());
				todoItemRepository.save(item);
			}
		}
		return "redirect:/";
	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute User user, Model model, HttpSession session) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
			session.setAttribute("user", existingUser);
			return "redirect:/";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// 在注册成功后将用户放入 session
	@PostMapping("/register")
	public String register(@ModelAttribute User user, Model model, HttpSession session) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser != null) {
			model.addAttribute("error", "Username already exists");
			return "register";
		}
		userRepository.save(user);
		session.setAttribute("user", user); // 将新注册的用户放入 session
		return "redirect:/login";
	}

	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus, HttpSession session) {
		sessionStatus.setComplete();
		session.invalidate();
		return "redirect:/login";
	}
}