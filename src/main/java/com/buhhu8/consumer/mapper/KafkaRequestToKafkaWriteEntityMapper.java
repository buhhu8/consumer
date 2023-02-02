package com.buhhu8.consumer.mapper;

import com.buhhu8.consumer.config.ObjectMapper;
import com.buhhu8.consumer.model.write.KafkaWriteEntity;
import org.mapstruct.Mapper;

@Mapper
public interface KafkaRequestToKafkaWriteEntityMapper extends ObjectMapper<generated.KafkaRequest, KafkaWriteEntity> {

}
