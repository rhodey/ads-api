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

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class TwitterClientFactory {

  private final TwitterConfig config;

  public TwitterClientFactory(TwitterConfig config) {
    this.config = config;
  }

  public BasicClient create(BlockingQueue<String> queue, List<? extends TweetListener> listeners) {
    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
    Authentication         auth     = new OAuth1(
        config.getTwitterApiKey(),     config.getTwitterApiSecret(),
        config.getTwitterAccountKey(), config.getTwitterAccountSecret()
    );

    endpoint.trackTerms(
        listeners.stream().map(TweetListener::getTrackTerm).collect(Collectors.toList())
    );

    return new ClientBuilder()
        .name(getClass().getSimpleName())
        .hosts(Constants.STREAM_HOST)
        .endpoint(endpoint)
        .authentication(auth)
        .processor(new StringDelimitedProcessor(queue))
        .build();
  }

}
