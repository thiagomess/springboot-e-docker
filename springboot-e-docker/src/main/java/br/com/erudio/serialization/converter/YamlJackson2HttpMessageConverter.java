package br.com.erudio.serialization.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

//Cria um converter de Media Type para YAML

public final class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter{

	public YamlJackson2HttpMessageConverter() {
		super(new YAMLMapper(), MediaType.parseMediaType("application/x-yaml"));
	}

}