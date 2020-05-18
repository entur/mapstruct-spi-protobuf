package no.entur.mapstruct.example.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import no.entur.mapstruct.example.protobuf.DepartmentsProtos;
import no.entur.mapstruct.example.protobuf.House;
import no.entur.mapstruct.example.protobuf.TestCase1;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedSourcePolicy = ReportingPolicy.ERROR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCase1Mapper {

	TestCase1Mapper INSTANCE = Mappers.getMapper(TestCase1Mapper.class);

	House map(DepartmentsProtos.HouseDTO house);

	DepartmentsProtos.HouseDTO map(House house);

	TestCase1 map(DepartmentsProtos.TestCase1DTO dto);

	DepartmentsProtos.TestCase1DTO map(TestCase1 value);
}
