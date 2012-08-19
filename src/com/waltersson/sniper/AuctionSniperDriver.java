package com.waltersson.sniper;

import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.timing.Timeout.timeout;
import static org.fest.swing.edt.GuiActionRunner.execute;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Condition;

public class AuctionSniperDriver
{

  private FrameFixture frame;

  public AuctionSniperDriver(int timeout, Robot robot)
  {
    frame = WindowFinder.findFrame(Main.MAIN_WINDOW_NAME).withTimeout(timeout).using(robot);
  }

  public void showsSniperStatus(final String statusText)
  {
    pause(new Condition("Status label to equal: " + statusText)
    {

      public boolean test()
      {
        return execute(new GuiQuery<Boolean>()
        {
          public Boolean executeInEDT()
          {
            return frame.label(MainWindow.SNIPER_STATUS_NAME).target.getText().equals(statusText);
          }
        });
      }

    }, timeout(5000));
  }

  public void dispose()
  {
  }

}
