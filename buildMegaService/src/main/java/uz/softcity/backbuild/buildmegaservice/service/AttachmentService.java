package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.AttachmentResult;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @param id                  found attachment id
     * @param httpServletResponse - handle multipart file
     * @throws IOException when no return request
     */
    public void getAttachmentById(long id, HttpServletResponse httpServletResponse) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachment_Id(id);
            if (optionalAttachmentContent.isPresent()) {
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalFileName() + "\"");
                httpServletResponse.setStatus(200);
                httpServletResponse.setContentType(attachment.getContentType());
                FileCopyUtils.copy(optionalAttachmentContent.get().getBytes(), httpServletResponse.getOutputStream());
            }
        } else {
            httpServletResponse.setStatus(404);
        }
    }

    /**
     * @param request handle file
     * @return AttachmentResult class
     * @throws IOException when do not add the attachment
     */
    public AttachmentResult addAttachment(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames;
        MultipartFile file;
        try {
            fileNames = request.getFileNames();
            file = request.getFile(fileNames.next());
        } catch (Exception e) {
            return new AttachmentResult("wont wrong something", false);
        }
        if (file != null && !file.isEmpty()) {
            Attachment savedAttachment = attachmentRepository.save(
                    new Attachment(
                            file.getOriginalFilename(),
                            file.getSize(),
                            file.getContentType()
                    )
            );
            savedAttachment.setUrl(savedAttachment.getUrl().concat(String.valueOf(savedAttachment.getId())));
            attachmentRepository.save(savedAttachment);
            attachmentContentRepository.save(
                    new AttachmentContent(
                            file.getBytes(),
                            savedAttachment
                    )
            );
            return new AttachmentResult("added", true, savedAttachment.getId());
        }
        return new AttachmentResult("don't saved but file not found", false);
    }

    /**
     * @param id      - old attachment id for editing
     * @param request - handle file
     * @return ApiResponse class
     * @throws IOException when do not editing
     */
    public ApiResponse editAttachment(long id, MultipartHttpServletRequest request) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        Iterator<String> fileNames;
        MultipartFile file;
        try {
            fileNames = request.getFileNames();
            file = request.getFile(fileNames.next());
        } catch (Exception e) {
            return new ApiResponse("wont wrong something", false);
        }
        if (file != null && !file.isEmpty()) {
            Attachment attachment = optionalAttachment.get();
            attachment.setOriginalFileName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            attachmentRepository.save(attachment);
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachment_Id(id);
            if (!optionalAttachmentContent.isPresent())
                return new ApiResponse("attachment not found", false);
            AttachmentContent attachmentContent = optionalAttachmentContent.get();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(attachment);
            attachmentContentRepository.save(attachmentContent);
            return new ApiResponse("edited", true);
        }
        return new ApiResponse("don't saved but file not found", false);
    }
}
