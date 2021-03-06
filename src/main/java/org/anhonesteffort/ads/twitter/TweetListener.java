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

import twitter4j.Status;

public abstract class TweetListener {

  protected final String trackTerm;

  public TweetListener(String trackTerm) {
    this.trackTerm = trackTerm.toLowerCase();
  }

  public String getTrackTerm() {
    return trackTerm;
  }

  protected abstract void onTrackedTweet(Status status);

  public void onTweet(Status status) {
    if (status.getText().toLowerCase().contains(trackTerm)) {
      onTrackedTweet(status);
    }
  }

}
