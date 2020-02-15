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
import br.com.erudio.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Endpoint person v1", description = "Endpoint person v1", tags = {"personEndpoint"})
@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonController {

	@Autowired
	private PersonService service;

	@ApiOperation(value = "Find for id")
	@GetMapping("/{id}")
	public PersonVO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
	@ApiOperation(value = "Find all")
	@GetMapping
	public List<PersonVO> findAll() {
		return service.findAll();
	}

	@ApiOperation(value = "Create person")
	@PostMapping
	public PersonVO create(@RequestBody PersonVO person) {
		return service.createPerson(person);
	}
	
	@ApiOperation(value = "Update person")
	@PutMapping
	public PersonVO update(@RequestBody PersonVO person) {
		return service.updatePerson(person);
	}

	@ApiOperation(value = "Delete person")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.deletePerson(id);
		return ResponseEntity.noContent().build();
	}
}
