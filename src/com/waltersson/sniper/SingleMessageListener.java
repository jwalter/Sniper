package com.waltersson.sniper;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class SingleMessageListener implements MessageListener
{
  private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

  @Override
  public void processMessage(Chat chat, Message message)
  {
    messages.add(message);
  }

  public void receivesAMessage() throws InterruptedException
  {
    assertThat(messages.poll(5, TimeUnit.SECONDS)).isNotNull();
  }

  public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException
  {
    final Message message = messages.poll(5, TimeUnit.SECONDS);
    assertThat(message).isNotNull();
    assertThat(message.getBody(), messageMatcher);
  }
}
