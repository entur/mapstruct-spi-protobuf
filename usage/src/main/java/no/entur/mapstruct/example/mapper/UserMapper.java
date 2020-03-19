/**
 * Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 * and/or other contributors as indicated by the @authors tag. See the
 * copyright.txt file in the distribution for a full listing of all
 * contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.entur.mapstruct.example.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import no.entur.mapstruct.example.protobuf.Department;
import no.entur.mapstruct.example.protobuf.MultiNumber;
import no.entur.mapstruct.example.protobuf.Permission;
import no.entur.mapstruct.example.protobuf.Status;
import no.entur.mapstruct.example.protobuf.User;
import no.entur.mapstruct.example.protobuf.UserProtos;
import no.entur.mapstruct.example.protobuf.UserProtos.DepartmentDTO;
import no.entur.mapstruct.example.protobuf.UserProtos.PermissionDTO;
import no.entur.mapstruct.example.protobuf.UserProtos.UserDTO;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedSourcePolicy = ReportingPolicy.ERROR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	default String mapString(String in) {
		if ((null == in) || in.isEmpty()) {
			return null;
		}
		return in;
	}

	default Double mapDouble(Double in) {
		return in;
	}

	default MultiNumber map(UserProtos.MultiNumberDTO number) {
		return new MultiNumber();
	}

	default UserProtos.MultiNumberDTO map(MultiNumber number) {
		return UserProtos.MultiNumberDTO.newBuilder().build();
	}

	UserDTO map(User user);

	User map(UserDTO userDTO);

	Permission map(PermissionDTO permissionDTO);

	PermissionDTO map(Permission perm);

	Status map(UserProtos.EnumStatus permissionDTO);

	UserProtos.EnumStatus map(Status perm);

	Department map(DepartmentDTO departmentDTO);

	DepartmentDTO map(Department department);
}
