package com.nttdata.ta.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import com.nttdata.ta.common.*;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @GetMapping("/items")
    public List<TodoItem> getItems(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return List.of();
        }
        return todoItemRepository.findByUser_Id(user.getId());
    }

    @PostMapping("/items")
    public TodoItem addItem(@RequestBody TodoItem item, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return null;
        }
        TodoItem newItem = new TodoItem(item.getCategory(), item.getName(), user);
        return todoItemRepository.save(newItem);
    }

    @PutMapping("/items/{id}")
    public TodoItem updateItem(@PathVariable Long id, @RequestBody TodoItem updatedItem, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return null;
        }

        Optional<TodoItem> optionalItem = todoItemRepository.findById(id);
        if (optionalItem.isPresent()) {
            TodoItem item = optionalItem.get();
            if (!item.getUser().getId().equals(user.getId())) {
                return null;
            }

            item.setCategory(updatedItem.getCategory());
            item.setName(updatedItem.getName());
            item.setComplete(updatedItem.isComplete());

            return todoItemRepository.save(item);
        }
        return null;
    }

    public String sayHello() {
        return "Hello World";
    }
}