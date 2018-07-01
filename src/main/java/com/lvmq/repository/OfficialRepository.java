package com.lvmq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.lvmq.model.Official;

@Component
public interface OfficialRepository  extends CrudRepository<Official, String> {

	Official findByType(String type);
}
