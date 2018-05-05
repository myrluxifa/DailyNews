package com.lvmq.repository.base;



import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T,String> {
	@Override
	Optional<T> findById(String id);
}
