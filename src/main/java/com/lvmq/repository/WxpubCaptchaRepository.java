package com.lvmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.WxpubCaptcha;

public interface WxpubCaptchaRepository extends JpaRepository<WxpubCaptcha, String> {

	WxpubCaptcha findTop1ByOpenidOrderByCreateTimeDesc(String string);

}
