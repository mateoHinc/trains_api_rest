package com.company.trains_api_rest.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.company.trains_api_rest.repository.UserRepository;
import com.company.trains_api_rest.model.User;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Buscar un usuario por email y lo adapta al formato que spring security necesita.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo electronico: "+email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getActive(), true, true, true, List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name())));
    }

}
