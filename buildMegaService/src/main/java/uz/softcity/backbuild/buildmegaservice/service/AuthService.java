package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.User;
import uz.softcity.backbuild.buildmegaservice.exeptions.ResourceNotFoundException;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.LoginDto;
import uz.softcity.backbuild.buildmegaservice.repository.RoleRepository;
import uz.softcity.backbuild.buildmegaservice.repository.UserRepository;
import uz.softcity.backbuild.buildmegaservice.security.JwtProvider;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     *
     * @param loginDto - for login user
     * @return api response class
     */
    public ApiResponse loginUser(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            return new ApiResponse("token", true, jwtProvider.generateToken(user.getUsername(), user.getRole()));
        } catch (Exception exception) {
            return new ApiResponse("password or login in correct", false);
        }
    }

    /**
     *
     * @param username username for search
     * @return User class
     * @throws UsernameNotFoundException when do not dound user
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
    }
}
