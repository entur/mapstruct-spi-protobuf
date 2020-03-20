package no.entur.mapstruct.example.mapper;

/*-
 * #%L
 * protobuf-usage
 * %%
 * Copyright (C) 2018 - 2020 Entur AS and original authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import no.entur.mapstruct.example.protobuf.DepartmentsProtos;
import no.entur.mapstruct.example.protobuf.House;
import no.entur.mapstruct.example.protobuf.TestCase1;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedSourcePolicy = ReportingPolicy.ERROR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCase1Mapper {

    TestCase1Mapper INSTANCE = Mappers.getMapper(TestCase1Mapper.class);

    House map(DepartmentsProtos.HouseDTO house);
    DepartmentsProtos.HouseDTO map(House house);

    TestCase1 map(DepartmentsProtos.TestCase1DTO dto);

    DepartmentsProtos.TestCase1DTO map(TestCase1 value);
}
