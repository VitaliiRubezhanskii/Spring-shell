package com.homoloa.dto;

import com.homoloa.domain.ParseEntity;
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
    private List<ParseEntity> data = new ArrayList<>();
}
