package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.SocialNetwork;

import java.util.List;
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Long> {
    List<SocialNetwork> findAllByIdNotNullOrderByIdAsc();
}
