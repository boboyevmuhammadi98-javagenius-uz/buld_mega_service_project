package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.Navigation;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.service.NavigationService;

@RestController
@RequestMapping("/api/navigation")
public class NavigationController {
    @Autowired
    NavigationService navigationService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllNavigation() {
        return ResponseEntity.ok(navigationService.getAllNavigation());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getNavigationById(@PathVariable long id) {
        Navigation navigationById = navigationService.getNavigationById(id);
        return ResponseEntity.status(navigationById != null ? 200 : 409).body(navigationById);
    }

    /**
     * @param navigation - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_NAVIGATION')")
    @PostMapping
    public HttpEntity<?> addNavigation(@RequestBody Navigation navigation) {
        ApiResponse apiResponse = navigationService.addNavigation(navigation);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id         - old object id
     * @param navigation - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_NAVIGATION')")
    @PutMapping("/{id}")
    public HttpEntity<?> editNavigation(@PathVariable long id, @RequestBody Navigation navigation) {
        ApiResponse apiResponse = navigationService.editNavigation(id, navigation);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_NAVIGATION')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteNavigation(@PathVariable long id) {
        ApiResponse apiResponse = navigationService.deleteNavigation(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
