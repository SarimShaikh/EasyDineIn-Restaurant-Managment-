package com.easydinein.easy_dinein.repositories;

import com.easydinein.easy_dinein.domain.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
