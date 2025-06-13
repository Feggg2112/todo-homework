package com.nttdata.ta.user;

import com.nttdata.ta.common.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}