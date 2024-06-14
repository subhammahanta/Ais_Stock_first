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

import com.watsoo.device.management.dto.ClientDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.ClientService;


@RestController
@RequestMapping("api")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	

    @PostMapping("/add/company")
    public ResponseEntity<?> addCompany(@RequestBody ClientDTO clientDTO) {
        Response<?> response = clientService.saveOrUpdateCompany(clientDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get/company")
    public ResponseEntity<Response<?>> getAllOrGetById(
            @RequestParam(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(clientService.getById(id));
        } else {
            return ResponseEntity.ok(clientService.getAll());
        }						
    }
    
    @PostMapping("/client")
    public ResponseEntity<?> getAllClient(@RequestParam(required = true, defaultValue = "0") int pageNo,
			@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody ClientDTO clientDTO ){
    	
    	Pagination<List<ClientDTO>> response = clientService.getAllClient(pageNo,pageSize,clientDTO);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/add/dealer")
    public ResponseEntity<?> addAisAdminUser(@RequestBody ClientDTO clientDTO) {
        Response<?> response = clientService.addAisAdminUser(clientDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}