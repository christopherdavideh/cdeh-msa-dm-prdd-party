package com.banking.cdeh_msa_dm_prdd_party.service.impl;

import com.banking.cdeh_msa_dm_prdd_party.domain.entity.Party;
import com.banking.cdeh_msa_dm_prdd_party.exception.BadRequestException;
import com.banking.cdeh_msa_dm_prdd_party.exception.ResourceNotFoundException;
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
                .onErrorResume(e -> Mono.error(new BadRequestException(LogMessages.ERROR_CREATING_PARTY + e.getMessage())));
    }

    @Override
    public Mono<Party> getPartyById(UUID partyId) {
        return partyRepository.findById(partyId)
                .doFirst(() -> log.info(LogMessages.GET_PARTY_BY_ID_REQUEST, partyId))
                .doOnSuccess(party -> log.info(LogMessages.GET_PARTY_BY_ID_SUCCESS, party))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(LogMessages.PARTY_NOT_FOUND, partyId);
                    return Mono.error(new ResourceNotFoundException("Party", "id", partyId));
                }))
                .doOnError(error -> log.error(LogMessages.GET_PARTY_RETRIEVE_ERROR, error.getMessage()))
                .onErrorResume(e -> e instanceof ResourceNotFoundException ?
                        Mono.error(e) :
                        Mono.error(new BadRequestException("Error retrieving Party: " + e.getMessage())));
    }

    @Override
    public Mono<Party> updateParty(UUID partyId, Party party) {
        return partyRepository.findById(partyId)
                .doFirst(() -> log.info(LogMessages.UPDATE_PARTY_REQUEST, partyId, party.getName(), party.getGender(), party.getAge(), party.getAddress(), party.getPhone()))
                .doOnNext(existingParty -> log.info(LogMessages.PARTY_FOUND_FOR_UPDATE, existingParty))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(LogMessages.PARTY_UPDATE_NOT_FOUND, partyId);
                    return Mono.error(new ResourceNotFoundException("Party", "id", partyId));
                }))
                .flatMap(existing -> {
                    party.setPartyId(partyId);
                    if (!existing.getIdentification().equals(party.getIdentification())) {
                        return partyRepository.findAll()
                                .filter(p -> p.getIdentification().equals(party.getIdentification()) && !p.getPartyId().equals(partyId))
                                .hasElements()
                                .doOnNext(exists -> log.info(LogMessages.PARTY_ID_DUPLICATE_CHECK, exists))
                                .flatMap(exists -> {
                                    if (exists) {
                                        log.error(LogMessages.PARTY_ID_ALREADY_EXISTS, party.getIdentification());
                                        return Mono.error(new BadRequestException("Ya existe otro registro con la identificación: " + party.getIdentification()));
                                    } else {
                                        return partyRepository.save(party)
                                                .doOnSuccess(savedParty -> log.info(LogMessages.PARTY_UPDATED_SUCCESS, savedParty))
                                                .doOnError(error -> log.error(LogMessages.UPDATE_PARTY_ERROR, error.getMessage()));
                                    }
                                });
                    } else {
                        return partyRepository.save(party)
                                .doOnSuccess(savedParty -> log.info(LogMessages.PARTY_UPDATED_SUCCESS, savedParty))
                                .doOnError(error -> log.error(LogMessages.UPDATE_PARTY_ERROR, error.getMessage()));
                    }
                })
                .doOnError(e -> log.error(LogMessages.UPDATE_PARTY_ERROR, e.getMessage()))
                .onErrorResume(e -> e instanceof ResourceNotFoundException ?
                        Mono.error(e) :
                        Mono.error(new BadRequestException(LogMessages.ERROR_UPDATING_PARTY + e.getMessage())));
    }

}