package com.buhhu8.consumer.mapper;

import com.buhhu8.consumer.config.ObjectMapper;
import com.buhhu8.consumer.model.KafkaEntity;
import org.mapstruct.Mapper;

@Mapper
public interface KafkaRequestToKafkaReadEntity extends ObjectMapper<generated.KafkaRequest, KafkaEntity> {
}
