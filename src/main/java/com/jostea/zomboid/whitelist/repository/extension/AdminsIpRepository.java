package com.jostea.zomboid.whitelist.repository.extension;

import com.jostea.zomboid.whitelist.repository.domain.model.AdminsIP;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = AdminsIP.class, idClass = Integer.class)
public interface AdminsIpRepository {

    List<AdminsIP> findAll();
}
