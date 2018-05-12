package com.lvmq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lvmq.model.VideosInfo;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface VideosInfoRepository extends BaseRepository<VideosInfo> {
   List<VideosInfo>	findByFlag(Pageable pageable,int flag);
}
