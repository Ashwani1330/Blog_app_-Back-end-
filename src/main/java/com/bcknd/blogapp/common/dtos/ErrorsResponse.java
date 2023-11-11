package com.bcknd.blogapp.common.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorsResponse {
    private String message;
}
