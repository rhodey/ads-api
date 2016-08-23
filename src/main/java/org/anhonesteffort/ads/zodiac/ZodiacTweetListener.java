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

package org.anhonesteffort.ads.zodiac;

import org.anhonesteffort.ads.twitter.TweetListener;
import org.anhonesteffort.ads.util.VersionedEntry;
import org.anhonesteffort.ads.util.VersioningQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;

public class ZodiacTweetListener extends TweetListener {

  private static final Logger log = LoggerFactory.getLogger(ZodiacTweetListener.class);
  private final VersioningQueue<Status> tweetQueue;

  public ZodiacTweetListener(String trackTerm, int queueCapacity) {
    super(trackTerm);
    tweetQueue = new VersioningQueue<>(queueCapacity);
  }

  public VersioningQueue<Status> getTweetQueue() {
    return tweetQueue;
  }

  @Override
  protected void onTrackedTweet(Status status) {
    VersionedEntry<Status> entry = tweetQueue.add(status);
    log.debug("received tweet for term: " + trackTerm + ", version: " + entry.getVersion() + ", text: " + status.getText());
  }

}
