package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.User;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.UserDto;
import uz.softcity.backbuild.buildmegaservice.repository.RoleRepository;
import uz.softcity.backbuild.buildmegaservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * @return all object
     */
    public List<User> getAllUser() {
        return userRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * @param userDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addUser(UserDto userDto) {
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUserName());
        if (existsByUsername)
            return new ApiResponse("username already exists", false);
        userRepository.save(
                new User(
                        userDto.getUserName(),
                        passwordEncoder.encode(userDto.getPassword()),
                        roleRepository.findAllByRoleName("Admin").orElse(null)
                )
        );
        return new ApiResponse("success added", true);
    }

    /**
     * @param id      old object id for finding
     * @param userDto new object
     * @return ApiResponse class
     */
    public ApiResponse editUser(long id, UserDto userDto) {
        if (id == 1)
            return new ApiResponse("don't edited", false);
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("user not found", false);
        if (!optionalUser.get().getUsername().equals(userDto.getUserName()))
            if (userRepository.existsByUsername(userDto.getUserName()))
                return new ApiResponse("username already exists", false);
        User user = optionalUser.get();
        user.setUsername(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return new ApiResponse("success edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteUser(long id) {
        if (id == 1)
            return new ApiResponse("dont deleted", false);
        userRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }
}
