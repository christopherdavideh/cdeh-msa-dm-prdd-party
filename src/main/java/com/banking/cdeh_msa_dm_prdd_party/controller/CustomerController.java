package com.banking.cdeh_msa_dm_prdd_party.controller;

import com.banking.cdeh_msa_dm_prdd_party.service.CustomerService;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerRequestDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.CustomerResponseDto;
import com.banking.cdeh_msa_dm_prdd_party.service.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public Flux<CustomerResponseDto> getAllClientes() {
            return customerService.getAllCustomerDTOs();
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerResponseDto>> createCliente(@RequestBody CustomerRequestDto input) {
        return customerService.createCustomerWithParty(input)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @GetMapping("/{clienteId}")
    public Mono<ResponseEntity<CustomerResponseDto>> getClienteById(@PathVariable UUID clienteId) {
        return customerService.getCustomerDTOById(clienteId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{clienteId}")
    public Mono<ResponseEntity<CustomerResponseDto>> updateCliente(@PathVariable UUID clienteId, @RequestBody CustomerRequestDto input) {
        return customerService.updateCustomerWithParty(clienteId, input)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{clienteId}")
    public Mono<ResponseEntity<Void>> deleteCliente(@PathVariable UUID clienteId) {
        return customerService.deleteCustomer(clienteId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
