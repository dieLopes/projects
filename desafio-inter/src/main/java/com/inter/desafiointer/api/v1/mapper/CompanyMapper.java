package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.company.CompanyCreateDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyCreateListDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyResponseDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyUpdateDTO;
import com.inter.desafiointer.domain.Company;
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

    public static List<Company> dtosToEntities (CompanyCreateListDTO companyCreateListDTO) {
        return companyCreateListDTO
                .getCompanies()
                .stream()
                .map(CompanyMapper::createDtoToEntity)
                .collect(Collectors.toList());
    }

    public static List<CompanyResponseDTO> entitiesToDTOs (List<Company> companies) {
        return companies
            .stream()
            .map(CompanyMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
