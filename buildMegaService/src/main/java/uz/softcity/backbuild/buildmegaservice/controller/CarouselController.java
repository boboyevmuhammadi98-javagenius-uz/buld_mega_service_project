package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.Carousel;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.CarouselDto;
import uz.softcity.backbuild.buildmegaservice.service.CarouselService;

@RestController
@RequestMapping("/api/carousel")
public class CarouselController {

    @Autowired
    CarouselService carouselService;

    /**
     * @return - success and all object otherwise empty body
     */
    @GetMapping()
    public HttpEntity<?> getAllCarousel() {
        return ResponseEntity.ok(carouselService.getAllCarousel());
    }

    /**
     * @param id - found object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getCarouselById(@PathVariable long id) {
        Carousel carousel = carouselService.getCarouselById(id);
        return ResponseEntity.status(carousel != null ? 200 : 409).body(carousel);
    }

    /**
     * @param carouselDto - new carousel data
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_CAROUSEL')")
    @PostMapping
    public HttpEntity<?> addCarousel(@RequestBody CarouselDto carouselDto) {
        ApiResponse apiResponse = carouselService.addCarousel(carouselDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id          - editing object id
     * @param carouselDto - new data for editing object
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_CAROUSEL')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCarousel(@PathVariable long id, @RequestBody CarouselDto carouselDto) {
        ApiResponse apiResponse = carouselService.editCarousel(id, carouselDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - object id for deleting
     * @return when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_CAROUSEL')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable long id) {
        ApiResponse apiResponse = carouselService.deleteCarousel(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
