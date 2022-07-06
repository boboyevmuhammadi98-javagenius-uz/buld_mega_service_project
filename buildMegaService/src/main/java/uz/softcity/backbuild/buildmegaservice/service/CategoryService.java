package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Category;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * @return all object
     */
    public List<Category> getAllCategory() {
        return categoryRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * @param category - new object
     * @return ApiResponse class
     */
    public ApiResponse addCategory(Category category) {
        categoryRepository.save(new Category(category.getName(), category.getNameRu()));
        return new ApiResponse("added", true);
    }

    /**
     * @param id       old object id for finding
     * @param category new object
     * @return ApiResponse class
     */
    public ApiResponse editCategory(long id, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("category not found", false);
        Category savedCategory = optionalCategory.get();
        savedCategory.setName(category.getName());
        savedCategory.setNameRu(category.getNameRu());
        categoryRepository.save(savedCategory);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteCategory(long id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
