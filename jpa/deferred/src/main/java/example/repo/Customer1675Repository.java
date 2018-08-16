package example.repo;

import example.model.Customer1675;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1675Repository extends CrudRepository<Customer1675, Long> {

	List<Customer1675> findByLastName(String lastName);
}
