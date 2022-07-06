package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.RecentComment;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.service.RecentCommentService;

@RestController
@RequestMapping("/api/recentComment")
public class RecentCommentController {

    @Autowired
    RecentCommentService recentCommentService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllRecentComment() {
        return ResponseEntity.ok(recentCommentService.getAllRecentComment());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getRecentCommentById(@PathVariable long id) {
        RecentComment recentCommentById = recentCommentService.getRecentCommentById(id);
        return ResponseEntity.status(recentCommentById != null ? 200 : 409).body(recentCommentById);
    }

    /**
     * @param recentComment - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_COMMENT')")
    @PostMapping
    public HttpEntity<?> addRecentComment(@RequestBody RecentComment recentComment) {
        ApiResponse apiResponse = recentCommentService.addRecentComment(recentComment);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id            - old object id
     * @param recentComment - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_COMMENT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editRecentComment(@PathVariable long id, @RequestBody RecentComment recentComment) {
        ApiResponse apiResponse = recentCommentService.editRecentComment(id, recentComment);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_COMMENT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteRecentComment(@PathVariable long id) {
        ApiResponse apiResponse = recentCommentService.deleteRecentComment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
