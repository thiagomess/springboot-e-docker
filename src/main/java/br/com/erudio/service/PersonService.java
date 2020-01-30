package br.com.erudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.erudio.converts.DozerConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.exceptions.DataIntegrityException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	public PersonVO createPerson(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		Person vo = repository.save(entity);
		return DozerConverter.parseObject(vo, PersonVO.class);
	}

	public PersonVO updatePerson(PersonVO person) {
		PersonVO p = findById(person.getId());
		updateData(person, p);
		
		var entity = DozerConverter.parseObject(person, Person.class);
		Person vo = repository.save(entity);
		return DozerConverter.parseObject(vo, PersonVO.class);
		
	}

	public void deletePerson(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir devido ter dados relacionados.");
		}
	}

	public PersonVO findById(Long id) {

		 Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado com esse id"));
		 return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}

	private void updateData(PersonVO person, PersonVO p) {
		p.setFirstName(person.getFirstName());
		p.setLastName(person.getLastName());
		p.setAddress(person.getAddress());
		p.setGender(person.getGender());
	}
}
