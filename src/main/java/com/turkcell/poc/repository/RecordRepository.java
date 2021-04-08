package com.turkcell.poc.repository;

import com.turkcell.poc.entity.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends MongoRepository<Record, String> {

}
