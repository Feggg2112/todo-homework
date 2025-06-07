package com.nttdata.ta.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoRestController {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/items")
    public List<TodoItem> getItems(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 处理未登录情况，可以返回空列表或抛出异常
            return List.of();
        }
        return todoItemRepository.findByUser_Id(user.getId());
    }

    @PostMapping("/items")
    public TodoItem addItem(@RequestBody TodoItem item, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 处理未登录情况
            return null;
        }
        // 使用正确的构造器，传入用户对象
        TodoItem newItem = new TodoItem(item.getCategory(), item.getName(), user);
        return todoItemRepository.save(newItem);
    }

    @PutMapping("/items/{id}")
    public TodoItem updateItem(@PathVariable Long id, @RequestBody TodoItem updatedItem, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 处理未登录情况
            return null;
        }

        Optional<TodoItem> optionalItem = todoItemRepository.findById(id);
        if (optionalItem.isPresent()) {
            TodoItem item = optionalItem.get();
            // 确保当前用户只能更新自己的待办事项
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
}