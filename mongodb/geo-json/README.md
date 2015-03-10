# Spring Data MongoDB - GeoJSON examples

This project contains samples of [GeoJSON](http://geojson.org) specific features of Spring Data (MongoDB).

## Support for GeoJSON types in domain classes

Using [GeoJSON](http://geojson.org) types in domain classes is straight forward. The `org.springframework.data.mongodb.core.geo` package contains types like `GeoJsonPoint` or `GeoJsonPolygon` which are extensions to the existing `org.springframework.data.geo` types.
Find more information in the [MongoDB manual on GeoJSON support](http://docs.mongodb.org/manual/core/2dsphere/#geospatial-indexes-store-geojson) to learn about requirements and restrictions.

```java
public class Store {

	String id;

	/**
	 * location is stored in GeoJSON format.
	 * {
	 *   "type" : "Point",
	 *   "coordinates" : [ x, y ]
	 * }
	 */
	GeoJsonPoint location;
}
```

## Support for GeoJSON types in repository methods

Using GeoJson types as repository query parameters forces usage of the `$geometry` operator when creating the query.

```java
public interface StoreRepository extends CrudRepository<Store, String> {

	List<Store> findByLocationWithin(Polygon polygon);
}
```

```java
/* {
 *   "location": {
 *     "$geoWithin": {
 *       "$geometry": {
 *         "type": "Polygon",
 *         "coordinates": [
 *           [
 *             [-73.992514,40.758934],
 *             [-73.961138,40.760348],
 *             [-73.991658,40.730006],
 *             [-73.992514,40.758934]
 *           ]
 *         ]
 *       }
 *     }
 *   }
 * }
 */
repo.findByLocationWithin(
  new GeoJsonPolygon(
    new Point(-73.992514, 40.758934),
    new Point(-73.961138, 40.760348),
    new Point(-73.991658, 40.730006),
    new Point(-73.992514, 40.758934)));

/* {
 *   "location" : {
 *     "$geoWithin" : {
 *        "$polygon" : [ [ -73.992514, 40.758934 ] , [ -73.961138, 40.760348 ] , [ -73.991658, 40.730006 ] ]
 *     }
 *   }
 * }
 */
repo.findByLocationWithin(
  new Polygon(
    new Point(-73.992514, 40.758934),
    new Point(-73.961138, 40.760348),
    new Point(-73.991658, 40.730006));
```
