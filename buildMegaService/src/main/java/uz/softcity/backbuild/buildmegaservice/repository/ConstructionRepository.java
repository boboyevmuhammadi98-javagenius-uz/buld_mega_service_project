package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.Construction;

import java.util.List;

public interface ConstructionRepository extends JpaRepository<Construction, Long> {
    List<Construction> findAllByIdNotNullOrderByIdAsc();
}
