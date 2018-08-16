package example.repo;

import example.model.Customer112;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer112Repository extends CrudRepository<Customer112, Long> {

	List<Customer112> findByLastName(String lastName);
}
