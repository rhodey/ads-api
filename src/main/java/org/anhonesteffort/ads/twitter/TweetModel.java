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
import twitter4j.Status;

import javax.validation.constraints.NotNull;

public class TweetModel {

  @NotNull private Long   id;
  @NotNull private Long   timeMs;
  @NotNull private String handle;
  @NotNull private String accountPic;
  @NotNull private String text;

  public TweetModel(Status status) {
    id         = status.getId();
    timeMs     = status.getCreatedAt().getTime();
    handle     = status.getUser().getScreenName();
    accountPic = status.getUser().getProfileImageURL();
    text       = status.getText();
  }

  @JsonProperty
  public Long getId() {
    return id;
  }

  @JsonProperty
  public Long getTimeMs() {
    return timeMs;
  }

  @JsonProperty
  public String getHandle() {
    return handle;
  }

  @JsonProperty
  public String getAccountPic() {
    return accountPic;
  }

  @JsonProperty
  public String getText() {
    return text;
  }

}
