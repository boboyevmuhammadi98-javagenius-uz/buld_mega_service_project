package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.entity.Carousel;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.CarouselDto;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.CarouselRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarouselService {
    @Autowired
    CarouselRepository carouselRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @return all object
     */
    public List<Carousel> getAllCarousel() {
        return carouselRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public Carousel getCarouselById(long id) {
        return carouselRepository.findById(id).orElse(null);
    }

    /**
     * @param carouselDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addCarousel(CarouselDto carouselDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(carouselDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        carouselRepository.save(
                new Carousel(
                        carouselDto.getName(),
                        carouselDto.getNameRu(),
                        optionalAttachment.get()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id          old object id for finding
     * @param carouselDto new object
     * @return ApiResponse class
     */
    public ApiResponse editCarousel(long id, CarouselDto carouselDto) {
        Optional<Carousel> optionalCarousel = carouselRepository.findById(id);
        if (!optionalCarousel.isPresent())
            return new ApiResponse("carousel not found", false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(carouselDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        Carousel carousel = optionalCarousel.get();
        carousel.setName(carouselDto.getName());
        carousel.setNameRu(carouselDto.getNameRu());
        carousel.setAttachment(optionalAttachment.get());
        carouselRepository.save(carousel);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteCarousel(long id) {
        Optional<Carousel> optionalCarousel = carouselRepository.findById(id);
        if (!optionalCarousel.isPresent())
            return new ApiResponse("carousel not found", true);
        try {
            Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(optionalCarousel.get().getAttachment().getId());
            attachmentContentRepository.delete(byAttachment_id.get());
            carouselRepository.deleteById(id);
            attachmentRepository.delete(optionalCarousel.get().getAttachment());
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
