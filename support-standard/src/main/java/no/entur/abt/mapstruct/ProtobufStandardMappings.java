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

import java.time.Duration;
import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Durations;
import com.google.protobuf.util.Timestamps;

/***
 *
 * Note: This mapper must be kept in sync with its corresponding 'lite' equivalent
 *
 */

@Mapper
public interface ProtobufStandardMappings extends no.entur.abt.mapstruct.common.ProtobufStandardMappings {

	ProtobufStandardMappings INSTANCE = Mappers.getMapper(ProtobufStandardMappings.class);

	default Instant mapToInstant(Timestamp t) {
		if (Timestamps.isValid(t) && (t.getSeconds() > 0 || t.getNanos() > 0)) {
			return Instant.ofEpochSecond(t.getSeconds(), t.getNanos());
		} else {
			return null;
		}
	}

	default Long toEpochMilliseconds(Timestamp instance) {
		if (instance != null) {
			return Timestamps.toMillis(instance);
		} else {
			return null;
		}
	}

	default Timestamp mapToTimestamp(Instant i) {
		if (i == null || i.getEpochSecond() == 0) {
			return null;
		} else {
			return Timestamp.newBuilder().setSeconds(i.getEpochSecond()).setNanos(i.getNano()).build();
		}
	}

	default Timestamp fromEpochMilliseconds(long millis) {
		return Timestamps.fromMillis(millis);
	}

	default Duration mapDuration(com.google.protobuf.Duration t) {
		if (t != null) {
			return Duration.ofSeconds(t.getSeconds(), t.getNanos());
		} else {
			return null;
		}
	}

	default com.google.protobuf.Duration mapDuration(Duration t) {
		if (t != null) {
			return Durations.fromNanos(t.toNanos());
		} else {
			return null;
		}
	}

}
