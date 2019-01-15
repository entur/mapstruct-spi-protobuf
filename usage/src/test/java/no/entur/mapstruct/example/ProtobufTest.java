/**
 * Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 * and/or other contributors as indicated by the @authors tag. See the
 * copyright.txt file in the distribution for a full listing of all
 * contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.entur.mapstruct.example;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import no.entur.mapstruct.example.mapper.UserMapper;
import no.entur.mapstruct.example.protobuf.Department;
import no.entur.mapstruct.example.protobuf.MultiNumber;
import no.entur.mapstruct.example.protobuf.Permission;
import no.entur.mapstruct.example.protobuf.Status;
import no.entur.mapstruct.example.protobuf.User;
import no.entur.mapstruct.example.protobuf.UserProtos.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Kratz
 */
public class ProtobufTest {

    private User generateUser() {
        User user = new User();

        user.setId(null);
        user.setEmail("test");
        user.getMainDepartments().add(new Department("SALES"));
        user.getDepartments().add(new Department("AFTER_MARKET"));

        user.setV1(1.0);
        user.setV2(2);
        user.setV3(3);
        user.setV4(4);
        user.setV5(5);
        user.setV6(6);
        user.setV7(7);
        user.setV8(8);
        user.setV9(9);
        user.setV10(10);
        user.setV11(11);
        user.setV12(12);
        user.setV13(true);
        user.setV14("some string");
        user.setV15(ByteString.copyFromUtf8("byte string"));
        user.setV16(Status.STARTED);

        Map<String, String> v19 = new HashMap<>();
        v19.put("some key", "some value");
        // user.setV19(v19);

        Map<String, Department> v20 = new HashMap<>();
        Department d = new Department();
        d.setName("department name");
        v20.put("soem department", d);
        // user.setV20(v20);

        user.setRv1(Arrays.asList(1.0));
        user.setRv2(Arrays.asList(2.0f));
        user.setRv3(Arrays.asList(3));
        user.setRv4(Arrays.asList(4L));
        user.setRv5(Arrays.asList(5));
        user.setRv6(Arrays.asList(6L));
        user.setRv7(Arrays.asList(7));
        user.setRv8(Arrays.asList(8L));
        user.setRv9(Arrays.asList(9));
        user.setRv10(Arrays.asList(10L));
        user.setRv11(Arrays.asList(11));
        user.setRv12(Arrays.asList(12L));
        user.setRv13(Arrays.asList(true));
        user.setRv14(Arrays.asList("some string"));
        user.setRv15(Arrays.asList(ByteString.copyFromUtf8("some byte string")));
        user.setRv16(Arrays.asList(Status.STARTED));

        MultiNumber mm = new MultiNumber();
        mm.setNumber(1);
        
        user.setMultiNumber(mm);
        user.setRepMultiNumbers(Arrays.asList(mm));

        return user;
    }

