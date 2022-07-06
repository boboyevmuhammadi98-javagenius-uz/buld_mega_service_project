package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.History;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByIdNotNullOrderByIdAsc();
}
