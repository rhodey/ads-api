/*
 * Copyright (C) 2016 An Honest Effort LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.anhonesteffort.ads.twitter;

import com.twitter.hbc.httpclient.BasicClient;
import io.dropwizard.lifecycle.Managed;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONObjectType;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ManagedTweetStream implements Managed {

  private final ExecutorService         pool           = Executors.newFixedThreadPool(1);
  private final BlockingQueue<String>   queue          = new LinkedBlockingQueue<>(10_000);
  private final CompletableFuture<Void> shutdownFuture = new CompletableFuture<>();

  private final BasicClient client;
  private final List<? extends TweetListener> listeners;

  public ManagedTweetStream(TwitterConfig config, List<? extends TweetListener> listeners) {
    this.listeners = listeners;
    client         = new TwitterClientFactory(config).create(queue, listeners);
  }

  public CompletableFuture<Void> getShutdownFuture() {
    return shutdownFuture;
  }

  @Override
  public void start() {
    client.connect();
    pool.submit(new QueuePollingCallable());
  }

  @Override
  public void stop() {
    client.stop();
    pool.shutdownNow();
  }

  private class QueuePollingCallable implements Callable<Long> {
    private long statusCount = 0l;

    @Override
    public Long call() {
      while (!client.isDone()) {
        try {

          String message = queue.poll(20, TimeUnit.SECONDS);
          if (message == null) {
            throw new IOException("timed out polling twitter message queue");
          } else if (JSONObjectType.determine(new JSONObject(message)) == JSONObjectType.Type.STATUS) {
            Status status = TwitterObjectFactory.createStatus(message);
            listeners.forEach(listener -> listener.onTweet(status));
          }

        } catch (InterruptedException e) {
          shutdownFuture.complete(null);
        } catch (TwitterException | JSONException | IOException e) {
          shutdownFuture.completeExceptionally(e);
        }
      }

      if (client.isDone()) {
        shutdownFuture.completeExceptionally(new IOException("twitter client stopped unexpectedly"));
      }

      return statusCount;
    }
  }

}
