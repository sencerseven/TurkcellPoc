package com.turkcell.poc.entity;

import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "menu")
public class Menu {

  @Id
  private String id;

  private String name;

  private String menuDescription;
}
