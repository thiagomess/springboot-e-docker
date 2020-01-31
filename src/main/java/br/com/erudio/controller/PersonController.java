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

import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo.PersonVO2;
import br.com.erudio.service.PersonService;

@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonController {

	@Autowired
	private PersonService service;

	@GetMapping("/{id}")
	public PersonVO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}

	@GetMapping
	public List<PersonVO> findAll() {
		return service.findAll();
	}

	@PostMapping
	public PersonVO create(@RequestBody PersonVO person) {
		return service.createPerson(person);
	}
	
	@PutMapping
	public PersonVO update(@RequestBody PersonVO person) {
		return service.updatePerson(person);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.deletePerson(id);
		return ResponseEntity.noContent().build();
	}
}
