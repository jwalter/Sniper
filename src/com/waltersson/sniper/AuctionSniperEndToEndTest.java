package com.waltersson.sniper;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.FestSwingTestCaseTemplate;
import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest extends FestSwingTestCaseTemplate {
  private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
  private ApplicationRunner application;
  
  @Test public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
    JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {

      @Override protected JFrame executeInEDT() throws Throwable {
        return new JFrame();
      }
      
    });
    FrameFixture frameFixture = new FrameFixture(frame);
    assertThat(frameFixture.robot).isNotNull();
    application = new ApplicationRunner(frameFixture.robot);
    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFromSniper();
    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }
  
  @After public void stopAuction() {
    auction.stop();
  }
  
  @After public void stopApplication() {
    application.stop();
  }
}
