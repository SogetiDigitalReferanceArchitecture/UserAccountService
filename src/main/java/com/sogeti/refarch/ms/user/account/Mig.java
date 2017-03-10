package com.sogeti.refarch.ms.user.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Mig {

	@RequestMapping("/")
	public String msg()	{
		return "successfully build....";
	}
}
