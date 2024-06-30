package booksville.infrastructure.security;

import booksville.entities.model.UserEntity;
import booksville.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userEntityRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                rolesToAuthorities(userEntity)
        );
    }

    public Collection<GrantedAuthority> rolesToAuthorities(UserEntity userEntity){
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(userEntity.getRoles())));
    }
}
