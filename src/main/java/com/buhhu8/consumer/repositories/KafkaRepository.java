package com.buhhu8.consumer.repositories;

import com.buhhu8.consumer.config.annotations.SwitchDataSource;
import com.buhhu8.consumer.model.KafkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KafkaRepository extends JpaRepository<KafkaEntity, String> {

    @Override
    @SwitchDataSource(value = "read")
    List<KafkaEntity> findAll();

    @Override
    @SwitchDataSource(value = "write")
    KafkaEntity save(KafkaEntity entity);
}
