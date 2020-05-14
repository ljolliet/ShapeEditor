package ui.javafx;

public class ApplyButtonJFx extends ButtonJFx{
    public ApplyButtonJFx() {
        super("Apply");
    }

    @Override
    public String getName() {
        return "ApplyButtonJFx";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.applyEdit();
    }
}
