package com.easydinein.easy_dinein.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {

  private Long categoryId;
  private String name;
  private Set<DishCommand> dishes = new HashSet<>();

}
