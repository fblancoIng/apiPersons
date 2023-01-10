package com.blanco.crud.responses;

import com.blanco.crud.domain.Status;
import com.blanco.crud.dtos.StatisticsDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StatisticsResponse extends Status {

    private StatisticsDto statisticsDto;

}
