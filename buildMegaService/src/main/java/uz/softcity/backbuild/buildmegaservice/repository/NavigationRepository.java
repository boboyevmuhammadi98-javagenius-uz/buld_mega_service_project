package uz.softcity.backbuild.buildmegaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softcity.backbuild.buildmegaservice.entity.Navigation;

import java.util.List;
public interface NavigationRepository extends JpaRepository<Navigation, Long> {
    List<Navigation> findAllByIdNotNullOrderByIdAsc();
}
