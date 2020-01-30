package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.PersonVO2;
import br.com.erudio.service.PersonService;

@RestController
@RequestMapping(value = "/person/v2")
public class PersonControllerV2 {

	@Autowired
	private PersonService service;

	@PostMapping
	public PersonVO2 create(@RequestBody PersonVO2 person) {
		return service.createPersonV2(person);
	}

}
