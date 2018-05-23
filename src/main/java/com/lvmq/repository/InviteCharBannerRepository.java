package com.lvmq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lvmq.model.InviteCharBanner;

@Repository
public interface InviteCharBannerRepository extends CrudRepository<InviteCharBanner, String> {

}
