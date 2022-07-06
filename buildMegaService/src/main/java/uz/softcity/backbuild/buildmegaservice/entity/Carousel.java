package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Carousel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String nameRu;

    @OneToOne(optional = false)
    private Attachment attachment;

    public Carousel(String name, String nameRu, Attachment attachment) {
        this.name = name;
        this.nameRu = nameRu;
        this.attachment = attachment;
    }
}
