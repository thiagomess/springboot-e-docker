package br.com.erudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.DataIntegrityException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	public Person createPerson(Person person) {
		return repository.save(person);
	}

	public Person updatePerson(Person person) {
		Person p = findById(person.getId());
		updateData(person, p);
		return repository.save(person);
	}

	public void deletePerson(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir devido ter dados relacionados.");
		}
	}

	public Person findById(Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado com esse id"));
	}

	public List<Person> findAll() {
		return repository.findAll();
	}

	private void updateData(Person person, Person p) {
		p.setFirstName(person.getFirstName());
		p.setLastName(person.getLastName());
		p.setAndress(person.getAndress());
		p.setGender(person.getGender());
	}
}
