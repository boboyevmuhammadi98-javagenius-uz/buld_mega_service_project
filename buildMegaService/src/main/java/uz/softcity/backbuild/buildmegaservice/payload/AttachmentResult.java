package uz.softcity.backbuild.buildmegaservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttachmentResult {
    private String message;
    private boolean success;
    private long attachmentId;

    public AttachmentResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
