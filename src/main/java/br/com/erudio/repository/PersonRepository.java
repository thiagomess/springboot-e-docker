package br.com.erudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.data.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
