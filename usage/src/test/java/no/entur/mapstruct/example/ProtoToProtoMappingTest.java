package no.entur.mapstruct.example;

/*-
 * #%L
 * protobuf-usage
 * %%
 * Copyright (C) 2019 - 2021 Entur
 * %%
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * #L%
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProtoToProtoMappingTest {
	@Test
	public void testProtoToProtoEnum() {
		assertEquals(UserProtos.EnumStatus.ENUM_STATUS_UNSPECIFIED, ProtoToProtoMapper.INSTANCE.map(UserProtos.EnumStatus.ENUM_STATUS_UNSPECIFIED));

	}

}
