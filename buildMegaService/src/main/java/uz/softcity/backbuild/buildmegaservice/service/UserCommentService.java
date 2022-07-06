package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.entity.UserComment;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.UserCommentDto;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.UserCommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserCommentService {
    @Autowired
    UserCommentRepository userCommentRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @return all object
     */
    public List<UserComment> getAllUserComment() {
        return userCommentRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public UserComment getUserCommentById(long id) {
        return userCommentRepository.findById(id).orElse(null);
    }

    /**
     * @param userCommentDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addCUserComment(UserCommentDto userCommentDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(userCommentDto.getAttachmentId());
        userCommentRepository.save(
                new UserComment(
                        userCommentDto.getName(),
                        userCommentDto.getEmail(),
                        userCommentDto.getComment(),
                        optionalAttachment.orElse(null)
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteUserComment(long id) {
        Optional<UserComment> optionalUserComment = userCommentRepository.findById(id);
        if (!optionalUserComment.isPresent())
            return new ApiResponse("userComment not found", true);
        try {
            if (optionalUserComment.get().getAttachment() != null) {
                Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(optionalUserComment.get().getAttachment().getId());
                attachmentContentRepository.delete(byAttachment_id.get());
                userCommentRepository.deleteById(id);
                attachmentRepository.delete(optionalUserComment.get().getAttachment());
            } else {
                userCommentRepository.deleteById(id);
            }
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }

}
