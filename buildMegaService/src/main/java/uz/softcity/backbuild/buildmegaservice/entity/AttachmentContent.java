package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 2000000000)
    private byte[] bytes;

    @OneToOne
    private Attachment attachment;

    public AttachmentContent(byte[] bytes, Attachment attachment) {
        this.bytes = bytes;
        this.attachment = attachment;
    }
}
