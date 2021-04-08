package com.turkcell.poc.converter;

import com.turkcell.poc.entity.Record;
import com.turkcell.poc.model.RecordInputDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordMapper {

  public Record recordInputDtoToRecord(RecordInputDto recordInputDto);

}
