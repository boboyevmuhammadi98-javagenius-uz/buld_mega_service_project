package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.Carousel;

import java.util.List;

public interface CarouselRepository extends JpaRepository<Carousel, Long> {
    List<Carousel> findAllByIdNotNullOrderByIdAsc();
}
