package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.entity.News;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.NewsDto;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.NewsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @return all object
     */
    public List<News> getAllNews() {
        return newsRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public News getNewsById(long id) {
        return newsRepository.findById(id).orElse(null);
    }

    /**
     * @param newsDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addNews(NewsDto newsDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(newsDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        newsRepository.save(
                new News(
                        newsDto.getName(),
                        newsDto.getNameRu(),
                        newsDto.getDescription(),
                        newsDto.getDescriptionRu(),
                        optionalAttachment.get()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id      old object id for finding
     * @param newsDto new object
     * @return ApiResponse class
     */
    public ApiResponse editNews(long id, NewsDto newsDto) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent())
            return new ApiResponse("news not found", false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(newsDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        News news = optionalNews.get();
        news.setName(newsDto.getName());
        news.setNameRu(newsDto.getNameRu());
        news.setDescription(newsDto.getDescription());
        news.setDescriptionRu(newsDto.getDescriptionRu());
        news.setAttachment(optionalAttachment.get());
        newsRepository.save(news);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteNews(long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent())
            return new ApiResponse("news not found", false);
        try {
            Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(optionalNews.get().getAttachment().getId());
            attachmentContentRepository.delete(byAttachment_id.get());
            newsRepository.deleteById(id);
            attachmentRepository.delete(optionalNews.get().getAttachment());
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
