package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.BoxDeviceDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.BoxService;

@RestController
@RequestMapping("api")
public class BoxController {
	@Autowired
	private BoxService boxService;

	@PostMapping("/save/box")
	public ResponseEntity<?> saveBoxDetails(@RequestBody BoxDTO boxDto) {

		if (boxDto.getCreatedById() != null) {
			// change to v2 for stock management
			BoxDTO boxDetails = boxService.saveBoxDetails(boxDto);
			return new ResponseEntity<>(boxDetails, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(value = "v1/get/all/box")
	public ResponseEntity<?> getVehicleMaintenance(@RequestParam(required = true, defaultValue = "0") int pageNo,
			@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody BoxDTO boxDTO) {
		Response<Pagination<List<BoxDTO>>> response = new Response<>();
		// Pagination<List<BoxDTO>> resp = boxService.getAllBox(pageNo, pageSize,
		// boxDTO);
		Pagination<List<BoxDTO>> resp = boxService.getAllBoxLite(pageNo, pageSize, boxDTO);
		response.setData(resp);
		response.setResponseCode(HttpStatus.OK.value());
		response.setMessage(HttpStatus.OK.getReasonPhrase());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get/box/byId")
	public ResponseEntity<?> getById(@RequestParam("id") Long id) {
		Response<BoxDeviceDTO> boxDTO = boxService.getById(id);
		return new ResponseEntity<>(boxDTO, HttpStatus.OK);
	}

//	@GetMapping(value = "v1/get/all/product/type")
//	public ResponseEntity<?> getAllProductType() {
//		Response<List<ProductTypeDTO>> response = new Response<>();
//		List<ProductTypeDTO> productTypeDTOresponse = boxService.getAllProduct();
//		response.setData(productTypeDTOresponse);
//		response.setResponseCode(HttpStatus.OK.value());
//		response.setMessage(HttpStatus.OK.getReasonPhrase());
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@PostMapping(value = "v1/get/all/product/status")
//	public ResponseEntity<?> getAllProductStatus(@RequestBody ProductStatusMappingDTO productStatusMappingDTO) {
//		Response<List<ProductStatusMappingDTO>> response = new Response<>();
//		List<ProductStatusMappingDTO> productStatusDTOresponse = boxService
//				.getAllProductStatus(productStatusMappingDTO);
//		response.setData(productStatusDTOresponse);
//		response.setResponseCode(HttpStatus.OK.value());
//		response.setMessage(HttpStatus.OK.getReasonPhrase());
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@PostMapping("/create/box")
	public Response<?> createBox(@RequestBody BoxDTO boxDto) {
		return boxService.createBox(boxDto);
	}

	@PostMapping("/add/device/in/box")
	public Response<?> addDeviceInBox(@RequestBody BoxDTO boxDto) {
		return boxService.addDeviceInBox(boxDto);
	}
}
