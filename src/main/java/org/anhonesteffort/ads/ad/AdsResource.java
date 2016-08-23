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

package org.anhonesteffort.ads.ad;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.SecureRandom;

@Path("/ads")
@Produces(MediaType.APPLICATION_JSON)
public class AdsResource {

  private final SecureRandom random = new SecureRandom();

  @GET
  public AdModel getAd() {
    return new AdModel(
        random.nextLong(), "http://twitter.com/notrhodey",
        "http://ad-design.966v.com/static_images/20160813/988fb5a204e28c01faccbfa43c6e7eeee9d8548acd67759cadd24a94.jpg"
    );
  }

}
