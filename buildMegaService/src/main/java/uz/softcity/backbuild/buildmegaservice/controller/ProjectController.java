package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.Project;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.ProjectDto;
import uz.softcity.backbuild.buildmegaservice.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProject());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getProjectById(@PathVariable long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.status(project != null ? 200 : 409).body(project);
    }

    /**
     * @param projectDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_PROJECT')")
    @PostMapping
    public HttpEntity<?> addProject(@RequestBody ProjectDto projectDto) {
        ApiResponse apiResponse = projectService.addProject(projectDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id      - old object id
     * @param projectDto - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_PROJECT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCarousel(@PathVariable long id, @RequestBody ProjectDto projectDto) {
        ApiResponse apiResponse = projectService.editProject(id, projectDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_PROJECT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable long id) {
        ApiResponse apiResponse = projectService.deleteProject(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
