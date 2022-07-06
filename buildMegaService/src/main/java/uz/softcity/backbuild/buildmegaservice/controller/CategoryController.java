package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.Category;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * @return - success and all category otherwise empty body
     */
    @GetMapping()
    public HttpEntity<?> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    /**
     * @param id category id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getCategoryById(@PathVariable long id) {
        Category categoryById = categoryService.getCategoryById(id);
        return ResponseEntity.status(categoryById != null ? 200 : 409).body(categoryById);
    }

    /**
     * @param category - new category data
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_CATEGORY')")
    @PostMapping
    public HttpEntity<?> addCategory(@RequestBody Category category) {
        ApiResponse apiResponse = categoryService.addCategory(category);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id       - old category id
     * @param category - new category for update
     * @return - when updated success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_CATEGORY')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable long id, @RequestBody Category category) {
        ApiResponse apiResponse = categoryService.editCategory(id, category);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id id for deleting category
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_CATEGORY')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable long id) {
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
