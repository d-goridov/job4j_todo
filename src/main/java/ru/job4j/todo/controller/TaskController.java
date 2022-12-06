package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "allTasks";
    }

    @GetMapping("/formCreateTask")
    public String formCreateTask(Model model) {
        model.addAttribute("task", new Task(0, "описание", LocalDateTime.now(), false));
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        service.add(task);
        return "redirect:/all";
    }

    @GetMapping("/getInfo/{taskId}")
    public String getInfo(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
        return "taskInfo";
    }

    @PostMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        service.delete(id);
        return "redirect:/all";
    }

    @PostMapping("/complete/{taskId}")
    public String setCompleteStatus(@PathVariable("taskId") int id) {
        service.complete(id);
        return "redirect:/all";
    }

    @GetMapping("/edit/{taskId}")
    public String getFormEditTask(@PathVariable("taskId") int id, Model model) {
        model.addAttribute("task", service.findById(id));
        return "editTask";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task) {
        service.update(task);
        return "redirect:/all";
    }

    @GetMapping("/newTasks")
    public String getNewTasksList(Model model) {
        model.addAttribute("newTasks", service.findNewTasks());
        return "newTasks";
    }

    @GetMapping("/doneTasks")
    public String getDoneTasksList(Model model) {
        model.addAttribute("doneTasks", service.findFinishedTasks());
        return "doneTasks";
    }
}
