package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.Construction;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.service.ConstructionService;

@RestController
@RequestMapping("/api/construction")
public class ConstructionController {

    @Autowired
    ConstructionService constructionService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllConstruction() {
        return ResponseEntity.ok(constructionService.getAllConstruction());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getConstructionById(@PathVariable long id) {
        Construction constructionById = constructionService.getConstructionById(id);
        return ResponseEntity.status(constructionById != null ? 200 : 409).body(constructionById);
    }

    /**
     * @param construction - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_CONSTRUCTION')")
    @PostMapping
    public HttpEntity<?> addConstruction(@RequestBody Construction construction) {
        ApiResponse apiResponse = constructionService.addConstruction(construction);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id           - old object id
     * @param construction - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_CONSTRUCTION')")
    @PutMapping("/{id}")
    public HttpEntity<?> editConstruction(@PathVariable long id, @RequestBody Construction construction) {
        ApiResponse apiResponse = constructionService.editConstruction(id, construction);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_CONSTRUCTION')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteConstruction(@PathVariable long id) {
        ApiResponse apiResponse = constructionService.deleteConstruction(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
