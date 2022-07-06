package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String nameRu;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String descriptionRu;

    @OneToOne(optional = false)
    private Attachment attachment;

    public Project(String name, String nameRu, String description, String descriptionRu, Attachment attachment) {
        this.name = name;
        this.nameRu = nameRu;
        this.description = description;
        this.descriptionRu = descriptionRu;
        this.attachment = attachment;
    }
}
