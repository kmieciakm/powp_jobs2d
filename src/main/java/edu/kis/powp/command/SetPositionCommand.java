package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;

public class SetPositionCommand implements IDriverCommand {
    private int x;
    private int y;

    public SetPositionCommand(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void executeOn(Job2dDriver driver) {
        driver.setPosition(x, y);
    }
}