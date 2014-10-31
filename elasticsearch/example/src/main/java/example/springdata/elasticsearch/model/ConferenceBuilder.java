package example.springdata.elasticsearch.model;

/**
 * Created by akonczak on 26/10/2014.
 */
public class ConferenceBuilder {

    private Conference conference = new Conference();

    public ConferenceBuilder id(String id) {
        conference.setId(id);
        return this;
    }

    public ConferenceBuilder name(String name) {
        conference.setName(name);
        return this;
    }

    public ConferenceBuilder start(String date) {
        conference.setDate(date);
        return this;
    }

    public ConferenceBuilder date(String date) {
        conference.setDate(date);
        return this;
    }

    public ConferenceBuilder keyword(String... keywords) {
        conference.setKeywords(keywords);
        return this;
    }

    public ConferenceBuilder location(String location) {
        conference.setLocation(location);
        return this;
    }

    public Conference build() {
        return conference.copy();
    }
}
