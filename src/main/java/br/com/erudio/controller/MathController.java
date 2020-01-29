package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.converts.NumberConvert;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.math.SimpleMath;

@RestController
public class MathController {
	
	@Autowired
	private SimpleMath math;

	@GetMapping("/sum/{numberOne}/{numberTwo}")
	public Double soma(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception {

		if (!NumberConvert.isNumeric(numberOne) || !NumberConvert.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Defina um valor numérico");
		}

		return math.soma(NumberConvert.convertDouble(numberOne), NumberConvert.convertDouble(numberTwo));
	}
	
	
	@GetMapping("/sub/{numberOne}/{numberTwo}")
	public Double subtracao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		if (!NumberConvert.isNumeric(numberOne) || !NumberConvert.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Defina um valor numérico");
		}
		return  math.subtracao(NumberConvert.convertDouble(numberOne), NumberConvert.convertDouble(numberTwo));
	}
	
	@GetMapping("/mult/{numberOne}/{numberTwo}")
	public Double multiplicacao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		if (!NumberConvert.isNumeric(numberOne) || !NumberConvert.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Defina um valor numérico");
		}
		return  math.multiplicacao(NumberConvert.convertDouble(numberOne), NumberConvert.convertDouble(numberTwo));
	}
	
	@GetMapping("/div/{numberOne}/{numberTwo}")
	public Double divisao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		if (!NumberConvert.isNumeric(numberOne) || !NumberConvert.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Defina um valor numérico");
		}
		return math.divisao(NumberConvert.convertDouble(numberOne), NumberConvert.convertDouble(numberTwo));
	}
	
	@GetMapping("/media/{numberOne}/{numberTwo}")
	public Double media(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
		if (!NumberConvert.isNumeric(numberOne) || !NumberConvert.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Defina um valor numérico");
		}
		return  math.media(NumberConvert.convertDouble(numberOne), NumberConvert.convertDouble(numberTwo));
	}
	
	@GetMapping("/raiz/{number}")
	public Double raiz(@PathVariable("number") String number) {
		if (!NumberConvert.isNumeric(number)) {
			throw new UnsupportedMathOperationException("Defina um valor numérico");
		}
		return math.raiz(NumberConvert.convertDouble(number));
	}
	
	
	
}
