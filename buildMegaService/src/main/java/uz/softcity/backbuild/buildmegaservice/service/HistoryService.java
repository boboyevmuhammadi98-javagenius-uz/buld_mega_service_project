package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.entity.History;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.HistoryDto;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.HistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @return all object
     */
    public List<History> getAllHistory() {
        return historyRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public History getHistoryById(long id) {
        return historyRepository.findById(id).orElse(null);
    }

    /**
     * @param historyDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addHistory(HistoryDto historyDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(historyDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        historyRepository.save(
                new History(
                        historyDto.getName(),
                        historyDto.getNameRu(),
                        historyDto.getDescription(),
                        historyDto.getDescriptionRu(),
                        optionalAttachment.get()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id         old object id for finding
     * @param historyDto new object
     * @return ApiResponse class
     */
    public ApiResponse editHistory(long id, HistoryDto historyDto) {
        Optional<History> optionalHistory = historyRepository.findById(id);
        if (!optionalHistory.isPresent())
            return new ApiResponse("history not found", false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(historyDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        History history = optionalHistory.get();
        history.setName(historyDto.getName());
        history.setNameRu(historyDto.getNameRu());
        history.setDescription(historyDto.getDescription());
        history.setDescriptionRu(historyDto.getDescriptionRu());
        history.setAttachment(optionalAttachment.get());
        historyRepository.save(history);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteHistory(long id) {
        Optional<History> optionalHistory = historyRepository.findById(id);
        if (!optionalHistory.isPresent())
            return new ApiResponse("history not found", true);
        try {
            Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(optionalHistory.get().getAttachment().getId());
            attachmentContentRepository.delete(byAttachment_id.get());
            historyRepository.deleteById(id);
            attachmentRepository.delete(optionalHistory.get().getAttachment());
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
