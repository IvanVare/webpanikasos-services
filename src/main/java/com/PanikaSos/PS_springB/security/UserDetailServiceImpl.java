package com.PanikaSos.PS_springB.security;

import com.PanikaSos.PS_springB.model.ControlUser;
import com.PanikaSos.PS_springB.model.User;
import com.PanikaSos.PS_springB.model.dto.AuthResponse;
import com.PanikaSos.PS_springB.model.dto.LoginDTO;
import com.PanikaSos.PS_springB.repository.ControlUserRepository;
import com.PanikaSos.PS_springB.repository.UserRepository;
import com.PanikaSos.PS_springB.utils.EncryptPassword;
import com.PanikaSos.PS_springB.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private EncryptPassword encryptPassword;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ControlUserRepository controlUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<ControlUser> controlUser = controlUserRepository.findByEmail(email);
        if (controlUser.isPresent()){
            Set<SimpleGrantedAuthority> authorities = controlUser.get().getCuser_rol().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
                    .collect(Collectors.toSet());

            return UserPrincipal.builder()
                    .controlUser(controlUser.get())
                    .firstName(controlUser.get().getFirstName())
                    .lastName(controlUser.get().getLastName())
                    .id(controlUser.get().getId())
                    .email(controlUser.get().getEmail())
                    .password(controlUser.get().getPassword())
                    .authorities(authorities)
                    .build();
        }
        else if (user.isPresent()){
            Set<SimpleGrantedAuthority> authorities = user.get().getUser_rol().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
                    .collect(Collectors.toSet());

            return UserPrincipal.builder()
                    .firstName(user.get().getFirst_name())
                    .lastName(user.get().getLast_name())
                    .id(user.get().getId_user().longValue())
                    .email(user.get().getEmail())
                    .password(user.get().getPassword())
                    .authorities(authorities)
                    .build();
        }
        return null;
    }

    public AuthResponse login(LoginDTO loginDTO){
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Authentication authentication = this.authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        Optional <ControlUser> controlUser = controlUserRepository.findByEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        if (controlUser.isPresent()){
            if (controlUser.get().getStatus()!=1){
                throw new UsernameNotFoundException("User not found");
            }
            authResponse.setId(controlUser.get().getId());
            authResponse.setFirstName(controlUser.get().getFirstName());
            authResponse.setLastName(controlUser.get().getLastName());
            authResponse.setEmail(controlUser.get().getEmail());
            authResponse.setRol(controlUser.get().getCuser_rol());
            authResponse.setToken(accessToken);
            return authResponse;

        }else if (user.isPresent()){
            if (user.get().getStatus()!=1){
                throw new UsernameNotFoundException("User not found");
            }
            authResponse.setId(user.get().getId_user().longValue());
            authResponse.setFirstName(user.get().getFirst_name());
            authResponse.setLastName(user.get().getLast_name());
            authResponse.setEmail(user.get().getEmail());
            authResponse.setRol(user.get().getUser_rol());
            authResponse.setToken(accessToken);

            return authResponse;
        }

        return null;
    }


    public Authentication authenticate(String email,String password){
        UserDetails userDetails = this.loadUserByUsername(email);
        if (userDetails == null){
            throw new BadCredentialsException("Invalid email or password");
        }
        if (!encryptPassword.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }

        return new UsernamePasswordAuthenticationToken(email,userDetails.getPassword(),userDetails.getAuthorities());
    }
}
