package com.app.task.controller;

import com.app.task.dto.PersonDTO;
import com.app.task.exception.ModelNotFoundException;
import com.app.task.model.Person;
import com.app.task.service.IPersonService;
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
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private IPersonService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        List<PersonDTO> list = service.findAll().stream().map(p -> mapper.map(p, PersonDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Integer id) {
        PersonDTO dtoResponse;
        Person obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, PersonDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PersonDTO dto) {
        Person p = service.save(mapper.map(dto, Person.class));
        //localhost:8080/persons/3
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdPerson()).toUri();
        return ResponseEntity.created(url).build();
    }

    @PutMapping
    public ResponseEntity<Person> update(@Valid @RequestBody PersonDTO dto) {
        Person obj = service.findById(dto.getIdPerson());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdPerson());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Person.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Person obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/hateoas/{id}")
    public EntityModel<PersonDTO> findByIdHateoas(@PathVariable("id") Integer id){

        PersonDTO dtoResponse;

        Person obj = service.findById(id);
        if(obj==null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, PersonDTO.class);
        }

        EntityModel<PersonDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder linkNew = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder linkNew2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());
        resource.add(linkNew.withRel("person-info!"));
        resource.add(linkNew2.withRel("person-full!"));

        return resource;
    }
}
