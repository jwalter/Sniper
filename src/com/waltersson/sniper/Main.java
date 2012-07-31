package com.waltersson.sniper;

import javax.swing.SwingUtilities;

public class Main
{
  private MainWindow ui;
  
  public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";

  public static final String SNIPER_STATUS_TEXT = "sniper status";

  public Main() throws Exception {
    startUserInterface();
  }
  
  public static void main(String... args) throws Exception
  {
    Main main = new Main();
  }

  private void startUserInterface() throws Exception
  {
    SwingUtilities.invokeAndWait(new Runnable() {

      @Override public void run() {
        ui = new MainWindow();
      }
    });
  }

}
