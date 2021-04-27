/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.ldap;

import lombok.Data;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * {@link Person} object stored inside LDAP.
 *
 * @author Mark Paluch
 */
@Entry(base = "ou=people,dc=springframework,dc=org", objectClasses = "inetOrgPerson")
@Data
public class Person {

	private @Id Name id;
	private @DnAttribute(value = "uid", index = 3) String uid;
	private @Attribute(name = "cn") String fullName;
	private @Attribute(name = "sn") String lastname;
	private String userPassword;
}
