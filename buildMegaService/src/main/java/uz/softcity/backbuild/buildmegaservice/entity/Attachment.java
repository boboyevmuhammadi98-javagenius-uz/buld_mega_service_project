package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String originalFileName;

    private long size;

    private String contentType;

    private String url = "/api/attachmentContent/";

    /**
     * this is constructor
     * @param originalFileName ...
     * @param size ...
     * @param contentType ...
     */
    public Attachment(String originalFileName, long size, String contentType) {
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
    }
}
