package br.com.erudio.converts;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

/*
 * Utilizado a lib DOZER para fazer os converts
 *Documentação: https://github.com/DozerMapper/dozer
*/
public class DozerConverter {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	//Converte objeto
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}

	//Converte Lista
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<>();
		for (Object o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}

}
