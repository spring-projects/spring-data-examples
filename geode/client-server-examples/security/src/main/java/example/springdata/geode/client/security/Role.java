/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.client.security;

import org.apache.geode.security.ResourcePermission;
import org.cp.elements.lang.Identifiable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * The [Role] class is an Abstract Data Type (ADT) modeling a role of a user (e.g. Admin).
 *
 * @author John Blum
 * @see Serializable
 * @see Comparable
 * @see Iterable
 * @see ResourcePermission
 * @see org.cp.elements.lang.Identifiable
 * @since 1.0.0
 */

public class Role implements Comparable<Role>, Identifiable<String>, Iterable<ResourcePermission>, Serializable {

	private String name;
	private HashSet<ResourcePermission> permissions = new HashSet<>();

	public Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return name;
	}

	@Override
	public void setId(String id) {
		throw new UnsupportedOperationException("Operation Not Supported");
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int compareTo(Role other) {
		return name.compareTo(other.name);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Role) {
			if (other == this) {
				return true;
			}

			return name.equals(((Role) other).getName());
		}
		return false;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return 17 * 37 + name.hashCode();
	}

	/**
	 * Determines whether this [Role] has been assigned (granted) the given [permission][ResourcePermission].
	 *
	 * @param permission [ResourcePermission] to evaluate.
	 * @return a boolean value indicating whether this [Role] has been assigned (granted)
	 * the given [permission][ResourcePermission].
	 * @see ResourcePermission
	 */
	public boolean hasPermission(ResourcePermission permission) {
		return this.permissions.contains(permission);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Iterator<ResourcePermission> iterator() {
		return this.permissions.iterator();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Adds (assigns/grants) all given [persmissions][ResourcePermission] to this [Role].
	 *
	 * @param permissions [ResourcePermission]s to assign/grant to this [Role].
	 * @return this [Role].
	 * @see ResourcePermission
	 */
	public Role withPermissions(ResourcePermission... permissions) {
		this.permissions.addAll((Arrays.asList(permissions)));
		return this;
	}

	/**
	 * Adds (assigns/grants) all given [persmissions][ResourcePermission] to this [Role].
	 *
	 * @param permissions [ResourcePermission]s to assign/grant to this [Role].
	 * @return this [Role].
	 * @see ResourcePermission
	 */
	public Role withPersmissions(Iterable<ResourcePermission> permissions) {
		this.permissions.addAll((Collection<? extends ResourcePermission>) permissions);
		return this;
	}

	/**
	 * Factory method used to construct a new instance of [Role] initialized with the given name.
	 *
	 * @param name [String] indicating the name of the new [Role].
	 * @return a new [Role] initialized with the given name.
	 * @see Role
	 */
	public static Role newRole(String name) {
		return new Role(name);
	}

	public HashSet<ResourcePermission> getPermissions() {
		return permissions;
	}
}
