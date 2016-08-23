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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.anhonesteffort.ads.util.VersionedEntries;
import org.anhonesteffort.ads.util.VersionedEntry;
import twitter4j.Status;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class TweetListModel {

  @NotNull private Long version;
  @NotNull private List<TweetModel> tweets;

  public TweetListModel(VersionedEntries<Status> tweets) {
    version     = tweets.getVersion();
    this.tweets = tweets.getEntries().stream()
                        .map(VersionedEntry::getValue)
                        .map(TweetModel::new)
                        .collect(Collectors.toList());
  }

  @JsonProperty
  public Long getVersion() {
    return version;
  }

  @JsonProperty
  public List<TweetModel> getTweets() {
    return tweets;
  }

}
