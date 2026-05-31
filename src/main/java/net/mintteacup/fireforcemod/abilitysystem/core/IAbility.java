package net.mintteacup.fireforcemod.abilitysystem.core;

public interface IAbility {
    public boolean canActivate(AbilityContext context);
    public void execute(AbilityContext context);
    public String getID();
}
