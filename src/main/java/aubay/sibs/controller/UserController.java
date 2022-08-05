package aubay.sibs.controller;

import aubay.sibs.model.User;
import aubay.sibs.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        return userService.update(id, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
