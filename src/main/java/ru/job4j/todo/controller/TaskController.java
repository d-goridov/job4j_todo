package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "allTasks";
    }

    @GetMapping("/formCreate")
    public String formCreateTask(Model model) {
        model.addAttribute("task", new Task(0, "описание", LocalDateTime.now(), false));
        return "addTask";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        service.add(task);
        return "redirect:/tasks/";
    }

    @GetMapping("/getInfo/{id}")
    public String getInfo(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", service.findById(id));
        return "taskInfo";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/tasks/";
    }

    @PostMapping("/complete/{id}")
    public String setCompleteStatus(@PathVariable("id") int id) {
        service.complete(id);
        return "redirect:/tasks/";
    }

    @GetMapping("/edit/{id}")
    public String getFormEditTask(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", service.findById(id));
        return "editTask";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task) {
        service.update(task);
        return "redirect:/tasks/";
    }

    @GetMapping("/newTasks")
    public String getNewTasksList(Model model) {
        model.addAttribute("newTasks", service.findTasks(false));
        return "newTasks";
    }

    @GetMapping("/doneTasks")
    public String getDoneTasksList(Model model) {
        model.addAttribute("doneTasks", service.findTasks(true));
        return "doneTasks";
    }
}
