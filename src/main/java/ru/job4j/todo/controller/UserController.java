package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;
import ru.job4j.todo.util.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> optionalUser = service.add(user);
        if (optionalUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("message", "Пользователь с такой почтой уже существует");
        return "auth/fail";
    }

    /**
     * Метод возвращает представление с сообщением
     * об успешной попытке добавления пользователя
     * @param model - объект типа Model
     * @return - представление success"
     */
    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("message", "Регистрация прошла успешно!");
        return "auth/success";
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        return "auth/addUser";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userHbm = service.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userHbm.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userHbm.get());
        return "redirect:/tasks/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/tasks/";
    }
}
