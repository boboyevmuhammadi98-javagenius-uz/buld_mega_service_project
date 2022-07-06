package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetworkUrl;

import java.util.List;
public interface SocialNetworkUrlRepository extends JpaRepository<SocialNetworkUrl, Long> {
    List<SocialNetworkUrl> findAllByIdNotNullOrderByIdAsc();
}
