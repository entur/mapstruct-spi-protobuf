package no.entur.abt.mapstruct.common;

/*-
 * #%L
 * protobuf-support-lite
 * %%
 * Copyright (C) 2019 - 2022 Entur
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

import com.google.protobuf.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Timestamps {

    private static final Logger LOGGER = LoggerFactory.getLogger(Timestamps.class);
    ;
    static final long TIMESTAMP_SECONDS_MIN = -62135596800L;
    static final long TIMESTAMP_SECONDS_MAX = 253402300799L;
    static final int NANOS_PER_SECOND = 1000000000;


    public static final Timestamp MAX_VALUE = Timestamp.newBuilder().setSeconds(TIMESTAMP_SECONDS_MAX).setNanos(NANOS_PER_SECOND - 1).build();
    public static final Timestamp MIN_VALUE = Timestamp.newBuilder().setSeconds(TIMESTAMP_SECONDS_MIN).setNanos(0).build();


    /**
     * Sanitize Timestamps outside legal range where possible.
     */
    public static Timestamp sanitize(Timestamp t) {
        if (t.getNanos() < 0 || t.getNanos() >= NANOS_PER_SECOND) {
            throw new IllegalArgumentException(String.format("Timestamp is not valid. See proto definition for valid values. Seconds (%s) must be in range [-62,135,596,800, +253,402,300,799]. Nanos (%s) must be in range [0, +999,999,999].", t.getSeconds(), t.getNanos()));
        }
        if (t.getSeconds() > TIMESTAMP_SECONDS_MAX) {
            LOGGER.warn(String.format("Cannot map to timestamp greater than allowed MAX: (%s). Using MAX_TIMESTAMP instead: (%s)", t, Timestamps.MAX_VALUE));
            return MAX_VALUE;
        }
        if (t.getSeconds() < TIMESTAMP_SECONDS_MIN) {
            LOGGER.warn(String.format("Cannot map to timestamp less than allowed MIN: (%s). Using MIN_TIMESTAMP instead: (%s)", t, Timestamps.MIN_VALUE));
            return MIN_VALUE;
        }

        return t;
    }


}
