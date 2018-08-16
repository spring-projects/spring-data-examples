package example.repo;

import example.model.Customer743;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer743Repository extends CrudRepository<Customer743, Long> {

	List<Customer743> findByLastName(String lastName);
}
