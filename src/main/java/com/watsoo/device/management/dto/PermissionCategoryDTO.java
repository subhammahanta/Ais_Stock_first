package com.watsoo.device.management.dto;

import java.util.ArrayList;
import java.util.List;

public class PermissionCategoryDTO {

    private Long id;

    private String name;

    private boolean isActive;

    private List<PermissionDTO> permissionDTOList = new ArrayList<>();

    public PermissionCategoryDTO(Long id, String name, boolean isActive, List<PermissionDTO> permissionDTOList) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.permissionDTOList = permissionDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<PermissionDTO> getPermissionDTOList() {
        return permissionDTOList;
    }

    public void setPermissionDTOList(List<PermissionDTO> permissionDTOList) {
        this.permissionDTOList = permissionDTOList;
    }
   
}
