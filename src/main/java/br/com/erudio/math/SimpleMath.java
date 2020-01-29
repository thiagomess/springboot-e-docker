package br.com.erudio.math;

import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.UnsupportedMathOperationException;

@Service
public class SimpleMath {

	public Double soma(Double numberOne, Double numberTwo) {
		return numberOne + numberTwo;
	}

	public Double subtracao(Double numberOne, Double numberTwo) {
		return numberOne - numberTwo;
	}

	public Double multiplicacao(Double numberOne, Double numberTwo) {
		return numberOne * numberTwo;
	}

	public Double divisao(Double numberOne, Double numberTwo) {
		if (numberTwo == 0) {
			throw new UnsupportedMathOperationException("Não é possível dividir número por zero");
		}
		return numberOne / numberTwo;
	}

	public Double media(Double numberOne, Double numberTwo) {
		return (numberOne + numberTwo) / 2;
	}

	public Double raiz(Double numberOne) {
		return Math.sqrt(numberOne);
	}

}
