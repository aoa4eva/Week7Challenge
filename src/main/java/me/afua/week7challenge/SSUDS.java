package me.afua.week7challenge;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUDS implements UserDetailsService {
    AppUserRepository userRepo;

    public SSUDS(AppUserRepository userRepository) {
        userRepo = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(s);
        if(user==null)
            throw new UsernameNotFoundException("Invalid username or password");

        return new User(user.getUsername(),user.getPassword(),getAuthorities(user));

    }

    private Set<GrantedAuthority> getAuthorities(AppUser user) {
        HashSet <GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(AppRole eachRole:user.getRoles())
        {
            SimpleGrantedAuthority a = new SimpleGrantedAuthority(eachRole.getRoleName());
            grantedAuthorities.add(a);
        }
        return grantedAuthorities;
    }
}
