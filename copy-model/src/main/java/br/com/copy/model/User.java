package br.com.copy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends GenericEntity {
	
	private String name;
	private Integer age;
}
