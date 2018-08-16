package example.repo;

import example.model.Customer424;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer424Repository extends CrudRepository<Customer424, Long> {

	List<Customer424> findByLastName(String lastName);
}
