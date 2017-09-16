package com.bslota.hermetization.customers;

import com.bslota.hermetization.util.Sequence;

import java.io.Serializable;

/**
 * Created by bslota on 28.07.17.
 */
public final class ContactPerson implements Serializable {
    final private long id;
    final private String email;

    private ContactPerson(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static ContactPerson valueOf(String email) {
        return new ContactPerson(Sequence.nextValue(), email);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
            "email='" + email + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ContactPerson that = (ContactPerson) o;

        if (id != that.id) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public ContactPerson copy() {
        return new ContactPerson(this.id, this.email);
    }
}
