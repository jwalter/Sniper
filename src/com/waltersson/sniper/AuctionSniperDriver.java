package com.waltersson.sniper;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;

public class AuctionSniperDriver
{

  private FrameFixture frame;

  public AuctionSniperDriver(int timeout, Robot robot)
  {
    frame = WindowFinder.findFrame(Main.MAIN_WINDOW_NAME).withTimeout(timeout).using(robot);
  }
  
  public void showsSniperStatus(String statusText) {
    frame.label(Main.SNIPER_STATUS_TEXT).requireText(statusText);
  }

  public void dispose()
  {
  }

}
