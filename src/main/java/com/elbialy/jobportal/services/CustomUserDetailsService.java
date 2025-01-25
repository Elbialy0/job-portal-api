package com.elbialy.jobportal.services;

import com.elbialy.jobportal.model.User;
import com.elbialy.jobportal.repository.UsersRepository;
import com.elbialy.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    private final UsersRepository usersRepository;
@Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user= usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(" couldn't found user"));
        return new CustomUserDetails(user);
    }
}
