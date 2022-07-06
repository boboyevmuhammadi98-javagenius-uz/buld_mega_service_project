package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.UserComment;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.UserCommentDto;
import uz.softcity.backbuild.buildmegaservice.service.UserCommentService;

@RestController
@RequestMapping("/api/userComment")
public class UserCommentController {
    @Autowired
    UserCommentService userCommentService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllUserComment() {
        return ResponseEntity.ok(userCommentService.getAllUserComment());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getUserCommentById(@PathVariable long id) {
        UserComment userComment = userCommentService.getUserCommentById(id);
        return ResponseEntity.status(userComment != null ? 200 : 409).body(userComment);
    }

    /**
     * @param userCommentDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PostMapping
    public HttpEntity<?> addUserComment(@RequestBody UserCommentDto userCommentDto) {
        ApiResponse apiResponse = userCommentService.addCUserComment(userCommentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_USER_COMMENT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUserComment(@PathVariable long id) {
        ApiResponse apiResponse = userCommentService.deleteUserComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
