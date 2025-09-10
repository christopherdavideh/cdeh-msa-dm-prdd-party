package com.banking.cdeh_msa_dm_prdd_party.controller;

import com.banking.cdeh_msa_dm_prdd_party.service.CustomerService;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerRequestDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CustomerResponseDto>>> getAllClientes() {
        Flux<CustomerResponseDto> activeClients = customerService.getAllCustomerDTOs();
        return activeClients.hasElements()
            .flatMap(hasAny -> hasAny
                ? Mono.just(ResponseEntity.ok(activeClients))
                : Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Flux.empty())));
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerResponseDto>> createCliente(@RequestBody CustomerRequestDto input) {
        return customerService.createCustomerWithParty(input)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @GetMapping("/{customerId}")
    public Mono<ResponseEntity<CustomerResponseDto>> getClienteById(@PathVariable UUID customerId) {
        return customerService.getCustomerDTOById(customerId)
            .map(ResponseEntity::ok);
    }

    @PutMapping("/{customerId}")
    public Mono<ResponseEntity<CustomerResponseDto>> updateCliente(@PathVariable UUID customerId, @RequestBody CustomerRequestDto input) {
        return customerService.updateCustomerWithParty(customerId, input)
            .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{customerId}")
    public Mono<ResponseEntity<Void>> deleteCliente(@PathVariable UUID customerId) {
        return customerService.deleteCustomer(customerId)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
