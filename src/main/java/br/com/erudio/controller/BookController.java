package br.com.erudio.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import br.com.erudio.data.vo.BookVO;
import br.com.erudio.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Endpoint Book", description = "Description for Book", tags = {"bookEndpoint"})
@RestController
@RequestMapping(value = "/api/v1/book")
public class BookController {

	@Autowired
	private BookService service;

	@ApiOperation(value = "Find all people", produces = "application/json, application/xml, application/x-yaml" )
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<BookVO> findAll() {
		List<BookVO> listBook = service.findAll();
		listBook.stream()
				.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel(),
						linkTo(methodOn(BookController.class).update(p)).withRel("update"),
						linkTo(methodOn(BookController.class).delete(p.getId())).withRel("delete")));
		return listBook;
	}

	@ApiOperation(value = "Find for id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BookVO findById(@PathVariable Long id) {
		BookVO bookVO = service.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return bookVO;
	}

	@ApiOperation(value = "Include book")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO create(@RequestBody BookVO bookVO) {
		BookVO book = service.create(bookVO);
		book.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).update(book)).withRel("update"),
				linkTo(methodOn(BookController.class).delete(book.getId())).withRel("delete"));

		return book;
	}

	@ApiOperation(value = "Update book")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO update(@RequestBody BookVO bookVO) {
		BookVO book = service.update(bookVO);
		book.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).update(book)).withRel("update"),
				linkTo(methodOn(BookController.class).delete(book.getId())).withRel("delete"));

		return book;
	}

	@ApiOperation(value = "Delete book")
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
