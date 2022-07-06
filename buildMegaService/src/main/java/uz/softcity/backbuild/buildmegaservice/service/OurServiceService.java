package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.entity.OurService;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.OurServiceDto;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.OurServiceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OurServiceService {
    @Autowired
    OurServiceRepository ourServiceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @return all object
     */
    public List<OurService> getAllOurService() {
        return ourServiceRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public OurService getOurServiceById(long id) {
        return ourServiceRepository.findById(id).orElse(null);
    }

    /**
     * @param ourServiceDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addOurService(OurServiceDto ourServiceDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(ourServiceDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        ourServiceRepository.save(
                new OurService(
                        ourServiceDto.getName(),
                        ourServiceDto.getNameRu(),
                        ourServiceDto.getDescription(),
                        ourServiceDto.getDescriptionRu(),
                        optionalAttachment.get()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id            old object id for finding
     * @param ourServiceDto new object
     * @return ApiResponse class
     */
    public ApiResponse editOurService(long id, OurServiceDto ourServiceDto) {
        Optional<OurService> optionalOurService = ourServiceRepository.findById(id);
        if (!optionalOurService.isPresent())
            return new ApiResponse("OurService not found", false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(ourServiceDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        OurService ourService = optionalOurService.get();
        ourService.setName(ourServiceDto.getName());
        ourService.setNameRu(ourServiceDto.getNameRu());
        ourService.setDescription(ourServiceDto.getDescription());
        ourService.setDescriptionRu(ourServiceDto.getDescriptionRu());
        ourService.setAttachment(optionalAttachment.get());
        ourServiceRepository.save(ourService);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteOurService(long id) {
        Optional<OurService> optionalOurService = ourServiceRepository.findById(id);
        if (!optionalOurService.isPresent())
            return new ApiResponse("don't deleted", false);
        try {
            Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(optionalOurService.get().getAttachment().getId());
            attachmentContentRepository.delete(byAttachment_id.get());
            ourServiceRepository.deleteById(id);
            attachmentRepository.delete(optionalOurService.get().getAttachment());
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
