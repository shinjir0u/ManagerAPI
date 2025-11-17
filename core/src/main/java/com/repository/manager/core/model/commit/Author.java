package com.repository.manager.core.model.commit;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
	private String name;
	private String email;
	private Date date;
}
