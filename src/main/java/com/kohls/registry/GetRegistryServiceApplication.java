package com.kohls.registry;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kohls.registry.dao.GetRegistryDao;
import com.kohls.registry.dao.GetRegistryItemDao;
import com.kohls.registry.dao.Registry;
import com.kohls.registry.dao.RegistryItem;

@SpringBootApplication
@EnableDiscoveryClient
public class GetRegistryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetRegistryServiceApplication.class, args);
	}

	@Bean
	public AlwaysSampler createSampler() {
		return new AlwaysSampler();
	}
}

@RestController
class GetRegistryController {

	@Autowired
	private GetRegistryDao dao;

	@Autowired
	private GetRegistryItemDao riDao;

	final Logger log = LoggerFactory.getLogger(GetRegistryController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public RegistryData findRegistry(@PathVariable("id") String id) {
		log.info("Trying to get registy and items for id: " + id);
		RegistryData rd = new RegistryData();

		Registry r = dao.findOne(id);
		rd.setRegistry(r);

		List<RegistryItem> ri = riDao.findByRegistry(r);
		// rs.forEach((row) -> rd.addItems(new RegistryItem(row.getUUID(1),
		// null, row.getString(2), row.getFloat(3))));
		rd.setRegistryItems(ri);

		return rd;
	}
}

class RegistryData {
	private Registry registry;
	private List<RegistryItem> registryItems = new ArrayList<>();

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public List<RegistryItem> getRegistryItems() {
		return registryItems;
	}

	public void setRegistryItems(List<RegistryItem> registryItems) {
		this.registryItems = registryItems;
	}

	public void addItems(RegistryItem item) {
		registryItems.add(item);
	}
}