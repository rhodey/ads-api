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

import javax.validation.constraints.NotNull;

public class TwitterConfig {

  @NotNull private String apiKey;
  @NotNull private String apiSecret;
  @NotNull private String accountKey;
  @NotNull private String accountSecret;

  @JsonProperty
  public String getApiKey() {
    return apiKey;
  }

  @JsonProperty
  public String getApiSecret() {
    return apiSecret;
  }

  @JsonProperty
  public String getAccountKey() {
    return accountKey;
  }

  @JsonProperty
  public String getAccountSecret() {
    return accountSecret;
  }

}
