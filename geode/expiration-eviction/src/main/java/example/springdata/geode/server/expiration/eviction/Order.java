/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.geode.server.expiration.eviction;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.expiration.IdleTimeoutExpiration;
import org.springframework.data.gemfire.expiration.TimeToLiveExpiration;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;

/**
 * Orders object used in the examples
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Data
@IdleTimeoutExpiration(action = "DESTROY", timeout = "1")
@TimeToLiveExpiration(action = "DESTROY", timeout = "2")
@Region("Orders")
public class Order implements Serializable {

	@Id
	private Long id;
	private Long total;
	private Address billingAddress;
	private Address shippingAddress;

	public Order(Long orderId, Long total, Address address) {
		this.id = orderId;
		this.total = total;
		this.billingAddress = address;
		this.shippingAddress = address;
	}
}
