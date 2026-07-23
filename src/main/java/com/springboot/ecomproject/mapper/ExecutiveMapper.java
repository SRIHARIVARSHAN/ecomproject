package com.springboot.ecomproject.mapper;

import com.springboot.ecomproject.dto.request.ExecutiveReqDto;
import com.springboot.ecomproject.dto.response.ExecutiveRespDto;
import com.springboot.ecomproject.model.Executive;
import org.springframework.stereotype.Component;

@Component
public class ExecutiveMapper {
    public static Executive convertDtoToEntity(ExecutiveReqDto dto){
        Executive executive = new Executive();
        executive.setName(dto.name());
        executive.setJobTitle(dto.jobTitle());
        return executive;
    }
    public static ExecutiveRespDto convertEntityToDto(Executive executive){
        return new ExecutiveRespDto(
                executive.getId(),
                executive.getName(),
                executive.getJobTitle()
        );
    }
}
