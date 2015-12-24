package net.sion.company.salary.domain;

import java.util.HashMap;
import java.util.Map;

public class PersonExtension<T extends Object> {
	
	
	String personId;
	
	Map<String,T> items = new HashMap<String,T>();
	
	Map<String,Map<String,T>> mapItems = new HashMap<String,Map<String,T>>();
	
	public PersonExtension() {
		
	}
	
	public PersonExtension(String personId) {
		super();
		this.personId = personId;
	}
	
	public PersonExtension(String personId, Map<String, T> items) {
		super();
		this.personId = personId;
		this.items = items;
	}
	
	public PersonExtension(String personId, Map<String, T> items,
			Map<String, Map<String, T>> mapItems) {
		super();
		this.personId = personId;
		this.items = items;
		this.mapItems = mapItems;
	}

	public void putItem(String key, T value) {
		items.put(key, value);
	}
	
	public void putAllItem(Map<String,T> item) {
		items.putAll(item);
	}
	
	public T getItemValue(String key) {
		return items.get(key);
	}
	
	public void putMapItems(String f_key, String s_key, T value) {
		Map<String,T> map = mapItems.get(f_key);
		if (map==null) {
			map = new HashMap<String,T>();
		}
		map.put(s_key, value);
		mapItems.put(f_key,map);
	}
	
	public T getMapItem(String f_key, String s_key) {
		Map<String,T> map = mapItems.get(f_key);
		if (map==null) {
			return null;
		}else {
			return map.get(s_key);
		}
	}
	
	
	public String getPersonId() {
		return personId;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Map<String, T> getItems() {
		return items;
	}

	public void setItems(Map<String, T> items) {
		this.items = items;
	}

	public Map<String, Map<String, T>> getMapItems() {
		return mapItems;
	}

	public void setMapItems(Map<String, Map<String, T>> mapItems) {
		this.mapItems = mapItems;
	}
	
	
}
