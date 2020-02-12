package br.com.erudio.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.erudio.data.vo.UploadFileResponseVO;
import br.com.erudio.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "FileEndpoint")
@RestController
@RequestMapping("/api/v1/file")
public class FileController {
	
	 private static Log log = LogFactory.getLog(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@ApiOperation(value = "Efetua Upload de um arquivo")
	@PostMapping("/upload")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storageFile(file);
		UriComponentsBuilder path = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/file/download/")
				.path(fileName);
		UploadFileResponseVO uploadFileResponseVO = new UploadFileResponseVO(fileName, path.toUriString(),
				file.getContentType(), file.getSize());

		return uploadFileResponseVO;
	}

	@ApiOperation(value = "Efetua Upload de multiplos arquivos")
	@PostMapping("/multipleUpload")
	public List<UploadFileResponseVO> multipleUploadFile(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Efetua download de um arquivo")
	@GetMapping("/download/{fileName:.+}")//aceita arquivo mais extensao
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

		Resource resource = fileStorageService.loadFileAsResource(fileName);

		String contentType = null;

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			log.info("Fazendo download");
		} catch (Exception e) {
			log.info("Não foi possível determinar o tipo do arquivo");
		}
		if (contentType == null) {
			contentType = "application/octet-stream"; // Type generico
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment, filename=\""+resource.getFilename() +"\"")
				.body(resource);
	}

}
