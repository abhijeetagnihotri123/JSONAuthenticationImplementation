package com.ODIN.HUMMVEE.Service;

import com.ODIN.HUMMVEE.Constants.Roles;
import com.ODIN.HUMMVEE.Repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ODIN.HUMMVEE.Entities.Users;

@Service
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UsersRepository usersRepository , PasswordEncoder passwordEncoder){

        Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);

        return args -> {
            if(usersRepository.findByUsername("admin").isEmpty()){
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                //admin.setRole("ROLE_ADMIN");

                admin.setRole(Roles.ADMIN);

                usersRepository.save(admin);
                log.info("Default admin user created with details as follows : {}" , admin.toString());

            }
            if(usersRepository.findByUsername("user").isEmpty()){
                Users user = new Users();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user1234"));

                user.setRole(Roles.USER);

                usersRepository.save(user);

                log.info("Default user created with username as {}" , user.getUsername());

            }
            else{
                log.info("The user with username {} , is already present :" , "admin");
            }
        };

    }

}
