package main.menu.generators;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.Optional;

/**
 * @author Andrew McGhie
 */
public class SaveFileAlertGenerator extends ScriptGenerator {

  private boolean success = true;
  private String mesg = "";

  public SaveFileAlertGenerator setSuccess() {
    this.success = true;
    return this;
  }

  public SaveFileAlertGenerator setError() {
    this.success = false;
    return this;
  }

  public SaveFileAlertGenerator setMesg(String mesg) {
    this.mesg = mesg;
    return this;
  }

  @Override
  Optional<String> load() {
    if (success) {
      return Optional.of("addSuccessMessage();");
    } else {
      return Optional.of("addErrorMessage('" + this.mesg + "');");
    }
  }
}
