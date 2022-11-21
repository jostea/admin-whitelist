package com.jostea.zomboid.whitelist.repository;

import com.jostea.zomboid.whitelist.repository.model.Whitelist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WhitelistRepository {

    private final JpaWhitelistRepository jpaWhitelistRepository;

    public List<Whitelist> findByAccesslevelIn(final List<String> accessLevel) {
        return jpaWhitelistRepository.findByAccesslevelIn(accessLevel);
    }

    public void saveAll(final List<Whitelist> whitelists) {
        jpaWhitelistRepository.saveAll(whitelists);
    }

    interface JpaWhitelistRepository extends JpaRepository<Whitelist, Integer> {

        List<Whitelist> findByAccesslevelIn(List<String> accessLevel);
    }
}
