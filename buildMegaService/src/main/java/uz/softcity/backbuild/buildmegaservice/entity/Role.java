package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softcity.backbuild.buildmegaservice.entity.enums.Permission;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roleName;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private Set<Permission> permissions;

    public Role(String roleName, String description, Set<Permission> permissions) {
        this.roleName = roleName;
        this.description = description;
        this.permissions = permissions;
    }
}
