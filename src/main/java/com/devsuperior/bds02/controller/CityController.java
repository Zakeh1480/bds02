package com.devsuperior.bds02.controller;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> findAllCity() {
        List<CityDTO> cityDTOList = cityService.findAll();
        return ResponseEntity.status(200).body(cityDTOList);
    }

    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
        cityDTO = cityService.createNewCity(cityDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cityDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(cityDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CityDTO> deleteCity(@PathVariable Long id) throws Exception {
        cityService.deleteCity(id);
        return ResponseEntity.status(204).build();
    }

}
