/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example.springdata.jpa.eclipselink.domain;

/**
 * Entity showing use of {@link org.eclipse.persistence.annotations.UuidGenerator} from
 * the EclipseLink JPA Provider. Does not extend the AbstractPersistable because of the UUID generator
 *
 * @author Jeremy Rickard
 */

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Participant {
    @Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    private String uuid;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phoneNumber;

    private int age;

    @Enumerated
    private Gender gender;

    @ManyToMany(mappedBy = "registrations")
    private List<Race> raceRegistrations;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Race> getRaceRegistrations() {
        return raceRegistrations;
    }

    public void setRaceRegistrations(List<Race> raceRegistrations) {
        this.raceRegistrations = raceRegistrations;
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(obj.getClass())) {
            return false;
        } else {
            Participant that = (Participant) obj;
            return null == this.getUuid() ? false : this.getUuid().equals(that.getUuid());
        }
    }

    public int hashCode() {
        byte hashCode = 17;
        int hashCode1 = hashCode + (null == this.getUuid() ? 0 : this.getUuid().hashCode() * 31);
        return hashCode1;
    }
}
