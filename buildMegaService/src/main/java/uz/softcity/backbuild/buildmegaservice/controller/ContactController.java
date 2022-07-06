package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.Contact;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.service.ContactService;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllContact() {
        return ResponseEntity.ok(contactService.getAllContact());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getContactById(@PathVariable long id) {
        Contact contactById = contactService.getContactById(id);
        return ResponseEntity.status(contactById != null ? 200 : 409).body(contactById);
    }

    /**
     * @param contact - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_CONTACT')")
    @PostMapping
    public HttpEntity<?> addContact(@RequestBody Contact contact) {
        ApiResponse apiResponse = contactService.addContact(contact);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id      - old object id
     * @param contact - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_CONTACT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editContact(@PathVariable long id, @RequestBody Contact contact) {
        ApiResponse apiResponse = contactService.editContact(id, contact);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_CONTACT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteContact(@PathVariable long id) {
        ApiResponse apiResponse = contactService.deleteContact(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
