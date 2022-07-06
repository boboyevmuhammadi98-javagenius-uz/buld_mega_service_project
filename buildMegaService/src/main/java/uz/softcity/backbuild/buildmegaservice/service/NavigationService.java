package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Navigation;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.repository.NavigationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NavigationService {
    @Autowired
    NavigationRepository navigationRepository;

    /**
     * @return all object
     */
    public List<Navigation> getAllNavigation() {
        return navigationRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public Navigation getNavigationById(long id) {
        return navigationRepository.findById(id).orElse(null);
    }

    /**
     * @param navigation - new object
     * @return ApiResponse class
     */
    public ApiResponse addNavigation(Navigation navigation) {
        navigationRepository.save(
                new Navigation(
                        navigation.getName(),
                        navigation.getNameRu()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id         old object id for finding
     * @param navigation new object
     * @return ApiResponse class
     */
    public ApiResponse editNavigation(long id, Navigation navigation) {
        Optional<Navigation> optionalNavigation = navigationRepository.findById(id);
        if (!optionalNavigation.isPresent())
            return new ApiResponse("Navigation not found", false);
        Navigation savedNavigation = optionalNavigation.get();
        savedNavigation.setName(navigation.getName());
        savedNavigation.setNameRu(navigation.getNameRu());
        navigationRepository.save(savedNavigation);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteNavigation(long id) {
        try {
            navigationRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
