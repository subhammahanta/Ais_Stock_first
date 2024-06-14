package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.BoxRequestDTO;
import com.watsoo.device.management.dto.DeviceDTO;
import com.watsoo.device.management.dto.MappingVehicleDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.service.AvailabilityService;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private BoxRepository boxRepository;

	@Override
	public Response<?> checkDeviceAvailability(int requestedQuantity) {
		List<BoxDTO> filteredBoxes = new ArrayList<>();
		Response<Object> response = new Response<>();
		Integer noOfDevices = 0;
		if (requestedQuantity > 0) {
			List<Box> boxes = boxRepository.findDevicePackedBoxes(StatusMaster.BOX_IN_STOCK.name());
			if (boxes.isEmpty()) {
				response.setData(null);
				response.setMessage("No devices Available For the Quantity");
				response.setResponseCode(HttpStatus.OK.value());
			} else {
				for (Box box : boxes) {
					noOfDevices = noOfDevices + box.getCurrentQuantity().intValue();
				}
				if (noOfDevices.intValue() >= requestedQuantity) {
					List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
							.collect(Collectors.toList());

					for (Box box : sortedBoxes) {
						List<DeviceDTO> deviceDTOs = new ArrayList<>();
						if (box.getQuantity() >= requestedQuantity) {
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							break;
						} else {
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							requestedQuantity = (int) (requestedQuantity - box.getQuantity());
						}
					}
					response.setData(filteredBoxes);
					response.setMessage(HttpStatus.OK.getReasonPhrase());
					response.setResponseCode(HttpStatus.OK.value());
				} else {
					response.setData(null);
					response.setMessage("Out of stock");
					response.setResponseCode(HttpStatus.OK.value());
				}
			}
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		}
		return response;
	}

	private DeviceDTO convertMappedDeviceToDeviceDto(MappingVehicleDTO device) {
		return new DeviceDTO(device.getId(), device.getImeiNo(), device.getIccidNo(), device.getUuidNo(),
				device.getSoftwareVersion(), device.getDetail(), device.getCreatedAt(), device.getUpdatedAt(),
				device.getRequestBody(), device.getOldIccid(), device.getIccidUpdatedAt(), device.getOldImei(),
				device.getImeiUpdatedAt(), device.getModifiedBy(), device.getState(), device.getStatus());
	}

	@Override
	public Response<?> checkDeviceAvailabilityV2(BoxRequestDTO boxRequestDTO) {
		List<BoxDTO> filteredBoxes = new ArrayList<>();
		Response<Object> response = new Response<>();
		Integer noOfDevices = 0;
		Integer requestedQuantity = boxRequestDTO.getRequestedQuantity();
		if (boxRequestDTO.getBoxesList() != null && boxRequestDTO.getBoxesList() != "") {
			List<String> boxIds = new ArrayList<>();
			if (boxRequestDTO.getBoxesList().contains(",")) {
				boxIds = Arrays.asList(boxRequestDTO.getBoxesList().split(","));
			} else {
				boxIds.add(boxRequestDTO.getBoxesList());
			}
			List<Box> boxes = boxRepository.findBoxesByBoxNo(boxIds, StatusMaster.BOX_IN_STOCK.name());
			Double currentQuantity = 0.0d;
			if (boxes != null && boxes.size() > 0) {
				currentQuantity = boxes.stream().filter(x -> x.getCurrentQuantity() != null)
						.mapToDouble(x -> x.getCurrentQuantity()).sum();
				if (currentQuantity >= boxRequestDTO.getRequestedQuantity()) {

					List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
							.collect(Collectors.toList());

					for (Box box : sortedBoxes) {
						List<DeviceDTO> deviceDTOs = new ArrayList<>();
						if (box.getQuantity() >= requestedQuantity) {
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							break;
						} else {
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							requestedQuantity = (int) (requestedQuantity - box.getQuantity());
						}
					}
					response.setData(filteredBoxes);
					response.setMessage(HttpStatus.OK.getReasonPhrase());
					response.setResponseCode(HttpStatus.OK.value());

				} else {
					if (requestedQuantity > 0) {
						List<Box> boxes2 = boxRepository.findDevicePackedBoxes(StatusMaster.BOX_IN_STOCK.name());
						if (boxes2.isEmpty()) {
							response.setData(null);
							response.setMessage("No devices Available For the Quantity");
							response.setResponseCode(HttpStatus.OK.value());
						} else {
							for (Box box : boxes2) {
								noOfDevices = noOfDevices + box.getCurrentQuantity().intValue();
							}
							if (noOfDevices.intValue() >= requestedQuantity) {
								List<Box> sortedBoxes = boxes2.stream().sorted(Comparator.comparing(Box::getCreatedAt))
										.collect(Collectors.toList());

								for (Box box : sortedBoxes) {
									List<DeviceDTO> deviceDTOs = new ArrayList<>();
									if (box.getQuantity() >= requestedQuantity) {
										List<MappingVehicleDTO> mappedDevices = boxRepository
												.findAllDevicesByBoxId(box.getId());
										if (mappedDevices != null && mappedDevices.size() > 0) {
											for (MappingVehicleDTO device : mappedDevices) {
												DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
												deviceDTOs.add(dto);
											}
										}
										BoxDTO boxDTO = box.convertEntityToDto(box);
										boxDTO.setDeviceDtoList(deviceDTOs);
										filteredBoxes.add(boxDTO);
										break;
									} else {
										List<MappingVehicleDTO> mappedDevices = boxRepository
												.findAllDevicesByBoxId(box.getId());
										if (mappedDevices != null && mappedDevices.size() > 0) {
											for (MappingVehicleDTO device : mappedDevices) {
												DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
												deviceDTOs.add(dto);
											}
										}
										BoxDTO boxDTO = box.convertEntityToDto(box);
										boxDTO.setDeviceDtoList(deviceDTOs);
										filteredBoxes.add(boxDTO);
										requestedQuantity = (int) (requestedQuantity - box.getQuantity());
									}
								}
								response.setData(filteredBoxes);
								response.setMessage(HttpStatus.OK.getReasonPhrase());
								response.setResponseCode(HttpStatus.OK.value());
							} else {
								response.setData(null);
								response.setMessage("Out of stock");
								response.setResponseCode(HttpStatus.OK.value());
							}
						}
					} else {
						response.setData(null);
						response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
						response.setResponseCode(HttpStatus.BAD_REQUEST.value());
					}

				}
			} else {
				response.setData(null);
				response.setMessage("No devices Available For the Quantity");
				response.setResponseCode(HttpStatus.OK.value());
			}

		} else {
			if (requestedQuantity > 0) {
				List<Box> boxes = boxRepository.findDevicePackedBoxes(StatusMaster.BOX_IN_STOCK.name());
				if (boxes.isEmpty()) {
					response.setData(null);
					response.setMessage("No devices Available For the Quantity");
					response.setResponseCode(HttpStatus.OK.value());
				} else {
					for (Box box : boxes) {
						noOfDevices = noOfDevices + box.getCurrentQuantity().intValue();
					}
					if (noOfDevices.intValue() >= requestedQuantity) {
						List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
								.collect(Collectors.toList());

						for (Box box : sortedBoxes) {
							List<DeviceDTO> deviceDTOs = new ArrayList<>();
							if (box.getQuantity() >= requestedQuantity) {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								break;
							} else {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								requestedQuantity = (int) (requestedQuantity - box.getQuantity());
							}
						}
						response.setData(filteredBoxes);
						response.setMessage(HttpStatus.OK.getReasonPhrase());
						response.setResponseCode(HttpStatus.OK.value());
					} else {
						response.setData(null);
						response.setMessage("Out of stock");
						response.setResponseCode(HttpStatus.OK.value());
					}
				}
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
				response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			}
		}

		return response;
	}

	@Override
	public Response<?> checkDeviceAvailabilityV3(BoxRequestDTO boxRequestDTO) {
		List<BoxDTO> filteredBoxes = new ArrayList<>();
		Response<Object> response = new Response<>();
		Integer noOfDevices = 0;
		Integer requestedQuantity = boxRequestDTO.getRequestedQuantity();
		if (boxRequestDTO.getBoxesList() != null && boxRequestDTO.getBoxesList() != "") {
			List<String> boxIds = new ArrayList<>();
			if (boxRequestDTO.getBoxesList().contains(",")) {
				boxIds = Arrays.asList(boxRequestDTO.getBoxesList().split(","));
			} else {
				boxIds.add(boxRequestDTO.getBoxesList());
			}
			List<Box> boxes = boxRepository.findBoxesByBoxNo(boxIds, StatusMaster.BOX_IN_STOCK.name());

			Double currentQuantity = 0.0d;
			if (boxes != null && boxes.size() > 0) {
				// get not found box
				List<String> boxNoList = boxes.parallelStream().map(Box::getBoxNo).collect(Collectors.toList());

				List<String> missingElements = boxIds.stream().filter(element -> !boxNoList.contains(element))
						.collect(Collectors.toList());
				String idString = String.join(",", missingElements);
				System.out.println(idString);
				if (idString != null && idString != "" && !idString.isEmpty()) {
					response.setRequestedURI("No box found with box number " + idString);
				}
				currentQuantity = boxes.stream().filter(x -> x.getCurrentQuantity() != null)
						.mapToDouble(x -> x.getCurrentQuantity()).sum();
				if (currentQuantity >= boxRequestDTO.getRequestedQuantity()) {

					List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
							.collect(Collectors.toList());

					for (Box box : sortedBoxes) {
						List<DeviceDTO> deviceDTOs = new ArrayList<>();
						if (box.getQuantity() >= requestedQuantity) {
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							break;
						} else {
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							requestedQuantity = (int) (requestedQuantity - box.getQuantity());
						}
					}
					response.setData(filteredBoxes);
					response.setMessage(HttpStatus.OK.getReasonPhrase());
					response.setResponseCode(HttpStatus.OK.value());

				} else {
					if (requestedQuantity > 0 && currentQuantity > 0 && currentQuantity < requestedQuantity) {

						List<Box> sortedBoxes2 = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
								.collect(Collectors.toList());

						for (Box box : sortedBoxes2) {
							List<DeviceDTO> deviceDTOs = new ArrayList<>();
							List<MappingVehicleDTO> mappedDevices = boxRepository.findAllDevicesByBoxId(box.getId());
							if (mappedDevices != null && mappedDevices.size() > 0) {
								for (MappingVehicleDTO device : mappedDevices) {
									DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
									deviceDTOs.add(dto);
								}
							}
							BoxDTO boxDTO = box.convertEntityToDto(box);
							boxDTO.setDeviceDtoList(deviceDTOs);
							filteredBoxes.add(boxDTO);
							requestedQuantity = (int) (requestedQuantity - box.getQuantity());
						}

						List<Box> boxes2 = boxRepository.findDevicePackedBoxes(StatusMaster.BOX_IN_STOCK.name());
						if (boxes2.isEmpty()) {
							response.setData(null);
							response.setMessage("No devices Available For the Quantity");
							response.setResponseCode(HttpStatus.OK.value());
						} else {
							for (Box box : boxes2) {
								noOfDevices = noOfDevices + box.getCurrentQuantity().intValue();
							}
							if (noOfDevices.intValue() >= requestedQuantity) {
								List<Box> sortedBoxes = boxes2.stream().sorted(Comparator.comparing(Box::getCreatedAt))
										.collect(Collectors.toList());

								for (Box box : sortedBoxes) {
									List<DeviceDTO> deviceDTOs = new ArrayList<>();
									if (box.getQuantity() >= requestedQuantity) {
										List<MappingVehicleDTO> mappedDevices = boxRepository
												.findAllDevicesByBoxId(box.getId());
										if (mappedDevices != null && mappedDevices.size() > 0) {
											for (MappingVehicleDTO device : mappedDevices) {
												DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
												deviceDTOs.add(dto);
											}
										}
										BoxDTO boxDTO = box.convertEntityToDto(box);
										boxDTO.setDeviceDtoList(deviceDTOs);
										filteredBoxes.add(boxDTO);
										break;
									} else {
										List<MappingVehicleDTO> mappedDevices = boxRepository
												.findAllDevicesByBoxId(box.getId());
										if (mappedDevices != null && mappedDevices.size() > 0) {
											for (MappingVehicleDTO device : mappedDevices) {
												DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
												deviceDTOs.add(dto);
											}
										}
										BoxDTO boxDTO = box.convertEntityToDto(box);
										boxDTO.setDeviceDtoList(deviceDTOs);
										filteredBoxes.add(boxDTO);
										requestedQuantity = (int) (requestedQuantity - box.getQuantity());
									}
								}
								response.setData(filteredBoxes);
								response.setMessage(HttpStatus.OK.getReasonPhrase());
								response.setResponseCode(HttpStatus.OK.value());
							} else {
								response.setData(null);
								response.setMessage("Out of stock");
								response.setResponseCode(HttpStatus.OK.value());
							}
						}
					} else {
						response.setData(null);
						response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
						response.setResponseCode(HttpStatus.BAD_REQUEST.value());
					}

				}
			} else {
				response.setData(null);
				response.setMessage("No devices Available For the Quantity");
				response.setResponseCode(HttpStatus.OK.value());
			}

		} else {
			if (requestedQuantity > 0) {
				List<Box> boxes = boxRepository.findDevicePackedBoxes(StatusMaster.BOX_IN_STOCK.name());
				if (boxes.isEmpty()) {
					response.setData(null);
					response.setMessage("No devices Available For the Quantity");
					response.setResponseCode(HttpStatus.OK.value());
				} else {
					for (Box box : boxes) {
						noOfDevices = noOfDevices + box.getCurrentQuantity().intValue();
					}
					if (noOfDevices.intValue() >= requestedQuantity) {
						List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
								.collect(Collectors.toList());

						for (Box box : sortedBoxes) {
							List<DeviceDTO> deviceDTOs = new ArrayList<>();
							if (box.getQuantity() >= requestedQuantity) {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								break;
							} else {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								requestedQuantity = (int) (requestedQuantity - box.getQuantity());
							}
						}
						response.setData(filteredBoxes);
						response.setMessage(HttpStatus.OK.getReasonPhrase());
						response.setResponseCode(HttpStatus.OK.value());
					} else {
						response.setData(null);
						response.setMessage("Out of stock");
						response.setResponseCode(HttpStatus.OK.value());
					}
				}
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
				response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			}
		}

		return response;
	}

	@Override
	public Response<?> checkDeviceAvailabilityV4(BoxRequestDTO boxRequestDTO) {
		List<BoxDTO> filteredBoxes = new ArrayList<>();
		Response<Object> response = new Response<>();
		Integer noOfDevices = 0;
		Integer requestedQuantity = boxRequestDTO.getRequestedQuantity();
		if (boxRequestDTO.getBoxesList() != null && boxRequestDTO.getBoxesList() != "") {
			List<String> boxIds = new ArrayList<>();
			if (boxRequestDTO.getBoxesList().contains(",")) {
				boxIds = Arrays.asList(boxRequestDTO.getBoxesList().split(","));
			} else {
				boxIds.add(boxRequestDTO.getBoxesList());
			}
			List<Box> boxes = boxRepository.findBoxesByBoxNo(boxIds, StatusMaster.BOX_IN_STOCK.name());
			Double currentQuantity = 0.0d;
			if (!boxes.isEmpty()) {
				if (boxes.size() == boxIds.size()) {
					currentQuantity = boxes.stream().filter(x -> x.getCurrentQuantity() != null)
							.mapToDouble(x -> x.getCurrentQuantity()).sum();

					if (currentQuantity >= boxRequestDTO.getRequestedQuantity()) {

						List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
								.collect(Collectors.toList());

						for (Box box : sortedBoxes) {
							List<DeviceDTO> deviceDTOs = new ArrayList<>();
							if (box.getQuantity() >= requestedQuantity) {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								break;
							} else {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								requestedQuantity = (int) (requestedQuantity - box.getQuantity());
							}
						}
						response.setData(filteredBoxes);
						response.setMessage(HttpStatus.OK.getReasonPhrase());
						response.setResponseCode(HttpStatus.OK.value());

					} else {
						response.setData(null);
						response.setMessage("available quantity is less than requested quantity");
						response.setResponseCode(HttpStatus.OK.value());
					}
				} else {
					// get not found box
					List<String> boxNoList = boxes.parallelStream().map(Box::getBoxNo).collect(Collectors.toList());

					List<String> missingElements = boxIds.stream().filter(element -> !boxNoList.contains(element))
							.collect(Collectors.toList());
					String idString = String.join(",", missingElements);
					if (idString != null && idString != "" && !idString.isEmpty()) {
						response.setData(null);
						response.setMessage("No box found with box number " + idString);
						response.setResponseCode(HttpStatus.OK.value());
					}
				}

			} else {
				response.setData(null);
				response.setMessage("No Boxes found For selected box numbres");
				response.setResponseCode(HttpStatus.OK.value());
			}
		} else {
			if (requestedQuantity > 0) {
				List<Box> boxes = boxRepository.findDevicePackedBoxes(StatusMaster.BOX_IN_STOCK.name());
				if (boxes.isEmpty()) {
					response.setData(null);
					response.setMessage("No devices Available For the Quantity");
					response.setResponseCode(HttpStatus.OK.value());
				} else {
					for (Box box : boxes) {
						noOfDevices = noOfDevices + box.getCurrentQuantity().intValue();
					}
					if (noOfDevices.intValue() >= requestedQuantity) {
						List<Box> sortedBoxes = boxes.stream().sorted(Comparator.comparing(Box::getCreatedAt))
								.collect(Collectors.toList());

						for (Box box : sortedBoxes) {
							List<DeviceDTO> deviceDTOs = new ArrayList<>();
							if (box.getQuantity() >= requestedQuantity) {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								break;
							} else {
								List<MappingVehicleDTO> mappedDevices = boxRepository
										.findAllDevicesByBoxId(box.getId());
								if (mappedDevices != null && mappedDevices.size() > 0) {
									for (MappingVehicleDTO device : mappedDevices) {
										DeviceDTO dto = convertMappedDeviceToDeviceDto(device);
										deviceDTOs.add(dto);
									}
								}
								BoxDTO boxDTO = box.convertEntityToDto(box);
								boxDTO.setDeviceDtoList(deviceDTOs);
								filteredBoxes.add(boxDTO);
								requestedQuantity = (int) (requestedQuantity - box.getQuantity());
							}
						}
						response.setData(filteredBoxes);
						response.setMessage(HttpStatus.OK.getReasonPhrase());
						response.setResponseCode(HttpStatus.OK.value());
					} else {
						response.setData(null);
						response.setMessage("Out of stock");
						response.setResponseCode(HttpStatus.OK.value());
					}
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
