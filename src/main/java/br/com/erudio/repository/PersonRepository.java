package br.com.erudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.erudio.data.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	@Modifying //Informa que a Query é de alteração
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
	void disablePersons(@Param("id") Long id);
}
