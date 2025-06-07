package com.sciencefl.flynn.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateClientRequest {
    private String clientName;
    private List<String> scopes;
}
