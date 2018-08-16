package example.repo;

import example.model.Customer831;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer831Repository extends CrudRepository<Customer831, Long> {

	List<Customer831> findByLastName(String lastName);
}
