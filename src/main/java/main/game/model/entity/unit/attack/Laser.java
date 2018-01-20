package main.game.model.entity.unit.attack;

import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.Direction;
import main.game.model.entity.Projectile;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.images.SpriteSheet.Sequence;
import main.images.SpriteSheet.Sheet;
import main.images.UnitSpriteSheet;
import main.util.MapSize;

/**
 * Ummm yea laser.
 * @author Andrew McGhie
 */
public class Laser extends BaseRangedAttack {

  public Laser() {
    super(CanEffect.ENEMIES);
  }

  @Override
  public Projectile createProjectile(
      Unit unit, Targetable target
  ) {
    Direction direction = Direction.between(unit.getCentre(), target.getLocation());
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.5, 0.5),
        unit,
        target,
        this,
        Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_FLY, direction),
        Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_IMPACT, direction),
        new MapSize(0.5, 0.5),
        0.1
    );
  }

  @Override
  double getRange(Unit unit) {
    return 3;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 1;
  }

  @Override
  double getDamage(Unit unit) {
    return 1;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0;
  }

  @Override
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return UnitSpriteSheet.Sequence.SPELL_CAST;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MAGIC;
  }
}