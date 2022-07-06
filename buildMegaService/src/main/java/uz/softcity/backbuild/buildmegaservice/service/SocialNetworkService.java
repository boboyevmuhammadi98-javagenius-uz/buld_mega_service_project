package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetwork;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.repository.SocialNetworkRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SocialNetworkService {
    @Autowired
    SocialNetworkRepository socialNetworkRepository;

    /**
     * @return all object
     */
    public List<SocialNetwork> getAllSocialNetwork() {
        return socialNetworkRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public SocialNetwork getSocialNetworkById(long id) {
        return socialNetworkRepository.findById(id).orElse(null);
    }

    /**
     * @param socialNetwork - new object
     * @return ApiResponse class
     */
    public ApiResponse addSocialNetwork(SocialNetwork socialNetwork) {
        socialNetworkRepository.save(
                new SocialNetwork(
                        socialNetwork.getName()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id            old object id for finding
     * @param socialNetwork new object
     * @return ApiResponse class
     */
    public ApiResponse editSocialNetwork(long id, SocialNetwork socialNetwork) {
        Optional<SocialNetwork> optionalSocialNetwork = socialNetworkRepository.findById(id);
        if (!optionalSocialNetwork.isPresent())
            return new ApiResponse("social network not found", false);
        SocialNetwork network = optionalSocialNetwork.get();
        network.setName(socialNetwork.getName());
        socialNetworkRepository.save(network);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteSocialNetwork(long id) {
        try {
            socialNetworkRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
