package no.entur.abt.mapstruct.common;

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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import com.google.protobuf.UInt32Value;
import com.google.protobuf.UInt64Value;

public interface ProtobufStandardMappings {

	default ByteString mapByteString(byte[] array) {
		if (array == null) {
			return ByteString.EMPTY;
		}
		return ByteString.copyFrom(array);
	}

	default byte[] mapByteString(ByteString in) {
		if (in != null && !in.isEmpty()) {
			return in.toByteArray();
		}

		return null;
	}

	default ByteString mapByteStringToString(String string) {
		return ByteString.copyFromUtf8(string != null ? string : "");
	}

	default String mapStringToByteString(ByteString in) {
		if (in != null && !in.isEmpty()) {
			return in.toStringUtf8();
		}

		return null;
	}

	default com.google.type.Date mapLocalDate(LocalDate t) {
		return t != null ? com.google.type.Date.newBuilder().setYear(t.getYear()).setMonth(t.getMonthValue()).setDay(t.getDayOfMonth()).build()
				: com.google.type.Date.getDefaultInstance();
	}

	default LocalDate mapDate(com.google.type.Date t) {
		return LocalDate.of(t.getYear(), t.getMonth(), t.getDay());
	}

	default com.google.type.TimeOfDay mapLocalTime(LocalTime t) {
		return t != null
				? com.google.type.TimeOfDay.newBuilder().setHours(t.getHour()).setMinutes(t.getMinute()).setSeconds(t.getSecond()).setNanos(t.getNano()).build()
				: com.google.type.TimeOfDay.getDefaultInstance();
	}

	default LocalTime mapTimeOfDay(com.google.type.TimeOfDay t) {
		return LocalTime.of(t.getHours(), t.getMinutes(), t.getSeconds(), t.getNanos());
	}

	default Timestamp map(LocalDateTime i) {
		if (i == null) {
			return Timestamp.getDefaultInstance();
		}

		TimeZone systemDefault = TimeZone.getDefault();

		int offset = systemDefault.getOffset(GregorianCalendar.AD, i.getYear(), i.getMonthValue() - 1, i.getDayOfMonth(), i.getDayOfWeek().getValue(),
				i.getNano() / 1000);

		return Timestamp.newBuilder().setSeconds(i.toEpochSecond(ZoneOffset.ofTotalSeconds(offset / 1000))).setNanos(i.getNano()).build();
	}

	default Timestamp map(OffsetDateTime in) {
		return in != null ? Timestamp.newBuilder().setSeconds(in.toEpochSecond()).setNanos(0).build() : Timestamp.getDefaultInstance();
	}

	default float map(FloatValue f) {
		return f.getValue();
	}

	default double map(DoubleValue f) {
		return f.getValue();
	}

	default int map(Int32Value f) {
		return f.getValue();
	}

	default long map(Int64Value f) {
		return f.getValue();
	}

	default int map(UInt32Value f) {
		return f.getValue();
	}

	default long map(UInt64Value f) {
		return f.getValue();
	}

	default String map(StringValue f) {
		return f.getValue();
	}

	default boolean map(BoolValue f) {
		return f.getValue();
	}

	default ByteString map(BytesValue f) {
		return f.getValue();
	}

	default Instant mapToInstant(Timestamp t) {
		if (t == null || Timestamp.getDefaultInstance().equals(t)) {
			return null;
		}
		Timestamp sanitized = Timestamps.sanitize(t);
		return Instant.ofEpochSecond(sanitized.getSeconds(), sanitized.getNanos());
	}

	default Timestamp mapToTimestamp(Instant i) {
		if (i == null) {
			return Timestamp.getDefaultInstance();
		}

		Timestamp t = Timestamp.newBuilder().setSeconds(i.getEpochSecond()).setNanos(i.getNano()).build();
		return Timestamps.sanitize(t);
	}
}
