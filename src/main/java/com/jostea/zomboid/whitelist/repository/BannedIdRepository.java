package com.jostea.zomboid.whitelist.repository;

import com.jostea.zomboid.whitelist.repository.model.BannedId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BannedIdRepository {

    private final JpaBannedIdRepository jpaBannedIdRepository;

    public void saveAll(List<BannedId> bannedIdList) {
        jpaBannedIdRepository.saveAll(bannedIdList);
    }

    interface JpaBannedIdRepository extends JpaRepository<BannedId, String> {

    }
}
