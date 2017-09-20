package main.Entity;

import java.util.Collection;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be
 * controlled by the user. It has abilities, and is able to pick up items
 * and use the items.
 */
public class HeroUnit extends Unit {
  private Collection<Ability> abilities; //todo decide collection type
  private Collection<Item> items; //todo decide collection type

  public void pickUp(Item item){
    throw new Error("NYI");
    //if item has ability, include in abilities
  }

  public void use(Item item){
    throw new Error("NYI");
  }

  public Collection<Ability> getAbilities(){
    throw new Error("NYI");
  }

  public Collection<Item> getItems(){
    throw new Error("NYI");
  }
}