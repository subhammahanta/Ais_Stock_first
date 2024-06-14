package com.watsoo.device.management.serviceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.watsoo.device.management.config.FileStorageProperties;
import com.watsoo.device.management.dto.FileUrlResponse;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Value("${imageUrlToken}")
	private String imageUrlToken;
	
	@Value("${ais.file.access.url}")
	private String aisfileAccessUrl;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			// throw new FileStorageException("Could not create the directory where the
			// uploaded files will be stored.", ex);
			//ex.printStackTrace();
		}
	}
	
	public String getFileUrl(MultipartFile file) {
		String fileUrl = null;
		try {
			System.out.println(aisfileAccessUrl);
			String url = aisfileAccessUrl;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
			map.add("files", new MultipartInputStreamFileResources(file.getInputStream(), file.getOriginalFilename()));

			// send POST request
			ResponseEntity<String> response = null;
			String responseBody = null;
			FileUrlResponse responseBodyDto = new FileUrlResponse();
			if (imageUrlToken != null && !imageUrlToken.isEmpty() && file != null || !file.isEmpty()) {
				try {
					map.add("token", imageUrlToken);
					HttpEntity<LinkedMultiValueMap<Object, Object>> entity = new HttpEntity<>(map, headers);
					response = restTemplate.postForEntity(url, entity, String.class);
					responseBody = response.getBody();
					responseBodyDto = new Gson().fromJson(responseBody, responseBodyDto.getClass());
					if (responseBodyDto.getResponseCode().equals(200) && responseBodyDto.getData() != null
							&& responseBodyDto.getData().getFileUrls() != null
							&& !responseBodyDto.getData().getFileUrls().isEmpty()) {
						fileUrl = responseBodyDto.getData().getFileUrls().get(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();

		}
		return fileUrl;
	}
}