package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.RecentComment;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.repository.RecentCommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecentCommentService {
    @Autowired
    RecentCommentRepository recentCommentRepository;

    /**
     * @return all object
     */
    public List<RecentComment> getAllRecentComment() {
        return recentCommentRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public RecentComment getRecentCommentById(long id) {
        return recentCommentRepository.findById(id).orElse(null);
    }

    /**
     * @param recentComment - new object
     * @return ApiResponse class
     */
    public ApiResponse addRecentComment(RecentComment recentComment) {
        recentCommentRepository.save(
                new RecentComment(
                        recentComment.getName(),
                        recentComment.getNameRu(),
                        recentComment.getDescription(),
                        recentComment.getDescriptionRu()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id            old object id for finding
     * @param recentComment new object
     * @return ApiResponse class
     */
    public ApiResponse editRecentComment(long id, RecentComment recentComment) {
        Optional<RecentComment> optionalRecentComment = recentCommentRepository.findById(id);
        if (!optionalRecentComment.isPresent())
            return new ApiResponse("RecentComment not found", false);
        RecentComment savedRecentComment = optionalRecentComment.get();
        savedRecentComment.setName(recentComment.getName());
        savedRecentComment.setNameRu(recentComment.getNameRu());
        savedRecentComment.setDescription(recentComment.getDescription());
        savedRecentComment.setDescriptionRu(recentComment.getDescriptionRu());
        recentCommentRepository.save(savedRecentComment);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteRecentComment(long id) {
        try {
            recentCommentRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
