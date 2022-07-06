package uz.softcity.backbuild.buildmegaservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsDto {
    private String name;

    private String nameRu;

    private String description;

    private String descriptionRu;

    private long attachmentId;
}
