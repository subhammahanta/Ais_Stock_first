package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watsoo.device.management.dto.AddUserResponse;
import com.watsoo.device.management.dto.ClientDTO;
import com.watsoo.device.management.dto.ClientResponseDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateGstDTO;
import com.watsoo.device.management.dto.StockClientDTO;
import com.watsoo.device.management.enums.Gender;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.ClientStateGstMapping;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.repository.ClientRepository;
import com.watsoo.device.management.repository.ClientStateGstMappingRepository;
import com.watsoo.device.management.service.ClientService;

//@Transactional
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Value("${sfa.add.client.url}")
	private String url;

	@Value("${ais.admin.add.dealer}")
	private String addDealerUrl;

	@Autowired
	private ClientStateGstMappingRepository clientStateGstMappingRepository;

	// @Autowired
	// private PlatformTransactionManager transactionManager;

	@Override
	public Response<?> saveOrUpdateCompany(ClientDTO clientDTO) {
		if (clientDTO.getId() != null) {
			// v1(old) change to v3(new)
			return updateCompanyV3(clientDTO);
		} else {
			// v1(old) change to v3(new)
			return addCompanyV3(clientDTO);
		}
	}

	private AtomicInteger idCounter = new AtomicInteger();

	private String generateNumericCode() {
		Random random = new Random();
		long code = random.nextLong();
		code = Math.abs(code) % 1000000000000L;
		return String.format("%012d", code);
	}

	private Response<?> addCompany(ClientDTO clientDTO) {

		Client existingClient = clientRepository.findByCompanyNameAndEmail(clientDTO.getCompanyName(),
				clientDTO.getEmail());
		if (existingClient != null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"A client with the same name and email already exists");
		}
		Client client = clientDTO.convertDTOToEntity(clientDTO);
		int generatedId = idCounter.incrementAndGet();
		String[] companyNameParts = clientDTO.getCompanyName().split(" ");

		StringBuilder companyIdBuilder = new StringBuilder();
		for (String part : companyNameParts) {
			companyIdBuilder.append(part);
			break;
		}
		companyIdBuilder.append(generatedId);
		String companyId = companyIdBuilder.toString();
		client.setCompanyId(companyId);
		client.setState(clientDTO.getState());
		String clientCode = generateNumericCode();
		client.setCompanyCode(clientCode);
		Client savedClient = clientRepository.save(client);
		clientDTO.setId(savedClient.getId());
		// create user in ais_admin
		try {
			Boolean flag = addDealerInAisAdmin(clientDTO);
			if (flag) {
				return new Response<>(HttpStatus.CREATED.value(), "dealer added in ais-admin successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response<>(HttpStatus.CREATED.value(), "Company added successfully");
	}

	private Response<?> updateCompany(ClientDTO clientDTO) {
		Long clientId = clientDTO.getId();
		Optional<Client> optionalCompany = clientRepository.findById(clientId);

		if (optionalCompany.isPresent()) {
			Client client = optionalCompany.get();

			if (clientDTO.getIsActive() != null) {
				client.setIsActive(clientDTO.getIsActive());
			}

			if (clientDTO.getCompanyName() != null) {
				client.setCompanyName(clientDTO.getCompanyName());
			}

			if (clientDTO.getCompanyAddress() != null) {
				client.setCompanyAddress(clientDTO.getCompanyAddress());
			}

			if (clientDTO.getCity() != null) {
				client.setCity(clientDTO.getCity());
			}

			if (clientDTO.getEmail() != null) {
				client.setEmail(clientDTO.getEmail());
			}

			if (clientDTO.getState() != null) {
				client.setState(clientDTO.getState());
			}

			if (clientDTO.getPhoneNumber() != null) {
				client.setPhoneNumber(clientDTO.getPhoneNumber());
			}

			if (clientDTO.getPanNumber() != null) {
				client.setPanNumber(clientDTO.getPanNumber());
			}

			if (clientDTO.getCompanyLogo() != null) {
				client.setCompanyLogo(clientDTO.getCompanyLogo());
			}

			if (clientDTO.getCompanyCode() != null) {
				client.setCompanyCode(clientDTO.getCompanyCode());
			}

			if (clientDTO.getLastIssueQuantity() != null) {
				client.setLastIssueQuantity(clientDTO.getLastIssueQuantity());
			}

			if (clientDTO.getGstNumber() != null) {
				client.setGstNumber(clientDTO.getGstNumber());
			}

			if (clientDTO.getIsActive() != null) {
				client.setIsActive(clientDTO.getIsActive());
			}

			if (clientDTO.getIsOwn() != null) {
				client.setIsOwn(clientDTO.getIsOwn());
			}

			Client updatedClient = clientRepository.save(client);

			if (updatedClient != null) {
				return new Response<>(HttpStatus.OK.value(), "Company updated successfully");
			} else {
				return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update company");
			}
		} else {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Company not found");
		}
	}

	@Override
	public Response<?> getAllOrGetById(Long id, int pageNo, int pageSize) {
		if (id != null) {
			return getById(id);
		} else {
			return getAll();
		}
	}

	@Override
	public Response<?> getAll() {
		List<Client> clientPage = clientRepository.findAll();
		return new Response<>(HttpStatus.OK.value(), "All clients retrieved", clientPage);
	}

	@Override
	public Response<?> getById(Long id) {
		Optional<Client> optionalClient = clientRepository.findById(id);
		if (optionalClient.isPresent()) {
			Client client = optionalClient.get();
			List<ClientStateGstMapping> clientStateGstMapping = client.getClientStateGstMapping();
			List<ClientStateGstMapping> activeClientStateGstMapping = clientStateGstMapping.stream()
					.filter(o -> o.getIsActive()).collect(Collectors.toList());
			client.setClientStateGstMapping(activeClientStateGstMapping);
			return new Response<>(HttpStatus.OK.value(), "Client retrieved successfully", client);
		} else {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Client not found");
		}
	}

	@Override
	public Pagination<List<ClientDTO>> getAllClient(int pageNo, int pageSize, ClientDTO clientDTO) {
		Pagination<List<ClientDTO>> response = new Pagination<>();
		List<ClientDTO> clientDtoList = new ArrayList<>();
		Page<Client> clientDetails = null;
		Pageable pageRequest = Pageable.unpaged();

		if (pageSize != 0) {
			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("lastIssueDate").descending());
			clientDetails = clientRepository.findAll(clientDTO, pageRequest);
		} else {
			pageRequest = PageRequest.of(pageNo, Integer.MAX_VALUE, Sort.by("lastIssueDate").descending());
			clientDetails = clientRepository.findAll(clientDTO, pageRequest);
		}

		for (Client client : clientDetails.getContent()) {
			ClientDTO clientDetailsDto = client.convertEntityToDto(client);
			clientDtoList.add(clientDetailsDto);
		}
		response.setData(clientDtoList);
		response.setNumberOfElements(clientDetails.getNumberOfElements());
		response.setTotalElements(clientDetails.getTotalElements());
		response.setTotalPages(clientDetails.getTotalPages());

		return response;
	}

	@SuppressWarnings("unused")
	private Response<?> addCompanyV2(ClientDTO clientDTO) {

		Client existingClient = clientRepository.findByCompanyNameAndEmail(clientDTO.getCompanyName(),
				clientDTO.getEmail());
		if (existingClient != null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"A client with the same name and email already exists");
		}
		Client client = clientDTO.convertDTOToEntity(clientDTO);
		int generatedId = idCounter.incrementAndGet();
		String[] companyNameParts = clientDTO.getCompanyName().split(" ");

		StringBuilder companyIdBuilder = new StringBuilder();
		for (String part : companyNameParts) {
			companyIdBuilder.append(part);
			break;
		}
		companyIdBuilder.append(generatedId);

		String companyId = companyIdBuilder.toString();
		client.setCompanyId(companyId);
		client.setState(clientDTO.getState());
		String clientCode = generateNumericCode();
		client.setCompanyCode(clientCode);
		Client clientEntity = clientRepository.save(client);
		// add client in store start
		Boolean addClientInStore = addClientInStore(clientEntity);
		if (addClientInStore) {
			System.out.println("Client added successfully in stock management");
		} else {
			System.out.println("Client added failed in stock management");
		}
		// add client in store end
		return new Response<>(HttpStatus.CREATED.value(), "Company added successfully");
	}

	public Boolean addClientInStore(Client client) {
		Boolean flag = false;
		RestTemplate template = new RestTemplate();
		StockClientDTO payload = new StockClientDTO();
		payload.setAddress(client.getCompanyAddress());
		payload.setCity(client.getCity());
		payload.setCliendCode(client.getCompanyCode());
		payload.setContactNo(client.getPhoneNumber().toString());
		payload.setEmailid(client.getEmail());
		payload.setVisitingCardUrl(client.getCompanyLogo());
		payload.setPanCardNumber(client.getPanNumber());
		payload.setState(client.getState().getName());
		payload.setName(client.getCompanyName());
		payload.setGstNumber(client.getGstNumber());
		payload.setEmpCode("Watsoo-admin");
		payload.setContactPerson(client.getCompanyName());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<StockClientDTO> requestEntity = new HttpEntity<>(payload, headers);
		ResponseEntity<ClientResponseDTO> response = null;
		try {
			response = template.exchange(url + "/client_communicatin/v1/create/client/for/ais/stock", HttpMethod.POST,
					requestEntity, ClientResponseDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response != null && response.getBody() != null && response.getBody().getResponseCode() == 200) {
			flag = true;
		}
		return flag;
	}

	private Response<?> updateCompanyV2(ClientDTO clientDTO) {
		Long clientId = clientDTO.getId();
		Optional<Client> optionalCompany = clientRepository.findById(clientId);

		if (optionalCompany.isPresent()) {
			Client client = optionalCompany.get();

			if (clientDTO.getIsActive() != null) {
				client.setIsActive(clientDTO.getIsActive());
			}

			if (clientDTO.getCompanyName() != null) {
				client.setCompanyName(clientDTO.getCompanyName());
			}

			if (clientDTO.getCompanyAddress() != null) {
				client.setCompanyAddress(clientDTO.getCompanyAddress());
			}

			if (clientDTO.getCity() != null) {
				client.setCity(clientDTO.getCity());
			}

			if (clientDTO.getEmail() != null) {
				client.setEmail(clientDTO.getEmail());
			}

			if (clientDTO.getState() != null) {
				client.setState(clientDTO.getState());
			}

			if (clientDTO.getPhoneNumber() != null) {
				client.setPhoneNumber(clientDTO.getPhoneNumber());
			}

			if (clientDTO.getPanNumber() != null) {
				client.setPanNumber(clientDTO.getPanNumber());
			}

			if (clientDTO.getCompanyLogo() != null) {
				client.setCompanyLogo(clientDTO.getCompanyLogo());
			}

			if (clientDTO.getCompanyCode() != null) {
				client.setCompanyCode(clientDTO.getCompanyCode());
			}

			if (clientDTO.getLastIssueQuantity() != null) {
				client.setLastIssueQuantity(clientDTO.getLastIssueQuantity());
			}

			if (clientDTO.getGstNumber() != null) {
				client.setGstNumber(clientDTO.getGstNumber());
			}

			if (clientDTO.getIsActive() != null) {
				client.setIsActive(clientDTO.getIsActive());
			}

			if (clientDTO.getIsOwn() != null) {
				client.setIsOwn(clientDTO.getIsOwn());
			}

			Client updatedClient = clientRepository.save(client);

			if (updatedClient != null) {
				// add client in store start
				Boolean addClientInStore = addClientInStore(updatedClient);
				if (addClientInStore) {
					System.out.println("Client added successfully in stock management");
				} else {
					System.out.println("Client failed to add in stock management");
				}
				// add client in store end
				return new Response<>(HttpStatus.OK.value(), "Company updated successfully");
			} else {
				return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update company");
			}
		} else {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Company not found");
		}
	}

	public Boolean addDealerInAisAdmin(ClientDTO clientDTO) {
		Boolean res = false;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("address", clientDTO.getCompanyAddress());
		map.add("createdAt", new Date());
		map.add("email", clientDTO.getEmail());
		map.add("firstName", clientDTO.getCompanyName());
		map.add("isActive", true);
		map.add("mobileNo", clientDTO.getPhoneNumber().toString());
		map.add("name", clientDTO.getCompanyName());
		map.add("officialEmail", clientDTO.getEmail());
		map.add("gender", Gender.MALE);
		map.add("phoneNumber", clientDTO.getPhoneNumber().toString());
		map.add("designation", 1l);
		map.add("company", 1l);
		map.add("productionClientId", clientDTO.getId());

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		ResponseEntity<String> response = restTemplate.exchange(addDealerUrl, HttpMethod.POST, entity, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = response.getBody();
		try {
			AddUserResponse resp = objectMapper.readValue(jsonResponse, AddUserResponse.class);
			if (resp != null && resp.getId() != null) {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response<?> addAisAdminUser(ClientDTO clientDTO) {

		Client existingClient = clientRepository.findByCompanyNameAndEmailAndPanNumberV2(clientDTO.getCompanyName(),
				clientDTO.getEmail(), clientDTO.getPanNumber());
		if (existingClient != null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"A client with the same name and email already exists");
		}
		Client client = clientDTO.convertDTOToEntity(clientDTO);
		int generatedId = idCounter.incrementAndGet();
		String[] companyNameParts = clientDTO.getCompanyName().split(" ");

		StringBuilder companyIdBuilder = new StringBuilder();
		for (String part : companyNameParts) {
			companyIdBuilder.append(part);
			break;
		}
		companyIdBuilder.append(generatedId);
		String companyId = companyIdBuilder.toString();
		client.setCompanyId(companyId);
		client.setState(clientDTO.getState());
		String clientCode = generateNumericCode();
		client.setCompanyCode(clientCode);
		client.setAisUserId(clientDTO.getAisUserId());
		Client savedClient = clientRepository.save(client);
		clientDTO.setId(savedClient.getId());

		return new Response<>(HttpStatus.CREATED.value(), "Company added successfully");
	}

	private Response<?> addCompanyV3(ClientDTO clientDTO) {

		List<Client> allClient = clientRepository.findAll();

//		Map<String, Client> panMap = allClient.stream().filter(o -> o.getPanNumber() != null && o.getPanNumber() != "")
//				.collect(Collectors.toMap(Client::getPanNumber, Function.identity()));
//		
//		Map<String, List<Client>> duplicateClient = allClient.stream()
//				.filter(o -> o.getPanNumber() != null && o.getPanNumber() != "").collect(Collectors.groupingBy(o -> o.getPanNumber()));
//		
//		Client clientExist = panMap.get(clientDTO.getPanNumber());
//		if (clientExist != null) {
//			return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "pan number is already reported");
//		}
		List<Client> clientList = allClient.stream()
				.filter(o -> o.getCompanyName() != null && o.getCompanyName() != "" && o.getEmail() != null
						&& o.getEmail() != "" && o.getPanNumber() != null && o.getPanNumber() != ""
						&& (o.getCompanyName().equalsIgnoreCase(clientDTO.getCompanyName())
								|| o.getEmail().equalsIgnoreCase(clientDTO.getEmail())
								|| o.getPanNumber().equalsIgnoreCase(clientDTO.getPanNumber())))
				.collect(Collectors.toList());
		// Client existingClient =
		// clientRepository.findByCompanyNameAndEmailAndPanNumberV2(clientDTO.getCompanyName(),
		// clientDTO.getEmail(), clientDTO.getPanNumber());
		if (!clientList.isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"A client with the same name or email or panNo already exists");
		}
		if (clientDTO.getStateGstList().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "state and gst can't be empty");
		}
		Client client = clientDTO.convertDTOToEntity(clientDTO);
		int generatedId = idCounter.incrementAndGet();
		String[] companyNameParts = clientDTO.getCompanyName().split(" ");

		StringBuilder companyIdBuilder = new StringBuilder();
		for (String part : companyNameParts) {
			companyIdBuilder.append(part);
			break;
		}
		companyIdBuilder.append(generatedId);
		String companyId = companyIdBuilder.toString();
		client.setCompanyId(companyId);
		// client.setState(clientDTO.getState());
		String clientCode = generateNumericCode();
		client.setCompanyCode(clientCode);
		client.setClientOwner(clientDTO.getClientOwner());
		client.setClientPOC(clientDTO.getClientPOC());
		client.setIsActive(true);
		client.setCreatedAt(new Date());
		client.setCreatedBy(clientDTO.getUserId());
		Client savedClient = clientRepository.save(client);
		clientDTO.setId(savedClient.getId());
		List<ClientStateGstMapping> allClientStateGstMappings = clientStateGstMappingRepository.findAll();
//		Map<String, ClientStateGstMapping> gstMap = allClientStateGstMappings.stream()
//				.filter(o -> o.getGst() != null && o.getGst() != "")
//				.collect(Collectors.toMap(ClientStateGstMapping::getGst, Function.identity()));
		Map<String, List<ClientStateGstMapping>> gstMap = allClientStateGstMappings.stream()
				.filter(o -> o.getGst() != null && o.getGst() != "").collect(Collectors.groupingBy(o -> o.getGst()));

		List<ClientStateGstMapping> createClientStateGstMapping = new ArrayList<>();
		for (StateGstDTO stateGst : clientDTO.getStateGstList()) {
			List<ClientStateGstMapping> clientStateGstMapping2 = gstMap.get(stateGst.getGst());
			if (clientStateGstMapping2 != null) {
				for (ClientStateGstMapping clientStateGstMapping : clientStateGstMapping2) {
					if (clientStateGstMapping != null && clientStateGstMapping.getIsActive()) {
						return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "gst number is already exist");
					}
				}

			}

			ClientStateGstMapping clientStateGst = new ClientStateGstMapping();
			clientStateGst.setCreatedAt(new Date());
			clientStateGst.setCreatedBy(clientDTO.getUserId());
			clientStateGst.setGst(stateGst.getGst());
			clientStateGst.setIsActive(true);
			clientStateGst.setStateId(new State(stateGst.getStateId()));
			clientStateGst.setClientId(savedClient);
			createClientStateGstMapping.add(clientStateGst);
			List<ClientStateGstMapping> addNew = new ArrayList<>();
			addNew.add(clientStateGst);
			gstMap.put(stateGst.getGst(), addNew);
		}
		clientStateGstMappingRepository.saveAll(createClientStateGstMapping);
		// savedClient.setClientStateGstMapping(createClientStateGstMapping);
		// clientRepository.save(savedClient);
		// create user in ais_admin
		try {
			Boolean flag = addDealerInAisAdmin(clientDTO);
			if (flag) {
				return new Response<>(HttpStatus.CREATED.value(), "dealer added in ais-admin successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response<>(HttpStatus.CREATED.value(), "Company added successfully");
	}

	// @Transactional
	private Response<?> updateCompanyV3(ClientDTO clientDTO) {
		Long clientId = clientDTO.getId();
		Optional<Client> optionalCompany = clientRepository.findById(clientId);

		if (optionalCompany.isPresent()) {
			Client client = optionalCompany.get();

			if (clientDTO.getIsActive() != null) {
				client.setIsActive(clientDTO.getIsActive());
			}

			if (clientDTO.getCompanyName() != null) {
				client.setCompanyName(clientDTO.getCompanyName());
			}

			if (clientDTO.getCompanyAddress() != null) {
				client.setCompanyAddress(clientDTO.getCompanyAddress());
			}

			if (clientDTO.getCity() != null) {
				client.setCity(clientDTO.getCity());
			}

			if (clientDTO.getEmail() != null) {
				client.setEmail(clientDTO.getEmail());
			}

			if (clientDTO.getState() != null) {
				client.setState(clientDTO.getState());
			}

			if (clientDTO.getPhoneNumber() != null) {
				client.setPhoneNumber(clientDTO.getPhoneNumber());
			}

			if (clientDTO.getPanNumber() != null) {
				client.setPanNumber(clientDTO.getPanNumber());
			}

			if (clientDTO.getCompanyLogo() != null) {
				client.setCompanyLogo(clientDTO.getCompanyLogo());
			}

			if (clientDTO.getCompanyCode() != null) {
				client.setCompanyCode(clientDTO.getCompanyCode());
			}

			if (clientDTO.getLastIssueQuantity() != null) {
				client.setLastIssueQuantity(clientDTO.getLastIssueQuantity());
			}

			if (clientDTO.getGstNumber() != null) {
				client.setGstNumber(clientDTO.getGstNumber());
			}

			if (clientDTO.getIsActive() != null) {
				client.setIsActive(clientDTO.getIsActive());
			}

			if (clientDTO.getIsOwn() != null) {
				client.setIsOwn(clientDTO.getIsOwn());
			}
			if (clientDTO.getClientOwner() != null && clientDTO.getClientOwner() != "") {
				client.setClientOwner(clientDTO.getClientOwner());
			}
			if (clientDTO.getClientPOC() != null && clientDTO.getClientPOC() != "") {
				client.setClientPOC(clientDTO.getClientPOC());
			}
			client.setUpdatedAt(new Date());
			client.setUpdatedBy(clientDTO.getUserId());
			// Client updatedClient = clientRepository.save(client);

			if (clientDTO.getStateGstList() != null && !clientDTO.getStateGstList().isEmpty()) {
				// delete existing
				// TransactionDefinition def = new DefaultTransactionDefinition();
				// TransactionStatus status = transactionManager.getTransaction(def);
				// try {
				// clientStateGstMappingRepository.deleteExistingMappingByClientId(clientId);
				// transactionManager.commit(status);
				// } catch (Exception ex) {
				// transactionManager.rollback(status);
				// throw ex;
				// }

				// try {
				// clientStateGstMappingRepository.deleteExistingMappingByClientId(client.getId());
				// } catch (Exception e) {
				// e.printStackTrace();
				// }

				// List<Long> clientStateGstIds = clientDTO.getStateGstList().stream()
				// .map(StateGstDTO::getClientStateGstId).collect(Collectors.toList());
				// List<ClientStateGstMapping> stateGstMappings =
				// clientStateGstMappingRepository
				// .findbyIds(clientStateGstIds);
				List<ClientStateGstMapping> allClientStateGstMappings = clientStateGstMappingRepository.findAll();
				// List<ClientStateGstMapping> stateGstMappings =
				// clientStateGstMappingRepository
				// .findbyClientIds(clientId);
				List<ClientStateGstMapping> clientGst = allClientStateGstMappings
						.stream().filter(o -> o != null && o.getClientId() != null
								&& o.getClientId().getId().equals(clientId) && o.getIsActive())
						.collect(Collectors.toList());
				for (ClientStateGstMapping clientStateGstMapping : clientGst) {
					clientStateGstMapping.setIsActive(false);
				}
				clientStateGstMappingRepository.saveAll(clientGst);
				// Map<Long, ClientStateGstMapping> clientMap = stateGstMappings.stream()
				// .collect(Collectors.toMap(ClientStateGstMapping::getId,
				// Function.identity()));

//				Map<String, ClientStateGstMapping> gstMap = allClientStateGstMappings.stream()
//						.filter(o -> o.getGst() != null && o.getGst() != "")
//						.collect(Collectors.toMap(ClientStateGstMapping::getGst, Function.identity()));

				Map<String, List<ClientStateGstMapping>> gstMap = allClientStateGstMappings.stream()
						.filter(o -> o.getGst() != null && o.getGst() != "")
						.collect(Collectors.groupingBy(o -> o.getGst()));

				List<ClientStateGstMapping> updateClientStateGstMapping = new ArrayList<>();
				for (StateGstDTO stateGst : clientDTO.getStateGstList()) {
					// if (stateGst.getClientStateGstId() != null) {
					// ClientStateGstMapping clientStateGstMapping =
					// clientMap.get(stateGst.getClientStateGstId());
					// clientStateGstMapping.setUpdatedAt(new Date());
					// clientStateGstMapping.setUpdatedBy(clientDTO.getUserId());
					// if (stateGst.getGst() != null && stateGst.getGst() != "") {
					// clientStateGstMapping.setGst(stateGst.getGst());
					// }
					// if (stateGst.getStateId() != null) {
					// clientStateGstMapping.setStateId(new State(stateGst.getStateId()));
					// }
					// updateClientStateGstMapping.add(clientStateGstMapping);
					// } else {
					List<ClientStateGstMapping> clientStateGstMapping2 = gstMap.get(stateGst.getGst());
					if (clientStateGstMapping2 != null) {
						for (ClientStateGstMapping clientStateGstMapping : clientStateGstMapping2) {
							if (clientStateGstMapping != null && clientStateGstMapping.getIsActive()) {
								return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
										"gst number is already exist");
							}
						}

					}

					ClientStateGstMapping clientStateGst = new ClientStateGstMapping();
					clientStateGst.setCreatedAt(new Date());
					clientStateGst.setCreatedBy(clientDTO.getUserId());
					clientStateGst.setGst(stateGst.getGst());
					clientStateGst.setIsActive(true);
					clientStateGst.setStateId(new State(stateGst.getStateId()));
					clientStateGst.setClientId(client);
					updateClientStateGstMapping.add(clientStateGst);
					List<ClientStateGstMapping> addNew = new ArrayList<>();
					addNew.add(clientStateGst);
					gstMap.put(stateGst.getGst(), addNew);
					// }
				}
				clientStateGstMappingRepository.saveAll(updateClientStateGstMapping);
				// client.setClientStateGstMapping(updateClientStateGstMapping);

			}
			clientRepository.save(client);
			return new Response<>(HttpStatus.OK.value(), "Company updated successfully");
			// if (updatedClient != null) {
			// return new Response<>(HttpStatus.OK.value(), "Company updated successfully");
			// } else {
			// return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to
			// update company");
			// }
		} else {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Company not found");
		}
	}

	public String saveclientStateGstRecord() {
		try {
			List<Client> clientList = clientRepository.findAll();
			List<ClientStateGstMapping> list = new ArrayList<>();
			for (Client client : clientList) {
				if (client.getGstNumber() != null && client.getGstNumber() != "" && client.getState() != null) {
					ClientStateGstMapping clientStateGstMapping = new ClientStateGstMapping();
					clientStateGstMapping.setClientId(client);
					clientStateGstMapping.setStateId(client.getState());
					clientStateGstMapping.setGst(client.getGstNumber());
					clientStateGstMapping.setCreatedAt(new Date());
					clientStateGstMapping.setIsActive(true);
					clientStateGstMapping.setCreatedBy(client.getCreatedBy());
					list.add(clientStateGstMapping);
				}

			}
			clientStateGstMappingRepository.saveAll(list);
			return "ClientStateGst Record Succeefully Saved";

		} catch (Exception e) {
			e.getStackTrace();
			return e.getMessage();
		}
	}
}