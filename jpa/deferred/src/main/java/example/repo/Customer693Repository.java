package example.repo;

import example.model.Customer693;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer693Repository extends CrudRepository<Customer693, Long> {

	List<Customer693> findByLastName(String lastName);
}
