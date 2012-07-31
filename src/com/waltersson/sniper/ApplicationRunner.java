package com.waltersson.sniper;

import org.fest.swing.core.Robot;

public class ApplicationRunner
{
  public static final String SNIPER_ID = "sniper";
  public static final String SNIPER_PASSWORD = "sniper";
  private static final String STATUS_JOINING = "Joining";
  private static final String STATUS_LOST = "Lost";
  private AuctionSniperDriver driver;
  private Robot robot;
  
  public ApplicationRunner(Robot robot)
  {
    this.robot = robot;
  }

  public void startBiddingIn(final FakeAuctionServer auction) {
    Thread thread = new Thread("Test Application") {
      @Override public void run() {
        try {
          Main.main(FakeAuctionServer.XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    thread.setDaemon(true);
    thread.start();
    driver = new AuctionSniperDriver(1000, robot);
    driver.showsSniperStatus(STATUS_JOINING);
  }
  
  public void showsSniperHasLostAuction() {
    driver.showsSniperStatus(STATUS_LOST);
  }
  
  public void stop() {
    if (driver != null) {
      driver.dispose();
    }    
  }
}
