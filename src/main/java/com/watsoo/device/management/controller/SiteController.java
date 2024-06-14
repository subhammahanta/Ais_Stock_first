package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.SiteDTO;
import com.watsoo.device.management.dto.UserSiteRoleDTO;
import com.watsoo.device.management.service.SiteService;
import com.watsoo.device.management.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("site")
public class SiteController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "v1/add/user/site/role")
	public ResponseEntity<?> setUserSitRoles(@RequestBody UserSiteRoleDTO userSiteRoleDTO) {
		siteService.setUserSitRoles(userSiteRoleDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(value = "v1/get/user/site/role")
	public ResponseEntity<?> getUserSiteRole(@RequestParam Long userId) {
		Response<?> resp = siteService.getSiteRole(userId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "v1/remove/user/site/role")
	public ResponseEntity<?> removeUserSitRoles(@RequestBody UserSiteRoleDTO userSiteRole) {
		siteService.removeUserSitRoles(userSiteRole);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/save/site")
	public ResponseEntity<?> saveSite(@RequestBody SiteDTO siteDto) {
		Response<?> response = siteService.saveSite(siteDto);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@PutMapping("/update/site")
	public ResponseEntity<?> updateSite(@RequestBody SiteDTO siteDto) {
		Response<?> response = siteService.updateSite(siteDto);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping("/getById/site")
	public ResponseEntity<?> getByIdSite(@RequestParam Long id) {
		Response<?> response = siteService.getSiteById(id);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

}
