package com.jostea.zomboid.whitelist.domain.repository.game;

import com.jostea.zomboid.whitelist.domain.model.Whitelist;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Whitelist.class, idClass = Integer.class)
public interface WhitelistRepository {

    List<Whitelist> findByAccessLevelIn(List<String> accessLevel);
}
