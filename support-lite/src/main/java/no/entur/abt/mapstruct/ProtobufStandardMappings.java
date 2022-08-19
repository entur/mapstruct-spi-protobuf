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
import java.util.concurrent.TimeUnit;

import no.entur.abt.mapstruct.common.Timestamps;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.google.protobuf.Timestamp;

/***
 *
 * Note: This mapper must be kept in sync with its corresponding 'standard' equivalent
 *
 */

@Mapper
public interface ProtobufStandardMappings extends no.entur.abt.mapstruct.common.ProtobufStandardMappings {

	ProtobufStandardMappings INSTANCE = Mappers.getMapper(ProtobufStandardMappings.class);



	default Long toEpochMilliseconds(Timestamp instance) {
		Instant instant = mapToInstant(instance);
		return instant == null ? null : instant.toEpochMilli();
	}


	default Timestamp fromEpochMilliseconds(Long instance) {
		if (instance == null) {
			return null;
		}
		Instant instant = Instant.ofEpochMilli(instance);
		return mapToTimestamp(instant);
	}

	default Duration mapDuration(com.google.protobuf.Duration t) {
		return Duration.ofSeconds(t.getSeconds(), t.getNanos());
	}

	default com.google.protobuf.Duration mapDuration(Duration t) {
		long seconds = t.getSeconds();
		int nanos = t.getNano();

		// Protobuf requires same sign for seconds & nanos parts. Java time treats nano part as relative adjustment. Adjust to proto expectations
		if (seconds < 0 && nanos > 0) {
			seconds = seconds + 1;
			nanos = (int) (nanos - TimeUnit.SECONDS.toNanos(1));
		}

		return com.google.protobuf.Duration.newBuilder().setSeconds(seconds).setNanos(nanos).build();
	}
}
