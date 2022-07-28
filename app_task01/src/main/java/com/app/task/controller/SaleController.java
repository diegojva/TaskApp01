package com.app.task.controller;

import com.app.task.dto.SaleDTO;
import com.app.task.exception.ModelNotFoundException;
import com.app.task.model.Sale;
import com.app.task.service.ISaleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private ISaleService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAll() {
        List<SaleDTO> list = service.findAll().stream().map(p -> mapper.map(p, SaleDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable("id") Integer id) {
        SaleDTO dtoResponse;
        Sale obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, SaleDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SaleDTO dto) {
        Sale sale = mapper.map(dto,Sale.class);
        //List<Exam> exams = mapper.map(dto.getLstExam(),new TypeToken<List<Exam>>(){}.getType());

        Sale obj = service.saveSale(sale);
        //localhost:8080/sales/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sale.getIdSale()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Sale> update(@Valid @RequestBody SaleDTO dto) {
        Sale obj = service.findById(dto.getIdSale());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdSale());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Sale.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Sale obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SaleDTO> findByIdHateoas(@PathVariable("id") Integer id){

        SaleDTO dtoResponse;

        Sale obj = service.findById(id);
        if(obj==null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, SaleDTO.class);
        }

        EntityModel<SaleDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder linkNew = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder linkNew2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());
        resource.add(linkNew.withRel("sale-info!"));
        resource.add(linkNew2.withRel("sale-full!"));

        return resource;
    }
}
