package com.devsuperior.bds02.service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.service.exception.DatabaseException;
import com.devsuperior.bds02.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityDTO> findAll() {
        List<City> cityList = cityRepository.findAll(Sort.by("name"));
        return cityList.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO createNewCity(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        cityRepository.save(city);
        return new CityDTO(city);
    }

    @Transactional
    public void deleteCity(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException erro) {
            throw new ResourceNotFoundException("ID não existe");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("ID dependente de outro");
        }
    }
}