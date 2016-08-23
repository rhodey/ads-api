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

import org.anhonesteffort.ads.twitter.TweetListModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/zodiac/tweets")
@Produces(MediaType.APPLICATION_JSON)
public class ZodiacTweetsResource {

  private final List<ZodiacTweetListener> listeners;

  public ZodiacTweetsResource(List<ZodiacTweetListener> listeners) {
    this.listeners = listeners;
  }

  @GET
  @Path("/{sign}/{version}")
  public Optional<TweetListModel> getTweetsForSign(@PathParam("sign")    String sign,
                                                   @PathParam("version") Long   version)
  {
    Optional<ZodiacTweetListener> listener = listeners.stream().filter(
        l -> l.getTrackTerm().equals(sign.toLowerCase())
    ).findFirst();

    if (listener.isPresent()) {
      return Optional.of(new TweetListModel(listener.get().getTweetQueue().getUpdates(version)));
    } else {
      return Optional.empty();
    }
  }

}
