package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.ConfigChangeTrailDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateCommandDTO;
import com.watsoo.device.management.enums.OperationType;
import com.watsoo.device.management.model.ConfigChangeTrail;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.StateCmdMstrEntity;
import com.watsoo.device.management.repository.ConfigChangeTrailRepository;
import com.watsoo.device.management.repository.StateCmdMstrRepository;
import com.watsoo.device.management.repository.StateRepository;
import com.watsoo.device.management.service.StateService;

@Service
public class StateServiceimpl implements StateService {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateCmdMstrRepository stateCmdMstrRepository;

	@Autowired
	private ConfigChangeTrailRepository configChangeTrailRepository;

	@Override
	public List<State> getAllStates() {
		List<State> stateList = stateRepository.findAll();
		return stateList;
	}

	@Override
	public State getById(Long id) {
		State state = null;
		Optional<State> stateOp = stateRepository.findById(id);
		if (stateOp.isPresent()) {
			state = stateOp.get();
		}
		return state;
	}

	@Override
	public List<StateCmdMstrEntity> getAllStateCmd() {
		List<StateCmdMstrEntity> responseList = new ArrayList<>();
		try {
			return stateCmdMstrRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@Override
	public Pagination<List<StateCommandDTO>> getStateCommand(int pageNo, int pageSize, StateCommandDTO dto) {
		Pagination<List<StateCommandDTO>> response = new Pagination<>();
		Page<StateCmdMstrEntity> statteCmdMstr = null;
		List<StateCommandDTO> commandDTOs = new ArrayList<>();
		Pageable pageRequest = Pageable.unpaged();
		if (pageSize > 0) {
			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
		}
		statteCmdMstr = stateCmdMstrRepository.getAllStateCommand(dto, pageRequest);
		Map<Long, List<ConfigChangeTrail>> config = new HashMap<>();
		List<Long> ids = statteCmdMstr.stream().filter(x -> x.getId() != null).map(y -> y.getId())
				.collect(Collectors.toList());

		List<ConfigChangeTrail> configChangeTrails = configChangeTrailRepository.getByConfigIds(ids);

		config = configChangeTrails.stream().filter(x -> x.getId() != null)
				.collect(Collectors.groupingBy(ConfigChangeTrail::getStateCmdMstrId));

		for (StateCmdMstrEntity st : statteCmdMstr.getContent()) {
			StateCommandDTO commandDTO = st.convertToDtoV2(st);
			List<ConfigChangeTrailDTO> changeTrailDTOs = new ArrayList<>();
			if (config.containsKey(st.getId())) {
				List<ConfigChangeTrail> configChangeTrailList = config.get(st.getId());
				changeTrailDTOs = configChangeTrailList.stream().map(x -> x.convertToDto())
						.collect(Collectors.toList());
				commandDTO.setConfigDto(changeTrailDTOs);
			}
			commandDTOs.add(commandDTO);
		}
//		commandDTOs = statteCmdMstr.getContent().stream()
//				.map(x -> x.convertToDto(configChangeTrailRepository.getByConfigId(x.getId()), x))
//				.collect(Collectors.toList());
		response.setData(commandDTOs);
		response.setNumberOfElements(statteCmdMstr.getNumberOfElements());
		response.setTotalElements(statteCmdMstr.getTotalElements());
		response.setTotalPages(statteCmdMstr.getTotalPages());
		return response;
	}

	@Override
	public Response<?> addUpdateConfig(StateCommandDTO dto) {
		Response<Object> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		if (dto.getStateCmdMstrId() != null) {
			// update or delete
			Optional<StateCmdMstrEntity> stateCmd = stateCmdMstrRepository.findById(dto.getStateCmdMstrId());
			if (stateCmd != null) {
				if (dto.getIsActive() != null && !dto.getIsActive()) {
					stateCmd.get().setIsActive(false);
					stateCmdMstrRepository.save(stateCmd.get());
					ConfigChangeTrail trail = new ConfigChangeTrail();
					trail.setModifiedAt(new Date());
					trail.setUser(dto.getUser());
					trail.setModifiedBy(dto.getModifiedBy());
					trail.setOperationType(OperationType.DELETED);
					trail.setStateCmdMstrId(dto.getStateCmdMstrId());
					configChangeTrailRepository.save(trail);
					response.setData(null);
					response.setMessage("Command successfully de-activated");
					response.setResponseCode(HttpStatus.OK.value());
				} else {
					if (dto.getCommand() != null && dto.getCommand() != "") {
						
						ConfigChangeTrail trail = new ConfigChangeTrail();
						trail.setModifiedAt(new Date());
						trail.setUser(dto.getUser());
						trail.setModifiedBy(dto.getModifiedBy());
						trail.setOperationType(OperationType.UPDATED);
						trail.setStateCmdMstrId(dto.getStateCmdMstrId());
						trail.setUpdatedCommand(dto.getCommand());
						trail.setLastCommand(stateCmd.get().getCommand());
						configChangeTrailRepository.save(trail);
						
						stateCmd.get().setCommand(dto.getCommand());
						stateCmdMstrRepository.save(stateCmd.get());
						response.setData(null);
						response.setMessage("Command successfully updated");
						response.setResponseCode(HttpStatus.OK.value());
					}

				}
			}
		} else {
			// create new StateCmdMstrEntity && ConfigChangeTrail
			if (dto.getClient() != null && dto.getState() != null && dto.getCommand() != null && dto.getCommand() != ""
					&& dto.getComndName() != null && dto.getComndName() != "") {
				List<StateCmdMstrEntity> stateCmd = stateCmdMstrRepository
						.getAllByStateIdAndClientId(dto.getClient().getId(), dto.getState().getId());
				if (stateCmd != null && stateCmd.size() > 0) {
					response.setData(null);
					response.setMessage("Configuration already exist");
					response.setResponseCode(HttpStatus.ALREADY_REPORTED.value());

				} else {
					StateCmdMstrEntity entity = new StateCmdMstrEntity();
					entity.setClient(dto.getClient());
					entity.setCommand(dto.getCommand());
					entity.setComndName(dto.getComndName());
					entity.setState(dto.getState());
					entity.setIsActive(true);
					StateCmdMstrEntity entity2 = stateCmdMstrRepository.save(entity);
					ConfigChangeTrail trail = new ConfigChangeTrail();
					trail.setModifiedAt(new Date());
					trail.setUser(dto.getUser());
					trail.setModifiedBy(dto.getModifiedBy());
					trail.setOperationType(OperationType.CREATED);
					trail.setStateCmdMstrId(entity2.getId());
					trail.setUpdatedCommand(dto.getCommand());
					configChangeTrailRepository.save(trail);
					response.setData(null);
					response.setMessage("Configuration created successfully");
					response.setResponseCode(HttpStatus.CREATED.value());

				}
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
				response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			}
		}
		return response;
	}

}
