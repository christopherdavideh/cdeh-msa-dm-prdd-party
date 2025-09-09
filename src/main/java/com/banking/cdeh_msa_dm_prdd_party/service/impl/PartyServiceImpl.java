package com.banking.cdeh_msa_dm_prdd_party.service.impl;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.repository.PartyRepository;
import com.banking.cdeh_msa_dm_prdd_party.service.PartyService;
import com.banking.cdeh_msa_dm_prdd_party.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {
    private static final Logger log = LoggerFactory.getLogger(PartyServiceImpl.class);
    private final PartyRepository partyRepository;

    @Override
    public Mono<Party> createParty(Party party) {
        return partyRepository.save(party)
                .doFirst(() -> log.info(LogMessages.CREATE_PARTY_REQUEST, party))
                .doOnSuccess(savedParty -> log.info(LogMessages.CREATE_PARTY_SUCCESS, savedParty))
                .doOnError(error -> log.error(LogMessages.CREATE_PARTY_ERROR, error.getMessage()))
                .onErrorResume(e -> Mono.error(new RuntimeException(LogMessages.ERROR_CREATING_PARTY + e.getMessage())));
    }

    @Override
    public Mono<Party> getPartyById(UUID partyId) {
        return partyRepository.findById(partyId)
                .doFirst(() -> log.info(LogMessages.GET_PARTY_BY_ID_REQUEST, partyId))
                .doOnSuccess(party -> log.info(LogMessages.GET_PARTY_BY_ID_SUCCESS, party))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(LogMessages.PARTY_NOT_FOUND, partyId);
                    return Mono.error(new RuntimeException("Party not found with id: " + partyId));
                }))
                .doOnError(error -> log.error(LogMessages.GET_PARTY_RETRIEVE_ERROR, error.getMessage()))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error retrieving Party: " + e.getMessage())));
    }

    @Override
    public Mono<Party> updateParty(UUID partyId, Party party) {
        return partyRepository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with id: " + partyId)))
                .flatMap(existing -> {
                    party.setPartyId(partyId);
                    return partyRepository.save(party);
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating Party: " + e.getMessage())));
    }

}