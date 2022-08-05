package aubay.sibs.service;

import aubay.sibs.exception.ObjectNotFoundException;
import aubay.sibs.model.User;
import aubay.sibs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no user with given id"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("There is no user with given id"));
        user.setId(existingUser.getId());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
