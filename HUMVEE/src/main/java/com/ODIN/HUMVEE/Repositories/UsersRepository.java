package com.ODIN.HUMMVEE.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ODIN.HUMMVEE.Entities.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users>findByUsername(String username);

}
