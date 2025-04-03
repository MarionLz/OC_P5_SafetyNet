package com.openclassrooms.safetynet.repository;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IDataWriterRepository {
	
    public void add(String collectionName, ObjectNode newNode);

}
