package com.watsoo.device.management.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.AddDeviceResponse;
import com.watsoo.device.management.dto.ConfigurationDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.SendEmailRequest;
import com.watsoo.device.management.dto.VehicleLazyEntity;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.ClientLiteV2;
import com.watsoo.device.management.model.Configuration;
import com.watsoo.device.management.model.CredentialMaster;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceConfigMaster;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.DeviceLiteV2;
import com.watsoo.device.management.model.DeviceLiteV3;
import com.watsoo.device.management.model.IccidMaster;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserLiteV2;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.ClientLiteV2Repository;
import com.watsoo.device.management.repository.ClientRepository;
import com.watsoo.device.management.repository.ConfigurationRepository;
import com.watsoo.device.management.repository.CredentialMasterRepository;
import com.watsoo.device.management.repository.DeviceConfigMasterRepository;
import com.watsoo.device.management.repository.DeviceLiteRepository;
import com.watsoo.device.management.repository.DeviceLiteV2Repository;
import com.watsoo.device.management.repository.DeviceLiteV3Repository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.IccidMasterRepository;
import com.watsoo.device.management.repository.StateRepository;
import com.watsoo.device.management.repository.UserLiteV2Repository;
import com.watsoo.device.management.service.DeviceService;
import com.watsoo.device.management.service.EmailService;
import com.watsoo.device.management.service.EmailServiceVM;
import com.watsoo.device.management.util.DateUtil;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceLiteRepository deviceLiteRepository;

    @Autowired
    private ConfigurationRepository configRepo;

    @Autowired
    private CredentialMasterRepository credentialMstrRepo;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${ais.command.url}")
    private String aisCommandUrl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private DeviceLiteV2Repository deviceLiteV2Repository;

    @Autowired
    private IccidMasterRepository iccidMasterRepository;

    @Autowired
    private ClientLiteV2Repository clientLiteRepository;

    // @Autowired
    // private CredentialMasterLiteRepository credentialMasterLiteRepository;

    @Autowired
    private FileStorageService fileSystemStorageService;

    @Autowired
    private UserLiteV2Repository userLiteRepo;

    @Autowired
    private EmailServiceVM emailServiceVM;

    @Value("${ais.admin.add.device}")
    private String addDeviceUrl;

    @Autowired
    private DeviceLiteV3Repository deviceLiteV3Repository;

    @Autowired
    private DeviceConfigMasterRepository deviceConfigMasterRepository;

    @Override
    public Device saveDeviceData(Device deviceDTO) {
        try {
            // State state = stateRepository.findbyReferenceKey(deviceDTO.getStateRefKey());
            // if (state == null) {
            State state = stateRepository.findbyReferenceKey(Constant.OTHER_STATE);
            // }
            Optional<Device> imeiExist = deviceRepository.findByImeiNo(deviceDTO.getImeiNo());
            Optional<Device> iccidExist = deviceRepository.findByIccidNo(deviceDTO.getIccidNo());
            if (imeiExist.isPresent() && !iccidExist.isPresent()) {
                if (deviceDTO.getIccidNo() != null && (imeiExist.get().getIccidNo() != null
                        && !(deviceDTO.getIccidNo().equalsIgnoreCase(imeiExist.get().getIccidNo())))) {
                    imeiExist.get().setOldIccid(imeiExist.get().getIccidNo());
                    imeiExist.get().setIccidUpdatedAt(new Date());
                    imeiExist.get().setIccidNo(deviceDTO.getIccidNo());
                }
                if (deviceDTO.getSoftwareVersion() != null) {
                    imeiExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
                }
                if (deviceDTO.getDetail() != null) {
                    imeiExist.get().setDetail(deviceDTO.getDetail());
                }

                String requestBody = new Gson().toJson(deviceDTO);
                imeiExist.get().setUpdatedAt(new Date());
                imeiExist.get().setModifiedBy(deviceDTO.getUserId());
                imeiExist.get().setRequestBody(requestBody);
                imeiExist.get().setStatus(StatusMaster.TESTED);
                if (state != null) {
                    imeiExist.get().setState(state);
                }
                Device deviceObj = deviceRepository.save(imeiExist.get());
                return deviceObj;
            } else if (iccidExist.isPresent() && !imeiExist.isPresent()) {
                if (deviceDTO.getImeiNo() != null && (iccidExist.get().getImeiNo() != null
                        && !(deviceDTO.getImeiNo().equalsIgnoreCase(iccidExist.get().getImeiNo())))) {
                    iccidExist.get().setOldImei(iccidExist.get().getImeiNo());
                    iccidExist.get().setImeiUpdatedAt(new Date());
                    iccidExist.get().setImeiNo(deviceDTO.getImeiNo());
                }
                if (deviceDTO.getSoftwareVersion() != null) {
                    iccidExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
                }
                if (deviceDTO.getDetail() != null) {
                    iccidExist.get().setDetail(deviceDTO.getDetail());
                }

                String requestBody = new Gson().toJson(deviceDTO);
                iccidExist.get().setUpdatedAt(new Date());
                iccidExist.get().setModifiedBy(deviceDTO.getUserId());
                iccidExist.get().setRequestBody(requestBody);
                iccidExist.get().setStatus(StatusMaster.TESTED);
                if (state != null) {
                    iccidExist.get().setState(state);
                }
                Device deviceObj = deviceRepository.save(iccidExist.get());
                return deviceObj;
            } else if (imeiExist.isPresent() && iccidExist.isPresent()) {
                if (imeiExist.get().getId() != null && iccidExist.get().getId() != null
                        && imeiExist.get().getId().intValue() == iccidExist.get().getId().intValue()) {
                    if (deviceDTO.getSoftwareVersion() != null) {
                        imeiExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
                    }
                    if (deviceDTO.getDetail() != null) {
                        imeiExist.get().setDetail(deviceDTO.getDetail());
                    }
                    String requestBody = new Gson().toJson(deviceDTO);
                    imeiExist.get().setUpdatedAt(new Date());
                    imeiExist.get().setModifiedBy(deviceDTO.getUserId());
                    imeiExist.get().setRequestBody(requestBody);
                    imeiExist.get().setStatus(StatusMaster.TESTED);
                    if (state != null) {
                        imeiExist.get().setState(state);
                    }
                    Device deviceObj = deviceRepository.save(imeiExist.get());
                    return deviceObj;
                } else {
                    return null;
                }
            } else {
                Device device = new Device();
                if (deviceDTO.getImeiNo() != null) {
                    device.setImeiNo(deviceDTO.getImeiNo());
                } else {
                    throw new RuntimeException("Imei no can't be null");
                }
                if (deviceDTO.getIccidNo() != null) {
                    device.setIccidNo(deviceDTO.getIccidNo());
                } else {
                    throw new RuntimeException("Iccid no can't be null");
                }
                if (deviceDTO.getSoftwareVersion() != null) {
                    device.setSoftwareVersion(deviceDTO.getSoftwareVersion());
                }
                if (deviceDTO.getDetail() != null) {
                    device.setDetail(deviceDTO.getDetail());
                }

                device.setUuidNo(getUuidNo(deviceDTO.getImeiNo()));
                String requestBody = new Gson().toJson(deviceDTO);
                device.setCreatedAt(new Date());
                device.setUpdatedAt(new Date());
                device.setCreatedBy(new User(deviceDTO.getUserId()));
                device.setModifiedBy(deviceDTO.getUserId());
                device.setRequestBody(requestBody);
                device.setStatus(StatusMaster.TESTED);
                device.setState(state);
                Device deviceObj = deviceRepository.save(device);
                return deviceObj;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String getUuidNo(String imeiNumber) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("YYYY");
        String year = dayFormat.format(new Date());

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String month = monthFormat.format(new Date());

        // LAST 8 CHARACTERS OF IMEI NUMBER
        Integer size = imeiNumber.length() - 8;
        String uuid = year.substring(2, 4) + month + imeiNumber.substring(size, (imeiNumber.length()));
        return uuid;
    }

    @Override
    public Response<Device> getDevice(String imei) {
        Response<Device> response = new Response<>();
        Optional<Device> deviceExist = deviceRepository.findByImeiNo(imei);
        if (deviceExist.isPresent()) {
            response.setData(deviceExist.get());
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setResponseCode(HttpStatus.OK.value());
            return response;
        } else {
            response.setData(null);
            response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }

    @Override
    public Response<GenericRequestBody> getAppVersion() {
        Response<GenericRequestBody> response = new Response<>();
        try {
            Configuration config = configRepo.findByKey("version");
            if (config != null) {
                GenericRequestBody resp = new GenericRequestBody(config.getValue());
                response.setData(resp);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setResponseCode(HttpStatus.OK.value());
            } else {
                response.setData(null);
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setData(null);
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Pagination<List<Device>> getAllDevice(GenericRequestBody requestDTO) {
        Pagination<List<Device>> response = new Pagination<>();
        List<Device> deviceList = new ArrayList<>();
        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("packedDate").descending());
            }
            Page<Device> page = deviceRepository.findAll(requestDTO, pageRequest);
            List<Long> userIds = page.getContent().stream().filter(o -> o.getModifiedBy() != null)
                    .map(o -> o.getModifiedBy()).distinct().collect(Collectors.toList());
            List<CredentialMaster> userList = credentialMstrRepo.findAllById(userIds);
            Map<Object, List<CredentialMaster>> userMap = userList.stream().filter(o -> o.getId() != null)
                    .collect(Collectors.groupingBy(o -> o.getId()));

            for (Device device : page.getContent()) {
                CredentialMaster lastWorkedUser = userMap.get(device.getModifiedBy()) != null
                        && userMap.get(device.getModifiedBy()).size() > 0 ? userMap.get(device.getModifiedBy()).get(0)
                        : null;
                if (lastWorkedUser != null) {
                    device.setCreatedAt(DateUtil.addMinutesToJavaUtilDate(device.getCreatedAt(), 330));
                    device.setUpdatedAt(DateUtil.addMinutesToJavaUtilDate(device.getUpdatedAt(), 330));
                    device.setUserName(lastWorkedUser.getName());
                    device.setUserEmail(lastWorkedUser.getEmail());
                }
                deviceList.add(device);
            }
            List<Device> deviceList1 = deviceRepository.findAll();
            long tested = deviceList1.stream()
                    .filter(o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name()))
                    .count();
            long boxPacked = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name()))
                    .count();
            long devicePacked = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name()))
                    .count();
            long count = deviceList1.stream().count();
            deviceList = deviceList.stream().filter(o -> o.getUpdatedAt() != null)
                    .sorted(Comparator.comparing(Device::getPackedDate).reversed()).collect(Collectors.toList());
            response.setData(deviceList);
            response.setTestedCount(tested);
            response.setBoxPackedCount(boxPacked);
            response.setDevicePackedCount(devicePacked);
            response.setNumberOfElements(page.getNumberOfElements());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
            response.setTotalDevice(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Pagination<List<DeviceLite>> getAllDeviceV2(GenericRequestBody requestDTO) {
        Pagination<List<DeviceLite>> response = new Pagination<>();
        List<DeviceLite> deviceList = new ArrayList<>();
        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("packedDate").descending());
            }
            Page<DeviceLite> page = deviceLiteRepository.findAllV2(requestDTO, pageRequest);
            List<Long> userIds = page.getContent().stream().filter(o -> o.getModifiedBy() != null)
                    .map(o -> o.getModifiedBy()).distinct().collect(Collectors.toList());
            List<Long> clientIds = page.getContent().stream().filter(o -> o.getClientId() != null)
                    .map(o -> o.getClientId()).distinct().collect(Collectors.toList());
            List<ClientLiteV2> clientList = new ArrayList<>();
            Map<Long, ClientLiteV2> clientMap = new HashMap<>();
            if (clientIds != null && !clientIds.isEmpty()) {
                clientList = clientLiteRepository.findAllById(clientIds);
                if (clientList != null && !clientList.isEmpty()) {
                    clientMap = clientList.stream().filter(x -> x != null && x.getId() != null)
                            .collect(Collectors.toMap(ClientLiteV2::getId, Function.identity()));
                }
            }
            List<UserLiteV2> userList = userLiteRepo.findAllById(userIds);
            Map<Long, UserLiteV2> userMap = userList.stream().filter(o -> o.getId() != null)
                    .collect(Collectors.toMap(UserLiteV2::getId, Function.identity()));

            for (DeviceLite device : page.getContent()) {
                UserLiteV2 lastWorkedUser = userMap.get(device.getModifiedBy()) != null
                        ? userMap.get(device.getModifiedBy())
                        : null;
                if (lastWorkedUser != null) {
//					device.setCreatedAt(DateUtil.addMinutesToJavaUtilDate(device.getCreatedAt(), 330));
//					device.setUpdatedAt(DateUtil.addMinutesToJavaUtilDate(device.getUpdatedAt(), 330));
                    device.setUserName(lastWorkedUser.getName());
                    device.setUserEmail(lastWorkedUser.getEmail());
                }
                if (device.getClientId() != null && clientMap != null && !clientMap.isEmpty()
                        && clientMap.containsKey(device.getClientId())) {
                    device.setClientName(clientMap.get(device.getClientId()).getCompanyName());
                }
                if (device.getStatus().equals(StatusMaster.BOX_PACKED)) {
                    device.setBoxNo(StatusMaster.BOX_PACKED + "(" + device.getBoxCode() + ")");
                } else {
                    device.setBoxNo(device.getStatus().toString());
                }
                device.setPackedDate(device.getPackedDate() != null
                        ? DateUtil.addMinutesToJavaUtilDate(device.getPackedDate(), Constant.ADD_IST_TIME)
                        : null);
                deviceList.add(device);
            }

            // Calendar cal = Calendar.getInstance();
            // cal.setTime(new Date());
            // cal.set(Calendar.HOUR_OF_DAY, 0);
            // cal.set(Calendar.MINUTE, 0);
            // cal.set(Calendar.SECOND, 0);
            // cal.set(Calendar.MILLISECOND, 0);
            // Date dateMidnightToday = cal.getTime();

            LocalDate currentDate = LocalDate.now();
            // LocalDate yesterday = currentDate.minusDays(1);
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime midnightToday = LocalDateTime.of(currentDate, midnight);
            Date dateMidnightToday = Date.from(midnightToday.atZone(ZoneId.systemDefault()).toInstant());
            // LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
            Date today = new Date();
            // Date yesterday =
            // Date.from(twentyFourHoursAgo.atZone(ZoneId.systemDefault()).toInstant());
            List<DeviceLiteV2> deviceList1 = deviceLiteV2Repository.findAll();
            long tested = 0l;
            long boxPacked = 0l;
            long devicePacked = 0l;
            long count = 0l;
            if (requestDTO.getFromDate() != null && requestDTO.getToDate() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(requestDTO.getFromDate()));
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                Date dateFrom = cal.getTime();
                Calendar calendar = Calendar.getInstance();
                Date endDate = new Date(requestDTO.getToDate());
                calendar.setTime(endDate);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                Date dateTo = calendar.getTime();
                tested = deviceList1.stream()
                        .filter(o -> o.getId() != null
                                && (o.getCreatedAt().toInstant().isAfter(dateFrom.toInstant())
                                && o.getCreatedAt().toInstant().isBefore(dateTo.toInstant()))
                                && o.getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name()))
                        .count();
                boxPacked = deviceList1.stream()
                        .filter(o -> o.getId() != null
                                && (o.getCreatedAt().toInstant().isAfter(dateFrom.toInstant())
                                && o.getCreatedAt().toInstant().isBefore(dateTo.toInstant()))
                                && o.getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name()))
                        .count();
                devicePacked = deviceList1.stream()
                        .filter(o -> o.getId() != null
                                && (o.getCreatedAt().toInstant().isAfter(dateFrom.toInstant())
                                && o.getCreatedAt().toInstant().isBefore(dateTo.toInstant()))
                                && o.getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name()))
                        .count();

                count = deviceList1.stream()
                        .filter(o -> o.getId() != null && (o.getCreatedAt().toInstant().isAfter(dateFrom.toInstant())
                                && o.getCreatedAt().toInstant().isBefore(dateTo.toInstant())))
                        .count();
            }
            // else {
            // tested = deviceList1.stream().filter(
            // o -> o.getId() != null &&
            // o.getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name()))
            // .count();
            // boxPacked = deviceList1.stream().filter(
            // o -> o.getId() != null &&
            // o.getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name()))
            // .count();
            // devicePacked = deviceList1.stream().filter(o -> o.getId() != null
            // &&
            // o.getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name())).count();
            // count = deviceList1.size();
            // }
            // long tested = deviceList1.stream()
            // .filter(o -> o.getId() != null &&
            // o.getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name()))
            // .count();
            // long boxPacked = deviceList1.stream().filter(
            // o -> o.getId() != null &&
            // o.getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name()))
            // .count();
            // long devicePacked = deviceList1.stream().filter(
            // o -> o.getId() != null &&
            // o.getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name()))
            // .count();
            long deviceIssued = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.ISSUED_DEVICES.name()))
                    .count();
            // long count = deviceList1.size();
            // long onlineDeviceCount = deviceList1.stream().filter(o -> o.getId() != null
            // && o.getPackedDate() != null
            // &&( o.getPackedDate().after(dateMidnightToday) &&
            // o.getPackedDate().before(today))).count();

            List<DeviceLiteV2> onlineDevices = deviceList1.stream()
                    .filter(o -> o.getId() != null && o.getPackedDate() != null
                            && (o.getPackedDate().after(dateMidnightToday) && o.getPackedDate().before(today)))
                    .collect(Collectors.toList());

            long onlineDeviceCount = onlineDevices.size();
            deviceList = deviceList.stream().sorted(
                            Comparator.comparing(DeviceLite::getPackedDate, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());

            if (requestDTO.getPageNo() == null && requestDTO.getPageSize() == null) {
                for (DeviceLite device : deviceList) {
                    device.setPackedDate(null);
                    device.setPacket(null);
                    device.setCreatedBy(null);
                    device.setRequestBody(null);
                    // device.setDetail(null);
                    device.setSoftwareVersion(null);
                    device.setSim1ActivationDate(null);
                    device.setSim2ActivationDate(null);
                    device.setBoxNo(null);
                    device.setSim1ExpiryDate(null);
                    device.setSim2ExpiryDate(null);
                    device.setUserEmail(null);
                    device.setUserName(null);
                    device.setSim1Provider(null);
                    device.setSim2Provider(null);
                    device.setSim1Operator(null);
                    device.setSim2Operator(null);
                    device.setSim1Number(null);
                    device.setSim2Number(null);
                    device.setState(
                            device.getState() != null ? new State(device.getState().getId()) : device.getState());
                }
            }

            response.setData(deviceList);
            response.setTestedCount(tested);
            response.setBoxPackedCount(boxPacked);
            response.setDevicePackedCount(devicePacked);
            response.setNumberOfElements(page.getNumberOfElements());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
            response.setTotalDevice(count);
            response.setDeviceIssuedCount(deviceIssued);
            response.setTotalOnlineDevice(onlineDeviceCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<?> countNumberOfDeviceCreatedToday() {
        Response<Integer> response = new Response<>();
        try {
            System.out.println("************************************************************************************");
            System.out.println("*********************************DAILY PRODUCTION EMAIL*****************************");
            System.out.println("************************************************************************************");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = new Date();
            String dateString = dateFormat.format(today);
            List<Device> deviceExist = deviceRepository.findByCreatedAt(dateString);
            if (deviceExist != null && !deviceExist.isEmpty()) {
                Configuration config = configRepo.findByKey("daily_production_email");
                String[] stringArray = config.getValue().split("\\s*,\\s*");
                String message = "Thanks & Regards";
                String endBody = "Watsoo Care";
                Integer count = deviceExist.size();
                sendEmail("<html lang=\"en\">\n" + "<head>\n" + "  <title>Watsoo VTS</title>\n"
                                + "  <meta charset=\"UTF-8\">\n" + "  <meta name=\"description\" content=\"html documents\">\n"
                                + "  <meta name=\"keywords\" content=\"Emailer\">\n"
                                + "  <meta name=\"author\" content=\"watsoo vts\">\n"
                                + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                                + "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n"
                                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0 \" />\n"
                                + "</head>\n" + "\n"
                                + "<body style=\"padding:0px; margin:0px; font-family: Verdana,sans-serif;\">\n"
                                + "  <div style=\"margin:0px auto; border:1px solid #ccc;\">\n"
                                + "    <div style=\"padding:15px;\">\n" + "      <div style=\"width:100%;\">\n"
                                + "          <p style=\"margin-bottom:15px; font-size:14px;\">Hello,</p>\n"
                                + "          <p style=\"margin-bottom:15px; font-size:14px;\">Today production count is "
                                + count + "" + "</p>\n" + "          <p style=\"margin-bottom:15px; font-size:14px;\">"
                                + message + "" + "</p>\n" + "          <p style=\"margin-bottom:15px; font-size:14px;\">"
                                + endBody + "" + "</p>\n" + "        </div>\n" + "      <div style=\"clear:both;\"></div>\n"
                                + "    </div>\n" + "\n" + "  </div>\n" + "</body>\n" + "</html>\n" + "\n" + "", stringArray,
                        deviceExist.size(), dateString.split(" ")[0]);
                response.setData(deviceExist.size());
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setResponseCode(HttpStatus.OK.value());
                return response;
            } else {
                response.setData(0);
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public void sendEmail(String content, String[] toEmailList, Integer noOfDevices, String today) {
        try {
            SendEmailRequest newEmail = new SendEmailRequest();
            newEmail.setHost(host);
            newEmail.setPort(port);
            newEmail.setSenderEmail(username);
            newEmail.setToEmailIds(toEmailList);
            newEmail.setSubject("Production count on " + today + " : " + noOfDevices + " devices");
            newEmail.setContent(content);
            newEmail.setSenderPassword(password);
            emailService.sendEmailV1(newEmail);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public Response<List<Device>> updateDeviceStatus(GenericRequestBody requestBody) {
        List<Device> imeiExist = deviceRepository.findAllById(requestBody.getIds());
        Response<List<Device>> response = new Response<>();
        if (imeiExist != null && imeiExist.size() > 0) {
            for (Device device : imeiExist) {
                device.setStatus(requestBody.getStatusMaster());
                device.setUpdatedAt(new Date());
                device.setModifiedBy(requestBody.getUserId());
            }
            imeiExist = deviceRepository.saveAll(imeiExist);
            response.setData(imeiExist);
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setResponseCode(HttpStatus.OK.value());
        } else {
            response.setData(null);
            response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
        }
        return response;
    }

    @Override
    public Response<Device> updateDeviceState(GenericRequestBody requestBody) {
        Response<Device> response = new Response<>();
        if (requestBody.getDeviceId() != null && requestBody.getDeviceId() > 0 && requestBody.getStateId() != null
                && requestBody.getStateId() > 0) {
            Optional<Device> deviceExist = deviceRepository.findById(requestBody.getDeviceId());
            if (deviceExist.isPresent()) {
                Optional<State> stateExist = stateRepository.findById(requestBody.getStateId());
                if (stateExist.isPresent()) {
                    deviceExist.get().setState(stateExist.get());
                    Device deviceObj = deviceRepository.save(deviceExist.get());
                    response.setData(deviceObj);
                    response.setMessage(HttpStatus.OK.getReasonPhrase());
                    response.setResponseCode(HttpStatus.OK.value());
                } else {
                    response.setData(null);
                    response.setMessage("State not found");
                    response.setResponseCode(HttpStatus.NOT_FOUND.value());
                }

            } else {
                response.setData(null);
                response.setMessage("Device not found");
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        } else {
            response.setData(null);
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        }
        return response;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public Response<GenericRequestBody> getAllUniqueSoftwareVersion() {
        Response<GenericRequestBody> response = new Response<>();
        try {
            List<Device> deviceList = deviceRepository.findAll();
            if (deviceList != null && !deviceList.isEmpty()) {

                List<String> uniqueSoftwareVersionList = deviceList.stream()
                        .filter(x -> x != null && x.getSoftwareVersion() != null && !x.getSoftwareVersion().isEmpty())
                        .filter(distinctByKey(Device::getSoftwareVersion)).map(Device::getSoftwareVersion)
                        .collect(Collectors.toList());

                GenericRequestBody resp = new GenericRequestBody();

                resp.setSoftwareVersionList(uniqueSoftwareVersionList);
                response.setData(resp);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setResponseCode(HttpStatus.OK.value());
            } else {
                response.setData(null);
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setData(null);
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Pagination<List<DeviceLite>> getAllDeviceV3(GenericRequestBody requestDTO) {
        Pagination<List<DeviceLite>> response = new Pagination<>();
        List<DeviceLite> deviceList = new ArrayList<>();
        List<DeviceLite> updatedDeviceList = new ArrayList<>();
        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("updatedAt").descending());
            }
            Page<DeviceLite> page = deviceLiteRepository.findAllV2(requestDTO, pageRequest);
            List<Long> userIds = page.getContent().stream().filter(o -> o.getModifiedBy() != null)
                    .map(o -> o.getModifiedBy()).distinct().collect(Collectors.toList());
            List<Long> clientIds = page.getContent().stream().filter(o -> o.getClientId() != null)
                    .map(o -> o.getClientId()).distinct().collect(Collectors.toList());
            List<Client> clientList = new ArrayList<>();
            Map<Long, Client> clientMap = new HashMap<>();
            if (clientIds != null && !clientIds.isEmpty()) {
                clientList = clientRepository.findAllById(clientIds);
                if (clientList != null && !clientList.isEmpty()) {
                    clientMap = clientList.stream().filter(x -> x != null && x.getId() != null)
                            .collect(Collectors.toMap(Client::getId, Function.identity()));
                }
            }
            List<CredentialMaster> userList = credentialMstrRepo.findAllById(userIds);
            Map<Object, List<CredentialMaster>> userMap = userList.stream().filter(o -> o.getId() != null)
                    .collect(Collectors.groupingBy(o -> o.getId()));

            for (DeviceLite device : page.getContent()) {
                CredentialMaster lastWorkedUser = userMap.get(device.getModifiedBy()) != null
                        && userMap.get(device.getModifiedBy()).size() > 0 ? userMap.get(device.getModifiedBy()).get(0)
                        : null;
                if (lastWorkedUser != null) {
                    device.setCreatedAt(DateUtil.addMinutesToJavaUtilDate(device.getCreatedAt(), 330));
                    device.setUpdatedAt(DateUtil.addMinutesToJavaUtilDate(device.getUpdatedAt(), 330));
                    device.setUserName(lastWorkedUser.getName());
                    device.setUserEmail(lastWorkedUser.getEmail());
                }
                if (device.getClientId() != null && clientMap != null && !clientMap.isEmpty()
                        && clientMap.containsKey(device.getClientId())) {
                    device.setClientName(clientMap.get(device.getClientId()).getCompanyName());
                }
                deviceList.add(device);
            }
            List<DeviceLite> deviceList1 = deviceLiteRepository.findAll();
            long tested = deviceList1.stream()
                    .filter(o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name()))
                    .count();
            long boxPacked = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name()))
                    .count();
            long devicePacked = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name()))
                    .count();
            long deviceIssued = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.ISSUED_DEVICES.name()))
                    .count();
            long count = deviceList1.stream().count();
            deviceList = deviceList.stream().filter(o -> o.getUpdatedAt() != null)
                    .sorted(Comparator.comparing(DeviceLite::getUpdatedAt).reversed()).collect(Collectors.toList());
            //
            for (DeviceLite deviceLite : deviceList) {
                if (deviceLite.getStatus().equals(StatusMaster.BOX_PACKED)) {
                    // get box name by device Id
                    String boxNo = boxRepository.findBoxNoByDeviceId(deviceLite.getId());
                    deviceLite.setBoxNo(StatusMaster.BOX_PACKED + "(" + boxNo + ")");
                    updatedDeviceList.add(deviceLite);
                } else {
                    deviceLite.setBoxNo(deviceLite.getStatus().toString());
                    updatedDeviceList.add(deviceLite);
                }

            }
            response.setData(updatedDeviceList);
            //
            // response.setData(deviceList);
            response.setTestedCount(tested);
            response.setBoxPackedCount(boxPacked);
            response.setDevicePackedCount(devicePacked);
            response.setNumberOfElements(page.getNumberOfElements());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
            response.setTotalDevice(count);
            response.setDeviceIssuedCount(deviceIssued);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<?> getCurrentVersion() {
        Response<Object> response = new Response<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = aisCommandUrl + "api/getCurrentVersion";
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();
            response.setData(responseBody);
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setResponseCode(HttpStatus.OK.value());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<?> updateSoftwareVersion(ConfigurationDTO dto) {
        Response<Object> response = new Response<>();

        if (dto != null && dto.getSoftwareVersion() != null && dto.getSoftwareVersion() != "") {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = aisCommandUrl + "api/updateSoftwareVersion";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                Map<String, Object> map = new HashMap<>();
                map.put("softwareVersion", dto.getSoftwareVersion());
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
                int statusCode = responseEntity.getStatusCodeValue();
                if (statusCode == 200) {
                    response.setData(null);
                    response.setMessage(HttpStatus.OK.getReasonPhrase());
                    response.setResponseCode(HttpStatus.OK.value());
                } else {
                    response.setData(null);
                    response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                    response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setData(null);
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        }
        return response;
    }

    @Override
    public List<String> getAllSimOperator() {

        List<Device> deviceList = deviceRepository.findAll();
        if (deviceList.isEmpty()) {
            return null;
        } else {
            List<String> sim1Operator = deviceList.stream().filter(e -> e.getSim1Operator() != null)
                    .map(e -> e.getSim1Operator()).collect(Collectors.toList());

            List<String> sim2Operator = deviceList.stream().filter(e -> e.getSim2Operator() != null)
                    .map(e -> e.getSim2Operator()).collect(Collectors.toList());

            List<String> allOperators = Stream.concat(sim1Operator.stream(), sim2Operator.stream()).distinct()
                    .collect(Collectors.toList());

            return allOperators;
        }
    }

    @Override
    public Response<List<Device>> getAisDevice(String imei) {
        Response<List<Device>> response = new Response<>();
        List<Device> deviceExist = deviceRepository.findByLikeImeiNo(imei);
        if (deviceExist != null && deviceExist.size() > 0) {
            response.setData(deviceExist);
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setResponseCode(HttpStatus.OK.value());
            return response;
        } else {
            response.setData(null);
            response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }

    @Override
    public List<String> getAllESimProvider() {

        List<Device> deviceList = deviceRepository.findAll();
        if (deviceList.isEmpty()) {
            return null;
        } else {
            List<String> eSimProvider = deviceList.stream()
                    .filter(e -> e.getSim1Provider() != null && e.getSim1Provider() != "")
                    .map(e -> e.getSim1Provider().toUpperCase()).distinct().collect(Collectors.toList());

            // List<String> sim2Operator = deviceList.stream().filter(e ->
            // e.getSim2Operator() != null)
            // .map(e -> e.getSim2Operator()).collect(Collectors.toList());
            //
            // List<String> allOperators = Stream.concat(sim1Operator.stream(),
            // sim2Operator.stream()).distinct()
            // .collect(Collectors.toList());

            return eSimProvider;
        }
    }

    @Override
    public Pagination<List<DeviceLite>> getAllDeviceByImei(GenericRequestBody requestDTO) {
        Pagination<List<DeviceLite>> response = new Pagination<>();
        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("updatedAt").descending());
            }
            Page<DeviceLite> page = deviceLiteRepository.findAllV3(requestDTO, pageRequest);
            if (page.hasContent()) {
                response.setData(page.getContent());
                response.setNumberOfElements(page.getNumberOfElements());
                response.setTotalElements(page.getTotalElements());
                response.setTotalPages(page.getTotalPages());
            } else {
                response.setData(null);
                // response.setNumberOfElements(page.getNumberOfElements());
                // response.setTotalElements(page.getTotalElements());
                // response.setTotalPages(page.getTotalPages());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<GenericRequestBody> getAllUniqueSoftwareVersionV2() {
        Response<GenericRequestBody> response = new Response<>();
        try {
            List<Device> deviceList = deviceRepository.findAll();
            if (deviceList != null && !deviceList.isEmpty()) {

                List<String> uniqueSoftwareVersionList = deviceList.stream()
                        .filter(x -> x != null && x.getSoftwareVersion() != null && !x.getSoftwareVersion().isEmpty())
                        .filter(distinctByKey(Device::getSoftwareVersion)).map(Device::getSoftwareVersion)
                        .collect(Collectors.toList());

                List<String> softwareVersionList = new ArrayList<>();
                // for (String version : uniqueSoftwareVersionList) {
                // String pattern = "(\\d+_\\d+_\\d+_\\d+_\\d+).*";
                // Pattern r = Pattern.compile(pattern);
                // Matcher m = r.matcher(version);
                // if (m.find()) {
                // String extractedSubstring = m.group(1);
                // softwareVersionList.add(extractedSubstring);
                // }
                // }
                // for (String version : uniqueSoftwareVersionList) {
                // String pattern = "^(.*?\\d+_\\d+_\\d+_\\d+_\\d+).*";
                // Pattern r = Pattern.compile(pattern);
                // Matcher m = r.matcher(version);
                // if (m.find()) {
                // String extractedSubstring = m.group(1);
                // if (extractedSubstring.length() < version.length()
                // && version.charAt(extractedSubstring.length()) == '.') {
                // extractedSubstring += '.';
                // }
                // softwareVersionList.add(extractedSubstring);
                // }
                // }
                for (String version : uniqueSoftwareVersionList) {
                    if (version.contains("_")) {
                        String[] parts = version.split("_");
                        if (parts.length >= 5) {
                            String extractedSubstring = String.join("_", Arrays.copyOf(parts, 5));
                            softwareVersionList.add(extractedSubstring);
                        }
                    }
                }

                List<String> uniqueVersion = softwareVersionList.stream().distinct().collect(Collectors.toList());
                GenericRequestBody resp = new GenericRequestBody();

                resp.setSoftwareVersionList(uniqueVersion);
                response.setData(resp);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setResponseCode(HttpStatus.OK.value());
            } else {
                response.setData(null);
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setData(null);
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Response<List<DeviceLite>> getAllDeviceByListOfImei(GenericRequestBody requestDTO) {
        Response<List<DeviceLite>> response = new Response<List<DeviceLite>>();
        if (requestDTO.getImeiNoList() != null && !requestDTO.getImeiNoList().isEmpty()) {
            try {
                List<DeviceLite> deviceLites = deviceLiteRepository.findAllByImeiNoIn(requestDTO.getImeiNoList());
                if (deviceLites != null && !deviceLites.isEmpty()) {
                    response.setData(deviceLites);
                    response.setMessage(HttpStatus.OK.getReasonPhrase());
                    response.setResponseCode(HttpStatus.OK.value());
                } else {
                    response.setData(null);
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setResponseCode(HttpStatus.NOT_FOUND.value());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setData(null);
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
        }

        return response;
    }

    public Response<GenericRequestBody> getAllUniqueSoftwareVersionV3() {
        Response<GenericRequestBody> response = new Response<>();
        try {
            List<Device> deviceList = deviceRepository.findAll();
            if (deviceList != null && !deviceList.isEmpty()) {

                List<String> uniqueSoftwareVersionList = deviceList.stream()
                        .filter(x -> x != null && x.getSoftwareVersion() != null && !x.getSoftwareVersion().isEmpty())
                        .filter(distinctByKey(Device::getSoftwareVersion)).map(Device::getSoftwareVersion)
                        .collect(Collectors.toList());

                List<String> softwareVersionList = new ArrayList<>();

                // for (String version : uniqueSoftwareVersionList) {
                // String pattern = "^(.*?\\d+_\\d+_\\d+_\\d+_\\d+).*";
                // Pattern r = Pattern.compile(pattern);
                // Matcher m = r.matcher(version);
                // if (m.find()) {
                // String extractedSubstring = m.group(1);
                // if (extractedSubstring.length() < version.length() &&
                // version.charAt(extractedSubstring.length()) == '.') {
                // extractedSubstring += '.';
                // }
                // softwareVersionList.add(extractedSubstring);
                // }
                // }
                for (String version : uniqueSoftwareVersionList) {
                    if (version.contains("_")) {
                        String[] parts = version.split("_");
                        if (parts.length >= 5) {
                            String extractedSubstring = String.join("_", Arrays.copyOf(parts, 5));
                            softwareVersionList.add(extractedSubstring);
                        }
                    }
                }

                List<String> uniqueVersion = softwareVersionList.stream().distinct().collect(Collectors.toList());
                GenericRequestBody resp = new GenericRequestBody();

                resp.setSoftwareVersionList(uniqueVersion);
                response.setData(resp);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setResponseCode(HttpStatus.OK.value());
            } else {
                response.setData(null);
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setData(null);
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public List<Device> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices;
    }

    @Override
    public Device saveDeviceDataV2(Device deviceDTO) {
        try {
            State state = stateRepository.findbyReferenceKey(Constant.OTHER_STATE);
            Optional<Device> imeiExist = deviceRepository.findByImeiNo(deviceDTO.getImeiNo());
            Optional<Device> iccidExist = deviceRepository.findByIccidNo(deviceDTO.getIccidNo());
            Optional<IccidMaster> iccidMasterExist = iccidMasterRepository.findByIccidNo(deviceDTO.getIccidNo());
            if (imeiExist.isPresent() && !iccidExist.isPresent()) {
                if (deviceDTO.getIccidNo() != null && (imeiExist.get().getIccidNo() != null
                        && !(deviceDTO.getIccidNo().equalsIgnoreCase(imeiExist.get().getIccidNo())))) {
                    imeiExist.get().setOldIccid(imeiExist.get().getIccidNo());
                    imeiExist.get().setIccidUpdatedAt(new Date());
                    imeiExist.get().setIccidNo(deviceDTO.getIccidNo());
                }
                if (deviceDTO.getSoftwareVersion() != null) {
                    imeiExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
                }
                if (deviceDTO.getDetail() != null) {
                    imeiExist.get().setDetail(deviceDTO.getDetail());
                }

                String requestBody = new Gson().toJson(deviceDTO);
                imeiExist.get().setUpdatedAt(new Date());
                imeiExist.get().setModifiedBy(deviceDTO.getUserId());
                imeiExist.get().setRequestBody(requestBody);
                imeiExist.get().setStatus(StatusMaster.TESTED);
                if (state != null) {
                    imeiExist.get().setState(state);
                }
                Device deviceObj = deviceRepository.save(imeiExist.get());
                return deviceObj;
            } else if (iccidExist.isPresent() && !imeiExist.isPresent()) {
                if (deviceDTO.getImeiNo() != null && (iccidExist.get().getImeiNo() != null
                        && !(deviceDTO.getImeiNo().equalsIgnoreCase(iccidExist.get().getImeiNo())))) {
                    iccidExist.get().setOldImei(iccidExist.get().getImeiNo());
                    iccidExist.get().setImeiUpdatedAt(new Date());
                    iccidExist.get().setImeiNo(deviceDTO.getImeiNo());
                }
                if (deviceDTO.getSoftwareVersion() != null) {
                    iccidExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
                }
                if (deviceDTO.getDetail() != null) {
                    iccidExist.get().setDetail(deviceDTO.getDetail());
                }

                String requestBody = new Gson().toJson(deviceDTO);
                iccidExist.get().setUpdatedAt(new Date());
                iccidExist.get().setModifiedBy(deviceDTO.getUserId());
                iccidExist.get().setRequestBody(requestBody);
                iccidExist.get().setStatus(StatusMaster.TESTED);
                if (state != null) {
                    iccidExist.get().setState(state);
                }
                Device deviceObj = deviceRepository.save(iccidExist.get());
                return deviceObj;
            } else if (imeiExist.isPresent() && iccidExist.isPresent()) {
                if (imeiExist.get().getId() != null && iccidExist.get().getId() != null
                        && imeiExist.get().getId().intValue() == iccidExist.get().getId().intValue()) {
                    if (deviceDTO.getSoftwareVersion() != null) {
                        imeiExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
                    }
                    if (deviceDTO.getDetail() != null) {
                        imeiExist.get().setDetail(deviceDTO.getDetail());
                    }
                    String requestBody = new Gson().toJson(deviceDTO);
                    imeiExist.get().setUpdatedAt(new Date());
                    imeiExist.get().setModifiedBy(deviceDTO.getUserId());
                    imeiExist.get().setRequestBody(requestBody);
                    imeiExist.get().setStatus(StatusMaster.TESTED);
                    if (state != null) {
                        imeiExist.get().setState(state);
                    }
                    Device deviceObj = deviceRepository.save(imeiExist.get());
                    return deviceObj;
                } else {
                    return null;
                }
            } else {
                Device device = new Device();
                if (deviceDTO.getImeiNo() != null) {
                    device.setImeiNo(deviceDTO.getImeiNo());
                } else {
                    throw new RuntimeException("Imei no can't be null");
                }
                if (deviceDTO.getIccidNo() != null) {
                    device.setIccidNo(deviceDTO.getIccidNo());
                    if (iccidMasterExist.isPresent()) {
                        IccidMaster iccidMaster = iccidMasterExist.get();
                        device.setSim1Provider(iccidMaster.getProvider());
                        device.setSim2Provider(iccidMaster.getProvider());
                        device.setSim1ActivationDate(iccidMaster.getSimActivationDate());
                        device.setSim2ActivationDate(iccidMaster.getSimActivationDate());
                        device.setSim1ExpiryDate(iccidMaster.getSimExpiryDate());
                        device.setSim2ExpiryDate(iccidMaster.getSimExpiryDate());
                        device.setSim1Number(iccidMaster.getSim1());
                        device.setSim2Number(iccidMaster.getSim2());
                        device.setSim1Operator(iccidMaster.getSim1Operator());
                        device.setSim2Operator(iccidMaster.getSim2Operator());
                    }
                } else {
                    throw new RuntimeException("Iccid no can't be null");
                }
                if (deviceDTO.getSoftwareVersion() != null) {
                    device.setSoftwareVersion(deviceDTO.getSoftwareVersion());
                }
                if (deviceDTO.getDetail() != null) {
                    device.setDetail(deviceDTO.getDetail());
                }

                device.setUuidNo(getUuidNo(deviceDTO.getImeiNo()));
                String requestBody = new Gson().toJson(deviceDTO);
                device.setCreatedAt(new Date());
                device.setUpdatedAt(new Date());
                device.setCreatedBy(new User(deviceDTO.getUserId()));
                device.setModifiedBy(deviceDTO.getUserId());
                device.setRequestBody(requestBody);
                device.setStatus(StatusMaster.TESTED);
                device.setState(state);
                Device deviceObj = deviceRepository.save(device);
                // add in ais-admin
                // v2/vehicle/add
                // Boolean isAdded = addDeviceInAisAdmin(deviceObj);
                if (addDeviceInAisAdmin(deviceObj)) {
                    System.out.println("device added in ais admin successfully");
                }
                return deviceObj;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Boolean addDeviceInAisAdmin(Device device) {
        Boolean res = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("imeiNo", device.getImeiNo());
        map.add("createdAt", new Date());
        map.add("updatedAt", new Date());
        map.add("companyId", 1l);
        map.add("isActive", true);
        map.add("chassisNumber", device.getImeiNo() + "CH");
        map.add("engineNumber", device.getImeiNo() + "EN");
        map.add("number", device.getImeiNo());
        map.add("userId", 59l);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(addDeviceUrl, HttpMethod.POST, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = response.getBody();
        try {
            AddDeviceResponse resp = objectMapper.readValue(jsonResponse, AddDeviceResponse.class);
            if (resp != null && resp.getResponseCode() != null && resp.getResponseCode().equals(201)) {
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    @Override
    public Pagination<List<DeviceLite>> getAllDeviceV4(GenericRequestBody requestDTO) {
        Pagination<List<DeviceLite>> response = new Pagination<>();
        List<DeviceLite> deviceList = new ArrayList<>();

        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("packedDate").descending());
            }

            Page<DeviceLite> page = deviceLiteRepository.findAllV2(requestDTO, pageRequest);

            List<Long> userIds = page.getContent().stream().map(DeviceLite::getModifiedBy).filter(Objects::nonNull)
                    .distinct().collect(Collectors.toList());

            List<Long> clientIds = page.getContent().stream().map(DeviceLite::getClientId).filter(Objects::nonNull)
                    .distinct().collect(Collectors.toList());

            Map<Long, Client> clientMap = clientRepository.findAllById(clientIds).stream()
                    .collect(Collectors.toMap(Client::getId, Function.identity()));

            Map<Long, List<CredentialMaster>> userMap = credentialMstrRepo.findAllById(userIds).stream()
                    .filter(o -> o.getId() != null).collect(Collectors.groupingBy(o -> o.getId()));

            for (DeviceLite device : page.getContent()) {
                CredentialMaster lastWorkedUser = userMap.getOrDefault(device.getModifiedBy(), Collections.emptyList())
                        .stream().findFirst().orElse(null);

                if (lastWorkedUser != null) {
                    device.setCreatedAt(DateUtil.addMinutesToJavaUtilDate(device.getCreatedAt(), 330));
                    device.setUpdatedAt(DateUtil.addMinutesToJavaUtilDate(device.getUpdatedAt(), 330));
                    device.setUserName(lastWorkedUser.getName());
                    device.setUserEmail(lastWorkedUser.getEmail());
                }

                device.setClientName(clientMap.getOrDefault(device.getClientId(), new Client()).getCompanyName());

                if (device.getStatus().equals(StatusMaster.BOX_PACKED)) {
                    device.setBoxNo(StatusMaster.BOX_PACKED + "(" + device.getBoxCode() + ")");
                } else {
                    device.setBoxNo(device.getStatus().toString());
                }

                device.setPackedDate(device.getPackedDate() != null
                        ? DateUtil.addMinutesToJavaUtilDate(device.getPackedDate(), Constant.ADD_IST_TIME)
                        : null);
                deviceList.add(device);
            }

            LocalDate currentDate = LocalDate.now();
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime midnightToday = LocalDateTime.of(currentDate, midnight);
            Date dateMidnightToday = Date.from(midnightToday.atZone(ZoneId.systemDefault()).toInstant());

            List<DeviceLiteV2> deviceList1 = deviceLiteV2Repository.findAll();

            long tested = deviceList1.stream()
                    .filter(o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name()))
                    .count();
            long boxPacked = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name()))
                    .count();
            long devicePacked = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name()))
                    .count();
            long deviceIssued = deviceList1.stream().filter(
                            o -> o.getId() != null && o.getStatus().name().equalsIgnoreCase(StatusMaster.ISSUED_DEVICES.name()))
                    .count();

            List<DeviceLiteV2> onlineDevices = deviceList1.stream()
                    .filter(o -> o.getId() != null && o.getPackedDate() != null
                            && o.getPackedDate().after(dateMidnightToday) && o.getPackedDate().before(new Date()))
                    .collect(Collectors.toList());

            response.setData(deviceList);
            response.setTestedCount(tested);
            response.setBoxPackedCount(boxPacked);
            response.setDevicePackedCount(devicePacked);
            response.setNumberOfElements(page.getNumberOfElements());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
            response.setTotalDevice((long) deviceList1.size());
            response.setDeviceIssuedCount(deviceIssued);
            response.setTotalOnlineDevice((long) onlineDevices.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void generateExcelAndNotify(GenericRequestBody requestDTO) throws IOException {
        List<DeviceLite> deviceResponse = fetchDeviceDate(requestDTO);
        Workbook workbook = generateExcelDeviceResponse(deviceResponse);
        String excelUrl = convertMultipartToUrl(workbook);
        // send mail
        Date date = new Date();
        if (requestDTO.getNotifyEmailId() != null && requestDTO.getNotifyEmailId() != "") {
            emailServiceVM.sendDeviceActRenSupportMail(requestDTO.getNotifyEmailId(),
                    DateUtil.localDateTimeToStringInFormat(
                            DateUtil.addMinutesToJavaUtilDate(date, Constant.IST_OFFSET_IN_MINUTES),
                            Constant.DATE_FORMAT_DD_MM_YYYY),
                    excelUrl);
        }
    }

    private Workbook generateExcelDeviceResponse(List<DeviceLite> deviceResponse) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Device Data");
        List<String> headers = Arrays.asList("SN", "Updated At", "Created By", "IMEI Number", "ICCID Number",
                "UUID Number", "Software Version", "Status", "State", "Client Name", "Sim Provider",
                "Sim Activation Date", "Expiry Date", "Sim1 Operator", "Sim2 Operator", "Sim1 Number", "Sim2 Number",
                "Issue Date", "Packet Date", "Packet", "Details");
        Row headerRow = sheet.createRow(0);
        IntStream.range(0, headers.size()).forEach(i -> {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        });
        for (int i = 0; i < deviceResponse.size(); i++) {
            Row row = sheet.createRow(i + 1);
            DeviceLite data = deviceResponse.get(i);
            row.createCell(0).setCellValue(i + 1);
            if (data.getUpdatedAt() != null) {
                row.createCell(1).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
                        DateUtil.addMinutesToJavaUtilDate(data.getUpdatedAt(), Constant.IST_OFFSET_IN_MINUTES)));
            }

            row.createCell(2).setCellValue((data.getCreatedBy() != null) ? data.getCreatedBy().getName() : null);
            row.createCell(3).setCellValue(data.getImeiNo());
            row.createCell(4).setCellValue(data.getIccidNo());
            row.createCell(5).setCellValue(data.getUuidNo());
            row.createCell(6).setCellValue(data.getSoftwareVersion());
            row.createCell(7).setCellValue((data.getStatus() != null) ? data.getStatus().name() : null);
            row.createCell(8).setCellValue((data.getState() != null) ? data.getState().getName() : null);
            row.createCell(9).setCellValue(data.getClientName());
            row.createCell(10).setCellValue(data.getSim1Provider());
            if (data.getSim1ActivationDate() != null) {
                row.createCell(11).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(DateUtil
                        .addMinutesToJavaUtilDate(data.getSim1ActivationDate(), Constant.IST_OFFSET_IN_MINUTES)));
            }
            if (data.getSim1ExpiryDate() != null) {
                row.createCell(12).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
                        DateUtil.addMinutesToJavaUtilDate(data.getSim1ExpiryDate(), Constant.IST_OFFSET_IN_MINUTES)));
            }

            row.createCell(13).setCellValue(data.getSim1Operator());
            row.createCell(14).setCellValue(data.getSim2Operator());
            row.createCell(15).setCellValue(data.getSim1Number());
            row.createCell(16).setCellValue(data.getSim2Number());
            if (data.getIssueDate() != null) {
                row.createCell(17).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
                        DateUtil.addMinutesToJavaUtilDate(data.getIssueDate(), Constant.IST_OFFSET_IN_MINUTES)));
            }
            if (data.getPackedDate() != null) {
                row.createCell(18).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
                        DateUtil.addMinutesToJavaUtilDate(data.getPackedDate(), Constant.IST_OFFSET_IN_MINUTES)));
            }

            row.createCell(19).setCellValue(data.getPacket());

            row.createCell(20).setCellValue(data.getDetail());
        }
        // workbook.write(outputStream);
        // workbook.close();

        return workbook;
    }

    private List<DeviceLite> fetchDeviceDate(GenericRequestBody requestDTO) {
        List<DeviceLite> deviceResponse = new ArrayList<>();
        List<DeviceLite> devices = new ArrayList<>();
        try {
            devices = deviceLiteRepository.findAllBySearch(requestDTO);
            List<Long> userIds = devices.stream().filter(o -> o.getModifiedBy() != null).map(o -> o.getModifiedBy())
                    .distinct().collect(Collectors.toList());
            List<Long> clientIds = devices.stream().filter(o -> o.getClientId() != null).map(o -> o.getClientId())
                    .distinct().collect(Collectors.toList());
            List<ClientLiteV2> clientList = new ArrayList<>();
            Map<Long, ClientLiteV2> clientMap = new HashMap<>();
            if (clientIds != null && !clientIds.isEmpty()) {
                clientList = clientLiteRepository.findAllById(clientIds);
                if (clientList != null && !clientList.isEmpty()) {
                    clientMap = clientList.stream().filter(x -> x != null && x.getId() != null)
                            .collect(Collectors.toMap(ClientLiteV2::getId, Function.identity()));
                }
            }
            List<UserLiteV2> userList = userLiteRepo.findAllById(userIds);
            Map<Long, UserLiteV2> userMap = userList.stream().filter(o -> o.getId() != null)
                    .collect(Collectors.toMap(UserLiteV2::getId, Function.identity()));

            for (DeviceLite device : devices) {
                UserLiteV2 lastWorkedUser = userMap.get(device.getModifiedBy()) != null
                        ? userMap.get(device.getModifiedBy())
                        : null;
                if (lastWorkedUser != null) {
                    device.setCreatedAt(DateUtil.addMinutesToJavaUtilDate(device.getCreatedAt(), 330));
                    device.setUpdatedAt(DateUtil.addMinutesToJavaUtilDate(device.getUpdatedAt(), 330));
                    device.setUserName(lastWorkedUser.getName());
                    device.setUserEmail(lastWorkedUser.getEmail());
                }
                if (device.getClientId() != null && clientMap != null && !clientMap.isEmpty()
                        && clientMap.containsKey(device.getClientId())) {
                    device.setClientName(clientMap.get(device.getClientId()).getCompanyName());
                }
                if (device.getStatus().equals(StatusMaster.BOX_PACKED)) {
                    device.setBoxNo(StatusMaster.BOX_PACKED + "(" + device.getBoxCode() + ")");
                } else {
                    device.setBoxNo(device.getStatus().toString());
                }
                device.setPackedDate(device.getPackedDate() != null
                        ? DateUtil.addMinutesToJavaUtilDate(device.getPackedDate(), Constant.ADD_IST_TIME)
                        : null);
                deviceResponse.add(device);
            }
            deviceResponse = deviceResponse.stream().sorted(
                            Comparator.comparing(DeviceLite::getPackedDate, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceResponse;
    }

    public String convertMultipartToUrl(Workbook workBook) {
        // Convert Workbook to MultipartFile
        String fileUrl = "";
        String filename = "Device.xlsx";
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workBook.write(outputStream);
            InputStream inputStream = new ByteArrayResource(outputStream.toByteArray()).getInputStream();
            MultipartFile multipartFile = new MockMultipartFile(filename, filename, contentType, inputStream);
            System.out.println("MultipartFile created successfully.");
            fileUrl = fileSystemStorageService.getFileUrl(multipartFile);
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean updateDeviceActivationExpiryDate(List<VehicleLazyEntity> request) {
        Boolean response = false;
        List<String> imeiList = request.stream()
                .filter(x -> x != null && x.getImeiNo() != null && !x.getImeiNo().isEmpty())
                .map(VehicleLazyEntity::getImeiNo).collect(Collectors.toList());
        if (imeiList != null) {
            List<Device> devices = deviceRepository.findAllByImeiNo(imeiList);

            Map<String, Device> imeiDeviceMap = devices.stream()
                    .filter(x -> x != null && x.getId() != null && x.getImeiNo() != null && x.getImeiNo() != "")
                    .collect(Collectors.toMap(Device::getImeiNo, Function.identity()));
            List<Device> deviceToUpdate = new ArrayList<>();
            for (VehicleLazyEntity dto : request) {
                Device device = imeiDeviceMap.get(dto.getImeiNo());
                if (device != null) {
                    if (dto.getIsActivationType()) {
                        device.setSim1ActivationDate(dto.getSim1ActivationDate());
                        device.setSim2ActivationDate(dto.getSim2ActivationDate());
                        device.setSim1ExpiryDate(dto.getSim1ExpiryDate());
                        device.setSim2ExpiryDate(dto.getSim2ExpiryDate());
                        device.setAppExpiryDate(dto.getAppExpiryDate());
                        deviceToUpdate.add(device);
                    } else {
                        device.setSim1ExpiryDate(dto.getSim1ExpiryDate());
                        device.setSim2ExpiryDate(dto.getSim2ExpiryDate());
                        device.setAppExpiryDate(dto.getAppExpiryDate());
                        deviceToUpdate.add(device);
                    }
                }
            }
            deviceRepository.saveAll(deviceToUpdate);
            response = true;
        }
        return response;
    }

    @Override
    public Pagination<List<DeviceLiteV3>> getAllDevicePackedDevice(GenericRequestBody requestDTO) {
        Pagination<List<DeviceLiteV3>> response = new Pagination<>();
        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("id").descending());
            }
            Page<DeviceLiteV3> page = deviceLiteV3Repository.findAll(requestDTO, pageRequest);
            response.setData(page.getContent());
            response.setNumberOfElements(page.getNumberOfElements());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private Workbook generateUnConfigureDevicesExcel(List<DeviceConfigMaster> deviceConfigMasterList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("UnSuccess Device Configuration");
        List<String> headers = Arrays.asList("SN", "IMEI Number", "Retry Count", "Last Retry At");
        Row headerRow = sheet.createRow(0);
        IntStream.range(0, headers.size()).forEach(i -> {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        });
        for (int i = 0; i < deviceConfigMasterList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            DeviceConfigMaster data = deviceConfigMasterList.get(i);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(data.getImeiNo());
            row.createCell(2).setCellValue(data.getRetryCount());
            if (data.getLastCommandShootAt() != null) {
                row.createCell(3).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(DateUtil
                        .addMinutesToJavaUtilDate(data.getLastCommandShootAt(), Constant.IST_OFFSET_IN_MINUTES)));
            }
        }
        return workbook;
    }

    @Override
    public void generateUnConfigureDevicesExcelAndNotify(GenericRequestBody requestDTO) throws Exception {
        List<DeviceConfigMaster> deviceConfigMasterList = deviceConfigMasterRepository
                .findAllUnConfigureDevices(new Date());
        Workbook workbook = generateUnConfigureDevicesExcel(deviceConfigMasterList);
        String excelUrl = convertMultipartToUrl(workbook);
        // send mail
        Configuration config = configRepo.findByKey("DAILY_UNCONFIGUR_DEVICES_MAIL");
        Date date = new Date();
        if (config.getValue() != null && config.getValue() != "") {
            emailServiceVM.sendUnConfigureDevicesExcelMail(config.getValue(),
                    DateUtil.localDateTimeToStringInFormat(
                            DateUtil.addMinutesToJavaUtilDate(date, Constant.IST_OFFSET_IN_MINUTES),
                            Constant.DATE_FORMAT_DD_MM_YYYY),
                    excelUrl);
        }
    }

    @Override
    public Pagination<List<DeviceLite>> getDeviceForReconfigure(GenericRequestBody requestDTO) {
        Pagination<List<DeviceLite>> response = new Pagination<>();
        try {
            Pageable pageRequest = Pageable.unpaged();
            if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
                pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
                        Sort.by("id").descending());
            }
            Page<DeviceLite> page = deviceLiteRepository.findAll(requestDTO, pageRequest);
            response.setData(page.getContent());
            response.setNumberOfElements(page.getNumberOfElements());
            response.setTotalElements(page.getTotalElements());
            response.setTotalPages(page.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}