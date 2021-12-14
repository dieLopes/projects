package com.diego.homebroker.api.v1.mapper;

import com.diego.homebroker.api.v1.dto.company.CompanyCreateDTO;
import com.diego.homebroker.api.v1.dto.company.CompanyResponseDTO;
import com.diego.homebroker.api.v1.dto.company.CompanyUpdateDTO;
import com.diego.homebroker.domain.Company;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Company createDtoToEntity(CompanyCreateDTO companyCreateDTO) {
        return mapper.map(companyCreateDTO, Company.class);
    }

    public static Company updateDtoToEntity (CompanyUpdateDTO companyUpdateDTO) {
        return mapper.map(companyUpdateDTO, Company.class);
    }

    public static CompanyResponseDTO entityToDTO(Company company) {
        return mapper.map(company, CompanyResponseDTO.class);
    }

    public static List<CompanyResponseDTO> entitiesToDTOs (List<Company> companies) {
        return companies
            .stream()
            .map(CompanyMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
