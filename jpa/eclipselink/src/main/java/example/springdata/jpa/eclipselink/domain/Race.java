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
import java.util.Date;
import java.util.List;

@Entity
public class Race {
    @Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    private String uuid;

    private double distance;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 5000)
    private String description;

    @ManyToMany
    @JoinTable(name = "RACE_REG",
            joinColumns = {@JoinColumn(name = "RACE_ID", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "PART_ID", referencedColumnName = "uuid", unique = true)})
    private List<Participant> registrations;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Participant> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Participant> registrations) {
        this.registrations = registrations;
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(obj.getClass())) {
            return false;
        } else {
            Race that = (Race) obj;
            return null == this.getUuid() ? false : this.getUuid().equals(that.getUuid());
        }
    }

    public int hashCode() {
        byte hashCode = 17;
        int hashCode1 = hashCode + (null == this.getUuid() ? 0 : this.getUuid().hashCode() * 31);
        return hashCode1;
    }
}
