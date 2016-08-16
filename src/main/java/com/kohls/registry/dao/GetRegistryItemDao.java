package com.kohls.registry.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GetRegistryItemDao extends CrudRepository<RegistryItem, String> {

	public List<RegistryItem> findByRegistry(Registry r);
}
