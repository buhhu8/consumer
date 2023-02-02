package com.buhhu8.consumer.mapper;

import com.buhhu8.consumer.config.ObjectMapper;
import com.buhhu8.consumer.model.KafkaEntity;
import org.mapstruct.Mapper;

@Mapper
public interface KafkaReadEntityToKafkaRequestMapper extends ObjectMapper<KafkaEntity, generated.KafkaRequest> {
}
