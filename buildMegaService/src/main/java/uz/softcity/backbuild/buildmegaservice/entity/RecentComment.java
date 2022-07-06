package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class RecentComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String nameRu;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String descriptionRu;

    public RecentComment(String name, String nameRu, String description, String descriptionRu) {
        this.name = name;
        this.nameRu = nameRu;
        this.description = description;
        this.descriptionRu = descriptionRu;
    }
}
