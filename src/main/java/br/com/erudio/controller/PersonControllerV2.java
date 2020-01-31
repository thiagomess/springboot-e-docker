package br.com.erudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.PersonVO2;
import br.com.erudio.service.PersonService;

@RestController
@RequestMapping(value = "/api/v2/person")
public class PersonControllerV2 {

	@Autowired
	private PersonService service;

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 findById(@PathVariable("id") Long id) {
		return service.findByIdV2(id);
	}

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<PersonVO2> findAll() {
		return service.findAllV2();
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
				consumes = {"application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 create(@RequestBody PersonVO2 person) {
		return service.createPersonV2(person);
	}

	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
				consumes = {"application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 update(@RequestBody PersonVO2 person) {
		return service.updatePersonV2(person);
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.deletePerson(id);
		return ResponseEntity.noContent().build();
	}

}
