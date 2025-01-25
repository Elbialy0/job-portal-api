package com.elbialy.jobportal.services;

import com.elbialy.jobportal.dto.UserRegistrationDTO;
import com.elbialy.jobportal.exceptions.EmailAlreadyExistsException;
import com.elbialy.jobportal.model.JobSeekerProfile;
import com.elbialy.jobportal.model.RecruiterProfile;
import com.elbialy.jobportal.model.User;
import com.elbialy.jobportal.model.UserType;
import com.elbialy.jobportal.repository.JobSeekerProfileRepository;
import com.elbialy.jobportal.repository.RecruiterProfileRepository;
import com.elbialy.jobportal.repository.UsersRepository;
import com.elbialy.jobportal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {
    private UsersRepository usersRepository;

    private UsersTypeRepository usersTypeRepository;

    private RecruiterProfileRepository recruiterProfileRepository;

    private JobSeekerProfileRepository jobSeekerProfileRepository;

    private final PasswordEncoder passwordEncoder;

@Autowired
    public UserService(UsersRepository usersRepository, UsersTypeRepository usersTypeRepository, RecruiterProfileRepository recruiterProfileRepository, JobSeekerProfileRepository jobSeekerProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.usersTypeRepository = usersTypeRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }





    public User register(UserRegistrationDTO userRegistrationDTO){
        // check if the user  exists or not
        if(usersRepository.existsByEmail(userRegistrationDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // create a new user
        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        UserType userType = usersTypeRepository.findByUserTypeName(userRegistrationDTO.getRole());
        user.setUsersType(userType);
        user.setRegistertionDate(LocalDate.now());
        if(userType.getUserTypeId()==2){
            jobSeekerProfileRepository.save(new JobSeekerProfile(user));
        }else {
            recruiterProfileRepository.save((new RecruiterProfile(user)));
        }
        usersRepository.save(user);
        return user;

    }
    public Object getCurrentUserProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
           User user = usersRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("not exists"));
            int userId =user.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                return  recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());


            }
            else  {
                return  jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
            }
        }
 return null;


    }
}
