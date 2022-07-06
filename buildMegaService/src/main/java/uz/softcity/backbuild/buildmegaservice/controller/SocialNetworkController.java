package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetwork;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.service.SocialNetworkService;

@RestController
@RequestMapping("/api/socialNetwork")
public class SocialNetworkController {
    @Autowired
    SocialNetworkService socialNetworkService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllSocialNetwork() {
        return ResponseEntity.ok(socialNetworkService.getAllSocialNetwork());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getSocialNetworkById(@PathVariable long id) {
        SocialNetwork socialNetwork = socialNetworkService.getSocialNetworkById(id);
        return ResponseEntity.status(socialNetwork != null ? 200 : 409).body(socialNetwork);
    }

    /**
     * @param socialNetwork - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_SOCIAL_NETWORK')")
    @PostMapping
    public HttpEntity<?> addSocialNetwork(@RequestBody SocialNetwork socialNetwork) {
        ApiResponse apiResponse = socialNetworkService.addSocialNetwork(socialNetwork);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id            - old object id
     * @param socialNetwork - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_SOCIAL_NETWORK')")
    @PutMapping("/{id}")
    public HttpEntity<?> editSocialNetwork(@PathVariable long id, @RequestBody SocialNetwork socialNetwork) {
        ApiResponse apiResponse = socialNetworkService.editSocialNetwork(id, socialNetwork);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_SOCIAL_NETWORK')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSocialNetwork(@PathVariable long id) {
        ApiResponse apiResponse = socialNetworkService.deleteSocialNetwork(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
