package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.CommandRequestMasterDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.model.CommandRequestMaster;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.repository.CommandRequestMasterRepository;
import com.watsoo.device.management.repository.UserRepository;
import com.watsoo.device.management.service.CommandRequestMasterService;

@Service
public class CommandRequestMasterServiceImpl implements CommandRequestMasterService {

	@Autowired
	private CommandRequestMasterRepository commandRequestMasterRepo;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Pagination<List<CommandRequestMasterDTO>> getAllCommands(GenericRequestBody requestDTO) {
		Pagination<List<CommandRequestMasterDTO>> response = new Pagination<>();
		List<CommandRequestMasterDTO> commandRequestMasters = new ArrayList<>();

		try {
			Sort.Direction sortDirection = Sort.Direction.DESC;

			// Create a Sort object for the "createdDate" field
			Sort sort = Sort.by(sortDirection, "createdAt");
			Pageable pageable = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(), sort);
			Page<CommandRequestMaster> commandRequestMasterPage = commandRequestMasterRepo.findAll(pageable);
			List<User> users = userRepository.findAll();
			Map<Long, User> userMap = users.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(User::getId, Function.identity()));
			for (CommandRequestMaster commandRequestMaster : commandRequestMasterPage.getContent()) {

				CommandRequestMasterDTO commandsDetailsDTO = commandRequestMaster
						.convertEntityToDTO(commandRequestMaster);
				commandsDetailsDTO.setUser(userMap.get(commandRequestMaster.getCreatedBy()));
				commandRequestMasters.add(commandsDetailsDTO);
			}
//			List<CommandRequestMasterDTO> sortedList = commandRequestMasters.stream()
//					.sorted(Comparator.comparing(CommandRequestMasterDTO::getCreatedAt).reversed()).collect(Collectors.toList());

			response.setData(commandRequestMasters);
			response.setNumberOfElements(commandRequestMasterPage.getNumberOfElements());
			response.setTotalElements(commandRequestMasterPage.getTotalElements());
			response.setTotalPages(commandRequestMasterPage.getTotalPages());

		} catch (Exception e) {

			System.out.println(e);
			e.printStackTrace();

		}

		return response;
	}

	@Override
	public List<CommandRequestMasterDTO> findAll() {
		List<CommandRequestMasterDTO> commandRequestMasters = new ArrayList<>();
		List<CommandRequestMaster> commandRequestMastersList = commandRequestMasterRepo.findAll();
		for (CommandRequestMaster commandRequestMaster : commandRequestMastersList) {
			CommandRequestMasterDTO commandsDetailsDTO = commandRequestMaster.convertEntityToDTO(commandRequestMaster);
			commandRequestMasters.add(commandsDetailsDTO);
		}
		List<CommandRequestMasterDTO> sortedList = commandRequestMasters.stream()
				.sorted(Comparator.comparing(CommandRequestMasterDTO::getId).reversed()).collect(Collectors.toList());
		return sortedList;
	}

}
