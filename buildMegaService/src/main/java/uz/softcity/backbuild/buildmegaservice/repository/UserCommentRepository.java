package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.UserComment;

import java.util.List;
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    List<UserComment> findAllByIdNotNullOrderByIdAsc();
}
