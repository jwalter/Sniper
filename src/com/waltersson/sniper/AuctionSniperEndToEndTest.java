package com.waltersson.sniper;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.FestSwingTestCaseTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuctionSniperEndToEndTest extends FestSwingTestCaseTemplate {
  private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
  private ApplicationRunner application;
  
  public void sniperMakesAHigherBidButLoses() throws Exception {
    auction.startSellingItem();
    
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
    
    auction.reportPrice(1000, 98, "other bidder");
    application.hasShownSniperIsBidding();
    
    auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
    
    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }
  
  @Test public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }
  
  @Before public void createApplicationRunner() {
    JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {

      @Override protected JFrame executeInEDT() throws Throwable {
        return new JFrame();
      }
      
    });
    FrameFixture frameFixture = new FrameFixture(frame);
    assertThat(frameFixture.robot).isNotNull();
    application = new ApplicationRunner(frameFixture.robot);
  }
  
  @After public void stopAuction() {
    auction.stop();
  }
  
  @After public void stopApplication() {
    application.stop();
  }
}
