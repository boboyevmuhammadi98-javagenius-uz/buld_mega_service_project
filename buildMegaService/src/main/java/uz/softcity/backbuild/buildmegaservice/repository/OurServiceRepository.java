package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.OurService;

import java.util.List;
public interface OurServiceRepository extends JpaRepository<OurService, Long> {
    List<OurService> findAllByIdNotNullOrderByIdAsc();
}
