package com.lvmq.repository.base;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.lvmq.model.NewsInfo;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T,String> {
	@Override
	Optional<T> findById(String id);

	List<T> findAllByFlag(int i);
	
	
}
