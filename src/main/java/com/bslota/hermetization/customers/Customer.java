package com.bslota.hermetization.customers;

import com.bslota.hermetization.util.Sequence;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bslota on 28.07.17.
 */
public class Customer implements Serializable {
    private final long id;
    private final String name;
    private final ZonedDateTime creationDate;
    private final ZonedDateTime activationDate;
    private List<ContactPerson> contactPeople;

    private Customer(long id, String name, ZonedDateTime creationDate, ZonedDateTime activationDate, List<ContactPerson> contactPeople) {
        if (contactPeople == null) {
            throw new IllegalArgumentException("Contact people list cannot be null");
        }
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.activationDate = activationDate;
        this.contactPeople = new ArrayList<>(contactPeople);
    }

    public static Customer valueOf(String name, List<ContactPerson> contactPeople) {
        return new Customer(Sequence.nextValue(), name, ZonedDateTime.now(), null, contactPeople);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public List<ContactPerson> getContactPeople() {
        return Collections.unmodifiableList(contactPeople);
    }

    public Customer activate() {
        return new Customer(this.id, this.name, this.creationDate, ZonedDateTime.now(), new ArrayList<>(this.contactPeople));
    }

    public boolean isActive() {
        return this.activationDate != null;
    }

    public Customer addContactPerson(ContactPerson contactPerson) {
        final List<ContactPerson> newContactPersonList = new ArrayList<>(this.contactPeople);
        newContactPersonList.add(contactPerson);
        return new Customer(this.id, this.name, this.creationDate, this.activationDate, newContactPersonList);
    }

    public Customer removeContactPerson(long contactPersonId) {
        final List<ContactPerson> newContactPersonList = new ArrayList<>(this.contactPeople);
        newContactPersonList.removeIf(it -> it.getId() == contactPersonId);
        return new Customer(this.id, this.name, this.creationDate, this.activationDate, newContactPersonList);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", creationDate=" + creationDate +
            ", activationDate=" + activationDate +
            ", contactPeople=" + contactPeople +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (creationDate != null ? !creationDate.equals(customer.creationDate) : customer.creationDate != null)
            return false;
        if (activationDate != null ? !activationDate.equals(customer.activationDate) : customer.activationDate != null)
            return false;
        return contactPeople != null ? contactPeople.equals(customer.contactPeople) : customer.contactPeople == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (activationDate != null ? activationDate.hashCode() : 0);
        result = 31 * result + (contactPeople != null ? contactPeople.hashCode() : 0);
        return result;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        if (contactPeople == null) {
            throw new NotSerializableException("Contact people list cannot be null");
        }
        if (StringUtils.isEmpty(name)) {
            throw new NotSerializableException("Name cannot be empty");
        }
        contactPeople = new ArrayList<>(contactPeople);
    }
}
