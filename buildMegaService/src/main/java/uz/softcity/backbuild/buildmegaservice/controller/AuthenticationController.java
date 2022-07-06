package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.LoginDto;
import uz.softcity.backbuild.buildmegaservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthService authService;

    /**
     * @param loginDto - handle the user data for login system
     * @return - success and token
     */
    @PostMapping
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.loginUser(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
