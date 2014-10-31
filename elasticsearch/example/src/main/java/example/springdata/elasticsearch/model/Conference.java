package example.springdata.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.util.Arrays;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

/**
 * Created by akonczak on 14/10/2014.
 */
@Document(indexName = "conference-index", shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
public class Conference {

    @Id
    private String id;
    private String name;
    @Field(type = Date)
    private String date;
    private String[] keywords;
    @GeoPointField
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Conference copy() {
        Conference newConference = new Conference();
        newConference.setName(name);
        newConference.setDate(date);
        newConference.setLocation(location);
        newConference.setKeywords(keywords);
        return newConference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public java.lang.String toString() {
        return "Conference{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", keywords=" + Arrays.toString(keywords) +
                ", location='" + location + '\'' +
                '}';
    }
}
