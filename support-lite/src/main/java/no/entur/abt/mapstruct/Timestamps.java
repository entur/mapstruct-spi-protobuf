package no.entur.abt.mapstruct;

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

public class Timestamps {

    public static boolean isValid(Timestamp timestamp) {
        return isValid(timestamp.getSeconds(), timestamp.getNanos());
    }

    public static boolean isValid(long seconds, int nanos) {
        if (seconds >= -62135596800L && seconds <= 253402300799L) {
            return nanos >= 0 && nanos < 1000000000;
        } else {
            return false;
        }
    }

    public static Timestamp checkValid(Timestamp timestamp) {
        long seconds = timestamp.getSeconds();
        int nanos = timestamp.getNanos();
        if (!isValid(seconds, nanos)) {
            throw new IllegalArgumentException(String.format("Timestamp is not valid. See proto definition for valid values. Seconds (%s) must be in range [-62,135,596,800, +253,402,300,799]. Nanos (%s) must be in range [0, +999,999,999].", seconds, nanos));
        } else {
            return timestamp;
        }
    }
}
