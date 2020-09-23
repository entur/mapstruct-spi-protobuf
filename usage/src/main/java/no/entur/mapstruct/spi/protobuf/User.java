package no.entur.mapstruct.spi.protobuf;

/*-
 * #%L
 * protobuf-usage
 * %%
 * Copyright (C) 2019 - 2020 Entur
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

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ByteString;

/**
 * @author Thomas Kratz
 */
public class User {

	double v1;
	float v2;
	int v3;
	long v4;
	int v5;
	long v6;
	int v7;
	long v8;
	int v9;

	// public List<Permission> getPermissions() {
//        return permissions;
//    }
//
//    public void setPermissions(List<Permission> permissions) {
//        this.permissions = permissions;
//    }
	long v10;
	int v11;
	long v12;
	boolean v13;
	String v14;
	ByteString v15;
	Status v16;
	User user;
	List<Double> rv1;
	List<Float> rv2;
	List<Integer> rv3;
	List<Long> rv4;
	List<Integer> rv5;
	List<Long> rv6;
	List<Integer> rv7;
	List<Long> rv8;
	List<Integer> rv9;
	List<Long> rv10;
	List<Integer> rv11;
	List<Long> rv12;
	List<Boolean> rv13;
	List<String> rv14;
	List<ByteString> rv15;
	List<Status> rv16;
	MultiNumber multiNumber;
	List<MultiNumber> repMultiNumbers;
	private String id;
	private String email;
	// private List<Permission> permissions = new ArrayList<>();
	private List<Department> mainDepartments = new ArrayList<>();
	private List<Department> departments = new ArrayList<>();
	private String nonRepeatableFieldWithSuffixList;
	private List<User> users;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Department> getMainDepartments() {
		return mainDepartments;
	}

	public void setMainDepartments(List<Department> mainDepartments) {
		this.mainDepartments = mainDepartments;
	}

	public double getV1() {
		return v1;
	}

	public void setV1(double v1) {
		this.v1 = v1;
	}

	public float getV2() {
		return v2;
	}

	public void setV2(float v2) {
		this.v2 = v2;
	}

	public int getV3() {
		return v3;
	}

	public void setV3(int v3) {
		this.v3 = v3;
	}

	public long getV4() {
		return v4;
	}

	public void setV4(long v4) {
		this.v4 = v4;
	}

	public int getV5() {
		return v5;
	}

	public void setV5(int v5) {
		this.v5 = v5;
	}

	public long getV6() {
		return v6;
	}

	public void setV6(long v6) {
		this.v6 = v6;
	}

	public int getV7() {
		return v7;
	}

	public void setV7(int v7) {
		this.v7 = v7;
	}

//    public int getOneofv1() {
//        return oneofv1;
//    }
//
//    public void setOneofv1(int oneofv1) {
//        this.oneofv1 = oneofv1;
//    }
//
//    public double getOneofv2() {
//        return oneofv2;
//    }
//
//    public void setOneofv2(double oneofv2) {
//        this.oneofv2 = oneofv2;
//    }
//
//    int oneofv1;
//    double oneofv2;

//    public Map<String, String> getV19() {
//        return v19;
//    }
//
//    public void setV19(Map<String, String> v19) {
//        this.v19 = v19;
//    }
//
//    public Map<String, Department> getV20() {
//        return v20;
//    }
//
//    public void setV20(Map<String, Department> v20) {
//        this.v20 = v20;
//    }
//
//    Map<String, String> v19;
//    Map<String, Department> v20;

	public long getV8() {
		return v8;
	}

	public void setV8(long v8) {
		this.v8 = v8;
	}

	public int getV9() {
		return v9;
	}

	public void setV9(int v9) {
		this.v9 = v9;
	}

	public long getV10() {
		return v10;
	}

	public void setV10(long v10) {
		this.v10 = v10;
	}

	public int getV11() {
		return v11;
	}

	public void setV11(int v11) {
		this.v11 = v11;
	}

	public long getV12() {
		return v12;
	}

	public void setV12(long v12) {
		this.v12 = v12;
	}

	public boolean isV13() {
		return v13;
	}

	public void setV13(boolean v13) {
		this.v13 = v13;
	}

	public String getV14() {
		return v14;
	}

	public void setV14(String v14) {
		this.v14 = v14;
	}

	public ByteString getV15() {
		return v15;
	}

	public void setV15(ByteString v15) {
		this.v15 = v15;
	}

	public Status getV16() {
		return v16;
	}

	public void setV16(Status v16) {
		this.v16 = v16;
	}

	public List<Double> getRv1() {
		return rv1;
	}

	public void setRv1(List<Double> rv1) {
		this.rv1 = rv1;
	}

	public List<Float> getRv2() {
		return rv2;
	}

	public void setRv2(List<Float> rv2) {
		this.rv2 = rv2;
	}

	public List<Integer> getRv3() {
		return rv3;
	}

	public void setRv3(List<Integer> rv3) {
		this.rv3 = rv3;
	}

	public List<Long> getRv4() {
		return rv4;
	}

	public void setRv4(List<Long> rv4) {
		this.rv4 = rv4;
	}

	public List<Integer> getRv5() {
		return rv5;
	}

	public void setRv5(List<Integer> rv5) {
		this.rv5 = rv5;
	}

	public List<Long> getRv6() {
		return rv6;
	}

	public void setRv6(List<Long> rv6) {
		this.rv6 = rv6;
	}

	public List<Integer> getRv7() {
		return rv7;
	}

	public void setRv7(List<Integer> rv7) {
		this.rv7 = rv7;
	}

	public List<Long> getRv8() {
		return rv8;
	}

	public void setRv8(List<Long> rv8) {
		this.rv8 = rv8;
	}

	public List<Integer> getRv9() {
		return rv9;
	}

	public void setRv9(List<Integer> rv9) {
		this.rv9 = rv9;
	}

	public List<Long> getRv10() {
		return rv10;
	}

	public void setRv10(List<Long> rv10) {
		this.rv10 = rv10;
	}

	public List<Integer> getRv11() {
		return rv11;
	}

	public void setRv11(List<Integer> rv11) {
		this.rv11 = rv11;
	}

	public List<Long> getRv12() {
		return rv12;
	}

	public void setRv12(List<Long> rv12) {
		this.rv12 = rv12;
	}

	public List<Boolean> getRv13() {
		return rv13;
	}

	public void setRv13(List<Boolean> rv13) {
		this.rv13 = rv13;
	}

	public List<String> getRv14() {
		return rv14;
	}

	public void setRv14(List<String> rv14) {
		this.rv14 = rv14;
	}

	public List<ByteString> getRv15() {
		return rv15;
	}

	public void setRv15(List<ByteString> rv15) {
		this.rv15 = rv15;
	}

	public List<Status> getRv16() {
		return rv16;
	}

	public void setRv16(List<Status> rv16) {
		this.rv16 = rv16;
	}

	public MultiNumber getMultiNumber() {
		return multiNumber;
	}

	public void setMultiNumber(MultiNumber multiNumber) {
		this.multiNumber = multiNumber;
	}

	public List<MultiNumber> getRepMultiNumbers() {
		return repMultiNumbers;
	}

	public void setRepMultiNumbers(List<MultiNumber> rMultiNumbers) {
		repMultiNumbers = rMultiNumbers;
	}

	public String getNonRepeatableFieldWithSuffixList() {
		return nonRepeatableFieldWithSuffixList;
	}

	public void setNonRepeatableFieldWithSuffixList(String nonRepeatableFieldWithSuffixList) {
		this.nonRepeatableFieldWithSuffixList = nonRepeatableFieldWithSuffixList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
