package no.entur.abt.mapstruct;

/*-
 * #%L
 * protobuf-support
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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.google.protobuf.Timestamp;

public class ProtobufStandardMappingsTest {

	ProtobufStandardMappings MAPPER = ProtobufStandardMappings.INSTANCE;

	@Test
	public void testMapLocalDateToTimestampSummertime() {
		LocalDateTime l = LocalDateTime.of(2000, 6, 1, 12, 0);

		Timestamp timestamp = MAPPER.map(l);
		Instant instant = MAPPER.mapToInstant(timestamp);

		LocalDateTime back = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		assertEquals(l, back);
	}

	@Test
	public void testMapLocalDateToTimestampWintertime() {
		LocalDateTime l = LocalDateTime.of(2000, 2, 1, 12, 0);

		Timestamp timestamp = MAPPER.map(l);
		Instant instant = MAPPER.mapToInstant(timestamp);

		LocalDateTime back = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		assertEquals(l, back);
	}

	@Test
	public void mapToInstant_whenSecondsAndNanosIs0_thenMapToNull() {
		assertNull(MAPPER.mapToInstant(Timestamp.newBuilder().build()));
	}

	@Test
	public void mapToInstant_whenNanosIsSet_thenMapToInstant() {
		assertEquals(3000, MAPPER.mapToInstant(Timestamp.newBuilder().setNanos(3000).build()).getNano());
	}

	@Test
	public void mapDuration() {
		Duration duration = Duration.of(3, ChronoUnit.NANOS);

		com.google.protobuf.Duration pbDuration = MAPPER.mapDuration(duration);
		assertEquals(duration, MAPPER.mapDuration(pbDuration));

	}
}
