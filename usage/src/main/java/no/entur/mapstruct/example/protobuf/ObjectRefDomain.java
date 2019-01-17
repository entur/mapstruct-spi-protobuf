package no.entur.mapstruct.example.protobuf;

public class ObjectRefDomain {

    protected String value;

    protected String ref;

    protected String versionRef;

    protected String version;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObjectRefDomain other = (ObjectRefDomain) obj;
        if (ref == null) {
            if (other.ref != null)
                return false;
        } else if (!ref.equals(other.ref))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        if (versionRef == null) {
            if (other.versionRef != null)
                return false;
        } else if (!versionRef.equals(other.versionRef))
            return false;
        return true;
    }

    public String getRef() {
        return ref;
    }

    public String getValue() {
        return value;
    }

    public String getVersion() {
        return version;
    }

    public String getVersionRef() {
        return versionRef;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ref == null) ? 0 : ref.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((versionRef == null) ? 0 : versionRef.hashCode());
        return result;
    }

    public void setRef(String value) {
        this.ref = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public void setVersionRef(String value) {
        this.versionRef = value;
    }

    @Override
    public String toString() {
        return "objectref";
    }

    public ObjectRefDomain withRef(String value) {
        setRef(value);
        return this;
    }

    public ObjectRefDomain withValue(String value) {
        setValue(value);
        return this;
    }

    public ObjectRefDomain withVersion(String value) {
        setVersion(value);
        return this;
    }

    public ObjectRefDomain withVersionRef(String value) {
        setVersionRef(value);
        return this;
    }

}
