package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.AttachmentResult;
import uz.softcity.backbuild.buildmegaservice.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/attachmentContent")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    /**
     * @param id       - id for searching attachment
     * @param response - return found attachment
     * @throws IOException - when do not return the attachment
     */
    @GetMapping("/{id}")
    public void getAttachmentById(@PathVariable long id, HttpServletResponse response) throws IOException {
        attachmentService.getAttachmentById(id, response);
    }

    /**
     * @param request - for handle file
     * @return - when added success otherwise conflict
     * @throws IOException - when do not saved this attachment
     */
    @PreAuthorize(value = "hasAuthority('ADD_ATTACHMENT')")
    @PostMapping
    public HttpEntity<?> addAttachment(MultipartHttpServletRequest request) throws IOException {
        AttachmentResult attachmentResult = attachmentService.addAttachment(request);
        return ResponseEntity.status(attachmentResult.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(attachmentResult);
    }

    /**
     * @param id      - editing attachment id
     * @param request - new attachment for editeing
     * @return - when edited success otherwise conflict
     * @throws IOException - when do not update the attachment
     */
    @PreAuthorize(value = "hasAuthority('EDIT_ATTACHMENT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editAttachment(@PathVariable long id, MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = attachmentService.editAttachment(id, request);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
