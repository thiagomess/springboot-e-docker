package br.com.erudio.converts.custom;

import org.springframework.stereotype.Service;

import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO2;

@Service
public class PersonConverter {

	public PersonVO2 convertEntityToVO(Person person) {
		return new PersonVO2(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(),
				person.getGender(), person.getBirthday());
	}

	public Person convertVOToEntity(PersonVO2 person) {
		return new Person(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(),
				person.getGender(), person.getBirthday());
	}
}
