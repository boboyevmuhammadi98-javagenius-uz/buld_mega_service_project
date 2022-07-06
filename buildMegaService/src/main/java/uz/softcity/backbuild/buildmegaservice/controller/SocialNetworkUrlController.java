package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetworkUrl;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.SocialNetworkUrlDto;
import uz.softcity.backbuild.buildmegaservice.service.SocialNetworkUrlService;

@RestController
@RequestMapping("/api/socialNetworkUrl")
public class SocialNetworkUrlController {
    @Autowired
    SocialNetworkUrlService socialNetworkUrlService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllSocialNetwork() {
        return ResponseEntity.ok(socialNetworkUrlService.getAllSocialNetworkUrl());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getSocialNetworkById(@PathVariable long id) {
        SocialNetworkUrl socialNetworkUrl = socialNetworkUrlService.getSocialNetworkUrlById(id);
        return ResponseEntity.status(socialNetworkUrl != null ? 200 : 409).body(socialNetworkUrl);
    }

    /**
     * @param socialNetworkUrlDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_SOCIAL_NETWORK_URL')")
    @PostMapping
    public HttpEntity<?> addSocialNetwork(@RequestBody SocialNetworkUrlDto socialNetworkUrlDto) {
        ApiResponse apiResponse = socialNetworkUrlService.addSocialNetworkUrl(socialNetworkUrlDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id                  - old object id
     * @param socialNetworkUrlDto - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_SOCIAL_NETWORK_URL')")
    @PutMapping("/{id}")
    public HttpEntity<?> editSocialNetwork(@PathVariable long id, @RequestBody SocialNetworkUrlDto socialNetworkUrlDto) {
        ApiResponse apiResponse = socialNetworkUrlService.editSocialNetworkUrl(id, socialNetworkUrlDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_SOCIAL_NETWORK_URL')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSocialNetwork(@PathVariable long id) {
        ApiResponse apiResponse = socialNetworkUrlService.deleteSocialNetwork(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
