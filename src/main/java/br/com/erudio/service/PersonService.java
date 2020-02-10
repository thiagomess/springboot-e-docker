package br.com.erudio.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Fidelity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.erudio.converts.DozerConverter;
import br.com.erudio.converts.custom.PersonConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo.PersonVO2;
import br.com.erudio.exceptions.DataIntegrityException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	@Autowired
	private PersonConverter converter;

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

	// API v2

	public PersonVO2 createPersonV2(PersonVO2 person) {
		var entity = converter.convertVOToEntity(person);
		Person vo = repository.save(entity);
		return converter.convertEntityToVO(vo);
	}

	public PersonVO2 updatePersonV2(PersonVO2 person) {
		PersonVO2 p = findByIdV2(person.getId());
		updateDataV2(person, p);

		var entity = converter.convertVOToEntity(person);
		Person vo = repository.save(entity);
		return converter.convertEntityToVO(vo);

	}

	public PersonVO2 findByIdV2(Long id) {

		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado com esse id"));
		return converter.convertEntityToVO(entity);
	}

	public List<PersonVO2> findAllV2() {
		return repository.findAll().stream().map(obj -> converter.convertEntityToVO(obj)).collect(Collectors.toList());
	}
	
	@Transactional //Devido ser uma query de modificação que não é padrao, tem que colocar Transactional
	public PersonVO2 disablePersonV2(Long id) {
		repository.disablePersons(id);
		return	findByIdV2(id);
	}

	private void updateDataV2(PersonVO2 person, PersonVO2 p) {
		p.setFirstName(person.getFirstName());
		p.setLastName(person.getLastName());
		p.setAddress(person.getAddress());
		p.setGender(person.getGender());
		p.setBirthday(person.getBirthday());
	}

}
