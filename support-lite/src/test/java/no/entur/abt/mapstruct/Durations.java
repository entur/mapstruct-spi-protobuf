package no.entur.abt.mapstruct;

/*-
 * #%L
 * protobuf-support-lite
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

public class Durations {

	static final long NANOS_PER_SECOND = 1000000000;

	static final long DURATION_SECONDS_MIN = -315576000000L;
	static final long DURATION_SECONDS_MAX = 315576000000L;

	public static com.google.protobuf.Duration checkValid(com.google.protobuf.Duration duration) {
		long seconds = duration.getSeconds();
		int nanos = duration.getNanos();
		if (!isValid(seconds, nanos)) {
			throw new IllegalArgumentException(String.format(
					"Duration is not valid. See proto definition for valid values. " + "Seconds (%s) must be in range [-315,576,000,000, +315,576,000,000]. "
							+ "Nanos (%s) must be in range [-999,999,999, +999,999,999]. " + "Nanos must have the same sign as seconds",
					seconds, nanos));
		}
		return duration;
	}

	public static boolean isValid(long seconds, int nanos) {
		if (seconds < DURATION_SECONDS_MIN || seconds > DURATION_SECONDS_MAX) {
			return false;
		}
		if (nanos < -999999999L || nanos >= NANOS_PER_SECOND) {
			return false;
		}
		if (seconds < 0 || nanos < 0) {
			if (seconds > 0 || nanos > 0) {
				return false;
			}
		}
		return true;
	}

}
