package example.repo;

import example.model.Customer457;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer457Repository extends CrudRepository<Customer457, Long> {

	List<Customer457> findByLastName(String lastName);
}