    private void assertUser(User orig, User back) {
        Assert.assertEquals(orig.getId(), back.getId());
        Assert.assertEquals(orig.getEmail(), back.getEmail());

        Assert.assertEquals(orig.getMainDepartments().size(), back.getMainDepartments().size());
        Assert.assertEquals(orig.getMainDepartments().get(0).getName(), back.getMainDepartments().get(0).getName());

        Assert.assertEquals(orig.getDepartments().size(), back.getDepartments().size());
        Assert.assertEquals(orig.getDepartments().get(0).getName(), back.getDepartments().get(0).getName());

        Assert.assertEquals(orig.getV1(), back.getV1(), 0.1);
        Assert.assertEquals(orig.getV2(), back.getV2(), 0.1);
        Assert.assertEquals(orig.getV3(), back.getV3());
        Assert.assertEquals(orig.getV4(), back.getV4());
        Assert.assertEquals(orig.getV5(), back.getV5());
        Assert.assertEquals(orig.getV6(), back.getV6());
        Assert.assertEquals(orig.getV7(), back.getV7());
        Assert.assertEquals(orig.getV8(), back.getV8());
        Assert.assertEquals(orig.getV9(), back.getV9());
        Assert.assertEquals(orig.getV10(), back.getV10());
        Assert.assertEquals(orig.getV11(), back.getV11());
        Assert.assertEquals(orig.getV12(), back.getV12());

        Assert.assertEquals(orig.isV13(), back.isV13());

        Assert.assertEquals(orig.getV14(), back.getV14());
        Assert.assertEquals(orig.getV15(), back.getV15());
        Assert.assertEquals(orig.getV16(), back.getV16());
        Assert.assertEquals(orig.getV16(), back.getV16());

        Assert.assertEquals(orig.getRv1().size(), back.getRv1().size());
        Assert.assertEquals(orig.getRv1().get(0), back.getRv1().get(0), 0.1);

        Assert.assertEquals(orig.getRv2().size(), back.getRv2().size());
        Assert.assertEquals(orig.getRv2().get(0), back.getRv2().get(0), 0.1);

        Assert.assertEquals(orig.getRv3().size(), back.getRv3().size());
        Assert.assertEquals(orig.getRv3().get(0), back.getRv3().get(0));

        Assert.assertEquals(orig.getRv4().size(), back.getRv4().size());
        Assert.assertEquals(orig.getRv4().get(0), back.getRv4().get(0));

        Assert.assertEquals(orig.getRv5().size(), back.getRv5().size());
        Assert.assertEquals(orig.getRv5().get(0), back.getRv5().get(0));

        Assert.assertEquals(orig.getRv6().size(), back.getRv6().size());
        Assert.assertEquals(orig.getRv6().get(0), back.getRv6().get(0));

        Assert.assertEquals(orig.getRv7().size(), back.getRv7().size());
        Assert.assertEquals(orig.getRv7().get(0), back.getRv7().get(0));

        Assert.assertEquals(orig.getRv8().size(), back.getRv8().size());
        Assert.assertEquals(orig.getRv8().get(0), back.getRv8().get(0));

        Assert.assertEquals(orig.getRv9().size(), back.getRv9().size());
        Assert.assertEquals(orig.getRv9().get(0), back.getRv9().get(0));

        Assert.assertEquals(orig.getRv10().size(), back.getRv10().size());
        Assert.assertEquals(orig.getRv10().get(0), back.getRv10().get(0));

        Assert.assertEquals(orig.getRv11().size(), back.getRv11().size());
        Assert.assertEquals(orig.getRv11().get(0), back.getRv11().get(0));

        Assert.assertEquals(orig.getRv12().size(), back.getRv12().size());
        Assert.assertEquals(orig.getRv12().get(0), back.getRv12().get(0));

        Assert.assertEquals(orig.getRv13().size(), back.getRv13().size());
        Assert.assertEquals(orig.getRv13().get(0), back.getRv13().get(0));

        Assert.assertEquals(orig.getRv14().size(), back.getRv14().size());
        Assert.assertEquals(orig.getRv14().get(0), back.getRv14().get(0));

        Assert.assertEquals(orig.getRv15().size(), back.getRv15().size());
        Assert.assertEquals(orig.getRv15().get(0), back.getRv15().get(0));

        Assert.assertEquals(orig.getRv16().size(), back.getRv16().size());
        Assert.assertEquals(orig.getRv16().get(0), back.getRv16().get(0));
    }

    @Test
    public void test() throws InvalidProtocolBufferException {
        User user = generateUser();

        UserDTO dto = UserMapper.INSTANCE.map(user);
        UserDTO deserialized = UserDTO.parseFrom(dto.toByteArray());
        User back = UserMapper.INSTANCE.map(deserialized);

        assertUser(user, back);
    }


    @Test
    public void testNulls() throws InvalidProtocolBufferException {
        User user = new User();
        user.setEmail("test");

        UserDTO dto = UserMapper.INSTANCE.map(user);
        UserDTO deserialized = UserDTO.parseFrom(dto.toByteArray());
        User back = UserMapper.INSTANCE.map(deserialized);

        Assert.assertEquals(null, back.getId());
        Assert.assertEquals("test", back.getEmail());
    }
}
