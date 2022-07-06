package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Construction;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.repository.ConstructionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConstructionService {
    @Autowired
    ConstructionRepository constructionRepository;

    /**
     * @return all object
     */
    public List<Construction> getAllConstruction() {
        return constructionRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public Construction getConstructionById(long id) {
        return constructionRepository.findById(id).orElse(null);
    }

    /**
     * @param construction - new object
     * @return ApiResponse class
     */
    public ApiResponse addConstruction(Construction construction) {
        constructionRepository.save(new Construction(construction.getName(), construction.getNameRu()));
        return new ApiResponse("added", true);
    }

    /**
     * @param id           old object id for finding
     * @param construction new object
     * @return ApiResponse class
     */
    public ApiResponse editConstruction(long id, Construction construction) {
        Optional<Construction> optionalConstruction = constructionRepository.findById(id);
        if (!optionalConstruction.isPresent())
            return new ApiResponse("Construction not found", false);
        Construction savedConstruction = optionalConstruction.get();
        savedConstruction.setName(construction.getName());
        savedConstruction.setNameRu(construction.getNameRu());
        constructionRepository.save(savedConstruction);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteConstruction(long id) {
        try {
            constructionRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
