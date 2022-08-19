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
import java.util.concurrent.TimeUnit;

import no.entur.abt.mapstruct.common.Timestamps;
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
	public void mapToInstant_whenValueIsTooLargeForRangeForTimestamp_thenMapFromMaxValidTimestamp() {
		assertEquals(MAPPER.mapToInstant(Timestamps.MAX_VALUE), MAPPER.mapToInstant(Timestamp.newBuilder().setSeconds(Long.MAX_VALUE).build()));
	}

	@Test
	public void mapToInstant_whenValueIsTooSmallForRangeForTimestamp_thenMapFromMinValidTimestamp() {
		assertEquals(MAPPER.mapToInstant(Timestamps.MIN_VALUE), MAPPER.mapToInstant(Timestamp.newBuilder().setSeconds(-Long.MAX_VALUE).build()));
	}


	@Test
	public void mapInstantToTimestamp_whenValueIsTooLargeForRangeForTimestamp_thenMapToMaxValidTimestamp() {
		assertEquals(Timestamps.MAX_VALUE, MAPPER.mapToTimestamp(Instant.now().plus(Integer.MAX_VALUE, ChronoUnit.DAYS)));
	}

	@Test
	public void mapInstantToTimestamp_whenValueIsTooSmallForRangeForTimestamp_thenMapToMinValidTimestamp() {
		assertEquals(Timestamps.MIN_VALUE, MAPPER.mapToTimestamp(Instant.now().minus(Integer.MAX_VALUE, ChronoUnit.DAYS)));
	}
	public void mapPositiveDuration() {
		Duration duration = Duration.of(3, ChronoUnit.NANOS);

		com.google.protobuf.Duration pbDuration = MAPPER.mapDuration(duration);
		Durations.checkValid(pbDuration);
		assertEquals(duration, MAPPER.mapDuration(pbDuration));
	}

	@Test
	public void mapNegativeDurationToProto_whenSecondsAreNegativeAndNanoPositive() {
		Duration duration = Duration.ofSeconds(-3, 2);

		com.google.protobuf.Duration pbDuration = MAPPER.mapDuration(duration);
		Durations.checkValid(pbDuration);
		assertEquals(duration, MAPPER.mapDuration(pbDuration));
	}

	@Test
	public void mapNegativeDurationToProto_whenSecondsArePositiveAndNanoNegative() {
		// Duration.ofSeconds accepts negative values. Will still be stored as positive values in Duration
		Duration duration = Duration.ofSeconds(3, -(TimeUnit.SECONDS.toNanos(1) - 2));

		com.google.protobuf.Duration pbDuration = MAPPER.mapDuration(duration);
		Durations.checkValid(pbDuration);
		assertEquals(duration, MAPPER.mapDuration(pbDuration));
	}

	@Test
	public void mapNegativeDuration_fromProto() {
		com.google.protobuf.Duration pbDuration = com.google.protobuf.Duration.newBuilder().setSeconds(-10).setNanos(-5).build();

		Duration duration = MAPPER.mapDuration(pbDuration);
		Durations.checkValid(pbDuration);
		assertEquals(pbDuration, MAPPER.mapDuration(duration));
	}

}
