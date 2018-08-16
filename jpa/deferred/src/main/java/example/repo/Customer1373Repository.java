package example.repo;

import example.model.Customer1373;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1373Repository extends CrudRepository<Customer1373, Long> {

	List<Customer1373> findByLastName(String lastName);
}
