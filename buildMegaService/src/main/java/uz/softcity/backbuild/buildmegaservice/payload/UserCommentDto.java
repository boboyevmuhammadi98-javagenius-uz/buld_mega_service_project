package uz.softcity.backbuild.buildmegaservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCommentDto {

    private String name;

    private String email;

    private String comment;

    private long attachmentId;
}
