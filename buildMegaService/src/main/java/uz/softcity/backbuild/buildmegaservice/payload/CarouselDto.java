package uz.softcity.backbuild.buildmegaservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarouselDto {

    private String name;

    private String nameRu;

    private long attachmentId;
}
