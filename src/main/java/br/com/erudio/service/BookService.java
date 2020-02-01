package br.com.erudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.erudio.converts.DozerConverter;
import br.com.erudio.data.model.Book;
import br.com.erudio.data.vo.BookVO;
import br.com.erudio.exceptions.DataIntegrityException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	public BookVO create(BookVO bookVO) {
		var entity = DozerConverter.parseObject(bookVO, Book.class);
		Book data = repository.save(entity);
		return DozerConverter.parseObject(data, BookVO.class);
	}

	public BookVO findById(Long id) {
		Book data = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado com esse id"));

		return DozerConverter.parseObject(data, BookVO.class);
	}

	public List<BookVO> findAll() {
		List<Book> listBook = repository.findAll();
		return DozerConverter.parseListObjects(listBook, BookVO.class);
	}

	public BookVO update(BookVO bookVO) {
		BookVO book = findById(bookVO.getId());
		updateBook(book, bookVO);
		Book entity = DozerConverter.parseObject(book, Book.class);
		Book data = repository.save(entity);
		return DozerConverter.parseObject(data, BookVO.class);
	}

	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir devido ter dados relacionados.");
		}

	}

	private void updateBook(BookVO book, BookVO bookVO) {
		book.setAuthor(bookVO.getAuthor());
		book.setLaunchDate(bookVO.getLaunchDate());
		book.setPrice(bookVO.getPrice());
		book.setTitle(bookVO.getTitle());

	}

}
