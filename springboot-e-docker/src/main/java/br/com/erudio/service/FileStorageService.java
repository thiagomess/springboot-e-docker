package br.com.erudio.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.exceptions.FileStorageException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Não foi possível criar o diretorio de Upload", e);
		}
	}

	public String storageFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("O arquivo contem invalido sequencia inválida na extensão " + fileName);
			}

			Path location = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Não foi possivel armazenar o arquivo " + fileName + " tente novamente", e);
		}
	}

	public Resource loadFileAsResource(String fileName) {

		try {
			Path location = this.fileStorageLocation.resolve(fileName).normalize();
			UrlResource resource = new UrlResource(location.toUri());
			if (resource.exists()) {
				return resource;
			}
			throw new FileStorageException("Arquivo " + fileName + "não encontrado");

		} catch (Exception e) {
			throw new FileStorageException("Arquivo " + fileName + "não encontrado", e);
		}

	}

}
