/*
 * Copyright 2014-2015 the original author or authors.
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
package example.springdata.rest.stores.web;

import static org.springframework.data.geo.Metrics.*;

import example.springdata.rest.stores.Store;
import example.springdata.rest.stores.StoreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Spring MVC controller to produce an HTML frontend.
 * 
 * @author Oliver Gierke
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class StoresController {

	private static final List<Distance> DISTANCES = Arrays.asList(new Distance(0.5, MILES), new Distance(1, MILES),
			new Distance(2, MILES));
	private static final Distance DEFAULT_DISTANCE = new Distance(1, Metrics.MILES);
	private static final Map<String, Point> KNOWN_LOCATIONS;

	static {

		Map<String, Point> locations = new HashMap<>();

		locations.put("Pivotal SF", new Point(-122.4041764, 37.7819286));
		locations.put("Timesquare NY", new Point(-73.995146, 40.740337));

		KNOWN_LOCATIONS = Collections.unmodifiableMap(locations);
	}

	private final StoreRepository repository;
	private final RepositoryEntityLinks entityLinks;

	/**
	 * Looks up the stores in the given distance around the given location.
	 * 
	 * @param model the {@link Model} to populate.
	 * @param location the optional location, if none is given, no search results will be returned.
	 * @param distance the distance to use, if none is given the {@link #DEFAULT_DISTANCE} is used.
	 * @param pageable the pagination information
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	String index(Model model, @RequestParam Optional<Point> location, @RequestParam Optional<Distance> distance,
			Pageable pageable) {

		Point point = location.orElse(KNOWN_LOCATIONS.get("Timesquare NY"));

		Page<Store> stores = repository.findByAddressLocationNear(point, distance.orElse(DEFAULT_DISTANCE), pageable);

		model.addAttribute("stores", stores);
		model.addAttribute("distances", DISTANCES);
		model.addAttribute("selectedDistance", distance.orElse(DEFAULT_DISTANCE));
		model.addAttribute("location", point);
		model.addAttribute("locations", KNOWN_LOCATIONS);
		model.addAttribute("api", entityLinks.linkToSearchResource(Store.class, "by-location", pageable).getHref());

		return "index";
	}
}
