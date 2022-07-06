package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.OurService;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.OurServiceDto;
import uz.softcity.backbuild.buildmegaservice.service.OurServiceService;

@RestController
@RequestMapping("/api/ourService")
public class OurServiceController {
    @Autowired
    OurServiceService ourServiceService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllOurService() {
        return ResponseEntity.ok(ourServiceService.getAllOurService());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getOurServiceById(@PathVariable long id) {
        OurService ourServiceById = ourServiceService.getOurServiceById(id);
        return ResponseEntity.status(ourServiceById != null ? 200 : 409).body(ourServiceById);
    }

    /**
     * @param ourServiceDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_SERVICE')")
    @PostMapping
    public HttpEntity<?> addOurService(@RequestBody OurServiceDto ourServiceDto) {
        ApiResponse apiResponse = ourServiceService.addOurService(ourServiceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id            - old object id
     * @param ourServiceDto - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_SERVICE')")
    @PutMapping("/{id}")
    public HttpEntity<?> editOurService(@PathVariable long id, @RequestBody OurServiceDto ourServiceDto) {
        ApiResponse apiResponse = ourServiceService.editOurService(id, ourServiceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_SERVICE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOurService(@PathVariable long id) {
        ApiResponse apiResponse = ourServiceService.deleteOurService(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
