package example.repo;

import example.model.Customer373;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer373Repository extends CrudRepository<Customer373, Long> {

	List<Customer373> findByLastName(String lastName);
}
