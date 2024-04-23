package com.softdesign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpf-validador",url = "${url.cpf-validador}", dismiss404 = true)
public interface ValidadorDeCpfClient {
    @GetMapping("/user/validate/{cpf}")
    ResponseEntity<ValidationDTO> validate(@PathVariable String cpf);
}
