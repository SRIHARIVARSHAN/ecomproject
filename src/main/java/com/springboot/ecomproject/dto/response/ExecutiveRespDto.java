package com.springboot.ecomproject.dto.response;

import com.springboot.ecomproject.enums.JobTitle;

public record ExecutiveRespDto(
        Long id,
        String name,
        JobTitle jobTitle
) {
}
