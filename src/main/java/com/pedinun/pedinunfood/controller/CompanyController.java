package com.pedinun.pedinunfood.controller;

import com.pedinun.pedinunfood.dto.request.CompanyRequest;
import com.pedinun.pedinunfood.dto.response.CompanyResponse;
import com.pedinun.pedinunfood.entity.company.Company;
import com.pedinun.pedinunfood.mapper.CompanyMapper;
import com.pedinun.pedinunfood.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> criarCompany(@RequestBody CompanyRequest companyRequest) {
        Company companyEntity = CompanyMapper.toEntity(companyRequest);
        Company companySalvo = companyService.saveCompany(companyEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(CompanyMapper.toResponse(companySalvo));
    }

    @GetMapping
    public ResponseEntity<CompanyResponse> findByNome( @RequestParam String nomeFantasia) {
        Company company = companyService.findByNome(nomeFantasia);
        return ResponseEntity.ok(CompanyMapper.toResponse(company));
    }


    @GetMapping("/restaurantes")
    public ResponseEntity<List<CompanyResponse>> findAll() {
        List<Company> companies = companyService.findAll();
        List<CompanyResponse> companyResponses = companies.stream()
                .map(CompanyMapper::toResponse)
                .toList();

        return ResponseEntity.ok(companyResponses);
    }
}