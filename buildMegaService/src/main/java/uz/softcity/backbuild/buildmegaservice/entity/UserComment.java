package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String name;

    private String email;

    @Column(columnDefinition = "text")
    private String comment;

    @OneToOne
    private Attachment attachment;

    public UserComment(String name, String email, String comment, Attachment attachment) {
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.attachment = attachment;
    }
}
