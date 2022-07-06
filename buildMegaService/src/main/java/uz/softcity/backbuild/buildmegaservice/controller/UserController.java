package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.User;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.UserDto;
import uz.softcity.backbuild.buildmegaservice.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * @return success and all object
     */
    @GetMapping
    @PreAuthorize(value = "hasAuthority('VIEW_USER')")
    public HttpEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('VIEW_USER')")
    public HttpEntity<?> getUserById(@PathVariable long id) {
        User userById = userService.getUserById(id);
        return ResponseEntity.status(userById != null ? 200 : 409).body(userById);
    }

    /**
     * @param userDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PostMapping
    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    public HttpEntity<?> addUser(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id      - old object id
     * @param userDto - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_USER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable long id, @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.editUser(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable long id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
