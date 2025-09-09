package com.banking.cdeh_msa_dm_prdd_party.repository;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface PartyRepository extends ReactiveCrudRepository<Party, UUID> {
}
