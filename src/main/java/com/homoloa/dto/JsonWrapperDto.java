package com.homoloa.dto;

import com.homoloa.domain.PaerseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonWrapperDto {
    private List<PaerseEntity> data = new ArrayList<>();
}
