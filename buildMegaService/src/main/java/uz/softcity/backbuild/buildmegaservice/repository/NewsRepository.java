package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.News;

import java.util.List;
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByIdNotNullOrderByIdAsc();
}
