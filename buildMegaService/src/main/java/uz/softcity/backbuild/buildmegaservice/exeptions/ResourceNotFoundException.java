package uz.softcity.backbuild.buildmegaservice.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
@Data
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String resourceField;
    private Object object;
}
