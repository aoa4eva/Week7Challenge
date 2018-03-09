package me.afua.week7challenge;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser,Long>{
    AppUser findByUsername(String username);
}
