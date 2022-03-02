package com.dvivasva.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash("Account")
@Document("account-kr")
public class Account implements Serializable {

	@Id
	private String id;
	private String number;
	private double availableBalance;

}
