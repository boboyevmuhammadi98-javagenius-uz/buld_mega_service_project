package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.RecentComment;

import java.util.List;

public interface RecentCommentRepository extends JpaRepository<RecentComment, Long> {
    List<RecentComment> findAllByIdNotNullOrderByIdAsc();
}
