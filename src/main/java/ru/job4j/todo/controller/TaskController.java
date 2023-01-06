package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.UserSession;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    public TaskController(TaskService service, PriorityService priorityService, CategoryService categoryService) {
        this.taskService = service;
        this.priorityService = priorityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String getAll(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping("/formCreate")
    public String formCreateTask(Model model) {
        model.addAttribute("task", new Task(0, "описание", LocalDateTime.now(),
                false, new User(), new Priority(), new ArrayList<>()));
        model.addAttribute("priorities", priorityService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "tasks/add";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, HttpSession session,
                             @RequestParam(value = "categories", required = false) List<Integer> categoriesIds) {
        User user = UserSession.getUser(session);
        task.setUser(user);
        task.setCreated(LocalDateTime.now());
        taskService.add(task, categoriesIds);
        return "redirect:/tasks/";
    }

    @GetMapping("/getInfo/{id}")
    public String getInfo(Model model, @PathVariable("id") int id) {
        Optional<Task> optTask = taskService.findById(id);
        if (optTask.isEmpty()) {
            return "shared/error";
        }
        Task task = optTask.get();
        model.addAttribute("task", task);
        model.addAttribute("priorities", priorityService.getAll());
        return "tasks/info";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        if (!taskService.delete(id)) {
            return "shared/error";
        }
        return "redirect:/tasks/";
    }

    @PostMapping("/complete/{id}")
    public String setCompleteStatus(@PathVariable("id") int id) {
        taskService.complete(id);
        return "redirect:/tasks/";
    }

    @GetMapping("/edit/{id}")
    public String getFormEditTask(@PathVariable("id") int id, Model model) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            return "shared/error";
        }
        model.addAttribute("task", task.get());
        model.addAttribute("priorities", priorityService.getAll());
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task) {
        if (!taskService.update(task)) {
            return "/tasks/error";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/newTasks")
    public String getNewTasksList(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("newTasks", taskService.findTasks(false));
        return "tasks/new";
    }

    @GetMapping("/doneTasks")
    public String getDoneTasksList(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("doneTasks", taskService.findTasks(true));
        return "tasks/done";
    }
}
