package example.repo;

import example.model.Customer952;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer952Repository extends CrudRepository<Customer952, Long> {

	List<Customer952> findByLastName(String lastName);
}
