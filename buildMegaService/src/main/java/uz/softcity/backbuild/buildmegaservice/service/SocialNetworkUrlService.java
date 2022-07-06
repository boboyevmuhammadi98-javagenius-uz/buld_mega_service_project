package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetwork;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetworkUrl;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.SocialNetworkUrlDto;
import uz.softcity.backbuild.buildmegaservice.repository.SocialNetworkRepository;
import uz.softcity.backbuild.buildmegaservice.repository.SocialNetworkUrlRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SocialNetworkUrlService {
    @Autowired
    SocialNetworkUrlRepository socialNetworkUrlRepository;

    @Autowired
    SocialNetworkRepository socialNetworkRepository;

    /**
     * @return all object
     */
    public List<SocialNetworkUrl> getAllSocialNetworkUrl() {
        return socialNetworkUrlRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public SocialNetworkUrl getSocialNetworkUrlById(long id) {
        return socialNetworkUrlRepository.findById(id).orElse(null);
    }

    /**
     * @param socialNetworkUrlDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addSocialNetworkUrl(SocialNetworkUrlDto socialNetworkUrlDto) {
        Optional<SocialNetwork> optionalSocialNetwork = socialNetworkRepository.findById(socialNetworkUrlDto.getSocialNetworkId());
        if (!optionalSocialNetwork.isPresent())
            return new ApiResponse("social network not found", false);
        socialNetworkUrlRepository.save(
                new SocialNetworkUrl(
                        socialNetworkUrlDto.getUrl(),
                        optionalSocialNetwork.get()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id                  old object id for finding
     * @param socialNetworkUrlDto new object
     * @return ApiResponse class
     */
    public ApiResponse editSocialNetworkUrl(long id, SocialNetworkUrlDto socialNetworkUrlDto) {
        Optional<SocialNetworkUrl> optionalSocialNetworkUrl = socialNetworkUrlRepository.findById(id);
        if (!optionalSocialNetworkUrl.isPresent())
            return new ApiResponse("social network Url not found", false);
        Optional<SocialNetwork> optionalSocialNetwork = socialNetworkRepository.findById(socialNetworkUrlDto.getSocialNetworkId());
        if (!optionalSocialNetwork.isPresent())
            return new ApiResponse("social network not found", false);
        SocialNetworkUrl network = optionalSocialNetworkUrl.get();
        network.setUrl(socialNetworkUrlDto.getUrl());
        network.setSocialNetwork(optionalSocialNetwork.get());
        socialNetworkUrlRepository.save(network);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteSocialNetwork(long id) {
        try {
            socialNetworkUrlRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
