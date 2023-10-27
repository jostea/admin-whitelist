package com.jostea.zomboid.whitelist.repository.extension;

import com.jostea.zomboid.whitelist.repository.domain.model.AdminsIP;
import com.jostea.zomboid.whitelist.repository.domain.model.AllowedPlayer;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = AdminsIP.class, idClass = Integer.class)
public interface AdminsIpRepository {

    List<AdminsIP> findAll();

    Optional<AdminsIP> findByIp(String ip);

    boolean existsByIp(String ip);

    void save(AdminsIP ip);

    void deleteByIp(String ip);
}
