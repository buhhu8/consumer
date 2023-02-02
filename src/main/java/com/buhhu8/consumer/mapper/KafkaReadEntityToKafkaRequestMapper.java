package com.buhhu8.consumer.mapper;

import com.buhhu8.consumer.config.ObjectMapper;
import com.buhhu8.consumer.model.read.KafkaReadEntity;
import com.buhhu8.consumer.model.write.KafkaWriteEntity;
import org.mapstruct.Mapper;

@Mapper
public interface KafkaReadEntityToKafkaRequestMapper extends ObjectMapper<KafkaReadEntity, generated.KafkaRequest> {
}
