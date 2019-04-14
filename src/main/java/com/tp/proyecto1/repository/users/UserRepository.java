package com.tp.proyecto1.repository.users;

import com.tp.proyecto1.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUser(String userName);

}
