package main.game.view.events;

import main.game.model.entity.usable.Ability;

/**
 * Data class for when a Ability Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface AbilityIconClick {

  Ability getAbility();
}
