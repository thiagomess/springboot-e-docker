package br.com.erudio.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.PersonVO2;
import br.com.erudio.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin Permite cross somente nesta classe
@Api(value = "Endpoint person v2", description = "Endpoint person v1", tags = {"personEndpointV2"})
@RestController
@RequestMapping(value = "/api/v2/person")
public class PersonControllerV2 {

	@Autowired
	private PersonService service;
	
	@Autowired
	private PagedResourcesAssembler<PersonVO2> assembler;

	//@CrossOrigin Permite cross somente neste metodo
	@ApiOperation(value = "Find for id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 findById(@PathVariable("id") Long id) {
		PersonVO2 personVO2 = service.findByIdV2(id);

		// adicionando HATEOAS - tem que adicionar os imports static
		personVO2.add(linkTo(methodOn(PersonControllerV2.class).findById(id)).withSelfRel());
		return personVO2;
	}
//	@CrossOrigin(origins = {"http://localhost8080","http//google.com"})// Permite cross somente neste metodo para esses sites
	@ApiOperation(value = "Find all person")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll( 
				@RequestParam(value = "page", defaultValue = "0") int page, 
				@RequestParam(value = "limit", defaultValue = "12") int limit,
				@RequestParam(value = "direction", defaultValue = "asc") String direction){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		PageRequest pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		 Page<PersonVO2> listPerson = service.findAllV2(pageable);
		 listPerson.stream().forEach(p -> p.add(
					linkTo(methodOn(PersonControllerV2.class).findById(p.getId())).withSelfRel(),
					 linkTo(methodOn(PersonControllerV2.class).update(p)).withRel("update"),
					 linkTo(methodOn(PersonControllerV2.class).delete(p.getId())).withRel("delete")
					));
		 
		 return ResponseEntity.ok(assembler.toModel(listPerson)); //adiciona  HATEOAS no retorno da lista
	}
	
	
	@ApiOperation(value = "Find all person by name for parameter")
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAllPersonByName( 
				@PathVariable("firstName") String firstName, //RECEBE PathVariable PARA DAR LIKE
				@RequestParam(value = "page", defaultValue = "0") int page, 
				@RequestParam(value = "limit", defaultValue = "12") int limit,
				@RequestParam(value = "direction", defaultValue = "asc") String direction){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		PageRequest pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		 Page<PersonVO2> listPerson = service.findPersonByName(firstName, pageable);
		 listPerson.stream().forEach(p -> p.add(
					linkTo(methodOn(PersonControllerV2.class).findById(p.getId())).withSelfRel(),
					 linkTo(methodOn(PersonControllerV2.class).update(p)).withRel("update"),
					 linkTo(methodOn(PersonControllerV2.class).delete(p.getId())).withRel("delete")
					));
		 
		 return ResponseEntity.ok(assembler.toModel(listPerson));//adiciona  HATEOAS no retorno da lista
	}

	@ApiOperation(value = "Include person")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
				consumes = {"application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 create(@RequestBody PersonVO2 person) {
		PersonVO2 personVO2 = service.createPersonV2(person);
		personVO2.add
		(linkTo(methodOn(PersonControllerV2.class).findById(personVO2.getId())).withSelfRel(),
		 linkTo(methodOn(PersonControllerV2.class).update(personVO2)).withRel("update"),
		 linkTo(methodOn(PersonControllerV2.class).delete(personVO2.getId())).withRel("delete"));
		return personVO2;
	}

	@ApiOperation(value = "Update person")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
				consumes = {"application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 update(@RequestBody PersonVO2 person) {
		PersonVO2 personVO2 = service.updatePersonV2(person);
		personVO2.add
		(linkTo(methodOn(PersonControllerV2.class).findById(personVO2.getId())).withSelfRel(),
		 linkTo(methodOn(PersonControllerV2.class).update(personVO2)).withRel("update"),
		 linkTo(methodOn(PersonControllerV2.class).delete(personVO2.getId())).withRel("delete"));
		return personVO2;
	}

	@ApiOperation(value = "Delete person")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.deletePerson(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Disable person")
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO2 disablePerson(@PathVariable("id") Long id) {
		PersonVO2 personVO2 = service.disablePersonV2(id);
		personVO2.add
		(linkTo(methodOn(PersonControllerV2.class).findById(personVO2.getId())).withSelfRel(),
		 linkTo(methodOn(PersonControllerV2.class).update(personVO2)).withRel("update"),
		 linkTo(methodOn(PersonControllerV2.class).delete(personVO2.getId())).withRel("delete"));
		return personVO2;
	}

}
